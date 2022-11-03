package com.xy.hkxannoeditor.component;

import com.xy.hkxannoeditor.Const;
import com.xy.hkxannoeditor.component.factory.AbstractComponentFactory;
import com.xy.hkxannoeditor.component.factory.ComponentFactoryProducer;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.xy.hkxannoeditor.component.factory.ComponentFactoryProducer.factoryType.TABLE_COLUMN;
import static com.xy.hkxannoeditor.entity.enums.ColumnName.*;
import static javafx.scene.input.MouseButton.SECONDARY;

@Component
public class StandardInitializer implements TableViewInitializer<StandardAnno> {

    @Resource
    private ContextMenu editRightMenu;

    @Override
    public void init(ScrollPane pane, TableView<StandardAnno> table) {
        AbstractComponentFactory tableColumnFactory = ComponentFactoryProducer.getFactory(TABLE_COLUMN);
        TableColumn timePointField = tableColumnFactory.tableColumn(time_point);
        TableColumn nameField = tableColumnFactory.tableColumn(name);
        TableColumn payloadField = tableColumnFactory.tableColumn(payload);

        table.getColumns().setAll(
                timePointField,
                nameField,
                payloadField
        );

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
            double weight = table.getPrefWidth() / table.getColumns().size();
            timePointField.setPrefWidth(weight);
            nameField.setPrefWidth(weight);
            payloadField.setPrefWidth(weight);
        });
    }
}
