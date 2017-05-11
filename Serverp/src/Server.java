import java.awt.EventQueue;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Server
{
	private static ArrayList<ServerThread> clients;
	private static ServerSocket sS;
	private static final int PORT = 1234;
	private static JFrame frame;
	private static boolean serverRunning = true;
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
					Server window = new Server();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		SetUpConnections();

	}

	/**
	 * Create the application.
	 */
	public Server()
	{
		initialize();
	}

	
	/*
	 * Server starts up and listens for connections, if the server is running
	 * then auto close
	 */
	private static void SetUpConnections()
	{
		System.out.println("Debug: server is running, waiting for connections");
		try
		{
			sS = new ServerSocket(PORT);
			while (serverRunning)
			{
				System.out.println("client has connected to server");
				Socket socket = sS.accept();
				ServerThread rc = new ServerThread(socket);
				// clients.add(rc);
				Thread tr = new Thread(rc);
				tr.start();
			}

		} catch (BindException e)
		{
			JOptionPane.showMessageDialog(frame, "instance of a server is already running");
			System.exit(0);
		} catch (Exception e)
		{

			e.printStackTrace();

		}
	}

	/*
	 * Runnable class to handle instances of clients that are connected
	 */
	private static class ServerThread implements Runnable
	{
		Socket socket;

		ServerThread(Socket socket)
		{
			this.socket = socket;
		}

		@Override
		public void run()
		{
			textArea.append("Debug : client connected to server \n");
		}

	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		textArea = new JTextArea();
		textArea.setBounds(35, 27, 381, 207);
		frame.getContentPane().add(textArea);
	}
}
