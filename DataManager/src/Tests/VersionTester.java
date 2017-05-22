package Tests;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JTextPane;

public class VersionTester implements Runnable
{
	JFrame		frame;
	
	Component[]	originalComps;
	Rectangle	origBounds;
	
	public void checkVer ( String lang )
	{
		
	}
	
	private void storeOriginals ()
	{
		origBounds = frame.getBounds();
		originalComps = frame.getComponents();
		
		for ( Component comp : originalComps )
		{
			comp.setVisible( false );
		}
		
		frame.setVisible( true );
	}
	
	private void restoreOriginals ()
	{
		for ( Component comp : originalComps )
		{
			comp.setVisible( true );
		}
		
		frame.setBounds( origBounds );
	}
	
	@Override
	public void run ()
	{
		IsKeyPressed.initialise();
		
		boolean end = false;
		
		frame = TestInterface.getFrame();
		storeOriginals();
		
		frame.setBounds( 100, 100, 450, 300 );
		
		JTextPane txtPane = new JTextPane();
		txtPane.setEditable( false );
		frame.getContentPane().add( txtPane, BorderLayout.CENTER );
		
		/*while ( !end )
		{
			System.out.println( "Made It!" );
			if ( IsKeyPressed.isEscPressed() )
			{
				end = true;
			}
		}*/
		
		restoreOriginals();
		txtPane.setVisible( false );
	}
}
