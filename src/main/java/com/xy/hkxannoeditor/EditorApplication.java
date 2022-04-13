package com.xy.hkxannoeditor;

import com.xy.hkxannoeditor.core.exceptions.NotFindHkanno64Exception;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class EditorApplication extends Application {
    public static String DUMP_COMMAND;
    public static String UPDATE_COMMAND;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            dirProcess();
            showMainWindow(stage);
        } catch (URISyntaxException | NotFindHkanno64Exception e) {
            System.out.println(e.getMessage());
            showFailedWindow(stage);
        }
    }

    private void showMainWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EditorApplication.class.getResource("editor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("HkxAnnoEditor");
        stage.setScene(scene);
        stage.show();
    }

    private void showFailedWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EditorApplication.class.getResource("failed-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),300,100);
        stage.setTitle("Error");
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * pre processes the current directory
     */
    private void dirProcess() throws URISyntaxException {
        String currPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath();
        String hka64Str = currPath + File.separator + "hkanno64.exe";
        File hkanno64 = new File(hka64Str);

        if (!hkanno64.exists())
            throw new NotFindHkanno64Exception("Error: Hkanno64 not found in current directory, please make sure you have hkanno64.exe it in " + currPath);

        DUMP_COMMAND = String.format(Const.DUMP_COMMAND_TEMPLATE, hka64Str, "%s", "%s");
        UPDATE_COMMAND = String.format(Const.UPDATE_COMMAND_TEMPLATE, hka64Str, "%s", "%s");
    }
}