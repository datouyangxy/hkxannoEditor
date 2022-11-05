package com.xy.hkxannoeditor.component.inputField;

import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.enums.ScarType;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

public class ScarTypeInputField implements InputFieldComponent{
    public static TextField create(ScarAnno scarAnno) {
        TextField scarType_TF = TextFields.createClearableTextField();
        scarType_TF.textProperty().bind(scarAnno.getScarJson().getType());
        TextFields.bindAutoCompletion(scarType_TF,ScarType.values());
        return scarType_TF;
    }
}
