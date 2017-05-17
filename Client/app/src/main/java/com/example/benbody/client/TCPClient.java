package com.example.benbody.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.ssl.SSLSocketFactory;

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
    private static final int SERVERPORT = 1234; //or found in some other method
    private static final String KEYSTOREPATH = "absolute path to your JKS keystore file";
    private static final String KEYSTOREPASS = "addpasswordhere"; //

    public TCPClient()
    {
        //set up socket & streams
        // TODO implement proper security
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try
        {
            socket = sf.createSocket(SERVERIP, SERVERPORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    //used by Request Task to request data from the server
    public ArrayList<Object> request(String request)
    {
        ArrayList<Object> result = null;
        try
        {
            // send request to server
            out.writeUTF(request);
            // receive response
            result = (ArrayList<Object>) in.readObject();
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

