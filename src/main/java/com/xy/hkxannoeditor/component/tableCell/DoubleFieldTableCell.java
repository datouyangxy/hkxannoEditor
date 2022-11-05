package com.xy.hkxannoeditor.component.tableCell;

import com.sun.javafx.scene.control.DoubleField;
import com.xy.hkxannoeditor.component.inputField.DoubleInputField;
import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import javafx.event.Event;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import static com.xy.hkxannoeditor.entity.enums.ColumnName.*;

public class DoubleFieldTableCell<T extends HkxAnno> extends TableCell<T, Number> {

    public static <T extends HkxAnno> Callback<TableColumn<T, Number>, TableCell<T, Number>> forTableColumn() {
        return tableColumn -> new DoubleFieldTableCell<>();
    }

    private DoubleField doubleField;

    public DoubleFieldTableCell() {
        this.getStyleClass().add("text-field-table-cell");
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (!isEditing()) {
            return;
        }
        if (doubleField == null) {
            createDoubleField();
        }
        if (doubleField != null) {
            doubleField.setValue((Double) getItem());
        }
        setText(null);
        setGraphic(doubleField);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(Double.toString((Double) getItem()));
        setGraphic(null);
    }

    @Override
    public void commitEdit(Number newValue) {
        if (!isEditing() && !newValue.equals(this.getItem())) {
            TableView<T> table = this.getTableView();
            if (table != null) {
                TableColumn<T, Number> col = this.getTableColumn();
                TableColumn.CellEditEvent<T, Number> event = new TableColumn.CellEditEvent<>(table,
                        new TablePosition<>(table, this.getIndex(), col), TableColumn.editCommitEvent(),
                        newValue);
                Event.fireEvent(col, event);
            }
        }
        super.commitEdit(newValue);
        updateItem(newValue, false);
    }

    @Override
    public void updateItem(Number item, boolean empty) {
        super.updateItem(item, empty);
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(doubleField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createDoubleField() {
        String colName = getTableColumn().getText();
        if (colName.equals(time_point.name()))
            doubleField = DoubleInputField.create(getTableRow().getItem().getTimePoint());
        else if (colName.equals(X.name()))
            doubleField = DoubleInputField.create(((AmrAnno) getTableRow().getItem()).getX());
        else if (colName.equals(Y.name()))
            doubleField = DoubleInputField.create(((AmrAnno) getTableRow().getItem()).getY());
        else if (colName.equals(Z.name()))
            doubleField = DoubleInputField.create(((AmrAnno) getTableRow().getItem()).getZ());
        else if (colName.equals(R.name()))
            doubleField = DoubleInputField.create(((AmrAnno) getTableRow().getItem()).getR());
        else if (colName.equals(MinDistance.name()))
            doubleField = DoubleInputField.create(((ScarAnno) getTableRow().getItem()).getScarJson().getMinDistance());
        else if (colName.equals(MaxDistance.name()))
            doubleField = DoubleInputField.create(((ScarAnno) getTableRow().getItem()).getScarJson().getMaxDistance());
        else if (colName.equals(StartAngle.name()))
            doubleField = DoubleInputField.create(((ScarAnno) getTableRow().getItem()).getScarJson().getStartAngle());
        else if (colName.equals(EndAngle.name()))
            doubleField = DoubleInputField.create(((ScarAnno) getTableRow().getItem()).getScarJson().getEndAngle());
        else if (colName.equals(Chance.name()))
            doubleField = DoubleInputField.create(((ScarAnno) getTableRow().getItem()).getScarJson().getChance());
    }

    private String getString() {
        return getItem() == null ? "" : Double.toString((Double) getItem());
    }
}
