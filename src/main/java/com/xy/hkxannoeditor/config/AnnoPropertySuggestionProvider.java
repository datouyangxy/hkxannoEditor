package com.xy.hkxannoeditor.config;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.util.*;

public class AnnoPropertySuggestionProvider extends SuggestionProvider<AnnoProperty> {
    private final Callback<AnnoProperty, String> stringConverter;
    private final List<AnnoProperty> possibleSuggestions = new ArrayList<>();
    private final Object possibleSuggestionsLock = new Object();

    private final Comparator<AnnoProperty> stringComparator = new Comparator<>() {
        @Override
        public int compare(AnnoProperty o1, AnnoProperty o2) {
            String o1str = stringConverter.call(o1);
            String o2str = stringConverter.call(o2);
            return o1str.compareTo(o2str);
        }
    };

    public AnnoPropertySuggestionProvider(Collection<AnnoProperty> possibleSuggestions) {
        addPossibleSuggestions(possibleSuggestions);
        // In case no stringConverter was provided, use the default strategy
        this.stringConverter = obj -> {
            return obj != null ? obj.toString() : ""; //$NON-NLS-1$
        };
    }

    @Override
    public void addPossibleSuggestions(AnnoProperty... newPossible){
        addPossibleSuggestions(Arrays.asList(newPossible));
    }

    /**
     * Add the given new possible suggestions to this  SuggestionProvider
     */
    @Override
    public void addPossibleSuggestions(Collection<AnnoProperty> newPossible){
        synchronized (possibleSuggestionsLock) {
            possibleSuggestions.addAll(newPossible);
        }
    }

    /**
     * Remove all current possible suggestions
     */
    @Override
    public void clearSuggestions(){
        synchronized (possibleSuggestionsLock) {
            possibleSuggestions.clear();
        }
    }

    @Override
    public Collection<AnnoProperty> call(final AutoCompletionBinding.ISuggestionRequest request) {
        List<AnnoProperty> suggestions = new ArrayList<>();
        synchronized (possibleSuggestionsLock) {
            for (AnnoProperty possibleSuggestion : possibleSuggestions) {
                if (isMatch(possibleSuggestion, request)) {
                    suggestions.add(possibleSuggestion);
                }
            }
        }
        suggestions.sort(getComparator());
        return suggestions;
    }

    @Override
    protected Comparator<AnnoProperty> getComparator() {
        return stringComparator;
    }

    @Override
    protected boolean isMatch(AnnoProperty suggestion, AutoCompletionBinding.ISuggestionRequest request) {
        String userTextLower = request.getUserText().toLowerCase();
        String suggestionStr = stringConverter.call(suggestion).toLowerCase();
        if (userTextLower.isEmpty())
            return true;
        return suggestionStr.contains(userTextLower);
    }
}
