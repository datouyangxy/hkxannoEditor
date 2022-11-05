package com.xy.hkxannoeditor.entity.bo;

import com.xy.hkxannoeditor.entity.enums.ScarType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScarJson {
    private StringProperty IdleAnimation = new SimpleStringProperty();
    private DoubleProperty MinDistance = new SimpleDoubleProperty();
    private DoubleProperty MaxDistance = new SimpleDoubleProperty();
    private DoubleProperty StartAngle = new SimpleDoubleProperty();
    private DoubleProperty EndAngle = new SimpleDoubleProperty();
    private DoubleProperty Chance = new SimpleDoubleProperty();
    private StringProperty Type = new SimpleStringProperty();

    public ScarJson(String IdleAnimation) {
        this.IdleAnimation.set(IdleAnimation);
        this.MinDistance.set(0);
        this.MaxDistance.set(0);
        this.StartAngle.set(-60);
        this.EndAngle.set(60);
        this.Chance.set(100);
        this.Type.set(ScarType.RA.name());
    }

    public void setIdleAnimation(String idleAnimation) {
        this.IdleAnimation.set(idleAnimation);
    }

    public void setMinDistance(double minDistance) {
        this.MinDistance.set(minDistance);
    }

    public void setMaxDistance(double maxDistance) {
        this.MaxDistance.set(maxDistance);
    }

    public void setStartAngle(double startAngle) {
        this.StartAngle.set(startAngle);
    }

    public void setEndAngle(double endAngle) {
        this.EndAngle.set(endAngle);
    }

    public void setChance(double chance) {
        this.Chance.set(chance);
    }

    public void setType(String type) {
        this.Type.set(type);
    }
}
