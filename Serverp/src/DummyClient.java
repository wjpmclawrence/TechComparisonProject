import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/*
 * @author Yasiru Dahanayake
 */
public class DummyClient implements Serializable
{

	private static JFrame frame;
	private static Socket socket;
	private static BufferedReader fromServer;
	private static JTextArea textArea;
	private static ObjectInputStream is;
	private static ArrayList<Object> ts;

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

		connectToServer();

	}

	/*
	 * Uses self signed certificate "ca.store" to authenticate a handshake with
	 * the server. if server not present closes automatically. once connected
	 * either read objects or strings from the socket Input stream
	 * 
	 */
	private static void connectToServer()
	{
		try
		{
			// using a self singed certificate
			System.setProperty("javax.net.ssl.trustStore", "ca.store");
			SocketFactory factory = SSLSocketFactory.getDefault();
			socket = factory.createSocket("127.0.0.1", 1234);
			// ReadStringFromServer();
			readObjectsFromServer();

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
	 * while there is data coming from stream, printline whatever strings that
	 * are coming through
	 */
	private static void readStringsFromServer()
	{
		String response = null;
		try
		{

			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((response = fromServer.readLine()) != null)
			{
				System.out.println("DEBUG 1");
				System.out.println(response);
				textArea.setText(response);

			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * method reads Object/ objects from the socket input stream write each
	 * object to the global ArrayList, displays each object in the ArrayList as
	 * a debug on console.
	 */
	private static void readObjectsFromServer()
	{
		boolean isAvailable = true;
		ts = new ArrayList<Object>();
		Object obj;

		try
		{
			is = new ObjectInputStream(socket.getInputStream());
			while (isAvailable != false)
			{
				obj = (Object) is.readObject();
				if (obj != null)
				{
					ts.add(obj);
				} else
				{
					isAvailable = false;
				}

				for (Object o : ts)
				{

					textArea.append("DEBUG: Objects recieved:  " + o + "\n");
				}
			}
		} catch (IOException | ClassNotFoundException e)
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
