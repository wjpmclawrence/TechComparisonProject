package com.example.benbody.client;

import android.content.Context;
import android.widget.ArrayAdapter;


/**
 * Created by BenBody on 10/05/2017.
 */

// Class which will format the data to be put into the list
public class FormattedArrayAdaptor<T> extends ArrayAdapter<T> {



    // constructor
    public FormattedArrayAdaptor(Context context, int resource)
    {
        super(context, resource);
    }



}
