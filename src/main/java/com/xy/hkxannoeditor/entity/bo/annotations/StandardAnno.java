package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

import static com.xy.hkxannoeditor.entity.enums.AnnoType.STANDARD;

@Setter
@Getter
public class StandardAnno extends HkxAnno {


    private final static String outTemplate = "{0} {1}{2}";
    private String payload = "";

    public StandardAnno(Double timePoint, String name) {
        super(name);
        this.timePoint = timePoint;
    }

    @Override
    public AnnoType getType() {
        return STANDARD;
    }

    @Override
    public String toString() {
        if (StringUtils.isNotEmpty(payload))
            return MessageFormat.format(outTemplate, String.format("%.6f", timePoint), name, "." + payload);
        return MessageFormat.format(outTemplate, String.format("%.6f", timePoint), name, payload);
    }
}
