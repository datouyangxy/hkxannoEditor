package com.xy.hkxannoeditor.entity.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScarJsonBase {
    @JsonProperty("IdleAnimation")
    private String IdleAnimation;
    @JsonProperty("MinDistance")
    private Double MinDistance;
    @JsonProperty("MaxDistance")
    private Double MaxDistance;
    @JsonProperty("StartAngle")
    private Double StartAngle;
    @JsonProperty("EndAngle")
    private Double EndAngle;
    @JsonProperty("Chance")
    private Double Chance;
    @JsonProperty("Type")
    private String Type;
}
