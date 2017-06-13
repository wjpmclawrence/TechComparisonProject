package ClientInterface;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.JOptionPane;

import DataManagement.RequestManager;

/**
 * @author Yasiru Dahanayake
 * @author Nathan Steer
 */
public class Server
{
	private static String				log				= "client_log.dat";
	private static SimpleDateFormat		time;
	
	private static ServerSocket			sS;
	private static final int			PORT			= 1234;
	private static boolean				serverRunning	= true;
	private static ObjectOutputStream	oos;
	
	/**
	 * Launch the application.
	 */
	public static void main ( String[] args )
	{
		EventQueue.invokeLater( new Runnable()
		{
			@SuppressWarnings ( "unused" )
			public void run ()
			{
				try
				{
					ServerGUI gui = new ServerGUI();
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		} );
		
		SetUpConnections();
	}
	
	/**
	 * Create the application.
	 */
	public Server()
	{
		
	}
	
	/**
	 * Uses self signed certificate "ca.store" to authenticate a handshake
	 * Server starts up and listens for connections, if an instance of the
	 * server is already running notify and close the current instance.
	 */
	private static void SetUpConnections ()
	{
		try
		{
			// using a self singed certificate
			// password is capita123
			// String trustStore =
			// Server.class.getResource("Resources").getPath();
			
			System.setProperty( "javax.net.ssl.keyStore", "keystore.jks" );
			System.setProperty( "javax.net.ssl.keyStorePassword", "capita123" );
			ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
			sS = factory.createServerSocket( PORT );
			
			time = new SimpleDateFormat( "dd/MM/yy HH:mm:ss" );
			
			ServerGUI.getTextArea().append( "Server running and listening for connections...\n" );
			while ( serverRunning )
			{
				Socket socket = sS.accept();
				ServerThread rc = new ServerThread( socket );
				Thread tr = new Thread( rc );
				tr.start();
				ServerGUI.getTextArea()
						.append( timeStamp() + " Client at " + socket.getInetAddress() + " Connected\n" );
				log( timeStamp() + " Client at " + socket.getInetAddress() + " Connected\n" );
			}
		}
		catch ( BindException e )
		{
			JOptionPane.showMessageDialog( ServerGUI.getsInterface(), "Instance of a server is already running" );
			System.exit( 0 );
		}
		catch ( Exception e )
		{
			ServerGUI.getTextArea().append( e.getMessage() + "\n" );
			e.printStackTrace();
		}
	}
	
	private static String timeStamp ()
	{
		return time.format( new Date() );
	}
	
	/**
	 * Writes supplied message to log
	 */
	private static void log ( String msg )
	{
		try
		{
			BufferedWriter writer = new BufferedWriter( new FileWriter( log, true ) );
			writer.newLine();
			writer.write( msg );
			writer.close();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a List of objects to the client through socket
	 */
	private static void writeToClient ( List<Object> list, Socket socket ) throws IOException
	{
		oos = new ObjectOutputStream( socket.getOutputStream() );
		ServerGUI.getTextArea()
				.append( timeStamp() + " " + list + " sent to client " + socket.getInetAddress() + "\n" );
		log( timeStamp() + " " + list + " sent to client " + socket.getInetAddress() + "\n" );
		oos.writeObject( list );
	}
	
	/**
	 * reads in a string from the client
	 */
	private static String readStringFromClient ( Socket socket ) throws IOException
	{
		BufferedReader fromClient = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		
		return fromClient.readLine();
	}
	
	/**
	 * Runnable class to handle instances of clients that are connected test
	 * method handle requests to for testing with dummy client.
	 * 
	 * @author Yasiru Dahanayake
	 * @author Nathan Steer
	 */
	private static class ServerThread implements Runnable
	{
		Socket	socket;
		String	frmClient;
		
		ServerThread( Socket socket )
		{
			this.socket = socket;
		}
		
		@Override
		public void run ()
		{
			try
			{
				frmClient = readStringFromClient( this.socket );
				ServerGUI.getTextArea().append( timeStamp() + " " + frmClient + "\n" );
				log( timeStamp() + " " + frmClient + "\n" );
				writeToClient( RequestManager.requestMade( frmClient ), socket );
				socket.close();
				ServerGUI.getTextArea().append(
						timeStamp() + " Client " + socket.getInetAddress() + " Disconnected, thread removed\n" );
				log( timeStamp() + " Client " + socket.getInetAddress() + " Disconnected, thread removed\n" );
			}
			catch ( IOException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
