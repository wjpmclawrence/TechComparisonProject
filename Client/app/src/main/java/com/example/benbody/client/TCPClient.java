package com.example.benbody.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by BenBody on 11/05/2017.
 */
// Class which handles sending and receiving of data
public class TCPClient
{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private static final String SERVERIP = ""; //These could potentially be read from file, or hard coded
    private static final String SERVERPORT = ""; //or found in some other method

    public TCPClient()
    {
        //set up socket & streams
    }

    //used by Request Task to request data from the server
    public ArrayList request(String request)
    {
        // send request to server
        // receive response
        // return response
        return null;
    }

}

