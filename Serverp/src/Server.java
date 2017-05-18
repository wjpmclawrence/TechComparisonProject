import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

/*
 * @author Yasiru Dahanayake
 */
public class Server
{
	private static ArrayList<ServerThread> clients;
	private static ServerSocket sS;
	private static final int PORT = 1234;
	private static JFrame frame;
	private static boolean serverRunning = true;
	private static JTextArea textArea;
	private static ObjectOutputStream oos;
	private static PrintWriter pw;

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
					Server window = new Server();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		SetUpConnections();

	}

	/**
	 * Create the application.
	 */
	public Server()
	{
		initialize();

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
			System.setProperty("javax.net.ssl.keyStore", "ca.store");
			System.setProperty("javax.net.ssl.keyStorePassword", "capita123");
			ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
			sS = factory.createServerSocket(PORT);

			while (serverRunning)
			{

				Socket socket = sS.accept();
				ServerThread rc = new ServerThread(socket);
				// clients.add(rc);
				Thread tr = new Thread(rc);
				tr.start();
				textArea.append("DEBUG: Client Connected \n");
			}

		} catch (BindException e)
		{
			JOptionPane.showMessageDialog(frame, "instance of a server is " + "already running");
			System.exit(0);
		} catch (Exception e)
		{

			e.printStackTrace();

		}
	}

	/*
	 * Writes a String to a client through socket socket
	 */
	private static void writeToCLient(String string, Socket socket) throws IOException
	{

		pw = new PrintWriter(socket.getOutputStream(), true);
		pw.println(string);

	}

	/*
	 * Writes an ArrayList of objects to the client through socket
	 */
	private static void writeToClient(List<Object> list, Socket socket) throws IOException
	{

		oos = new ObjectOutputStream(socket.getOutputStream());
		textArea.append("DEBUG: Objects sent to client \n");
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
			textArea.append("Client Disconnected \n ");
			clients.remove(thread);
		} catch (NullPointerException E)
		{
			textArea.append("thread removed\n ");
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

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		textArea = new JTextArea();
		textArea.setBounds(35, 27, 381, 207);
		frame.getContentPane().add(textArea);

		/*
		 * resume server button
		 */

	}
}
