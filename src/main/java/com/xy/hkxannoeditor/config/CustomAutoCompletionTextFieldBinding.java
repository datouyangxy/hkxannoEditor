package com.xy.hkxannoeditor.config;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.util.Collection;

public class CustomAutoCompletionTextFieldBinding<T> extends AutoCompletionBinding<T> {

    private static <T> StringConverter<T> defaultStringConverter() {
        return new StringConverter<T>() {
            @Override
            public String toString(T t) {
                return t == null ? null : t.toString();
            }

            @SuppressWarnings("unchecked")
            @Override
            public T fromString(String string) {
                return (T) string;
            }
        };
    }

    private final StringConverter<T> converter;

    public CustomAutoCompletionTextFieldBinding(Node completionTarget, Callback<ISuggestionRequest, Collection<T>> suggestionProvider) {
        this(completionTarget, suggestionProvider, defaultStringConverter());
    }

    public CustomAutoCompletionTextFieldBinding(Node completionTarget, Callback<ISuggestionRequest, Collection<T>> suggestionProvider, StringConverter<T> converter) {
        super(completionTarget, suggestionProvider, converter);
        this.converter = converter;
        getCompletionTarget().textProperty().addListener(textChangeListener);
        getCompletionTarget().focusedProperty().addListener(focusChangedListener);
    }

    @Override
    public TextField getCompletionTarget() {
        return (TextField) super.getCompletionTarget();
    }

    @Override
    public void dispose() {
        getCompletionTarget().textProperty().removeListener(textChangeListener);
        getCompletionTarget().focusedProperty().removeListener(focusChangedListener);
    }

    @Override
    protected void completeUserInput(T completion) {
        String newText = converter.toString(completion);
        getCompletionTarget().setText(newText);
        getCompletionTarget().positionCaret(newText.length());
    }


    /***************************************************************************
     *                                                                         *
     * Event Listeners                                                         *
     *                                                                         *
     **************************************************************************/
    private final ChangeListener<String> textChangeListener = (obs, oldText, newText) -> {
        if (getCompletionTarget().isFocused()) {
            setUserInput(newText);
        }
    };

    private final ChangeListener<Boolean> focusChangedListener = (obs, oldFocused, newFocused) -> {
        if (!newFocused)
            hidePopup();
        else if (getCompletionTarget().getText().isEmpty())
            setUserInput(getCompletionTarget().getText());
    };
}
