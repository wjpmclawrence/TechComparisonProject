import java.awt.EventQueue;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println(list);

		} catch (Exception E)
		{
			E.printStackTrace();
		}
	}
	
	/*
	 * Pauses listening for connections
	 */
	private void PauseServer(){
		
		serverRunning = false;
		textArea.append("Debug : server has been paused connection \n");
		resumeServer.setVisible(true);
		pauseServer.setVisible(false);
	}
	
	/*
	 * Resumes listening to connections 
	 */
	private void ResumeServer(){
		
		serverRunning = true;
		resumeServer.setVisible(false);
		pauseServer.setVisible(true);
		textArea.append("Debug : connections resumed \n");
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
			
			
		}
		
		/*
		 * CLoses the Connections once the data is sent 
		 */
		private void CloseConnection(){
			
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
		pauseServer = new JButton("Pause");
		pauseServer.setBounds(309, 246, 117, 29);
		frame.getContentPane().add(pauseServer);
		resumeServer = new JButton("Resume");
		resumeServer.setBounds(309, 246, 117, 29);
		resumeServer.setVisible(false);
		frame.getContentPane().add(resumeServer);
		
		
		/*
		 * resume server button 
		 */
		resumeServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ResumeServer();
			}
		});
		
		/*
		 * pause server button
		 */
		pauseServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PauseServer();
				
			}
		});
	}
}
