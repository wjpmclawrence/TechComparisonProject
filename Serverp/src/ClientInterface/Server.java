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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	private static String			log						= "client_log.dat";
	private static SimpleDateFormat	time;
	
	private static ServerSocket		sS;
	private static final int		PORT					= 1234;
	private static boolean			serverRunning			= true;
	private static boolean			acceptingConnections	= true;
	
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
	 * Toggles client connections (whether clients are accepted) on/off.
	 * 
	 * @return Either true or false, whichever is the case for client connections being accepted
	 */
	public static boolean toggleConnections ()
	{
		acceptingConnections = !acceptingConnections;
		log( "Currently Accepting Connections: " + String.valueOf( acceptingConnections ) );
		return acceptingConnections;
	}
	
	/**
	 * Obtains the persistent log stored in the clinet_log.dat file and displays it in the GUI.
	 */
	private static void dispLog ()
	{
		Path path = Paths.get( "client_log.dat" );
		
		if ( Files.exists( path ) )
		{
			try ( BufferedReader in = Files.newBufferedReader( path ) )
			{
				String logLine;
				
				while ( ( logLine = in.readLine() ) != null )
				{
					ServerGUI.getTextArea().append( logLine + System.lineSeparator() );
				}
			}
			catch ( IOException e )
			{
				System.out.println( e.getMessage() );
			}
		}
	}
	
	/**
	 * Authenticates client handshakes.
	 * <p>
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
			
			dispLog();
			log( "Server started and listening for connections..." );
			while ( serverRunning )
			{
				Socket socket = sS.accept();
				log( "Client " + socket.getInetAddress() + " Connected" );
				
				if ( acceptingConnections )
				{
					ServerThread rc = new ServerThread( socket );
					Thread tr = new Thread( rc );
					tr.start();
				}
				else
				{
					String message = "Server Currently Unavailable";
					List<Object> rtnList = new ArrayList<Object>();
					rtnList.add( message );
					ObjectOutputStream outStream = new ObjectOutputStream( socket.getOutputStream() );
					log( rtnList + " sent to client " + socket.getInetAddress() );
					outStream.writeObject( rtnList );
					socket.close();
					log( "Client " + socket.getInetAddress() + " Disconnected" );
				}
			}
		}
		catch ( BindException e )
		{
			JOptionPane.showMessageDialog( ServerGUI.getsInterface(), "Instance of a server is already running" );
			System.exit( 0 );
		}
		catch ( Exception e )
		{
			log( e.getMessage() );
			e.printStackTrace();
		}
	}
	
	/**
	 * Used to obtain the current time, correct to the second.
	 * @return	The current time.
	 */
	private static String timeStamp ()
	{
		return ( time.format( new Date() ) + " " );
	}
	
	/**
	 * Writes supplied message to log
	 */
	public static void log ( String msg )
	{
		try
		{
			String output = timeStamp() + msg + System.lineSeparator();
			
			BufferedWriter writer = new BufferedWriter( new FileWriter( log, true ) );
			writer.write( output );
			writer.close();
			
			ServerGUI.getTextArea().append( output );
			ServerGUI.checkScrollBar();
		}
		catch ( IOException e )
		{
			System.out.println( e.getMessage() );
		}
	}
	
	/**
	 * Runnable class to handle instances of clients that are connected test
	 * method handle requests to for testing with dummy client.
	 * 
	 * @author Yasiru Dahanayake
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
				ServerGUI.addClient();
				frmClient = readStringFromClient();
				log( frmClient );
				writeToClient( RequestManager.requestMade( frmClient ) );
				socket.close();
				log( "Client " + socket.getInetAddress() + " Disconnected, thread removed" );
				ServerGUI.removeClient();
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
		
		/**
		 * Writes a List of objects to the client through socket
		 */
		private void writeToClient ( List<Object> list ) throws IOException
		{
			ObjectOutputStream outStream = new ObjectOutputStream( socket.getOutputStream() );
			log( "sent to client " + socket.getInetAddress() + ":" + System.lineSeparator() + "	" + list );
			outStream.writeObject( list );
		}
		
		/**
		 * reads in a string from the client
		 */
		private String readStringFromClient () throws IOException
		{
			BufferedReader fromClient = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			
			return fromClient.readLine();
		}
	}
}
