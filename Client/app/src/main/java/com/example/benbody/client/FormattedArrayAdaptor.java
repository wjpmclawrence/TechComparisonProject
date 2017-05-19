package com.example.benbody.client;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;


/**
 * Created by BenBody on 10/05/2017.
 */

// Class which will format the data to be put into the list
public class FormattedArrayAdaptor<Language> extends ArrayAdapter<Language>
{
    private Context context;
    private List<Language> values;



    // constructor
    public FormattedArrayAdaptor(Context context, int resource, List<Language> values)
    {
        super(context, resource, (Language[]) values.toArray());
        this.context = context;
        this.values = values;
    }

    @Override // called to determine how each element in the list will be displayed
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO develop XML file to define individual list elements
        // TODO display Language objects in the list format
        return null;
    }

}
