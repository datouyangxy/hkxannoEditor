package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

@Setter
@Getter
public class StandardAnno extends HkxAnno {
    private final static String outTemplate = "{0} {1}{2}";
    private StringProperty payload = new SimpleStringProperty();
    private final AnnoType annoType;

    public StandardAnno(Double timePoint, String name, AnnoType annoType) {
        super(timePoint, name);
        this.annoType = annoType;
    }

    public StandardAnno(Double timePoint, String name, String payload, AnnoType annoType) {
        super(timePoint, name);
        this.payload.set(payload);
        this.annoType = annoType;
    }

    public void setPayload(String payload) {
        this.payload.set(payload);
    }

    @Override
    public AnnoType getType() {
        return annoType;
    }

    @Override
    public String toString() {
        if (StringUtils.isNotEmpty(payload.get()))
            return MessageFormat.format(outTemplate, String.format("%.6f", timePoint.get()), name.get(), "." + payload.get());
        return MessageFormat.format(outTemplate, String.format("%.6f", timePoint.get()), name.get(), "");
    }
}
