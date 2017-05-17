package com.example.benbody.client;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BenBody on 11/05/2017.
 */
// Changed to nested class inside MainActivity
/*
//AsyncTask used to perform connection on separate thread to GUI
public class RequestTask extends AsyncTask<String, String, ArrayList>
{

    @Override // Executes before doInBackground in UI thread
    protected void onPreExecute()
    {
        // TODO set up a loading graphic in UI
    }


    @Override // operates in separate thread
    protected ArrayList doInBackground(String... params)
    {
        // obtains the list from the TCP client
        return  new TCPClient().request(params[0]);
    }

    @Override // executes after doInBackground to update the UI
    protected void onPostExecute(ArrayList result)
    {
        // TODO Remove loading graphic
        if(result == null); // display error message
        else// take result, format it, and display in UI
        {
            switch ((String) result.get(0))
            {
                case "menuArray" :
                    List<String> menu = result.subList(1, result.size());

                    // TODO cache menu list and save to dat file
                    // TODO display "list updated"
                case "versionOk" :
                    // TODO load menu from dat file into the display method
                    break;
                case "subMenu" :
                    List submenu = result.subList(1, result.size());
                    // TODO use FormattedArrayAdapter to display list
                    break;
                default:
                    // Error has occured
            }
        }
    }
}
*/