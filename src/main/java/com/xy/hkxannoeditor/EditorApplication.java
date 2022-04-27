package com.xy.hkxannoeditor;

import com.xy.hkxannoeditor.core.exceptions.NotFindHkanno64Exception;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class EditorApplication extends Application {
    public static String DUMP_COMMAND;
    public static String UPDATE_COMMAND;
    private static ConfigurableApplicationContext context;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            dirProcess();
            setMainWindow(stage);
        } catch (URISyntaxException | NotFindHkanno64Exception e) {
            setFailedWindow(stage);
        }
        stage.show();
    }

    @Override
    public void init() {
        context = SpringApplication.run(EditorApplication.class);
    }

    @Override
    public void stop() {
        context.stop();
    }

    private void setMainWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EditorApplication.class.getResource("/fxml/editor-view.fxml"));
        fxmlLoader.setControllerFactory(param -> context.getBean(param));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("HkxAnnoEditor");
        stage.setScene(scene);
    }

    private void setFailedWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EditorApplication.class.getResource("/fxml/failed-view.fxml"));
        fxmlLoader.setControllerFactory(param -> context.getBean(param));
        Scene scene = new Scene(fxmlLoader.load(), 300, 100);
        stage.setTitle("Error");
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setScene(scene);
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