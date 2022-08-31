package com.xy.hkxannoeditor.config;

import com.xy.hkxannoeditor.entity.bo.HkxFile;
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

    @Bean(name = "currentFile")
    public HkxFile currentFile() {
        return null;
    }
}
