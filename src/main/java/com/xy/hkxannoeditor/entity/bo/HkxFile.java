package com.xy.hkxannoeditor.entity.bo;

import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.MetaAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HkxFile {
    private final File hkx;
    private final File txt;
    private final List<MetaAnno> metaList;
    private final List<StandardAnno> standardList;
    private final List<AmrAnno> amrList;
    private final List<ScarAnno> scarList;
    private String originAnno;
    private boolean edited = false;
    private boolean saved = true;

    public HkxFile(File hkx) {
        this.hkx = hkx;
        if (hkx.isFile())
            this.txt = new File(hkx.getPath().split("\\.")[0] + ".txt");
        else
            this.txt = null;
        metaList = new ArrayList<>();
        standardList = new ArrayList<>();
        amrList = new ArrayList<>();
        scarList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return hkx.getName();
    }
}
