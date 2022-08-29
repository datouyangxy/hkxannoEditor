package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

import static com.xy.hkxannoeditor.entity.enums.AnnoType.AMR;

@Getter
@Setter
public class AmrAnno extends HkxAnno {
    private Float x;
    private Float y;
    private Float z;
    private Float r;

    private final static String outTemplate = "{0} {1} {2} {3} {4}";

    public AmrAnno(Float timePoint, String name) {
        super(timePoint, name);
        this.timePoint = timePoint;
        this.x = 0.f;
        this.y = 0.f;
        this.z = 0.f;
    }

    public AmrAnno(Float timePoint, String name, Float x, Float y, Float z) {
        super(timePoint, name);
        this.timePoint = timePoint;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AmrAnno(Float timePoint, String name, Float r) {
        super(timePoint, name);
        this.timePoint = timePoint;
        this.r = r;
    }

    @Override
    public AnnoType getType() {
        return AMR;
    }

    @Override
    public String toString() {
        return MessageFormat.format(outTemplate, String.format("%.6f", timePoint), getName(), x, y, z);
    }
}
