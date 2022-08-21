package com.xy.hkxannoeditor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class FailedViewController {
    @FXML
    private Label errorText;

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) errorText.getScene().getWindow();
        stage.close();
    }
}
