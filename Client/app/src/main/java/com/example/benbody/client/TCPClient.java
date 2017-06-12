package com.example.benbody.client;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
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
    private Context context; // context required to load keystore
    // URL must be changed to the correct URL for the computer the server is running on
    private static final String SERVERIP = "192.168.1.71"; //These could potentially be read from file, or hard coded
    private static final int SERVERPORT = 1234; //or found in some other method
    private static final String KEYSTOREPASS = "capita123";

    // constructor initialises the context
    public TCPClient(Context context)
    {
        this.context = context;
    }

    //used by Request Task to request data from the server
    public List<Object> request(String request)
    {
        List<Object> result = null;
        SSLSocket socket = createSocket();

        if (socket != null)
        {
            try
            {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                System.out.println("request is " + request);
                // send request to server
                out.println(request);
                System.out.println("request sent");
                // create input stream
                System.out.println("creating input stream");
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                System.out.println("created input stream");
                // receive response
                result = (List<Object>) in.readObject();
                System.out.println("Object received: " + result);
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch(ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        // return response
        return result;
    }

    // Method returns an SSL socket
    private SSLSocket createSocket()
    {
        SSLSocket socket = null;
        try
        {
            KeyStore ks = KeyStore.getInstance("BKS");
            InputStream keyin = context.getResources().openRawResource(R.raw.keystoreformatt);
            ks.load(keyin, KEYSTOREPASS.toCharArray());
            keyin.close();
            // creates a TrustManagerFactory and adds the keystore
            TrustManagerFactory trustManagerFactory
                    = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(ks);
            // creates an SSLContext and adds the TrustManager
            SSLContext sslctx = SSLContext.getInstance("TLS");
            sslctx.init(null, trustManagerFactory.getTrustManagers(), null);
            // uses the SSLContext to initialise the SSLSocketFactory
            SSLSocketFactory sf = sslctx.getSocketFactory();
            // uses the SSLSocketFactory to create the socket
            System.out.println("creating socket");
            socket = (SSLSocket) sf.createSocket(SERVERIP, SERVERPORT);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return socket;
    }

}

