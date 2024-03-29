package com.xy.hkxannoeditor.component;

import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import com.xy.hkxannoeditor.entity.enums.ColumnName;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

import static com.xy.hkxannoeditor.entity.enums.ColumnName.*;

@Component
public class StandardInitializer extends AnnoEditorTableInitializer<StandardAnno> {
    @Override
    protected Collection<ColumnName> getColNames() {
        return Arrays.asList(time_point, name, payload);
    }
}
