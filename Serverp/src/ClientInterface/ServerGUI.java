package ClientInterface;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerGUI
{
	private static JFrame		sInterface;
	private static JScrollPane	scrollPane;
	private static JTextArea	textArea;
	
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
		sInterface.setTitle( "Server" );
		sInterface.setExtendedState( JFrame.MAXIMIZED_BOTH );
		sInterface.setUndecorated( true );
		sInterface.setVisible( true );
		scrollPane = new JScrollPane();
		scrollPane.setBounds( 0, 0, sInterface.getWidth(), sInterface.getHeight() );
		sInterface.getContentPane().add( scrollPane );
		textArea = new JTextArea();
		textArea.setEditable( false );
		scrollPane.setViewportView( textArea );
		
	}
}
