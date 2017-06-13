//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.SSLSocket;
//import javax.net.ssl.SSLSocketFactory;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author matth
// */
//public class Desktop {
//    private ObjectInputStream iStream;
//    private ObjectOutputStream oStream;
//    private String serverHostName;
//    private int serverPortNumber;
//    private SSLSocket socket;
//    private SSLSocketFactory sslSocketFactory;
//    
//    public Desktop(String serverHostName, int serverPortNumber, SSLSocket socket) throws IOException
//    {
//        this.serverHostName = serverHostName;
//        this.serverPortNumber = serverPortNumber;
//        this.socket = socket;
//        
//        System.out.println("Start of desktop");
//        System.out.println("Attempting to connect to server ...");
//        SSLSession session  = socket.getSession();
//        
//        System.setProperty("javax.net.ssl.trustStore", "ca.store");
//        
//        oStream = new ObjectOutputStream(socket.getOutputStream());
//        iStream = new ObjectInputStream(socket.getInputStream());
//        
//        socket.getUseClientMode();
//        
//        sslSocketFactory.createSocket(socket, iStream, true);
//    }
//    
//    public static void main(String[] args) throws IOException
//    {
//        
//    }
//}
import static com.sun.org.apache.bcel.internal.util.SecuritySupport.getResourceAsStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.naming.Context;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Created by BenBody on 11/05/2017.
 */
// Class which handles sending and receiving of data
public class TCPDesktop
{
    private SSLSocket socket;
    private Socket normalSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    // URL must be changed to the correct URL for the computer the server is running on
    private static final String SERVERIP = "192.168.1.48"; //These could potentially be read from file, or hard coded
    private static final int SERVERPORT = 1234; //or found in some other method
    private static final String KEYSTOREPASS = "capita123"; // same applies to this
    private boolean isSetUp; // whether the client has been successfully set up


    public boolean isSetUp()
    {
        return isSetUp;
    }

    public TCPDesktop()
    {
        //set up socket & streams
        try
        {
            // unclear whether this section is required or indeed works
            // Loads in the keystore from the resources
            System.out.println("starting setup");
            Security.addProvider(new BouncyCastleProvider());
            InputStream caInput = getResourceAsStream("keystore.bks");
            KeyStore keyStore = KeyStore.getInstance("BKS");
            keyStore.load(caInput, KEYSTOREPASS.toCharArray());
            // creates a TrustManagerFactory and adds the keystore
            TrustManagerFactory trustManagerFactory
                    = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            // creates an SSLContext and adds the TrustManager
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
            socket.close();
            isSetUp = true;
        } 
        catch (SocketException se)
        {
            System.out.println("Client has been closed due to server terminating connection");
        }
        catch (Exception e)
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

