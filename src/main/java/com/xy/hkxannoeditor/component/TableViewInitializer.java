package com.xy.hkxannoeditor.component;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;

public interface TableViewInitializer<T> {
    void init(ScrollPane pane, TableView<T> table);
}
