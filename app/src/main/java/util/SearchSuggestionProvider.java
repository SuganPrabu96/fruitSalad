package util;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Suganprabu on 12-05-2015.
 */
public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "app.TheDreamStop.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
