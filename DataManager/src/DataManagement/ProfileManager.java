package DataManagement;

import java.util.ArrayList;
import java.util.List;

import Utils.NotImplemented;

public class ProfileManager
{
	public static List<Object> add( String in ) throws NotImplemented
	{
		String[] received = in.split( ";;" );
		String[][] detagged = new String[received.length][];
		List<List<String>> out = new ArrayList<List<String>>();
		
		for (int i = 0; i < received.length; i++)
		{
			detagged[i] = received[i].split( "_" );
			out.add( new ArrayList<String>() );
			out.get( i ).add( detagged[i][0] );
			String[] tmp = detagged[i][1].split( "," );
			for ( int j = 0; j < tmp.length; j++ )
			{
				out.get( i ).add( tmp[j] );
			}
		}
		
		
		System.out.println( "Received:" );
		
		for ( int i = 0; i < out.size(); i++ )
		{
			for ( int j = 0; j < out.get( i ).size(); j++ )
			{
				System.out.println( out.get( i ).get( j ) );
			}
			System.out.println(  );
		}
		
		throw new NotImplemented();
	}
	
	public static List<Object> remove ( String in ) throws NotImplemented
	{
		String[] received = in.split( "_" );
		System.out.println( "Received: " + received[0] + " " + received[1] );
		throw new NotImplemented();
	}
	
	public static List<Object> check ( String in ) throws NotImplemented
	{
		throw new NotImplemented();
	}
}
