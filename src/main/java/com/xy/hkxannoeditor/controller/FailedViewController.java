package com.xy.hkxannoeditor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FailedViewController {
    @FXML
    private Label errorText;

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) errorText.getScene().getWindow();
        stage.close();
    }
}
