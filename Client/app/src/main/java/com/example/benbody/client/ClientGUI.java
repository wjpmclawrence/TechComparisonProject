package com.example.benbody.client;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

/**
 * Created by William J P Mclawrence on 14/06/2017.
 *
 * Container Class for all the GUI Elements
 */

public class ClientGUI
{
    private ListView listView;
    private Spinner spinner;
    private ProgressBar progressBar;

    // GUI Methods

    public ListView getListView()
    {
        return listView;
    }

    public void setListView(ListView listView)
    {
        this.listView = listView;
    }

    public Spinner getSpinner()
    {
        return spinner;
    }

    public void setSpinner(Spinner spinner)
    {
        this.spinner = spinner;
    }

    public ProgressBar getProgressBar()
    {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar)
    {
        this.progressBar = progressBar;
    }
}
