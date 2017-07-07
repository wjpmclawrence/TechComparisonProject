package ClientInterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerGUI
{
	// General Frame Components
	private static JFrame		sInterface;
	private static JScrollPane	scrollPane;
	private static JTextArea	textArea;
	
	// Buttons
	private static JButton		btnClose;
	private static JButton		btnPause;
	private static JButton		btnModDB;
	
	// Labels
	private static JLabel		lblClients;
	
	// Basic Variables
	private static int			clientsConnected	= 0;
	private static int			btnHeight			= 50;
	private static int			btnWidth			= 250;
	
	public static JScrollPane getScrollPane ()
	{
		return scrollPane;
	}
	
	public static JTextArea getTextArea ()
	{
		return textArea;
	}
	
	public static JFrame getsInterface ()
	{
		return sInterface;
	}
	
	public static void addClient ()
	{
		clientsConnected++;
		updateClientsLbl();
	}
	
	public static void removeClient ()
	{
		clientsConnected--;
		updateClientsLbl();
	}
	
	/**
	 * Ensure that the scroll bar is set to the bottom of the chat display area
	 */
	public static void checkScrollBar ()
	{
		JScrollBar bar = scrollPane.getVerticalScrollBar();
		bar.setValue( bar.getMaximum() );
	}
	
	private static void updateClientsLbl ()
	{
		String lblText = String.valueOf( clientsConnected + " " );
		
		if ( clientsConnected != 1 )
		{
			lblText += "Clients Connected";
		}
		else
		{
			lblText += "Client Connected";
		}
		
		lblClients.setText( lblText );
	}
	
	private static void updatePauseBtn ( boolean conAccepted )
	{
		if ( conAccepted )
		{
			btnPause.setText( "Accepting Connections" );
			btnPause.setBackground( new Color( 88, 188, 90 ) );
			btnPause.setForeground( Color.BLACK );
		}
		else
		{
			btnPause.setText( "Not Accepting Connections" );
			btnPause.setBackground( new Color( 188, 3, 3 ) );
			btnPause.setForeground( Color.WHITE );
		}
	}
	
	private static void shutdown ()
	{
		Server.toggleConnections();
		
		if ( clientsConnected == 0 )
		{
			Server.log( "Server Shutdown" );
			System.exit( 0 );
		}
		else
		{
			try
			{
				Thread.sleep( 5000 );
				shutdown();
			}
			catch ( InterruptedException e )
			{
				Server.log( e.getMessage() );;
			}
		}
	}
	
	public ServerGUI()
	{
		initialize();
		sInterface.setVisible( true );
	}
	
	private void initialize ()
	{
		sInterface = new JFrame();
		sInterface.setBounds( 100, 100, 450, 300 );
		sInterface.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		sInterface.setLayout( null );
		sInterface.setExtendedState( JFrame.MAXIMIZED_BOTH );
		sInterface.setUndecorated( true );
		sInterface.setVisible( true );
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds( 0, 0, sInterface.getWidth(), sInterface.getHeight() - 70 );
		sInterface.getContentPane().add( scrollPane );
		textArea = new JTextArea();
		textArea.setEditable( false );
		scrollPane.setViewportView( textArea );
		
		lblClients = new JLabel();
		lblClients.setBounds( 10, sInterface.getHeight() - ( 10 + btnHeight ), 200, 50 );
		updateClientsLbl();
		sInterface.getContentPane().add( lblClients );
		
		btnClose = new JButton();
		btnClose.setBounds( ( sInterface.getWidth() - btnWidth ) / 2, sInterface.getHeight() - ( 10 + btnHeight ),
				btnWidth, btnHeight );
		btnClose.setText( "Shutdown" );
		btnClose.setBackground( Color.RED );
		btnClose.setForeground( Color.WHITE );
		btnClose.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				shutdown();
			}
		} );
		sInterface.getContentPane().add( btnClose );
		
		btnPause = new JButton();
		btnPause.setBounds( btnClose.getX() - ( 10 + btnWidth ), sInterface.getHeight() - ( 10 + btnHeight ), btnWidth,
				btnHeight );
		updatePauseBtn( true );
		btnPause.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				updatePauseBtn( Server.toggleConnections() );
			}
		} );
		sInterface.getContentPane().add( btnPause );
		
		btnModDB = new JButton();
		btnModDB.setBounds( btnClose.getX() + ( 10 + btnWidth ), sInterface.getHeight() - ( 10 + btnHeight ), btnWidth,
				btnHeight );
		btnModDB.setText( "Add a Language (NOT IMPLEMENTED)" );
		btnModDB.setBackground( new Color( 0, 161, 193 ) );
		btnModDB.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				
			}
		} );
		sInterface.getContentPane().add( btnModDB );
	}
}
