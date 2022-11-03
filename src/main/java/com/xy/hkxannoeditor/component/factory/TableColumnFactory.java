package com.xy.hkxannoeditor.component.factory;

import com.xy.hkxannoeditor.component.tableCell.AutoCompletionFieldTableCell;
import com.xy.hkxannoeditor.component.tableCell.DoubleFieldTableCell;
import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import com.xy.hkxannoeditor.entity.enums.ColumnName;
import javafx.scene.control.TableColumn;

public class TableColumnFactory extends AbstractComponentFactory {
    @Override
    public TableColumn tableColumn(ColumnName colName) {
        switch (colName) {
            case time_point -> {
                TableColumn<HkxAnno, Number> timePoint = new TableColumn<>(colName.name());
                timePoint.setCellFactory(DoubleFieldTableCell.forTableColumn());
                timePoint.setCellValueFactory(anno -> anno.getValue().getTimePoint());
                return timePoint;
            }
            case name -> {
                TableColumn<HkxAnno, String> name = new TableColumn<>(colName.name());
                name.setCellFactory(AutoCompletionFieldTableCell.forTableColumn());
                name.setCellValueFactory(anno -> anno.getValue().getName());
                return name;
            }
            case payload -> {
                TableColumn<StandardAnno, String> payload = new TableColumn<>(colName.name());
                payload.setCellFactory(AutoCompletionFieldTableCell.forTableColumn());
                payload.setCellValueFactory(anno -> anno.getValue().getPayload());
                return payload;
            }
            default -> {
                return null;
            }
        }
    }
}
