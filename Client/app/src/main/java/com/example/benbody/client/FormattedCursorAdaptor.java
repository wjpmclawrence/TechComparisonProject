package com.example.benbody.client;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by BenBody on 10/05/2017.
 */

// Class which will format the data to be put into the list
public class FormattedCursorAdaptor extends CursorAdapter {



    // constructor
    public FormattedCursorAdaptor(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }


    // overriden method which creates the view to display the data
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    // binds the view to the ListView which will display the data
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
