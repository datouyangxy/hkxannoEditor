package com.xy.hkxannoeditor.core.annoManager;

import com.xy.hkxannoeditor.entity.bo.FilePath;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SimpleDumper extends FixManager{

    public SimpleDumper(File root, List<FilePath> filePaths) {
        super(root, filePaths);
        System.out.println("start simple dumper");
    }


    @Override
    public void fixAnno() throws IOException {
    }

    @Override
    public void updateAnno() {
    }
}
