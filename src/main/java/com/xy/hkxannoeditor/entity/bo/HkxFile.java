package com.xy.hkxannoeditor.entity.bo;

import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.MetaAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

@Getter
@Setter
public class HkxFile {
    private final File hkx;
    private final File txt;
    private List<MetaAnno> metaList;
    private List<StandardAnno> standardList;
    private List<AmrAnno> amrList;

    public HkxFile(File hkx) {
        this.hkx = hkx;
        if (hkx.isFile())
            this.txt = new File(hkx.getPath().split("\\.")[0] + ".txt");
        else
            this.txt = null;
    }

    @Override
    public String toString() {
        return hkx.getName();
    }
}
