package com.example.benbody.client;

import android.content.Context;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

/**
 * Created by BenBody on 28/06/2017.
 *
 * Class which Extends AutocompleteTextView purely to allow dropdown to display instantly.
 *
 * Overrides the enoughToFilter method to always return true
 */

public class InstantAutoComplete extends AppCompatAutoCompleteTextView {

    public InstantAutoComplete(Context context) {
        super(context);
    }

    public InstantAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InstantAutoComplete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // always returns true so that the dropdown menu will always appear
    @Override
    public boolean enoughToFilter() {
        return true;
    }
}
