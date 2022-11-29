package com.xy.hkxannoeditor.component.tableCell;

import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.util.Callback;

public class AutoCompletionFieldTableCell<T extends HkxAnno> extends TableCell<T, String> {

    public static <T extends HkxAnno> Callback<TableColumn<T, String>, TableCell<T, String>> forTableColumn(
            Callback<T, TextField> bindValue
    ) {
        return tableColumn -> new AutoCompletionFieldTableCell<>(bindValue);
    }

    private Callback<T, TextField> createField;
    private TextField textField;

    public AutoCompletionFieldTableCell() {
        this.getStyleClass().add("text-field-table-cell");
    }

    public AutoCompletionFieldTableCell(Callback<T, TextField> createField) {
        this.createField = createField;
        this.getStyleClass().add("text-field-table-cell");
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (!isEditing()) {
            return;
        }
        if (textField == null) {
            createAutoCompletionTextField();
        }
        if (textField != null) {
            textField.setText(getString());
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
        setGraphic(null);
    }

    @Override
    public void commitEdit(String newValue) {
        if (!isEditing() && !newValue.equals(this.getItem())) {
            TableView<T> table = this.getTableView();
            if (table != null) {
                TableColumn<T, String> col = this.getTableColumn();
                TableColumn.CellEditEvent<T, String> event = new TableColumn.CellEditEvent<>(table,
                        new TablePosition<>(table, this.getIndex(), col), TableColumn.editCommitEvent(),
                        newValue);
                Event.fireEvent(col, event);
            }
        }
        super.commitEdit(newValue);
        updateItem(newValue, false);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createAutoCompletionTextField() {
        textField = createField.call(getTableRow().getItem());
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
