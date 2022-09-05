package com.xy.hkxannoeditor.controller;

import com.xy.hkxannoeditor.entity.bo.HkxFile;
import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import com.xy.hkxannoeditor.service.EditorService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.Map;

import static com.xy.hkxannoeditor.entity.enums.AnnoType.COMMON;
import static javafx.scene.input.MouseButton.PRIMARY;

@Slf4j
@Controller
public class EditorController {

    private final Map<String, HkxFile> fileContainer;
    private final EditorService editor;
    private HkxFile currentFile;

    public void setCurrentFile(HkxFile currentFile) {
        this.currentFile = currentFile;
    }

    @FXML
    private ScrollPane editUI;
    @FXML
    private VBox editContent;

    @FXML
    private TitledPane metaPane;
    @FXML
    private TitledPane commonPane;
    @FXML
    private TitledPane amrPane;
    @FXML
    private TitledPane scarPane;
    @FXML
    private TitledPane customPane;

    @FXML
    private VBox metaVBox;
    @FXML
    private VBox commonVBox;
    @FXML
    private VBox amrVBox;
    @FXML
    private VBox scarVBox;
    @FXML
    private VBox customVBox;

    @FXML
    private TreeView<HkxFile> fileTree;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private VBox rootLayout;
    @FXML
    private MenuItem openMenuItem;

    public EditorController(@Qualifier("fileContainer") Map<String, HkxFile> fileContainer, EditorService editor) {
        this.fileContainer = fileContainer;
        this.editor = editor;
    }

    @FXML
    public void initialize() {
        editContent.setVisible(false);
    }

    @FXML
    private void openDirectory(ActionEvent actionEvent) {
        File rootDir = ChooseDir();

        if (rootDir == null)
            return;

        editor.updateRoot(rootDir);
        fileTree.setRoot(editor.createRoot());
        fileTree.refresh();
    }

    @FXML
    private void closeApp(ActionEvent actionEvent) {

    }

    @FXML
    private void quitApp(ActionEvent actionEvent) {
        getStage().close();
    }

    private Stage getStage() {
        return (Stage) rootLayout.getScene().getWindow();
    }

    private File ChooseDir() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("请选择动作文件目录");
//        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return dirChooser.showDialog(getStage());
    }

    @FXML
    private void onClickTreeItem(MouseEvent mouseEvent) {
        TreeItem<HkxFile> item = fileTree.getSelectionModel().getSelectedItem();
        if (PRIMARY.equals(mouseEvent.getButton()) && item != null && item.isLeaf()) {
            HkxFile file = item.getValue();
            log.debug(file.toString());
            if (fileContainer.get(file.toString()) == null)
                editor.dumpAnno(file);
            setCurrentFile(file);
            refreshEditGUI();
        }
    }

    private void refreshEditGUI() {
        editContent.setVisible(true);
        refreshMeta();
        refreshCommon();
        refreshAmr();
        refreshScar();
        refreshCustom();
    }

    private void refreshMeta() {
        ObservableList<Node> itemList = metaVBox.getChildren();
        itemList.clear();
        for (String meta : currentFile.getMetaList()) {
            itemList.add(editor.createMetaView(meta));
        }
        if (!itemList.isEmpty())
            metaPane.setExpanded(true);
    }

    private void refreshCommon() {
        ObservableList<Node> itemList = commonVBox.getChildren();
        itemList.clear();
        for (StandardAnno standardAnno : currentFile.getStandardList()) {
            HBox row = editor.createStandardView(COMMON, standardAnno);
            if (row != null)
                itemList.add(row);
        }
        if (!itemList.isEmpty())
            commonPane.setExpanded(true);
    }

    private void refreshAmr() {
        ObservableList<Node> itemList = amrVBox.getChildren();
        itemList.clear();
        for (AmrAnno amrAnno : currentFile.getAmrList()) {
            itemList.add(editor.createAmrView(amrAnno));
        }
        if (!itemList.isEmpty())
            amrPane.setExpanded(true);
    }

    private void refreshScar() {
        ObservableList<Node> itemList = scarVBox.getChildren();
        itemList.clear();
        for (ScarAnno scarAnno : currentFile.getScarList()) {
            itemList.add(editor.createScarView(scarAnno));
        }
        if (!itemList.isEmpty())
            scarPane.setExpanded(true);
    }

    private void refreshCustom() {
        ObservableList<Node> itemList = customVBox.getChildren();
        itemList.clear();
        for (String customAnno : currentFile.getCustomList()) {
            itemList.add(editor.createCustomView(customAnno));
        }
        if (!itemList.isEmpty())
            customPane.setExpanded(true);
    }

    @FXML
    private void onCommonAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            commonVBox.getChildren().add(new HBox());
        }
    }

    @FXML
    private void onAmrAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            amrVBox.getChildren().add(new HBox());
        }
    }

    @FXML
    private void onScarAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            scarVBox.getChildren().add(new HBox());
        }
    }

    @FXML
    private void onCustomAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            customVBox.getChildren().add(new HBox());
        }
    }
}