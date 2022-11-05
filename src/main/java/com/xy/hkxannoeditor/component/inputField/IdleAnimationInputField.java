package com.xy.hkxannoeditor.component.inputField;

import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.enums.IdleAnimationType;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

public class IdleAnimationInputField implements InputFieldComponent {
    public static TextField create(ScarAnno scarAnno) {
        TextField idleAnimation_TF = TextFields.createClearableTextField();
        idleAnimation_TF.textProperty().bindBidirectional(scarAnno.getScarJson().getIdleAnimation());
        TextFields.bindAutoCompletion(idleAnimation_TF, IdleAnimationType.values());
        return idleAnimation_TF;
    }
}
