import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;





public class Server
{

	private static ArrayList<ServerThread> clients;
	private static ServerSocket sS;
	private static final int PORT = 1234;
	
	
	
	
	public static void main(String[] args)
	{
		SetUpConnections();
	
	}
	
	
	/*
	 * Listens to any connections,
	 * if a Connection is made then adds it to to the list of connections 
	 */
	private static void SetUpConnections()
	{
		try
		{
			sS= new ServerSocket(PORT);
			while (true)
			{
				
				System.out.println("Debug : server is running");
				Socket socket = sS.accept();
				ServerThread rc = new ServerThread(socket);
				clients.add(rc);
				Thread tr = new Thread(rc);
				tr.start();
				
				
				
				
				;

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * creates a new th
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
			
			
		}

	}

	
}
