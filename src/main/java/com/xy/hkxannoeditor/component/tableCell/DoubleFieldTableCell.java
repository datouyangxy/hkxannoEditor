package com.xy.hkxannoeditor.component.tableCell;

import com.sun.javafx.scene.control.DoubleField;
import com.xy.hkxannoeditor.component.inputField.DoubleInputField;
import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class DoubleFieldTableCell<T extends HkxAnno> extends TableCell<T, Number> {

    public static <T extends HkxAnno> Callback<TableColumn<T, Number>, TableCell<T, Number>> forTableColumn(
            Callback<T, DoubleProperty> bindValue
    ) {
        return tableColumn -> new DoubleFieldTableCell<>(bindValue);
    }

    private DoubleField doubleField;
    private Callback<T, DoubleProperty> bindValue;

    public DoubleFieldTableCell() {
        this.getStyleClass().add("text-field-table-cell");
    }

    public DoubleFieldTableCell(Callback<T, DoubleProperty> bindValue) {
        this.bindValue = bindValue;
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
        doubleField = DoubleInputField.create(bindValue.call(getTableRow().getItem()));
    }

    private String getString() {
        return getItem() == null ? "" : Double.toString((Double) getItem());
    }
}
