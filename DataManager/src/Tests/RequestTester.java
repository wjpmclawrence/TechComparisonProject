package Tests;

import java.util.List;

import javax.swing.JFrame;

import DataManagement.RequestManager;

public class RequestTester
{
	JFrame frame;
	
	public static void main ( String[] args0 )
	{
		sendReq( "C++" );
		sendReq( "C#" );
		sendReq( "HTML" );
		sendReq( "Java" );
		sendReq( "JavaScript" );
		sendReq( "Python" );
	}
	
	public static void sendReq ()
	{
		System.out.println( RequestManager.requestMade( "version~0" ) );
		List<Object> list = RequestManager.requestMade( "request~Java" );
		
		for ( Object i : list )
		{
			System.out.println( i );
		}
	}
	
	public static void sendReq ( String lang )
	{
		List<Object> list = RequestManager.requestMade( "request~" + lang );
		
		for ( Object i : list )
		{
			System.out.println( i );
		}
		
		System.out.println();
	}
}
