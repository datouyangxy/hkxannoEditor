package com.xy.hkxannoeditor.component.inputField;

import com.xy.hkxannoeditor.config.AnnoProperties;
import com.xy.hkxannoeditor.config.AnnoProperty;
import com.xy.hkxannoeditor.config.AnnoProperty.AnnoPropertyListConverter;
import com.xy.hkxannoeditor.config.AnnoPropertySuggestionProvider;
import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import static com.xy.hkxannoeditor.utils.SpringUtil.getBean;

public class NameInputField implements InputFieldComponent {
    public static TextField create(HkxAnno hkxAnno) {
        TextField name_TF = TextFields.createClearableTextField();
        name_TF.textProperty().bindBidirectional(hkxAnno.getName());
        SuggestionProvider<AnnoProperty> nameCallBack = new AnnoPropertySuggestionProvider(getBean(AnnoProperties.class).getAnnotations(hkxAnno.getType()));
        new CustomAutoCompletionTextFieldBinding<>(name_TF, nameCallBack, new AnnoPropertyListConverter());
        return name_TF;
    }
}
