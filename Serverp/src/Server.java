import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.JButton;


public class Server
{
	private static ArrayList<ServerThread> clients;
	private static ServerSocket sS;
	private static final int PORT = 1234;
	private static JFrame frame;
	private static boolean serverRunning = true;
	private static JTextArea textArea;
	private JButton pauseServer; 
	private JButton resumeServer;

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
	 * Server starts up and listens for connections, if the server is 
	 * running then auto close 
	 */
	private static void SetUpConnections()
	{
		System.out.println("Debug: server is running, waiting for "
														+ "connections");
		try
		{
			
			ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
			sS = factory.createServerSocket(PORT);
			while (serverRunning)
			{
				Socket socket = sS.accept();
				ServerThread rc = new ServerThread(socket);
				// clients.add(rc);
				Thread tr = new Thread(rc);
				tr.start();
			}

		} catch (BindException e)
		{
			JOptionPane.showMessageDialog(frame, "instance of a server is "
														+ "already running");
			System.exit(0);
		} catch (Exception e)
		{

			e.printStackTrace();

		}
	}
	
	/*
	 * Write
	 */
	private void WriteToCLient(ArrayList<Object> list, Socket socket){
		
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(list);

		} catch (Exception E)
		{
			E.printStackTrace();
		}
	}
	
	
	/*
	 * Runnable class to handle instances of clients that are connected
	 */
	private static class ServerThread implements Runnable, Serializable
	{
		Socket socket;
		
		//test array list 
		
		

		ServerThread(Socket socket)
		{
			this.socket = socket;
		}

		@Override
		public void run()
		{
			textArea.append("Debug : client connected to server \n");
			
			CloseConnection();
		}
		
		/*
		 * CLoses the client socket (removes connection)
		 * removes this thread from server thread.
		 */
		private void CloseConnection(){
			try
			{
				socket.close();
				textArea.append("Client Disconnected \n ");
				clients.remove(this);
			} catch (NullPointerException E ){
				textArea.append("thread removed\n ");
			}catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
		
		
		/*
		 * resume server button 
		 */
	
	}
}
