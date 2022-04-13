package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;

public abstract class HkxAnno {
    private final String name;

    protected HkxAnno(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract AnnoType getType();
}
