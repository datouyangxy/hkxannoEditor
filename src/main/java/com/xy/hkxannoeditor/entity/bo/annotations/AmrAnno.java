package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

import static com.xy.hkxannoeditor.entity.enums.AnnoType.AMR;

@Getter
@Setter
public class AmrAnno extends HkxAnno {
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private DoubleProperty z = new SimpleDoubleProperty();
    private DoubleProperty r = new SimpleDoubleProperty();

    private final static String outTemplate = "{0} {1} {2} {3} {4}";

    public AmrAnno(Double timePoint, String name) {
        super(timePoint, name);
        this.x.set(0.f);
        this.y.set(0.f);
        this.z.set(0.f);
    }

    public AmrAnno(Double timePoint, String name, Float x, Float y, Float z) {
        super(timePoint, name);
        this.x.set(x);
        this.y.set(y);
        this.z.set(z);
    }

    public AmrAnno(Double timePoint, String name, Float r) {
        super(timePoint, name);
        this.r.set(r);
    }

    public void setR(float r) {
        this.r.set(r);
    }

    public void setX(float x) {
        this.x.set(x);
    }

    public void setY(float y) {
        this.y.set(y);
    }

    public void setZ(float z) {
        this.z.set(z);
    }

    @Override
    public AnnoType getType() {
        return AMR;
    }

    @Override
    public String toString() {
        if (r.get() != 0d)
            return MessageFormat.format(outTemplate, String.format("%.6f", timePoint.get()), name.get(), r.get());
        return MessageFormat.format(outTemplate, String.format("%.6f", timePoint.get()), name.get(), x.get(), y.get(), z.get());
    }
}
