package com.xy.hkxannoeditor.config;

import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnoProperty {
    private String name;
    private String description;

    public AnnoProperty(String name) {
        this.name = name;
        this.description = null;
    }

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
        return name;
    }

    public static class AnnoPropertyListConverter extends StringConverter<AnnoProperty> {

        @Override
        public String toString(AnnoProperty object) {
            return object == null ? null : object.getName() + ": " + object.getDescription();
        }

        @Override
        public AnnoProperty fromString(String string) {
            return new AnnoProperty(string);
        }
    }

}
