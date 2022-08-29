package com.xy.hkxannoeditor.controller;

import com.xy.hkxannoeditor.config.AnnoProperties;
import com.xy.hkxannoeditor.entity.bo.HkxFile;
import com.xy.hkxannoeditor.service.EditorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.Map;

import static javafx.scene.input.MouseButton.PRIMARY;

@Slf4j
@Controller
public class EditorController {

    private final AnnoProperties properties;
    private final Map<String, HkxFile> fileContainer;
    private final EditorService editor;

    @FXML
    private ScrollPane editUI;
    @FXML
    private TitledPane annoPane;
    @FXML
    private TitledPane commonPane;
    @FXML
    private TitledPane amrPane;
    @FXML
    private TitledPane scarPane;
    @FXML
    private TitledPane customPane;
    @FXML
    private TreeView<HkxFile> fileTree;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private VBox rootLayout;
    @FXML
    private MenuItem openMenuItem;

    public EditorController(AnnoProperties properties, @Qualifier("fileContainer") Map<String, HkxFile> fileContainer, EditorService editor) {
        this.properties = properties;
        this.fileContainer = fileContainer;
        this.editor = editor;
    }

    @FXML
    private void onHelloButtonClick() {
    }

    @FXML
    private void openDirectory(ActionEvent actionEvent) {
        File rootDir = ChooseDir();
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
            annoPane.setExpanded(true);
            commonPane.setExpanded(true);
            amrPane.setExpanded(true);
            scarPane.setExpanded(true);
            customPane.setExpanded(true);
        }
    }
}