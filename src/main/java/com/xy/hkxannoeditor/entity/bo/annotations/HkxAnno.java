package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;
import lombok.Data;

@Data
public abstract class HkxAnno {
    protected final String name;
    protected Float timePoint;

    protected HkxAnno(Float timePoint, String name) {
        this.timePoint = timePoint;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract AnnoType getType();
}
