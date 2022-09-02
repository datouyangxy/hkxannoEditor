package com.xy.hkxannoeditor.config;

import javafx.util.StringConverter;

public class AnnoPropertyConverter extends StringConverter<AnnoProperty> {

    @Override
    public String toString(AnnoProperty object) {
        return object == null ? null : object.toString();
    }

    @Override
    public AnnoProperty fromString(String string) {
        return null;
    }
}
