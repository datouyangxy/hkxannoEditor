package com.xy.hkxannoeditor.core.annoManager;

import com.xy.hkxannoeditor.entity.bo.FilePath;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class SimpleUpdater extends FixManager{

    public SimpleUpdater(File root, List<FilePath> filePaths) {
        super(root, filePaths);
    }

    @Override
    public void dumpAnno() {
        RecDir(root, _filePaths);
    }

    @Override
    public void fixAnno() throws IOException {
    }

    @Override
    void createAnnoTextList(FilePath filePath, List<FilePath> filePaths) {
        filePaths.add(filePath);
    }

}
