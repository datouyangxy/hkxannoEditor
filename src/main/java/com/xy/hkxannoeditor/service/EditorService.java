package com.xy.hkxannoeditor.service;

import com.xy.hkxannoeditor.entity.bo.HkxFile;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;

import java.io.File;

public interface EditorService {
    void updateRoot(File root);

    File getRoot();

    TreeItem<HkxFile> createRoot();

    Label createMetaView(String meta);

    void dumpAnno(HkxFile file);

    void updateAnno(HkxFile file);

    void updateAllAnno();
}
