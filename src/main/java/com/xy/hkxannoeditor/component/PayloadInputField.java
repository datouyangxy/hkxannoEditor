package com.xy.hkxannoeditor.component;

import com.xy.hkxannoeditor.config.AnnoProperties;
import com.xy.hkxannoeditor.config.AnnoProperty;
import com.xy.hkxannoeditor.config.AnnoProperty.AnnoPropertyListConverter;
import com.xy.hkxannoeditor.config.AnnoPropertySuggestionProvider;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import static com.xy.hkxannoeditor.Const.ROW_HEIGHT;
import static com.xy.hkxannoeditor.utils.SpringUtil.getBean;

public class PayloadInputField implements InputFieldComponent {
    public static void create(StandardAnno standardAnno, ObservableList<Node> children) {
        Label pl = new Label("  payload: ");
        pl.setPrefHeight(ROW_HEIGHT);
        TextField payload_TF = TextFields.createClearableTextField();
        payload_TF.textProperty().bindBidirectional(standardAnno.getPayload());
        SuggestionProvider<AnnoProperty> payloadCallBack = new AnnoPropertySuggestionProvider(getBean(AnnoProperties.class).getPayloads(standardAnno.getAnnoType()));
        new CustomAutoCompletionTextFieldBinding<>(payload_TF, payloadCallBack,new AnnoPropertyListConverter());
        children.add(pl);
        children.add(payload_TF);
    }
}
