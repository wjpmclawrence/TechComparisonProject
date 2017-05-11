package com.example.benbody.client;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


/**
 * Created by BenBody on 10/05/2017.
 */

// Class which will format the data to be put into the list
public class FormattedArrayAdaptor<T> extends ArrayAdapter<T>
{
    private Context context;
    private ArrayList<T> values;



    // constructor
    public FormattedArrayAdaptor(Context context, int resource, ArrayList<T> values)
    {
        super(context, resource, (T[]) values.toArray());
        this.context = context;
        this.values = values;
    }

    @Override // called to determine how each element in the list will be displayed
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return null;
    }

}
