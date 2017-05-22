package Tests;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class IsKeyPressed
{
	private static volatile boolean escPressed = false;
	
	public static boolean isEscPressed ()
	{
		synchronized ( IsKeyPressed.class )
		{
			return escPressed;
		}
	}
	
	public static void initialise ()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher( new KeyEventDispatcher()
		{
			
			@Override
			public boolean dispatchKeyEvent ( KeyEvent ke )
			{
				synchronized ( IsKeyPressed.class )
				{
					switch ( ke.getID() )
					{
						case KeyEvent.KEY_PRESSED:
							if ( ke.getKeyCode() == KeyEvent.VK_ESCAPE)
							{
								escPressed = true;
							}
							break;
						
						case KeyEvent.KEY_RELEASED:
							if ( ke.getKeyCode() == KeyEvent.VK_W )
							{
								escPressed = false;
							}
							break;
					}
					return false;
				}
			}
		} );
	}
}