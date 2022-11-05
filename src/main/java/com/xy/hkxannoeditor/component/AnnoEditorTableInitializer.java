package com.xy.hkxannoeditor.component;

import com.xy.hkxannoeditor.Const;
import com.xy.hkxannoeditor.component.factory.AbstractComponentFactory;
import com.xy.hkxannoeditor.component.factory.ComponentFactoryProducer;
import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;
import com.xy.hkxannoeditor.entity.enums.ColumnName;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.xy.hkxannoeditor.component.factory.ComponentFactoryProducer.factoryType.TABLE_COLUMN;
import static javafx.scene.input.MouseButton.SECONDARY;

@Slf4j
public abstract class AnnoEditorTableInitializer<T extends HkxAnno> implements TableViewInitializer<T> {
    @Resource
    private ContextMenu editRightMenu;
    private List<TableColumn<T, ?>> columnList;

    @Override
    public void init(ScrollPane pane, TableView<T> table) {
        try {
            columnList = new ArrayList<>();
            AbstractComponentFactory tableColumnFactory = ComponentFactoryProducer.getFactory(TABLE_COLUMN);
            getColNames().forEach(columnName ->
                    columnList.add(tableColumnFactory.tableColumn(columnName))
            );

            table.getColumns().setAll(columnList);

            table.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (SECONDARY == event.getButton()) {
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node instanceof TableCell<?, ?>) {
                        editRightMenu.setUserData(node);
                        editRightMenu.show(node, event.getScreenX(), event.getScreenY());
                    } else if (node.getParent() instanceof TableCell<?, ?>) {
                        editRightMenu.setUserData(node.getParent());
                        editRightMenu.show(node, event.getScreenX(), event.getScreenY());
                    }
                }
            });

            table.itemsProperty().addListener((observable, o, n) -> table.setPrefHeight(Const.ROW_HEIGHT * (n.size() + 1)));
            pane.contentProperty().addListener(node -> {
                double width = table.getPrefWidth() / table.getColumns().size();
                for (TableColumn<T, ?> column : columnList) {
                    column.setPrefWidth(width);
                }
            });
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
    }

    protected abstract Collection<ColumnName> getColNames();
}
