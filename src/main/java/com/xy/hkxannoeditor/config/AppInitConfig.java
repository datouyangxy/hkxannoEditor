package com.xy.hkxannoeditor.config;

import com.xy.hkxannoeditor.component.EditorRightMenu;
import com.xy.hkxannoeditor.entity.bo.HkxFile;
import javafx.scene.control.ContextMenu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppInitConfig {

    @Bean(name = "fileContainer")
    public Map<String, HkxFile> fileContainer() {
        return new HashMap<>();
    }

    @Bean(name = "editRightMenu")
    public ContextMenu contextMenu() {
        return EditorRightMenu.create();
    }
}
