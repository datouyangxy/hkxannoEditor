package com.xy.hkxannoeditor.component;

import com.xy.hkxannoeditor.Const;
import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import org.kordamp.ikonli.javafx.FontIcon;

public class EditorRightMenu {
    public static ContextMenu create() {
        ContextMenu rightMenu = new ContextMenu();
        ObservableList<MenuItem> items = rightMenu.getItems();

        initDelete(items);
        initMoveUp(items);
        initMoveDown(items);
        initInsertAbove(items);
        initInsertBelow(items);

        return rightMenu;
    }

    private static void initDelete(ObservableList<MenuItem> menuItems) {
        MenuItem delete = new MenuItem("Delete");
        delete.setGraphic(new FontIcon("mdi-close"));
        delete.setOnAction(event -> {
            MenuItem o = (MenuItem) event.getSource();
            TableCell cell = (TableCell) o.getParentPopup().getUserData();
            TableView tableView = cell.getTableView();
            tableView.getItems().remove(cell.getIndex());
            tableView.setPrefHeight(Const.ROW_HEIGHT * (1 + tableView.getItems().size()));
        });
        menuItems.add(delete);
    }

    private static void initMoveUp(ObservableList<MenuItem> menuItems) {
        MenuItem moveUp = new MenuItem("Move Up");
        moveUp.setGraphic(new FontIcon("mdi-arrow-up"));
        moveUp.setOnAction(event -> {
            MenuItem o = (MenuItem) event.getSource();
            TableCell cell = (TableCell) o.getParentPopup().getUserData();
            int row = cell.getIndex();
            if (row > 0) {
                int newRow = row - 1;
                TableView tableView = cell.getTableView();
                ObservableList items = tableView.getItems();
                Object removed = items.remove(row);
                items.add(newRow, removed);
                tableView.getSelectionModel().select(newRow);
                tableView.setPrefHeight(Const.ROW_HEIGHT * (1 + items.size()));
            }
        });
        menuItems.add(moveUp);
    }

    private static void initMoveDown(ObservableList<MenuItem> menuItems) {
        MenuItem moveDown = new MenuItem("Move Down");
        moveDown.setGraphic(new FontIcon("mdi-arrow-down"));
        moveDown.setOnAction(event -> {
            MenuItem o = (MenuItem) event.getSource();
            TableCell cell = (TableCell) o.getParentPopup().getUserData();
            int row = cell.getIndex();
            TableView tableView = cell.getTableView();
            ObservableList items = tableView.getItems();
            if (row < items.size() - 1) {
                Object removed = items.remove(row);
                items.add(row + 1, removed);
                tableView.getSelectionModel().select(row + 1);
                tableView.setPrefHeight(Const.ROW_HEIGHT * (1 + items.size()));
            }
        });
        menuItems.add(moveDown);
    }

    private static void initInsertAbove(ObservableList<MenuItem> menuItems) {
        MenuItem insertAbove = new MenuItem("Insert Above");
        insertAbove.setOnAction(event -> {
            MenuItem o = (MenuItem) event.getSource();
            TableCell cell = (TableCell) o.getParentPopup().getUserData();
            int row = cell.getIndex();
            TableView tableView = cell.getTableView();
            ObservableList items = tableView.getItems();
            Object anno = items.get(row);
            if (anno instanceof StandardAnno) {
                items.add(row, new StandardAnno((double) 0, "", ((HkxAnno) anno).getType()));
            } else if (anno instanceof AmrAnno) {
                items.add(row, new AmrAnno((double) 0, ""));
            } else if (anno instanceof ScarAnno) {
                items.add(row, new ScarAnno((double) 0, ""));
            } else if (anno instanceof String) {
                items.add(row, "");
            }
            tableView.setPrefHeight(Const.ROW_HEIGHT * (1 + items.size()));
        });
        menuItems.add(insertAbove);
    }

    private static void initInsertBelow(ObservableList<MenuItem> menuItems) {
        MenuItem insertBelow = new MenuItem("Insert Below");
        insertBelow.setOnAction(event -> {
            MenuItem o = (MenuItem) event.getSource();
            TableCell cell = (TableCell) o.getParentPopup().getUserData();
            int row = cell.getIndex();
            TableView tableView = cell.getTableView();
            ObservableList items = tableView.getItems();
            Object anno = items.get(row);
            int newRow = row + 1;
            if (anno instanceof StandardAnno) {
                items.add(newRow, new StandardAnno((double) 0, "", ((HkxAnno) anno).getType()));
            } else if (anno instanceof AmrAnno) {
                items.add(newRow, new AmrAnno((double) 0, ""));
            } else if (anno instanceof ScarAnno) {
                items.add(newRow, new ScarAnno((double) 0, ""));
            } else if (anno instanceof String) {
                items.add(newRow, "");
            }
            tableView.setPrefHeight(Const.ROW_HEIGHT * (1 + items.size()));
        });
        menuItems.add(insertBelow);
    }
}
