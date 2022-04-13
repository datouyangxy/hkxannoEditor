package com.xy.hkxannoeditor.entity.bo.annotations;

import com.xy.hkxannoeditor.entity.enums.AnnoType;

import java.text.MessageFormat;

import static com.xy.hkxannoeditor.entity.enums.AnnoType.AMR;

public class AmrAnno extends HkxAnno {
    private Double timePoint;
    private Double x;
    private Double y;
    private Double z;
    private final static String outTemplate = "{0} {1} {2} {3} {4}";

    protected AmrAnno(Double timePoint,String name) {
        super(name);
        this.timePoint = timePoint;
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    protected AmrAnno(Double timePoint,String name,Double x,Double y,Double z) {
        super(name);
        this.timePoint = timePoint;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Double getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Double timePoint) {
        this.timePoint = timePoint;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    @Override
    public AnnoType getType() {
        return AMR;
    }

    @Override
    public String toString(){
        return MessageFormat.format(outTemplate,String.format("%.6f",timePoint),getName(),x,y,z);
    }
}
