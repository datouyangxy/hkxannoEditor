package com.xy.hkxannoeditor.component.inputField;

import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

public class IdleAnimationInputField implements InputFieldComponent {
    public static TextField create(ScarAnno scarAnno) {
        TextField idleAnimation_TF = TextFields.createClearableTextField();
        idleAnimation_TF.textProperty().bind(scarAnno.getScarJson().getIdleAnimation());
        return idleAnimation_TF;
    }
}
