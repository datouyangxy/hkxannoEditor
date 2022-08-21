package com.xy.hkxannoeditor.service;

import com.xy.hkxannoeditor.entity.bo.HkxFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class EditorServiceTest {

    private final EditorService editorService = new EditorServiceImpl(new HashMap<>());

    @Test
    void dumpAnno() {
        List<HkxFile> fileList = new ArrayList<>();
        fileList.add(new HkxFile(new File("C:\\Users\\59895\\Desktop\\HKBP-0-3\\animations\\mco_attack1.hkx")));
        fileList.add(new HkxFile(new File("C:\\Users\\59895\\Desktop\\HKBP-0-3\\animations\\mco_attack2.hkx")));
        fileList.add(new HkxFile(new File("C:\\Users\\59895\\Desktop\\HKBP-0-3\\animations\\mco_attack3.hkx")));
        fileList.add(new HkxFile(new File("C:\\Users\\59895\\Desktop\\HKBP-0-3\\animations\\mco_attack4.hkx")));

        for (HkxFile file : fileList) {
            editorService.dumpAnno(file);
        }
    }
}