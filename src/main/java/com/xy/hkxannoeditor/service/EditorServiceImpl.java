package com.xy.hkxannoeditor.service;

import com.xy.hkxannoeditor.component.ButtonGroup;
import com.xy.hkxannoeditor.component.NameInputField;
import com.xy.hkxannoeditor.component.PayloadInputField;
import com.xy.hkxannoeditor.config.AnnoProperties;
import com.xy.hkxannoeditor.entity.bo.HkxFile;
import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import com.xy.hkxannoeditor.entity.enums.AnnoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.xy.hkxannoeditor.Const.*;
import static com.xy.hkxannoeditor.utils.FileUtil.readFile;
import static java.lang.ProcessBuilder.Redirect.INHERIT;

@Service
@Slf4j
public class EditorServiceImpl implements EditorService {
    private File root;
    private final Map<String, HkxFile> hkxFileMap;
    private final AnnoProperties annoProperties;

    public EditorServiceImpl(@Qualifier("fileContainer") Map<String, HkxFile> hkxFileMap, AnnoProperties annoProperties) {
        this.hkxFileMap = hkxFileMap;
        this.annoProperties = annoProperties;
    }

    public void updateRoot(File root) {
        this.root = root;
    }

    public File getRoot() {
        return root;
    }

    public void dumpAnno(HkxFile file) {
        executeCmd(file, DUMP_COMMAND_TEMPLATE);
        try {
            file.setOriginAnno(readFile(file.getTxt()));
            file.deserialization();
        } catch (IOException e) {
            file.setOriginAnno(null);
            log.error(e.getMessage());
        }
        hkxFileMap.put(file.toString(), file);
    }

    /**
     * update fixed annos to txt files.
     */
    public void updateAnno(HkxFile file) {
        executeCmd(file, UPDATE_COMMAND_TEMPLATE);
    }

    public TreeItem<HkxFile> createRoot() {
        HkxFile rootFile = new HkxFile(root);
        return createNode(rootFile);
    }

    private void executeCmd(HkxFile file, String commandTemplate) {
        String txtPath = file.getTxt().getPath();
        String hkxPath = file.getHkx().getPath();
        String command = String.format(commandTemplate, txtPath, hkxPath);
        List<String> c = List.of(command.split(" "));
        try {
            ProcessBuilder pb = new ProcessBuilder(c);
            pb.redirectOutput(INHERIT);
            pb.redirectError(INHERIT);
            Process process = pb.start();
            process.waitFor();
        } catch (IOException ignored) {
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Label createMetaView(String meta) {
        Label metaLabel = new Label(meta);
        metaLabel.setPrefHeight(ROW_HEIGHT);
        return metaLabel;
    }

    @Override
    public HBox createStandardView(AnnoType annoType, StandardAnno standardAnno) {
        if (standardAnno.getAnnoType() == annoType) {
            HBox hBox = new HBox();
            hBox.setPrefHeight(ROW_HEIGHT);
            ObservableList<Node> children = hBox.getChildren();

//            TimePointInputField.create(standardAnno, children);
            NameInputField.create(standardAnno);
            PayloadInputField.create(standardAnno);
            ButtonGroup.create(standardAnno, children);

            return hBox;
        }
        return null;
    }

    @Override
    public HBox createAmrView(AmrAnno amrAnno) {
        HBox hBox = new HBox();
        hBox.setPrefHeight(ROW_HEIGHT);
        ObservableList<Node> children = hBox.getChildren();

//        TimePointInputField.create(amrAnno, children);
        NameInputField.create(amrAnno);
        ButtonGroup.create(amrAnno, children);
        return hBox;
    }

    @Override
    public HBox createScarView(ScarAnno scarAnno) {
        HBox hBox = new HBox();
        hBox.setPrefHeight(ROW_HEIGHT);
        ObservableList<Node> children = hBox.getChildren();

//        TimePointInputField.create(scarAnno, children);
        NameInputField.create(scarAnno);
        ButtonGroup.create(scarAnno, children);
        return hBox;
    }

    @Override
    public HBox createCustomView(String custom) {
        return new HBox();
    }

    private TreeItem<HkxFile> createNode(final HkxFile f) {
        return new TreeItem<>(f) {
            // We cache whether the File is a leaf or not. A File is a leaf if
            // it is not a directory and does not have any files contained within
            // it. We cache this as isLeaf() is called often, and doing the
            // actual check on File is expensive.
            private boolean isLeaf;

            // We do the children and leaf testing only once, and then set these
            // booleans to false so that we do not check again during this
            // run. A more complete implementation may need to handle more
            // dynamic file system situations (such as where a folder has files
            // added after the TreeView is shown). Again, this is left as an
            // exercise for the reader.
            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override
            public ObservableList<TreeItem<HkxFile>> getChildren() {
                if (isFirstTimeChildren) {
                    isFirstTimeChildren = false;

                    // First getChildren() call, so we actually go off and
                    // determine the children of the File contained in this TreeItem.
                    super.getChildren().setAll(buildChildren(this));
                }
                return super.getChildren();
            }

            @Override
            public boolean isLeaf() {
                if (isFirstTimeLeaf) {
                    isFirstTimeLeaf = false;
                    File f = getValue().getHkx();
                    isLeaf = f.isFile();
                }

                return isLeaf;
            }

            private ObservableList<TreeItem<HkxFile>> buildChildren(TreeItem<HkxFile> TreeItem) {
                File f = TreeItem.getValue().getHkx();
                if (f != null && f.isDirectory()) {
                    File[] files = f.listFiles();
                    if (files != null) {
                        ObservableList<TreeItem<HkxFile>> children = FXCollections.observableArrayList();

                        for (File childFile : files) {
                            if (childFile.isDirectory() || (childFile.isFile() && childFile.getName().toLowerCase().contains(".hkx")))
                                children.add(createNode(new HkxFile(childFile)));
                        }

                        return children;
                    }
                }

                return FXCollections.emptyObservableList();
            }
        };
    }
}
