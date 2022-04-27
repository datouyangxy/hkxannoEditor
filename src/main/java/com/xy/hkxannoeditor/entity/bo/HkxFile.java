package com.xy.hkxannoeditor.entity.bo;

import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.MetaAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class HkxFile {
    private final FilePath path;
    private List<MetaAnno> metaList;
    private List<StandardAnno> standardList;
    private List<AmrAnno> amrList;
}
