package Tests;

import java.util.List;

import javax.swing.JFrame;

import DataManagement.RequestManager;

public class RequestTester
{
	JFrame frame;
	
	public static void main ( String[] args0 )
	{
		sendReq();
	}
	
	public static void sendReq ()
	{
		System.out.println( RequestManager.requestMade( "version~0" ) );
		List<Object> list = RequestManager.requestMade( "request~Java" );
		
		for ( Object i : list )
		{
			System.out.println( i.toString() );
		}
	}
	
	public void sendReq ( String lang )
	{
		System.out.println( RequestManager.requestMade( "request~" + lang ) );
	}
}
