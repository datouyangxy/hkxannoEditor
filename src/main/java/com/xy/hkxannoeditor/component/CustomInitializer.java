package com.xy.hkxannoeditor.component;

import com.xy.hkxannoeditor.entity.bo.annotations.CustomAnno;
import com.xy.hkxannoeditor.entity.enums.ColumnName;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

import static com.xy.hkxannoeditor.entity.enums.ColumnName.name;
import static com.xy.hkxannoeditor.entity.enums.ColumnName.time_point;

@Component
public class CustomInitializer extends AnnoEditorTableInitializer<CustomAnno> {
    @Override
    protected Collection<ColumnName> getColNames() {
        return Arrays.asList(time_point, name);
    }
}
