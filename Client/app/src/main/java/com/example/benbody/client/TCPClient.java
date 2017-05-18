package com.example.benbody.client;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by BenBody on 11/05/2017.
 */
// Class which handles sending and receiving of data
public class TCPClient
{
    private SSLSocket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private static final String SERVERIP = ""; //These could potentially be read from file, or hard coded
    private static final int SERVERPORT = 1234; //or found in some other method
    private static final String KEYSTOREPASS = "addpasswordhere"; // same applies to this

    public TCPClient(Context context)
    {
        //set up socket & streams
        try
        {
            // unclear whether this section is required or indeed works
            // Loads in the keystore from the resources
            KeyStore ks = KeyStore.getInstance("BKS");
            InputStream keyin = context.getResources().openRawResource(R.raw.capitastore);
            ks.load(keyin, KEYSTOREPASS.toCharArray());
            keyin.close();
            // creates a KeyManagerFactory and adds the keystore
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(ks, KEYSTOREPASS.toCharArray());
            // creates an SSLContext and adds the KeyManager
            SSLContext sslctx = SSLContext.getInstance("TLS");
            sslctx.init(keyManagerFactory.getKeyManagers(), null, null);
            // uses the SSLContext to initialise the SSLSocketFactory
            SSLSocketFactory sf = sslctx.getSocketFactory();
            // uses the SSLSocketFactory to create the socket
            socket = (SSLSocket) sf.createSocket(SERVERIP, SERVERPORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //used by Request Task to request data from the server
    public List<Object> request(String request)
    {
        List<Object> result = null;
        try
        {
            // send request to server
            out.writeUTF(request);
            // receive response
            result = (List<Object>) in.readObject();
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

