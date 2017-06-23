package com.example.benbody.client;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import Utils.Language;


/**
 * Created by BenBody on 10/05/2017.
 */

// Class which will format the data to be put into the list
class FormattedArrayAdaptor extends ArrayAdapter<Language>
{
    private Context context;
    private List<Language> values;



    // constructor
    FormattedArrayAdaptor(Context context, int resource, List<Language> values)
    {
        super(context, resource, values.toArray(new Language[values.size()]));
        this.context = context;
        this.values = values;
    }

    // used to hold references to the Views
    private static class ViewHolder
    {
        private TextView langName;
        private TextView noWeeks;
        private TextView percentage;
        private ProgressBar percProgress;
    }

    @Override // called to determine how each element in the list will be displayed
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        // if no view to convert
        if (convertView == null)
        {
            // inflater is used to inflate the layout XML file
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_layout, parent, false);
            // creates a view holder to hold references
            ViewHolder viewHolder = new ViewHolder();
            // assigns each of the elements of the layout to a variable
            viewHolder.langName = (TextView) convertView.findViewById(R.id.langName);
            viewHolder.noWeeks = (TextView) convertView.findViewById(R.id.noWeeks);
            viewHolder.percentage = (TextView) convertView.findViewById(R.id.percentage);
            viewHolder.percProgress = (ProgressBar) convertView.findViewById(R.id.percentageProgress);
            // sets view holder as the tag
            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        // language at specific position
        Language lang = values.get(position);
        // sets each element of the layout to value from language
        holder.langName.setText(lang.getName());
        holder.noWeeks.setText(lang.getTrainingTime());
        holder.percentage.setText(String.format(Locale.ENGLISH, "%d%%",lang.getSimilarity()));
        holder.percProgress.setProgress(lang.getSimilarity());
        chooseProgressBarColour(holder.percProgress, lang.getSimilarity());

        if (position % 2 == 0) // Even
            convertView.setBackgroundResource(R.color.darkerGrey);
        else
            convertView.setBackgroundResource(R.color.lightGrey);

        return convertView;
    }

    // chooses the colour to set the progress bar
    private void chooseProgressBarColour(ProgressBar progressBar, int similarity)
    {
        int category = similarity/20; // splits into categories 0 - 4

        switch (category)
        {
            case 0: // 0 - 19
                setProgressBarColour(progressBar, R.color.red);
                break;
            case 1: // 20 - 39
                setProgressBarColour(progressBar, R.color.orange);
                break;
            case 2: // 40 - 59
                setProgressBarColour(progressBar, R.color.yellow);
                break;
            case 3: // 60 - 79
            case 4: // 80
                setProgressBarColour(progressBar, R.color.green);
                break;
        }
    }

    // takes colour resource and sets the progress bar to that colour
    private void setProgressBarColour(ProgressBar progressBar, @ColorRes int colour)
    {
        // if setProgressTintList is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            progressBar.setProgressTintList(ColorStateList.valueOf(
                    ResourcesCompat.getColor(context.getResources(), colour, null)));
        else
            progressBar.getProgressDrawable().setColorFilter(
                    ResourcesCompat.getColor(context.getResources(), colour, null),
                    PorterDuff.Mode.SRC_IN);
    }


}
