package com.xy.hkxannoeditor.component;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.util.Collection;

public class CustomAutoCompletionTextFieldBinding<T> extends AutoCompletionBinding<T> {

    private static <T> StringConverter<T> defaultResultConverter() {
        return new StringConverter<>() {
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

    private final StringConverter<T> resultConverter;

    public CustomAutoCompletionTextFieldBinding(Node completionTarget, Callback<ISuggestionRequest, Collection<T>> suggestionProvider) {
        this(completionTarget, suggestionProvider, defaultResultConverter(), defaultResultConverter());
    }

    public CustomAutoCompletionTextFieldBinding(Node completionTarget, Callback<ISuggestionRequest, Collection<T>> suggestionProvider, StringConverter<T> listConverter) {
        this(completionTarget, suggestionProvider, listConverter, defaultResultConverter());
    }

    public CustomAutoCompletionTextFieldBinding(Node completionTarget, Callback<ISuggestionRequest, Collection<T>> suggestionProvider, StringConverter<T> listConverter, StringConverter<T> resultConverter) {
        super(completionTarget, suggestionProvider, listConverter);
        this.resultConverter = resultConverter;
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
        String newText = resultConverter.toString(completion);
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
