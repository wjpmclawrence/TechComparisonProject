import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Yasiru Dahanayake
 * @author Nathan Steer
 */
public class DummyClient
{
	private static JFrame				frame;
	private static Socket				socket;
	private static BufferedReader		fromServer;
	private static JScrollPane			scrollPane;
	private static JTextArea			textArea;
	private static ObjectInputStream	is;
	private static List<Object>			ts;
	
	/**
	 * Launch the application.
	 */
	public static void main ( String[] args )
	{
		EventQueue.invokeLater( new Runnable()
		{
			public void run ()
			{
				try
				{
					DummyClient window = new DummyClient();
					window.frame.setVisible( true );
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		} );
	}
	
	/**
	 * Uses self signed certificate "ca.store" to authenticate a handshake with
	 * the server. if server not present closes automatically. once connected
	 * either read objects or strings from the socket Input stream
	 * 
	 */
	private static void connectToServer ()
	{
		try
		{
			// using a self singed certificate
			System.setProperty( "javax.net.ssl.trustStore", "ca.store" );
			SocketFactory factory = SSLSocketFactory.getDefault();
			socket = factory.createSocket( "127.0.0.1", 1234 );
			
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			
			JOptionPane.showMessageDialog( frame, "Cannot establish " + "connection to server, cliet will now exit " );
			System.exit( 0 );
			try
			{
				socket.close();
			}
			catch ( IOException e1 )
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	
	private static int disconnectFromServer ( boolean shuttingDown )
	{
		try
		{
			if ( !socket.isClosed() )
			{
				socket.close();
			}
			
			if ( shuttingDown )
			{
				System.exit( 0 );
			}
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * Create the application.
	 */
	public DummyClient()
	{
		initialize();
	}
	
	/**
	 * while there is data coming from stream, printline whatever strings that
	 * are coming through
	 */
	private static void readStringsFromServer ()
	{
		String response = null;
		try
		{
			
			fromServer = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			while ( ( response = fromServer.readLine() ) != null )
			{
				System.out.println( "DEBUG 1" );
				System.out.println( response );
				textArea.setText( response );
				
			}
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * method reads Object/ objects from the socket input stream write each
	 * object to the global ArrayList, displays each object in the ArrayList as
	 * a debug on console.
	 */
	@SuppressWarnings ( "unchecked" )
	private static void readObjectsFromServer ()
	{
		try
		{
			is = new ObjectInputStream( socket.getInputStream() );
			ts = (List<Object>) is.readObject();
			
			for ( Object o : ts )
			{
				textArea.append( o + "\n" );
				System.out.println( o );
			}
		}
		catch ( IOException | ClassNotFoundException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writemessage ( String message )
	{
		try
		{
			PrintWriter writer = new PrintWriter( socket.getOutputStream(), true );
			
			writer.println( message );
		}
		catch ( Exception E )
		{
			E.printStackTrace();
		}
		
	}
	
	private static void handleRequest ( String message )
	{
		connectToServer();
		writemessage( message );
		readObjectsFromServer();
		disconnectFromServer( false );
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize ()
	{
		frame = new JFrame();
		frame.setBounds( 100, 100, 450, 300 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.getContentPane().setLayout( null );
		frame.setResizable( false );
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds( 5, 5, 435, 220 );
		frame.getContentPane().add( scrollPane );
		
		textArea = new JTextArea();
		textArea.setEditable( false );
		scrollPane.setViewportView( textArea );
		
		JTextField txtVerInput = new JTextField();
		txtVerInput.setBounds( 5, 230, 20, 25 );
		frame.getContentPane().add( txtVerInput );
		
		JButton btnVerButt = new JButton( "Check Version" );
		btnVerButt.addActionListener( new ActionListener()
		{
			public void actionPerformed ( ActionEvent e )
			{
				handleRequest( "version~" + txtVerInput.getText() );
				txtVerInput.setText( "" );
			}
		} );
		btnVerButt.setBounds( 25, 230, 120, 25 );
		frame.getContentPane().add( btnVerButt );
		
		JTextField txtLangInput = new JTextField();
		txtLangInput.setBounds( 220, 230, 100, 25 );
		frame.getContentPane().add( txtLangInput );
		
		JButton btnLangButt = new JButton( "Req Language" );
		btnLangButt.addActionListener( new ActionListener()
		{
			public void actionPerformed ( ActionEvent e )
			{
				handleRequest( "request~" + txtLangInput.getText() );
				txtLangInput.setText( "" );
			}
		} );
		btnLangButt.setBounds( 320, 230, 120, 25 );
		frame.getContentPane().add( btnLangButt );
	}
}
