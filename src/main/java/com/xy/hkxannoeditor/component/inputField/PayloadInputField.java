package com.xy.hkxannoeditor.component.inputField;

import com.xy.hkxannoeditor.config.AnnoProperties;
import com.xy.hkxannoeditor.config.AnnoProperty;
import com.xy.hkxannoeditor.config.AnnoProperty.AnnoPropertyListConverter;
import com.xy.hkxannoeditor.config.AnnoPropertySuggestionProvider;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import static com.xy.hkxannoeditor.utils.SpringUtil.getBean;

public class PayloadInputField implements InputFieldComponent {
    public static TextField create(StandardAnno standardAnno) {
        TextField payload_TF = TextFields.createClearableTextField();
        payload_TF.textProperty().bindBidirectional(standardAnno.getPayload());
        SuggestionProvider<AnnoProperty> payloadCallBack = new AnnoPropertySuggestionProvider(getBean(AnnoProperties.class).getPayloads(standardAnno.getType()));
        new CustomAutoCompletionTextFieldBinding<>(payload_TF, payloadCallBack, new AnnoPropertyListConverter());
        return payload_TF;
    }
}
