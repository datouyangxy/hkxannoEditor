package com.xy.hkxannoeditor.controller;

import com.xy.hkxannoeditor.core.annoManager.FixManager;
import com.xy.hkxannoeditor.core.annoManager.SimpleRenamer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class EditorController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * simply performs dump, fix, and update action for FIXER
     */
    private static void simpleFix(FixManager fixer) throws IOException {
        fixer.dumpAnno();
        fixer.fixAnno();
        fixer.updateAnno();
    }

    private static void simpleRename(SimpleRenamer renamer) {
        renamer.rename();
    }
}