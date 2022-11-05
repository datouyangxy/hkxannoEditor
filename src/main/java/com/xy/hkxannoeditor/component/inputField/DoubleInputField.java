package com.xy.hkxannoeditor.component.inputField;

import com.sun.javafx.scene.control.DoubleField;
import javafx.beans.property.DoubleProperty;

public class DoubleInputField implements InputFieldComponent {
    public static DoubleField create(DoubleProperty doubleProperty) {
        DoubleField timePoint_DF = new DoubleField();
        timePoint_DF.valueProperty().bindBidirectional(doubleProperty);
        return timePoint_DF;
    }
}
