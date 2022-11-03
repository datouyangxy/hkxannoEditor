package com.xy.hkxannoeditor.component.inputField;

import com.sun.javafx.scene.control.DoubleField;
import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;

public class TimePointInputField implements InputFieldComponent {
    public static DoubleField create(HkxAnno hkxAnno) {
        DoubleField timePoint_DF = new DoubleField();
        timePoint_DF.valueProperty().bindBidirectional(hkxAnno.getTimePoint());
        return timePoint_DF;
    }
}
