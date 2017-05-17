package com.example.benbody.client;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final String LANGUAGESFILEPATH = "languages.dat";
    private final TCPClient client = new TCPClient();
    private List<String> options;

    public TCPClient getClient()
    {
        return client;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void displayOptions()
    {
        //TODO code to display options
    }

    private void displayResults(List results)
    {
        //TODO code to format and display results using FormattedArrayAdapter
    }

    //AsyncTask used to perform connection on separate thread to GUI
    private class RequestTask extends AsyncTask<String, String, ArrayList>
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
            return  getClient().request(params[0]);
        }

        @Override // executes after doInBackground to update the UI
        protected void onPostExecute(ArrayList result)
        {
            // TODO Remove loading graphic
            if(result == null); // display error message
            else// take result, format it, and display in UI
            {
                File languages = null;
                switch ((String) result.get(0))
                {
                    case "menuArray" :
                        //Saves the list to a .dat file
                        List<String> menu = result.subList(1, result.size()); //take a list of all options
                        languages = new File(MainActivity.this.getFilesDir(), LANGUAGESFILEPATH);
                        try
                        {
                            FileWriter writer = new FileWriter(languages);
                            for (String s: menu) //writes all the options separated by |
                                writer.write(s + "|");
                            writer.close();
                        }
                        catch (IOException e)
                        {
                            //TODO Exception Handling
                        }
                        // TODO display "list updated"
                    case "versionOk" :
                        // loads menu from dat file
                        if (languages == null) // in case of version OK
                            languages = new File(MainActivity.this.getFilesDir(), LANGUAGESFILEPATH);
                        try
                        {
                            Scanner reader = new Scanner(languages).useDelimiter("|");
                            //while there is more input
                            while (reader.hasNext())
                            {
                                options.add(reader.next()); //adds it to the option list
                            }
                            reader.close();
                        }
                        catch(IOException e)
                        {
                            //TODO Exception Handling
                        }
                        displayOptions(); //calls method to display the options
                        break;
                    case "subMenu" :
                        List submenu = result.subList(1, result.size());
                        displayResults(submenu);
                        break;
                    default:
                        // Error has occurred
                }
            }
        }
    }


}
