package com.xy.hkxannoeditor.service;

import com.xy.hkxannoeditor.entity.bo.HkxFile;
import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import com.xy.hkxannoeditor.entity.enums.AnnoType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

import java.io.File;

public interface EditorService {
    void updateRoot(File root);

    File getRoot();

    TreeItem<HkxFile> createRoot();

    Label createMetaView(String meta);

    HBox createStandardView(AnnoType annoType, StandardAnno standardAnno);

    HBox createAmrView(AmrAnno amrAnno);

    HBox createScarView(ScarAnno scarAnno);

    HBox createCustomView(String custom);

    void dumpAnno(HkxFile file);

    void updateAnno(HkxFile file);

    void updateAllAnno();
}
