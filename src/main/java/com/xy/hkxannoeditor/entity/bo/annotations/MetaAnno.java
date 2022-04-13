package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;

import java.text.MessageFormat;

import static com.xy.hkxannoeditor.entity.enums.AnnoType.META;

public class MetaAnno extends HkxAnno {
    private final Double metaValue;
    private final static String outTemplate = "# {0}: {1}";

    public MetaAnno(String name, Double metaValue) {
        super(name);
        this.metaValue = metaValue;
    }

    @Override
    public AnnoType getType() {
        return META;
    }

    public Double getMetaValue() {
        return metaValue;
    }

    @Override
    public String toString(){
        return MessageFormat.format(outTemplate,getName(),metaValue);
    }
}
