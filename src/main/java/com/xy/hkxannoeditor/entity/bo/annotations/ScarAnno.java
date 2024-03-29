package com.xy.hkxannoeditor.entity.bo.annotations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xy.hkxannoeditor.entity.bo.ScarJson;
import com.xy.hkxannoeditor.entity.bo.ScarJsonBase;
import com.xy.hkxannoeditor.entity.enums.AnnoType;
import com.xy.hkxannoeditor.utils.SpringUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

@Setter
@Getter
public class ScarAnno extends HkxAnno {
    private final ScarJson scarJson;
    private final static String outTemplate = "{0} {1}{2}";

    public ScarAnno(Double timePoint, String name, ScarJson scarJson) {
        super(timePoint, name);
        this.scarJson = scarJson;
    }

    public ScarAnno(Double timePoint, String name) {
        super(timePoint, name);
        scarJson = new ScarJson();
    }

    public ScarAnno() {
        super(0d, "");
        this.scarJson = new ScarJson("ADXP_NPCNormalAttack");
    }

    @Override
    public AnnoType getType() {
        return AnnoType.SCAR;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        try {
            String scarJsonString;
            if (StringUtils.isEmpty(scarJson.getIdleAnimation().get()))
                scarJsonString = "";
            else {
                ScarJsonBase scarJsonBase = new ScarJsonBase(
                        scarJson.getIdleAnimation().get()
                        , scarJson.getMinDistance().get()
                        , scarJson.getMaxDistance().get()
                        , scarJson.getStartAngle().get()
                        , scarJson.getEndAngle().get()
                        , scarJson.getChance().get()
                        , scarJson.getType().get());
                scarJsonString = objectMapper.writeValueAsString(scarJsonBase);
            }
            return MessageFormat.format(outTemplate, String.format("%.6f", timePoint.get()), name.get(), scarJsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
