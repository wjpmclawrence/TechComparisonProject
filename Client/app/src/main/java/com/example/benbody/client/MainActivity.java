package com.example.benbody.client;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final String LANGUAGESFILEPATH = "languages.dat";
    private static final String VERSIONNOFILEPATH = "versionno.dat";
    private static final String VERSIONREQUEST = "version";
    private static final String REQUEST = "request";
    private static final String DELIMITER = "~";
    private TCPClient client;
    private List<String> options;

    public TCPClient getClient()
    {
        return client;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startup();
        setContentView(R.layout.activity_main);
    }

    // startup method for things that need executing as soon as app starts
    private void startup()
    {
        //creates client
        client = new TCPClient(this);

        //uses requesttask to request from the server
        RequestTask requestTask = new RequestTask();
        requestTask.execute(VERSIONREQUEST + DELIMITER + getVersionNo());
    }

    private void displayOptions()
    {
        //TODO code to display options
    }

    private void displayResults(List results)
    {
        //TODO code to format and display results using FormattedArrayAdapter
    }

    private void setVersionNo(int versionNo)
    {
        try
        {
            File version = new File(this.getFilesDir(), VERSIONNOFILEPATH);
            FileWriter writer = new FileWriter(version);
            writer.write(Integer.toString(versionNo));
            writer.close();
        }
        catch(IOException e)
        {
            //TODO Exception handling
            e.printStackTrace();
        }
    }

    private int getVersionNo()
    {
        File version = new File(this.getFilesDir(), VERSIONNOFILEPATH);
        if(version.exists()) // if there is a version on record
        {
            try
            {
                Scanner in = new Scanner(version);
                if(in.hasNextInt())
                    return in.nextInt();
                in.close();
            }
            catch(IOException e)
            {
                //TODO Exception Handling
                e.printStackTrace();
            }
        }
        return 0; //if unable to find version on file, default to 0
    }

    //AsyncTask used to perform connection on separate thread to GUI
    private class RequestTask extends AsyncTask<String, String, List<Object>>
    {

        @Override // Executes before doInBackground in UI thread
        protected void onPreExecute()
        {
            // TODO set up a loading graphic in UI
        }


        @Override // operates in separate thread
        protected List<Object> doInBackground(String... params)
        {
            // obtains the list from the TCP client
            return  getClient().request(params[0]);
        }

        @Override // executes after doInBackground to update the UI
        protected void onPostExecute(List<Object> result)
        {
            // TODO Remove loading graphic
            if(result == null); // display error message
            else// take result, format it, and display in UI
            {
                File languages = null;
                switch ((String) result.get(0))
                {
                    case "menu_list" :
                        // 2nd item in the list will be the version no.
                        setVersionNo((Integer) result.get(1));
                        //Saves the list to a .dat file
                        List<Object> menu = result.subList(2, result.size()); //take a list of all options
                        languages = new File(MainActivity.this.getFilesDir(), LANGUAGESFILEPATH);
                        try
                        {
                            FileWriter writer = new FileWriter(languages);
                            for (Object s: menu) //writes all the options separated by delimiter
                                writer.write(s + DELIMITER);
                            writer.close();
                        }
                        catch (IOException e)
                        {
                            //TODO Exception Handling
                        }
                        // Toast used to display "languages updated"
                        Toast.makeText(MainActivity.this, "languages updated", Toast.LENGTH_SHORT).show();
                    case "version_ok" :
                        // loads menu from .dat file
                        if (languages == null) // in case of version OK
                            languages = new File(MainActivity.this.getFilesDir(), LANGUAGESFILEPATH);
                        try
                        {
                            Scanner reader = new Scanner(languages).useDelimiter(DELIMITER);
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
                    case "sub_menu_list" :
                        List<Object> submenu = result.subList(1, result.size());
                        displayResults(submenu);
                        break;
                    default:
                        // Error has occurred
                }
            }
        }
    }


}
