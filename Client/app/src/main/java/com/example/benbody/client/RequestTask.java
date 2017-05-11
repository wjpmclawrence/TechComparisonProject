package com.example.benbody.client;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by BenBody on 11/05/2017.
 */

//AsyncTask used to perform connection on separate thread to GUI
public class RequestTask extends AsyncTask<String, String, ArrayList>
{

    @Override // Executes before doInBackground in UI thread
    protected void onPreExecute()
    {
        // set up a loading graphic in UI
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
        // take result, format it, and display in UI
    }
}
