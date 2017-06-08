package com.example.benbody.client;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


/**
 * Created by BenBody on 11/05/2017.
 */
// Class which handles sending and receiving of data
public class TCPClient
{
    private Socket socket;
    private ObjectInputStream in;
    private PrintWriter out;
    // URL must be changed to the correct URL for the computer the server is running on
    private static final String SERVERIP = "192.168.1.112"; //These could potentially be read from file, or hard coded
    private static final int SERVERPORT = 1234; //or found in some other method
    private boolean isSetUp; // whether the client has been successfully set up


    public boolean isSetUp()
    {
        return isSetUp;
    }


    public TCPClient(Context context)
    {
        //set up socket & streams
        try
        {
            // create the socket
            System.out.println("creating socket");
            socket = new Socket(SERVERIP, SERVERPORT);
            if (socket.isConnected())
                System.out.println("socket connected");
            // creates output stream
            System.out.println("creating Output Stream");
            out = new PrintWriter(socket.getOutputStream(), true);
            //out.flush(); // sends the header so that it doesn't block, may not be necessary
            System.out.println("output stream created");

            isSetUp = true;
        } catch (Exception e)
        {
            e.printStackTrace();
            isSetUp = false;
        }

    }

    //used by Request Task to request data from the server
    public List<Object> request(String request)
    {
        List<Object> result = null;
        try
        {
            System.out.println("request is " + request);
            // send request to server
            out.println(request);
            System.out.println("request sent");
            // create input stream
            System.out.println("creating input stream");
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("created input stream");
            // receive response
            result = (List<Object>) in.readObject();
            System.out.println("Object received: " + result);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        // return response
        return result;
    }

}

