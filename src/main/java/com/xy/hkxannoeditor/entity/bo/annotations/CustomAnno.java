package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

@Getter
@Setter
public class CustomAnno extends HkxAnno {
    private final static String outTemplate = "{0} {1}";

    public CustomAnno(Double timePoint, String name) {
        super(timePoint, name);
    }

    @Override
    public AnnoType getType() {
        return AnnoType.CUSTOM;
    }

    @Override
    public String toString() {
        return MessageFormat.format(outTemplate, String.format("%.6f", timePoint.get()), name.get());
    }
}
