package com.xy.hkxannoeditor.entity.bo;

import com.xy.hkxannoeditor.entity.enums.ScarType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScarJson {
    private String IdleAnimation;
    private Float MinDistance;
    private Float MaxDistance;
    private Float StartAngle;
    private Float EndAngle;
    private Float Chance;
    private ScarType Type;

    public ScarJson(String IdleAnimation) {
        this.IdleAnimation = IdleAnimation;
        this.MinDistance = 0.f;
        this.MaxDistance = 0.f;
        this.StartAngle = -60.f;
        this.EndAngle = 60.f;
        this.Chance = 100.f;
        this.Type = ScarType.RA;
    }
}
