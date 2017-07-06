package com.capita.client;

import android.content.Context;

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
 *
 * Class which handles sending and receiving of data from server
 */

class TCPClient
{
    private Context context; // context required to load keystore
    // URL must be changed to the correct URL for the computer the server is running on
    private static final String SERVERIP = "93.97.129.217"; //These could potentially be read from file, or hard coded
    private static final int SERVERPORT = 1234; //or found in some other method
    private static final String KEYSTOREPASS = "capita123";

    // constructor initialises the context
    TCPClient(Context context)
    {
        this.context = context;
    }

    //used by Request Task to request data from the server
    List<?> request(String request)
    {
        List<?> result = null;
        SSLSocket socket = createSocket();

        if (socket != null)
        {
            try
            {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // send request to server
                out.println(request);
                // create input stream
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                // receive response
                result = (List<?>) in.readObject();
                socket.close();
            }
            catch (Exception e)
            {
                if (BuildConfig.DEBUG)
                    e.printStackTrace();
            }
        }
        // return response (or null if none received)
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
            socket = (SSLSocket) sf.createSocket(SERVERIP, SERVERPORT);
        }
        catch (Exception e)
        {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }
        return socket;
    }

}

