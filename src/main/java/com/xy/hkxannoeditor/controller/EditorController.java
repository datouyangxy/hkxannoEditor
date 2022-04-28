package com.xy.hkxannoeditor.controller;

import com.xy.hkxannoeditor.config.MyProperties;
import com.xy.hkxannoeditor.entity.bo.HkxFile;
import com.xy.hkxannoeditor.service.EditorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class EditorController {

    private final MyProperties properties;

    private final EditorService editor;
    @FXML
    private TreeView<HkxFile> fileTree;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private VBox rootLayout;
    @FXML
    private MenuItem openMenuItem;

    public EditorController(MyProperties properties, EditorService editor) {
        this.properties = properties;
        this.editor = editor;
    }

    @FXML
    protected void onHelloButtonClick() {
    }

    public void openDirectory(ActionEvent actionEvent) {
        File rootDir = ChooseDir();
        editor.setRoot(rootDir);
        fileTree.setRoot(editor.createRoot());
        fileTree.refresh();
    }

    @FXML
    protected void closeApp(ActionEvent actionEvent) {

    }

    @FXML
    protected void quitApp(ActionEvent actionEvent) {
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
}