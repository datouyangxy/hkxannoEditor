package com.xy.hkxannoeditor.service;

import com.xy.hkxannoeditor.entity.bo.HkxFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.xy.hkxannoeditor.EditorApplication.DUMP_COMMAND;
import static com.xy.hkxannoeditor.EditorApplication.UPDATE_COMMAND;

@Service
public class EditorServiceImpl implements EditorService {
    private File root;
    private final List<HkxFile> hkxFileList = new ArrayList<>();

    public void setRoot(File root) {
        this.root = root;
    }

    public File getRoot() {
        return root;
    }

    public List<HkxFile> getHkxFileList() {
        return hkxFileList;
    }

    public void dumpAnno(HkxFile file) {
        exceCmd(file, DUMP_COMMAND);
    }

    /**
     * update fixed annos to txt files.
     */
    public void updateAnno(HkxFile file) {
        exceCmd(file, UPDATE_COMMAND);
    }

    private void exceCmd(HkxFile file, String commandTemplate) {
        try {
            String txtPath = file.getTxt().getPath();
            String hkxPath = file.getHkx().getPath();
            String command = String.format(commandTemplate, txtPath, hkxPath);
            if (Runtime.getRuntime().exec(command).waitFor() == 0) {
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public TreeItem<HkxFile> createRoot() {
        HkxFile rootFile = new HkxFile(root);
        return createNode(rootFile);
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
