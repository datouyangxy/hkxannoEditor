package com.xy.hkxannoeditor.component;

import com.sun.javafx.scene.control.DoubleField;
import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;

import static com.xy.hkxannoeditor.Const.ROW_HEIGHT;

public class TimePointInputField implements InputFieldComponent {
    public static void create(HkxAnno hkxAnno, ObservableList<Node> children) {
        Label tpl = new Label("timePoint: ");
        tpl.setPrefHeight(ROW_HEIGHT);
        DoubleField timePoint_DF = new DoubleField();
        timePoint_DF.valueProperty().bindBidirectional(hkxAnno.getTimePoint());
        children.add(tpl);
        children.add(timePoint_DF);
    }
}
