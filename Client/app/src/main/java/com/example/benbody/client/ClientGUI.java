package com.example.benbody.client;

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

public class ClientGUI
{
    private ListView listView;
    private AutoCompleteTextView spinnerTextView;
    private ProgressBar progressBar;
    private View parent;

    // GUI Methods

    public ListView getListView()
    {
        return listView;
    }

    public void setListView(ListView listView)
    {
        this.listView = listView;
    }

    public AutoCompleteTextView getSpinnerTextView()
    {
        return spinnerTextView;
    }

    public void setSpinnerTextView(AutoCompleteTextView spinnerTextView)
    {
        this.spinnerTextView = spinnerTextView;
    }

    public ProgressBar getProgressBar()
    {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar)
    {
        this.progressBar = progressBar;
    }

    public View getParent()
    {
        return parent;
    }

    public void setParent(View parent)
    {
        this.parent = parent;
    }

    // utility method for hiding the keyboard
    public static void hideKeyboard(Activity activity) {
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
