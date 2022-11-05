package com.xy.hkxannoeditor.component.factory;

import com.xy.hkxannoeditor.component.tableCell.AutoCompletionFieldTableCell;
import com.xy.hkxannoeditor.component.tableCell.DoubleFieldTableCell;
import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import com.xy.hkxannoeditor.entity.enums.ColumnName;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;

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
            case X -> {
                TableColumn<AmrAnno, Number> x = new TableColumn<>(colName.name());
                x.setCellFactory(DoubleFieldTableCell.forTableColumn());
                x.setCellValueFactory(anno -> anno.getValue().getX());
                return x;
            }
            case Y -> {
                TableColumn<AmrAnno, Number> y = new TableColumn<>(colName.name());
                y.setCellFactory(DoubleFieldTableCell.forTableColumn());
                y.setCellValueFactory(anno -> anno.getValue().getY());
                return y;
            }
            case Z -> {
                TableColumn<AmrAnno, Number> z = new TableColumn<>(colName.name());
                z.setCellFactory(DoubleFieldTableCell.forTableColumn());
                z.setCellValueFactory(anno -> anno.getValue().getZ());
                return z;
            }
            case R -> {
                TableColumn<AmrAnno, Number> r = new TableColumn<>(colName.name());
                r.setCellFactory(DoubleFieldTableCell.forTableColumn());
                r.setCellValueFactory(anno -> anno.getValue().getR());
                return r;
            }
            case IdleAnimation -> {
                TableColumn<ScarAnno, String> idleAnimation = new TableColumn<>(colName.name());
                idleAnimation.setCellFactory(TextFieldTableCell.forTableColumn());
                idleAnimation.setCellValueFactory(anno -> anno.getValue().getScarJson().getIdleAnimation());
                return idleAnimation;
            }
            case MinDistance -> {
                TableColumn<ScarAnno, Number> minDistance = new TableColumn<>(colName.name());
                minDistance.setCellFactory(DoubleFieldTableCell.forTableColumn());
                minDistance.setCellValueFactory(anno -> anno.getValue().getScarJson().getMinDistance());
                return minDistance;
            }
            case MaxDistance -> {
                TableColumn<ScarAnno, Number> maxDistance = new TableColumn<>(colName.name());
                maxDistance.setCellFactory(DoubleFieldTableCell.forTableColumn());
                maxDistance.setCellValueFactory(anno -> anno.getValue().getScarJson().getMaxDistance());
                return maxDistance;
            }
            case StartAngle -> {
                TableColumn<ScarAnno, Number> startAngle = new TableColumn<>(colName.name());
                startAngle.setCellFactory(DoubleFieldTableCell.forTableColumn());
                startAngle.setCellValueFactory(anno -> anno.getValue().getScarJson().getStartAngle());
                return startAngle;
            }
            case EndAngle -> {
                TableColumn<ScarAnno, Number> endAngle = new TableColumn<>(colName.name());
                endAngle.setCellFactory(DoubleFieldTableCell.forTableColumn());
                endAngle.setCellValueFactory(anno -> anno.getValue().getScarJson().getEndAngle());
                return endAngle;
            }
            case Chance -> {
                TableColumn<ScarAnno, Number> chance = new TableColumn<>(colName.name());
                chance.setCellFactory(DoubleFieldTableCell.forTableColumn());
                chance.setCellValueFactory(anno -> anno.getValue().getScarJson().getChance());
                return chance;
            }
            case Type -> {
                TableColumn<ScarAnno, String> type = new TableColumn<>(colName.name());
                type.setCellFactory(AutoCompletionFieldTableCell.forTableColumn());
                type.setCellValueFactory(anno -> anno.getValue().getScarJson().getType());
                return type;
            }
            default -> {
                return null;
            }
        }
    }
}
