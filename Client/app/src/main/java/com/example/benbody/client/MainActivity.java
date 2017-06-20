package com.example.benbody.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Utils.Language;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String LANGUAGESFILEPATH = "languages.dat";
    private static final String VERSIONNOFILEPATH = "versionno.dat";
    private static final String VERSIONREQUEST = "version";
    private static final String REQUEST = "request";
    private static final String DELIMITER = "~";
    private TCPClient client;
    private ClientGUI gui = new ClientGUI();
    private List<String> options;
    private boolean optionsLoaded = false;

    public TCPClient getClient()
    {
        return client;
    }

    /**
     *  Called every time the app is created.
     *  Sets up references to the views and calls the startup Method
     *
     * @param savedInstanceState Bundle which is passed into the superclass method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gui.setProgressBar( (ProgressBar) findViewById(R.id.progressBar));
        gui.setListView((ListView) findViewById(R.id.info));
        gui.setSpinner((Spinner) findViewById(R.id.spinner));
        // Spinner click listener
        gui.getSpinner().setOnItemSelectedListener(this);

        startup();
    }


    /**
     * Method implemented from AdapterView.OnItemSelectedListener.
     * Uses the position selected to send a request to the server
     *
     * @param parent Never used
     * @param view Never used
     * @param position The position of the item selected
     * @param id Never used
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        request(position);
    }

    // Method never used
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}

    /**
     *  Startup Method used to execute all tasks required at startup
     *  Sets up the TCPClient and uses a RequestTask to send a request to the server
     */
    private void startup()
    {
        client = new TCPClient(this);

        //uses RequestTask to request from the server
        RequestTask requestTask = new RequestTask();
        requestTask.execute(VERSIONREQUEST + DELIMITER + getVersionNo());
    }

    /**
     * Used to display the options from the stored list.
     * Uses an ArrayAdapter to display the options in the Spinner.
     */
    private void displayOptions()
    {
        // sets the intially selected option to "Select Language"
        options.add(0, getString(R.string.select_lang));
        // uses an Array Adapter to contation the options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, options);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gui.getSpinner().setAdapter(adapter);
        optionsLoaded = true;
    }

    /**
     * Takes a list of Languages and displays them using a FormattedArrayAdapter
     *
     * @param results A list of Language Objects to be displayed
     */
    private void displayResults(List<?> results)
    {
        FormattedArrayAdaptor formArrayAdapt = new FormattedArrayAdaptor(this,
                R.layout.list_layout, (List<Language>) results);
        gui.getListView().setAdapter(formArrayAdapt);
    }

    /**
     * Sends a request of the language at the specified position in the list.
     * In the case that "Please Select A Language" is selected, nothing happens.
     * Uses a RequestTask
     *
     * @param i the position in the languages list that is selected
     */
    private void request(int i)
    {
        if(i == 0) // case where "select language" is selected
            return;
        // gets name of the language
        String lang = options.get(i);
        // creates and executes a requestTask
        RequestTask requestTask = new RequestTask();
        requestTask.execute(REQUEST + DELIMITER + lang);
    }

    /**
     * Updates the stored Version number for the app.
     * Saves the number in a .dat file in the directory supplied by android.
     *
     * @param versionNo New version number
     */
    private void setVersionNo(int versionNo)
    {
        try
        {
            File version = new File(getFilesDir(), VERSIONNOFILEPATH);
            FileWriter writer = new FileWriter(version);
            writer.write(Integer.toString(versionNo));
            writer.close();
        }
        catch(IOException e)
        {
            Toast.makeText(this, R.string.problem_version_no, Toast.LENGTH_SHORT).show();
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }
    }

    /**
     * Loads the current version from the .dat file
     *
     * @return Current version number
     */
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
                if (BuildConfig.DEBUG)
                    e.printStackTrace();
            }
        }
        return 0; //if unable to find version on file, default to 0
    }

    // displays a loading graphic on the UI
    private void startLoading()
    {
        gui.getProgressBar().setVisibility(View.VISIBLE);
    }

    // removes the loading graphic
    private void stopLoading()
    {
        gui.getProgressBar().setVisibility(View.GONE);
    }

    /**
     *  Displays an Alert Dialog to inform the user that the languages can't be loaded.
     *  Gives the option to attempt to get them from the server, or to close the app.
     */
    private void errorMessageLanguages()
    {
        // class which builds a dialog message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.languages_error)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // uses RequestTask to request from the server
                        // forces a new list to be loaded by using 0 as version
                        RequestTask requestTask = new RequestTask();
                        requestTask.execute(VERSIONREQUEST + DELIMITER + 0);
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
                })
                .show();

    }

    /**
     * Displays an Alert Dialog to inform the user that the server has not responded.
     * Gives the option to to resend the request or not.
     */
    private void errorMessageResponse()
    {
        // class which builds a dialog message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.response_error)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //uses RequestTask to request from the server
                        RequestTask requestTask = new RequestTask();
                        requestTask.execute(VERSIONREQUEST + DELIMITER + getVersionNo());
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    /**
     * Deals with the list returned by the server.
     * If the list is null, empty or the first element is not a string an error message is shown
     * In all other cases the first element is a string which describes what to do.
     * More information is in the project documentation.
     *
     * @param result The List received from the server
     */
    private void dealWithResponse(List<?> result)
    {
        if(result == null || result.isEmpty() || !(result.get(0) instanceof String))
        {
            if (optionsLoaded) // in case of failed request
                Toast.makeText(this, R.string.invalid_response, Toast.LENGTH_LONG).show();
            else // case where options have not been loaded
                errorMessageResponse();
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
                    List<String> menu = (List<String>) result.subList(2, result.size()); //take a list of all options
                    languages = new File(getFilesDir(), LANGUAGESFILEPATH);
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
                        if (BuildConfig.DEBUG)
                            e.printStackTrace();
                    }
                    // Toast used to display "languages updated"
                    Toast.makeText(MainActivity.this, R.string.language_update, Toast.LENGTH_SHORT).show();
                    displayOptions(); //calls method to display the options
                    break;
                case "version_ok" :
                    // loads menu from .dat file
                    options = new ArrayList<>();
                    languages = new File(getFilesDir(), LANGUAGESFILEPATH);
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
                        if (BuildConfig.DEBUG)
                            e.printStackTrace();
                    }
                    displayOptions(); //calls method to display the options
                    break;
                case "sub_menu_list" :
                    List<?> submenu = result.subList(1, result.size());
                    displayResults(submenu);
                    break;
                default:
                    Toast.makeText(this, R.string.invalid_response, Toast.LENGTH_LONG).show();
            }
        }
    }


    //AsyncTask used to perform connection on separate thread to GUI
    private class RequestTask extends AsyncTask<String, String, List<?>>
    {

        @Override // Executes before doInBackground in UI thread
        protected void onPreExecute()
        {
            startLoading();
        }


        @Override // operates in separate thread
        protected List<?> doInBackground(String... params)
        {
            // obtains the list from the TCP client
            return  getClient().request(params[0]);
        }

        @Override // executes after doInBackground to update the UI
        protected void onPostExecute(List<?> result)
        {
            stopLoading();
            dealWithResponse(result);
        }
    }

}
