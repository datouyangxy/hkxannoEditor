package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

@Setter
@Getter
public class StandardAnno extends HkxAnno {
    private final static String outTemplate = "{0} {1}{2}";
    private String payload = "";
    private final AnnoType annoType;

    public StandardAnno(Float timePoint, String name, AnnoType annoType) {
        super(timePoint, name);
        this.annoType = annoType;
    }

    public StandardAnno(Float timePoint, String name, String payload, AnnoType annoType) {
        super(timePoint, name);
        this.payload = payload;
        this.annoType = annoType;
    }

    @Override
    public AnnoType getType() {
        return annoType;
    }

    @Override
    public String toString() {
        if (StringUtils.isNotEmpty(payload))
            return MessageFormat.format(outTemplate, String.format("%.6f", timePoint), name, "." + payload);
        return MessageFormat.format(outTemplate, String.format("%.6f", timePoint), name, payload);
    }
}
