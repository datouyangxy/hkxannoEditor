package com.xy.hkxannoeditor.component;

import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.enums.ColumnName;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

import static com.xy.hkxannoeditor.entity.enums.ColumnName.*;

@Component
public class ScarInitializer extends AnnoEditorTableInitializer<ScarAnno> {
    @Override
    protected Collection<ColumnName> getColNames() {
        return Arrays.asList(time_point, name, IdleAnimation, MinDistance, MaxDistance, StartAngle, EndAngle, Chance, Type);
    }
}
