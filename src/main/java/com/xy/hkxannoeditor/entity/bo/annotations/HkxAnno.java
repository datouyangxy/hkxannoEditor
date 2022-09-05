package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public abstract class HkxAnno {
    protected StringProperty name = new SimpleStringProperty();
    protected DoubleProperty timePoint = new SimpleDoubleProperty();

    protected HkxAnno(Double timePoint, String name) {
        this.timePoint.set(timePoint);
        this.name.set(name);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setTimePoint(float timePoint) {
        this.timePoint.set(timePoint);
    }

    public abstract AnnoType getType();
}
