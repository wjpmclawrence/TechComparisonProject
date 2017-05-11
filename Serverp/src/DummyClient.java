import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DummyClient
{

	private static JFrame frame;
	private static Socket socket;

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
					DummyClient window = new DummyClient();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		ConnectToServer();
		
	}

	/*
	 * connects to the server, if server not present closes automatically. 
	 */
	private static void ConnectToServer() {
		try
		{
			socket = new Socket("127.0.0.1", 1234);
			

		} catch (Exception e)
		{
			e.printStackTrace();

			JOptionPane.showMessageDialog(frame, "Cannot establish "
					+ "connection to server, cliet will now exit ");
			System.exit(0);
			try
			{
				socket.close();
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
	
	/**
	 * Create the application.
	 */
	public DummyClient()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
