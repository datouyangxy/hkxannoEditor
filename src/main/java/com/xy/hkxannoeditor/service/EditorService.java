package com.xy.hkxannoeditor.service;

import com.xy.hkxannoeditor.entity.bo.HkxFile;
import javafx.scene.control.TreeItem;

import java.io.File;

public interface EditorService {
    void setRoot(File root);

    File getRoot();

    TreeItem<HkxFile> createRoot();

    void dumpAnno(HkxFile file);

    void updateAnno(HkxFile file);
}
