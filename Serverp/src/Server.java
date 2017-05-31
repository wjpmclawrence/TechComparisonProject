import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;

/*
 * @author Yasiru Dahanayake
 */
public class Server
{
	private static ArrayList<ServerThread> clients;
	private static ServerSocket sS;
	private static final int PORT = 1234;
	private static boolean serverRunning = true;
	private static ObjectOutputStream oos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ServerGUI gui = new ServerGUI();

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		while (serverRunning)
		{
			SetUpConnections();
		}

	}

	/**
	 * Create the application.
	 */
	public Server()
	{

	}

	/*
	 * Uses self signed certificate "ca.store" to authenticate a handshake
	 * Server starts up and listens for connections, if an instance of the
	 * server is already running notify and close the current instance.
	 */
	private static void SetUpConnections()
	{

		try
		{
			// using a self singed certificate
			// password is capita123
			// String trustStore =
			// Server.class.getResource("Resources").getPath();

			KeyStore ks = KeyStore.getInstance("BKS");
		    InputStream ksIs = new FileInputStream("/capitastore.bks");
		    try {
		        ks.load(ksIs, "capita123".toCharArray());
		    } finally {
		        if (ksIs != null) {
		            ksIs.close();
		        }
		    }

		    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
		            .getDefaultAlgorithm());
		    kmf.init(ks, "capita123".toCharArray());
		    
		 // creates an SSLContext and adds the KeyManager & TrustManager
	           SSLContext sslctx = SSLContext.getInstance("TLS");
	           sslctx.init(kmf.getKeyManagers(), null, null);
	           // uses the SSLContext to initialise the SSLSocketFactory
	        ServerSocketFactory factory = sslctx.getServerSocketFactory();
		    
			// System.setProperty("javax.net.ssl.keyStore",Server.class.getResourceAsStream("/ca.store"));
			//System.setProperty("javax.net.ssl.keyStore", "ca.store");
			//System.setProperty("javax.net.ssl.keyStorePassword", "capita123");
			//ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
			sS = factory.createServerSocket(PORT);
			ServerGUI.getTextArea().append("Server running and listening for connections... \n");
			while (true)
			{

				Socket socket = sS.accept();
				ServerThread rc = new ServerThread(socket);
				// clients.add(rc);
				Thread tr = new Thread(rc);
				tr.start();
				ServerGUI.getTextArea().append("DEBUG: Client Connected \n");
			}

		} catch (BindException e)
		{
			JOptionPane.showMessageDialog(ServerGUI.getsInterface(), "instance of a server is " + "already running");
			System.exit(0);
		} catch (Exception e)
		{
			//ServerGUI.getTextArea().append(e.getMessage() + "\n");
			e.printStackTrace();

		}
	}

	/*
	 * Writes an ArrayList of objects to the client through socket
	 */
	private static void writeToClient(List<Object> list, Socket socket) throws IOException
	{

		oos = new ObjectOutputStream(socket.getOutputStream());
		ServerGUI.getTextArea().append("DEBUG: Objects sent to client \n");
		oos.writeObject(list);
	}

	/*
	 * CLoses the client socket (removes connection) removes this thread from
	 * server thread.
	 */
	private static void closeConnection(Socket socket, ServerThread thread)
	{
		try
		{
			socket.close();
			clients.remove(thread);
			ServerGUI.getTextArea().append("Client Disconnected, thread removed \n ");
		} catch (NullPointerException E)
		{

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * reads in a string from the client
	 */
	private static String readStringFromClient(Socket socket) throws IOException
	{
		String message;

		BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		return message = fromClient.readLine();
	}

	/*
	 * Runnable class to handle instances of clients that are connected test
	 * method handle requests to for testing with dummy client.
	 * 
	 */
	private static class ServerThread implements Runnable, Serializable
	{
		Socket socket;
		String frmClient;

		ServerThread(Socket socket)
		{

			this.socket = socket;

		}

		@Override
		public void run()
		{
			try
			{
				
				while (true)
				{

					frmClient = readStringFromClient(this.socket);
					ServerGUI.getTextArea().append(frmClient + "\n");
					// is message is null then break out the loop 
					if (frmClient == null)
					{
						break;
					}

				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
