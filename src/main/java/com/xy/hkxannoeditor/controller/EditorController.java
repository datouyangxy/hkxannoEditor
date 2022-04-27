package com.xy.hkxannoeditor.controller;

import com.xy.hkxannoeditor.config.MyProperties;
import com.xy.hkxannoeditor.core.AnnoManager;
import com.xy.hkxannoeditor.core.editManager.EditManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class EditorController {
    private MyProperties properties;
    private AnnoManager manager;
    @FXML
    private TreeView fileTree;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private VBox rootLayout;
    @FXML
    private MenuItem openMenuItem;
    @Autowired
    public void setProperties(MyProperties properties) {
        this.properties = properties;
    }

    @FXML
    protected void onHelloButtonClick() {
    }

    public void openDirectory(ActionEvent actionEvent) {
        File rootDir = ChooseDir();
        manager = new EditManager(rootDir);
        fileTree = manager.createFileTree();
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