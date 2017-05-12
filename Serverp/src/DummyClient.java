import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/*
 * @author Yasiru Dahanayake
 */
public class DummyClient
{

	private static JFrame frame;
	private static Socket socket;
	private static BufferedReader fromServer;
	private static JTextArea textArea;

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
	 * 
	 */
	private static void ConnectToServer()
	{
		try
		{
			SocketFactory factory = SSLSocketFactory.getDefault();
			socket = factory.createSocket("127.0.0.1", 1234);
			ReadFromServer();

		} catch (Exception e)
		{
			e.printStackTrace();

			JOptionPane.showMessageDialog(frame, "Cannot establish " + "connection to server, cliet will now exit ");
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

	/*
	// while the stream is open print out whatever
	s// is coming from the server
	 */
	private static void ReadFromServer()
	{
		String message = null;
		try
		{
			
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((message = fromServer.readLine()) != null)
			{
				System.out.println("DEBUG 1");
				System.out.println(message);
				textArea.setText(message);
				
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textArea = new JTextArea();
		textArea.setBounds(50, 24, 350, 205);
		frame.getContentPane().add(textArea);
	}
}
