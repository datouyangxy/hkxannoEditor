package com.xy.hkxannoeditor.component.factory;

import com.xy.hkxannoeditor.entity.enums.ColumnName;
import javafx.scene.control.TableColumn;

public abstract class AbstractComponentFactory {
    public abstract TableColumn tableColumn(ColumnName colName);
}
