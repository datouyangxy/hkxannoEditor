package com.xy.hkxannoeditor.controller;

import com.xy.hkxannoeditor.Const;
import com.xy.hkxannoeditor.component.TableViewInitializer;
import com.xy.hkxannoeditor.entity.bo.HkxFile;
import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.CustomAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import com.xy.hkxannoeditor.entity.enums.AnnoType;
import com.xy.hkxannoeditor.service.EditorService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.util.Map;
import java.util.Optional;

import static javafx.scene.input.MouseButton.PRIMARY;

@Slf4j
@Controller
public class EditorController {

    private final Map<String, HkxFile> fileContainer;
    private final EditorService editor;
    private HkxFile currentFile;

    @Resource
    private TableViewInitializer<StandardAnno> standardInitializer;
    @Resource
    private TableViewInitializer<AmrAnno> amrInitializer;
    @Resource
    private TableViewInitializer<ScarAnno> scarInitializer;
    @Resource
    private TableViewInitializer<CustomAnno> customInitializer;

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
    private TitledPane mcoPane;
    @FXML
    private TitledPane precisionPane;
    @FXML
    private TitledPane amrPane;
    @FXML
    private TitledPane scarPane;
    @FXML
    private TitledPane customPane;

    @FXML
    private VBox metaVBox;
    @FXML
    private TableView<StandardAnno> commonTable;
    @FXML
    private TableView<StandardAnno> mcoTable;
    @FXML
    private TableView<StandardAnno> precisionTable;
    @FXML
    private TableView<AmrAnno> amrTable;
    @FXML
    private TableView<ScarAnno> scarTable;
    @FXML
    private TableView<CustomAnno> customTable;

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
        fileContainer.clear();
        setCurrentFile(null);
        editContent.setVisible(false);
    }

    @FXML
    private void saveAll(ActionEvent actionEvent) {
        editor.updateAllAnno();
        Alert saveSuccess = new Alert(Alert.AlertType.INFORMATION, "注解更新成功！");
        Optional<ButtonType> optional = saveSuccess.showAndWait();
        if (optional.isPresent()) {
            fileTree.refresh();
        }
    }

    @FXML
    private void revert(ActionEvent actionEvent) {
        fileContainer.forEach((key, file) -> {
            file.deserialization();
        });
        refreshEditGUI();
    }

    @FXML
    private void closeApp(ActionEvent actionEvent) {
        fileTree.setRoot(null);
        fileTree.refresh();
        fileContainer.clear();
        setCurrentFile(null);
        editContent.setVisible(false);
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
            item.setGraphic(new FontIcon("mdi-star"));
            HkxFile file = item.getValue();
            log.debug(file.getHkx().getPath());
            if (fileContainer.get(file.getHkx().getPath()) == null)
                editor.dumpAnno(file);
            setCurrentFile(file);
            refreshEditGUI();
        }
    }

    private void refreshEditGUI() {
        standardInitializer.init(editUI, commonTable);
        standardInitializer.init(editUI, mcoTable);
        standardInitializer.init(editUI, precisionTable);
        amrInitializer.init(editUI, amrTable);
        scarInitializer.init(editUI, scarTable);
        customInitializer.init(editUI, customTable);
        editContent.setVisible(true);
        refreshMeta();
        refreshStandard();
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
        metaPane.setExpanded(!itemList.isEmpty());
    }

    private void refreshStandard() {
        commonTable.setItems(currentFile.getCommonList());
        mcoTable.setItems(currentFile.getMcoList());
        precisionTable.setItems(currentFile.getPrecsisonList());

        commonPane.setExpanded(!commonTable.getItems().isEmpty());
        mcoPane.setExpanded(!mcoTable.getItems().isEmpty());
        precisionPane.setExpanded(!precisionTable.getItems().isEmpty());
    }

    private void refreshAmr() {
        amrTable.setItems(currentFile.getAmrList());
        amrPane.setExpanded(!amrTable.getItems().isEmpty());
    }

    private void refreshScar() {
        scarTable.setItems(currentFile.getScarList());
        scarPane.setExpanded(!scarTable.getItems().isEmpty());
    }

    private void refreshCustom() {
        customTable.setItems(currentFile.getCustomList());
        customPane.setExpanded(!customTable.getItems().isEmpty());
    }

    @FXML
    private void onCommonAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            commonTable.getItems().add(new StandardAnno(0d, "", AnnoType.COMMON));
            refreshHeight(commonTable);
            if (!commonPane.isExpanded()) commonPane.setExpanded(true);
        }
    }

    @FXML
    private void onMcoAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            mcoTable.getItems().add(new StandardAnno(0d, "", AnnoType.MCO));
            refreshHeight(mcoTable);
            if (!mcoPane.isExpanded()) mcoPane.setExpanded(true);
        }
    }

    @FXML
    private void onPrecisionAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            precisionTable.getItems().add(new StandardAnno(0d, "", AnnoType.PRECISION));
            refreshHeight(precisionTable);
            if (!precisionPane.isExpanded()) precisionPane.setExpanded(true);
        }
    }

    @FXML
    private void onAmrAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            amrTable.getItems().add(new AmrAnno(0d, ""));
            refreshHeight(amrTable);
            if (!amrPane.isExpanded()) amrPane.setExpanded(true);
        }
    }

    @FXML
    private void onScarAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            scarTable.getItems().add(new ScarAnno());
            refreshHeight(scarTable);
            if (!scarPane.isExpanded()) scarPane.setExpanded(true);
        }
    }

    @FXML
    private void onCustomAdd(MouseEvent mouseEvent) {
        if (PRIMARY.equals(mouseEvent.getButton())) {
            customTable.getItems().add(new CustomAnno(0d, ""));
            refreshHeight(customTable);
            if (!customPane.isExpanded()) customPane.setExpanded(true);
        }
    }

    private void refreshHeight(TableView tableView) {
        tableView.setPrefHeight(Const.ROW_HEIGHT * (tableView.getItems().size() + 1));
    }
}