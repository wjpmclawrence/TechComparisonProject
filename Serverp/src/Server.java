import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.JScrollPane;

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
					GUI gui = new GUI();

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

			// System.setProperty("javax.net.ssl.keyStore",Server.class.getResourceAsStream("/ca.store"));
			System.setProperty("javax.net.ssl.keyStore", "ca.store");
			System.setProperty("javax.net.ssl.keyStorePassword", "capita123");
			ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
			sS = factory.createServerSocket(PORT);
			GUI.getTextArea().append("Server running and listening for connections... \n");
			while (true)
			{

				Socket socket = sS.accept();
				ServerThread rc = new ServerThread(socket);
				// clients.add(rc);
				Thread tr = new Thread(rc);
				tr.start();
				GUI.getTextArea().append("DEBUG: Client Connected \n");
			}

		} catch (BindException e)
		{
			JOptionPane.showMessageDialog(GUI.getsInterface(), "instance of a server is " + "already running");
			System.exit(0);
		} catch (Exception e)
		{
			GUI.getTextArea().append(e.getMessage() + "\n");
			e.printStackTrace();

		}
	}

	/*
	 * Writes an ArrayList of objects to the client through socket
	 */
	private static void writeToClient(List<Object> list, Socket socket) throws IOException
	{

		oos = new ObjectOutputStream(socket.getOutputStream());
		GUI.getTextArea().append("DEBUG: Objects sent to client \n");
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
			GUI.getTextArea().append("Client Disconnected, thread removed \n ");
		} catch (NullPointerException E)
		{

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Runnable class to handle instances of clients that are connected test
	 * method handle requests to for testing with dummy client.
	 * 
	 */
	private static class ServerThread implements Runnable, Serializable
	{
		Socket socket;

		ServerThread(Socket socket)
		{

			this.socket = socket;

		}

		@Override
		public void run()
		{

		}

	}

}
