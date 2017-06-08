package com.example.benbody.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
        System.out.println("onCreate Called");
        setContentView(R.layout.activity_main);
        startup();
    }

    // startup method for things that need executing as soon as app starts
    // Functionality moved to Startuptask
    private void startup()
    {
      StartupTask startupTask = new StartupTask();
        startupTask.execute("");
    }

    private void displayOptions()
    {
        //TODO code to display options
    }

    private void displayResults(List<?> results)
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
            Toast.makeText(this, R.string.problem_version_no, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, R.string.no_version_no, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        return 0; //if unable to find version on file, default to 0
    }

    // displays a loading graphic on the UI
    private void startLoading()
    {
        //TODO display loading graphic
    }

    // removes the loading graphic
    private void stopLoading()
    {
        //TODO Remove loading graphic
    }

    // displays an error dialog in case the languages can't be loaded
    private void errorMessageLanguages()
    {
        // class which builds a dialog message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.languages_error)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // uses RequestTask to request from the server
                        // forces a new list to be loaded by using 0 as version
                        if (client.isSetUp())
                        {
                            RequestTask requestTask = new RequestTask();
                            requestTask.execute(VERSIONREQUEST + DELIMITER + 0);
                        }
                        else; // error has occurred
                    }
                })
                .setNegativeButton(R.string.cancel_shut_down, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // takes user back to the home screen
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });

    }

    private void dealWithResponse(List<Object> result)
    {
        System.out.println("dealing with response");
        if(result == null || result.isEmpty() || !(result.get(0) instanceof String))
        {
            Toast.makeText(this, "Invalid Response from server", Toast.LENGTH_LONG).show(); // TODO offer chance to retry
        }
        else// take result, format it, and display in UI
        {
            File languages;
            switch ((String) result.get(0))
            {
                case "menu_list" :
                    // 2nd item in the list will be the version no.
                    setVersionNo((Integer) result.get(1));
                    //Saves the list to a .dat file
                    List<String> menu = (List<String>) (Object) result.subList(2, result.size()); //take a list of all options
                    languages = new File(MainActivity.this.getFilesDir(), LANGUAGESFILEPATH);
                    options = new ArrayList<>(menu); //
                    try
                    {
                        FileWriter writer = new FileWriter(languages);
                        for (String s: menu) //writes all the options separated by delimiter
                            writer.write(s + DELIMITER);
                        writer.close();
                    }
                    catch (IOException e)
                    {
                        Toast.makeText(MainActivity.this,
                                R.string.prob_cache_lang, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    // Toast used to display "languages updated"
                    Toast.makeText(MainActivity.this, R.string.language_update, Toast.LENGTH_SHORT).show();
                    displayOptions(); //calls method to display the options
                    break;
                case "version_ok" :
                    // loads menu from .dat file
                    options = new ArrayList<>();
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
                    catch (IOException e)
                    {
                        errorMessageLanguages();
                        e.printStackTrace();
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



    // AsyncTask used as creating TCPClient requires network operations, which are not allowed on the main thread
    private class StartupTask extends AsyncTask<String, String, TCPClient>
    {
        @Override
        protected void onPreExecute()
        {
            startLoading();
        }

        @Override // operates in background thread
        protected TCPClient doInBackground(String... params)
        {
            return new TCPClient(MainActivity.this);
        }

        @Override
        protected void onPostExecute(TCPClient result)
        {
            stopLoading();
            //assigns client
            client = result;

            //uses RequestTask to request from the server
            if (client.isSetUp())
            {
                RequestTask requestTask = new RequestTask();
                requestTask.execute(VERSIONREQUEST + DELIMITER + getVersionNo());
            }
            else; // error has occurred
        }
    }

    //AsyncTask used to perform connection on separate thread to GUI
    private class RequestTask extends AsyncTask<String, String, List<Object>>
    {

        @Override // Executes before doInBackground in UI thread
        protected void onPreExecute()
        {
            startLoading();
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
            stopLoading();
            dealWithResponse(result);
        }
    }


}
