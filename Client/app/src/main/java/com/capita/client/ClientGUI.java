package com.capita.client;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Created by William J P Mclawrence on 14/06/2017.
 *
 * Container Class for all the GUI Elements
 */

class ClientGUI
{
    private ListView listView;
    private AutoCompleteTextView spinnerTextView;
    private ProgressBar progressBar;
    private View parent;


    ListView getListView()
    {
        return listView;
    }

    void setListView(ListView listView)
    {
        this.listView = listView;
    }

    AutoCompleteTextView getSpinnerTextView()
    {
        return spinnerTextView;
    }

    void setSpinnerTextView(AutoCompleteTextView spinnerTextView)
    {
        this.spinnerTextView = spinnerTextView;
    }

    ProgressBar getProgressBar()
    {
        return progressBar;
    }

    void setProgressBar(ProgressBar progressBar)
    {
        this.progressBar = progressBar;
    }

    View getParent()
    {
        return parent;
    }

    void setParent(View parent)
    {
        this.parent = parent;
    }

    // utility method for hiding the keyboard
    static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
