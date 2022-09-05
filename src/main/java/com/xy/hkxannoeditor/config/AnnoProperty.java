package com.xy.hkxannoeditor.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class AnnoProperty {
    private String name;
    private String description;

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other instanceof AnnoProperty)
            return StringUtils.equals(name, ((AnnoProperty) other).getName());
        if (other instanceof String)
            return StringUtils.equals(name, (String) other);
        return false;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}
