package Tests;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestInterface
{
	
	private static JFrame	frame;
	
	private VersionTester	ver;
	private RequestTester	req;
	
	/**
	 * Launch the application.
	 */
	public static void main ( String[] args )
	{
		EventQueue.invokeLater( new Runnable()
		{
			@SuppressWarnings ( "static-access" )
			public void run ()
			{
				try
				{
					TestInterface window = new TestInterface();
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
	 * Create the application.
	 */
	public TestInterface()
	{
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings ( { "unchecked", "rawtypes", "serial" } )
	private void initialize ()
	{
		ver = new VersionTester();
		req = new RequestTester();
		
		frame = new JFrame();
		frame.setResizable( false );
		frame.setBounds( 100, 100, 175, 170 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		JList list = new JList();
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
		list.setModel( new AbstractListModel()
		{
			String[] values = new String[] { "Version Manager", "Request Manager" };
			
			public int getSize ()
			{
				return values.length;
			}
			
			public Object getElementAt ( int index )
			{
				return values[index];
			}
		} );
		frame.getContentPane().add( list, BorderLayout.CENTER );
		
		JButton btnConf = new JButton( "Confirm" );
		btnConf.addMouseListener( new MouseAdapter()
		{
			@Override
			public void mouseReleased ( MouseEvent arg0 )
			{
				switch ( (String) (Object) list.getSelectedValue() )
				{
					case "Version Manager":
						ver.run();
						break;
					case "Request Manager":
						break;
					default:
						break;
				}
			}
		} );
		frame.getContentPane().add( btnConf, BorderLayout.SOUTH );
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation( dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2 );
	}
	
	public static JFrame getFrame ()
	{
		return frame;
	}
}
