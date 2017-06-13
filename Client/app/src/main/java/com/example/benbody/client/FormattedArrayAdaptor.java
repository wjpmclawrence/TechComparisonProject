package com.example.benbody.client;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import utils.Language;


/**
 * Created by BenBody on 10/05/2017.
 */

// Class which will format the data to be put into the list
public class FormattedArrayAdaptor extends ArrayAdapter<Language>
{
    private Context context;
    private List<Language> values;



    // constructor
    public FormattedArrayAdaptor(Context context, int resource, List<Language> values)
    {
        super(context, resource, values.toArray(new Language[values.size()]));
        this.context = context;
        this.values = values;
    }

    @Override // called to determine how each element in the list will be displayed
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        // inflater is used to inflate the layout XML file
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_layout, parent, false);

        // assigns each of the elements of the layout to a variable
        TextView langName = (TextView) rowView.findViewById(R.id.langName);
        TextView noWeeks = (TextView) rowView.findViewById(R.id.noWeeks);
        TextView percentage = (TextView) rowView.findViewById(R.id.percentage);
        ProgressBar percProgress = (ProgressBar) rowView.findViewById(R.id.percentageProgress);
        // language at specific position
        Language lang = values.get(position);
        // sets each element of the layout to value from language
        langName.setText(lang.getName());
        noWeeks.setText(lang.getTrainingTime());
        percentage.setText(String.format(Locale.ENGLISH, "%d%%",lang.getSimilarity()));
        percProgress.setProgress(lang.getSimilarity());

        return rowView;
    }

}
