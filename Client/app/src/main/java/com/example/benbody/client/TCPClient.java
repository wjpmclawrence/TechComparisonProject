package com.example.benbody.client;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by BenBody on 11/05/2017.
 */
// Class which handles sending and receiving of data
public class TCPClient
{
    private SSLSocket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private static final String SERVERIP = "192.168.1.83"; //These could potentially be read from file, or hard coded
    private static final int SERVERPORT = 1234; //or found in some other method
    private static final String KEYSTOREPASS = "capita123"; // same applies to this
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
            // unclear whether this section is required or indeed works
            // Loads in the keystore from the resources
            System.out.println("starting setup");
            KeyStore ks = KeyStore.getInstance("BKS");
            InputStream keyin = context.getResources().openRawResource(R.raw.capitastore);
            ks.load(keyin, KEYSTOREPASS.toCharArray());
            keyin.close();
            // creates a TrustManagerFactory and adds the keystore
            TrustManagerFactory trustManagerFactory
                    = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(ks);
            // creates an SSLContext and adds the KeyManager & trustmanager
            SSLContext sslctx = SSLContext.getInstance("TLS");
            sslctx.init(null, trustManagerFactory.getTrustManagers(), null);
            // uses the SSLContext to initialise the SSLSocketFactory
            SSLSocketFactory sf = sslctx.getSocketFactory();
            // uses the SSLSocketFactory to create the socket
            System.out.println("creating socket");
            socket = (SSLSocket) sf.createSocket(SERVERIP, SERVERPORT);
            if (socket.isConnected())
                System.out.println("socket connected");
            // TODO code currently blocks indefinitely on the next line
            // this is due to the handshake not working.
            // May be issue on server side (server I've been testing with currently doesn't do anything)
            System.out.println("creating Output Stream");
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush(); // sends the header so that it doesn't block
            System.out.println("output stream created");
            System.out.println("creating input stream");
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("all streams created");
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
            // send request to server
            out.writeUTF(request);
            System.out.println("request sent");
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

