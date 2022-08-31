package com.xy.hkxannoeditor.controller;

import com.xy.hkxannoeditor.config.AnnoProperties;
import com.xy.hkxannoeditor.entity.bo.HkxFile;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import com.xy.hkxannoeditor.service.EditorService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.Map;

import static com.xy.hkxannoeditor.Const.ROW_HEIGHT;
import static com.xy.hkxannoeditor.entity.enums.AnnoType.*;
import static javafx.scene.input.MouseButton.PRIMARY;

@Slf4j
@Controller
public class EditorController {

    private final Map<String, HkxFile> fileContainer;
    private final EditorService editor;
    private HkxFile currentFile;

    public void setCurrentFile(@Qualifier("currentFile") HkxFile currentFile) {
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
    private ListView<Label> metaListView;
    @FXML
    private ListView<HBox> commonListView;
    @FXML
    private ListView<HBox> amrListView;
    @FXML
    private ListView<HBox> scarListView;
    @FXML
    private ListView<HBox> customListView;

    @FXML
    private TreeView<HkxFile> fileTree;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private VBox rootLayout;
    @FXML
    private MenuItem openMenuItem;

    public EditorController(AnnoProperties properties, @Qualifier("fileContainer") Map<String, HkxFile> fileContainer, EditorService editor) {
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
        ObservableList<Label> itemList = metaListView.getItems();
        itemList.clear();
        for (String meta : currentFile.getMetaList()) {
            Label metaLabel = new Label(meta);
            metaLabel.setPrefHeight(ROW_HEIGHT);
            itemList.addAll(metaLabel);
        }
        if (!itemList.isEmpty())
            metaPane.setExpanded(true);
        metaListView.setPrefHeight((itemList.size() + 1) * ROW_HEIGHT);
    }

    private void refreshCommon() {
        ObservableList<HBox> itemList = commonListView.getItems();
        itemList.clear();
        for (StandardAnno standardAnno : currentFile.getStandardList()) {
            if (standardAnno.getAnnoType() == COMMON) {
                TextField timePoint_TF = TextFields.createClearableTextField();
                timePoint_TF.setText(String.format("%.6f", standardAnno.getTimePoint()));
                TextField name_TF = TextFields.createClearableTextField();
                name_TF.setText(standardAnno.getName());
                TextField payload_TF = TextFields.createClearableTextField();
                payload_TF.setText(standardAnno.getPayload());
                TextFields.bindAutoCompletion(name_TF, currentFile.getAnnoProperties().getCommon());
                TextFields.bindAutoCompletion(payload_TF, currentFile.getAnnoProperties().getPayload().get(COMMON));
                HBox hBox = new HBox(timePoint_TF, name_TF, payload_TF);
                hBox.setPrefHeight(ROW_HEIGHT);
                itemList.add(hBox);
            }
        }
        if (!itemList.isEmpty())
            commonPane.setExpanded(true);
        commonListView.setPrefHeight((itemList.size() + 1) * ROW_HEIGHT);
    }

    private void refreshAmr() {
        ObservableList<HBox> itemList = amrListView.getItems();
        itemList.clear();
        for (StandardAnno standardAnno : currentFile.getStandardList()) {
            if (standardAnno.getAnnoType() == AMR) {
                HBox hBox = new HBox();
                hBox.setPrefHeight(ROW_HEIGHT);
                itemList.add(hBox);
            }
        }
        if (!itemList.isEmpty())
            amrPane.setExpanded(true);
        amrListView.setPrefHeight((itemList.size() + 1) * ROW_HEIGHT);
    }

    private void refreshScar() {
        ObservableList<HBox> itemList = scarListView.getItems();
        itemList.clear();
        for (StandardAnno standardAnno : currentFile.getStandardList()) {
            if (standardAnno.getAnnoType() == SCAR) {

            }
        }
        if (!itemList.isEmpty())
            scarPane.setExpanded(true);
        scarListView.setPrefHeight((itemList.size() + 1) * ROW_HEIGHT);
    }

    private void refreshCustom() {
        ObservableList<HBox> itemList = customListView.getItems();
        itemList.clear();
        for (String customAnno : currentFile.getCustomList()) {
        }
        if (!itemList.isEmpty())
            customPane.setExpanded(true);
        customListView.setPrefHeight((itemList.size() + 1) * ROW_HEIGHT);
    }

    @FXML
    private void onCommonAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            commonListView.getItems().add(new HBox());
        }
    }

    @FXML
    private void onAmrAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            amrListView.getItems().add(new HBox());
        }
    }

    @FXML
    private void onScarAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            scarListView.getItems().add(new HBox());
        }
    }

    @FXML
    private void onCustomAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            customListView.getItems().add(new HBox());
        }
    }
}