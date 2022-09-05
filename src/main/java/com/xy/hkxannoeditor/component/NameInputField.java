package com.xy.hkxannoeditor.component;

import com.xy.hkxannoeditor.config.AnnoProperties;
import com.xy.hkxannoeditor.config.AnnoProperty;
import com.xy.hkxannoeditor.config.AnnoPropertySuggestionProvider;
import com.xy.hkxannoeditor.config.CustomAutoCompletionTextFieldBinding;
import com.xy.hkxannoeditor.entity.bo.annotations.HkxAnno;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import static com.xy.hkxannoeditor.Const.ROW_HEIGHT;
import static com.xy.hkxannoeditor.utils.SpringUtil.getBean;

public class NameInputField implements InputFieldComponent {
    public static void create(HkxAnno hkxAnno, ObservableList<Node> children) {
        Label nl = new Label("  name: ");
        nl.setPrefHeight(ROW_HEIGHT);
        TextField name_TF = TextFields.createClearableTextField();
        name_TF.textProperty().bindBidirectional(hkxAnno.getName());
        SuggestionProvider<AnnoProperty> nameCallBack = new AnnoPropertySuggestionProvider(getBean(AnnoProperties.class).getAnnotations(hkxAnno.getType()));
        AutoCompletionBinding<AnnoProperty> binding = new CustomAutoCompletionTextFieldBinding<>(name_TF, nameCallBack);
        binding.setOnAutoCompleted(event -> {
            AnnoProperty annoProperty = event.getCompletion();
            CustomAutoCompletionTextFieldBinding textField = (CustomAutoCompletionTextFieldBinding) event.getTarget();
            textField.getCompletionTarget().setText(annoProperty.getName());
            System.out.println(hkxAnno.getName().get());
        });
        children.add(nl);
        children.add(name_TF);
    }
}
