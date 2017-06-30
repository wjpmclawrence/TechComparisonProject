package DataManagement;

import java.util.ArrayList;
import java.util.List;

import Utils.NotImplemented;

public class ProfileManager
{
	/**
	 * Organises received data to create a Novus profile in the database.
	 * <p>
	 * Splits the string received from the client into the tag, name, languages, and technologies.
	 * The tag for each section is then dropped and the data is sent to the database interface to be inserted into the database.
	 * 
	 * @param in
	 *            The request that was received from the client
	 * @return Returns an object list that will only contain a single element; either "added"; or error code 5
	 * @throws NotImplemented
	 *             For now, this is not fully implemented, so will throw the NotImplemented exception
	 */
	public static List<Object> add ( String in ) throws NotImplemented
	{
		String[] received = in.split( ";;" );
		String[][] detagged = new String[received.length][];
		List<List<String>> out = new ArrayList<List<String>>();
		
		for ( int i = 0; i < received.length; i++ )
		{
			detagged[i] = received[i].split( "_" );
			out.add( new ArrayList<String>() );
			out.get( i ).add( detagged[i][0] );
			String[] tmp = detagged[i][1].split( "," );
			String name = detagged[i][0];
			detagged[i] = new String[tmp.length + 1];
			detagged[i][0] = name;
			
			for ( int j = 0; j < tmp.length; j++ )
			{
				detagged[i][j + 1] = tmp[i];
				out.get( i ).add( tmp[j] );
			}
		}
		
		System.out.println( "Received:" );
		System.out.println( detagged );
		
		for ( int i = 0; i < out.size(); i++ )
		{
			for ( int j = 0; j < out.get( i ).size(); j++ )
			{
				System.out.println( out.get( i ).get( j ) );
			}
			System.out.println();
		}
		
		throw new NotImplemented();
	}
	
	/**
	 * Requests that the Novus Profile (requested by Novus name) be removed from the database.
	 * <p>
	 * Splits the string received from the client into the tag and name. The name is then sent to the
	 * database interface where it is used to remove the profile.
	 * 
	 * @param in
	 *            The request that was received from the client
	 * @return Returns an object list that will only contain a single element; either "removed"; or error code 4
	 * @throws NotImplemented
	 *             For now, this is not fully implemented, so will throw the NotImplemented exception
	 */
	public static List<Object> remove ( String in ) throws NotImplemented
	{
		String[] received = in.split( "_" );
		System.out.println( "Received: " + received[0] + " " + received[1] );
		throw new NotImplemented();
	}
	
	/**
	 * Checks if any of the stored Novus Profiles match given job requirements
	 * <p>
	 * Splits the string received from the client into the tag, languages, and technologies.
	 * All Novus Profiles are then requested and all languages and technologies they know are compared
	 * to the job requirements in order to create a list of compatible Novus. This list is then returned.
	 * 
	 * @param in
	 *            The request that was received from the client
	 * @return An object list containing the Novus Profiles that match the job requirement
	 * @throws NotImplemented
	 *             For now, this is not fully implemented, so will throw the NotImplemented exception
	 */
	public static List<Object> check ( String in ) throws NotImplemented
	{
		throw new NotImplemented();
	}
}
