package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;

import java.text.MessageFormat;

import static com.xy.hkxannoeditor.entity.enums.AnnoType.STANDARD;

public class StandardAnno extends HkxAnno {
    private Double timePoint;
    private final static String outTemplate = "{0} {1}";

    public StandardAnno(Double timePoint,String name) {
        super(name);
        this.timePoint = timePoint;
    }

    @Override
    public AnnoType getType() {
        return STANDARD;
    }

    public void setTimePoint(Double timePoint) {
        this.timePoint = timePoint;
    }

    public Double getTimePoint() {
        return timePoint;
    }

    @Override
    public String toString() {
        return MessageFormat.format(outTemplate,String.format("%.6f",timePoint),getName());
    }
}
