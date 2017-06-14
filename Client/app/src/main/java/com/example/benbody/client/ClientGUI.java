package com.example.benbody.client;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BenBody on 14/06/2017.
 */

public class ClientGUI {
    private List<String> categories;
    private String info;
    public TextView textView;

    // Constructor
    ClientGUI()
    {
        categories = new ArrayList<String>();
        info = "Waiting for Server";
    }
    // GUI Methods

    // Populate the categories list
    public void GenerateList(String[] list)
    {
        for (int i = 0; i< list.length; i++)
        {
            categories.add(list[i]);
        }
    }

    // Return the current list of categories
    public List<String> GetList()
    {
        return categories;
    }

    // Take in selected object string,
    public String GetInfo(String lang) {
        String msg = lang;
        return msg;
    }


}
