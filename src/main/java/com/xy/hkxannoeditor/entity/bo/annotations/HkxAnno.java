package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;
import lombok.Data;

@Data
public abstract class HkxAnno {
    protected final String name;
    protected Double timePoint;

    protected HkxAnno(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract AnnoType getType();
}
