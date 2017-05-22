package DataManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Utils.Language;

/**
 * 
 * @author Nathan Steer
 *
 */
public class RequestManager
{
	private static Tests.DBInterface database = null;
	
	/**
	 * Checks if the provided version number matches the servers version number
	 * <p>
	 * Compares the provided version number to the servers version number. If they
	 * match, the version_ok message is returned. If not, the current list of languages is obtained
	 * and returned.
	 * 
	 * @param clientVer
	 *            The clients version number, provided as part of the request
	 * @return Returns either a one element list with the OK message, or the current language list
	 */
	@SuppressWarnings ( { "unchecked", "rawtypes" } )
	private static List<Object> checkVersion ( int clientVer )
	{
		List<Object> list = new ArrayList();
		
		if ( clientVer == VersionManager.getVersion() )
		{
			list.add( "version_ok" );
		}
		else
		{
			list.addAll( Arrays.asList( database.request() ) );
			Collections.sort( (List<String>) (Object) list );
			
			list.add( 0, "menu_list" );
			list.add( 1, VersionManager.getVersion() );
		}
		
		return list;
	}
	
	/**
	 * Obtains a list of languages with their similarity to the provided language
	 * <p>
	 * Checks that that provided language exists in the database. If it does, the
	 * list of languages is obtained from the database with their similarity to
	 * the provided language, and put into a list.
	 * <p>
	 * This list is then sorted by similarity in descending order and returned. If
	 * the language is not present in the database, an error message is returned.
	 * 
	 * @param langName
	 *            The provided language, to which the other languages should be compared
	 * @return Returns a list of Languages, sorted in descending order by similarity
	 */
	@SuppressWarnings ( { "unchecked", "rawtypes" } )
	private static List<Object> getSubMenu ( String langName )
	{
		boolean langAvail = false;
		String[] tmp = database.request();
		
		for ( String i : tmp )
		{
			if ( i.equalsIgnoreCase( langName ) )
			{
				langAvail = true;
				break;
			}
		}
		
		List<Object> returnList = new ArrayList();
		
		if ( langAvail )
		{
			String[][] array = database.request( langName );
			
			for ( int i = 0; i < array.length; i++ )
			{
				if ( !array[i][0].equals( langName ) )
				{
					returnList.add( new Language( array[i][0], Integer.parseInt( array[i][1] ) ) );
				}
			}
			
			Collections.sort( (List<Language>) (Object) returnList );
			returnList.add( 0, "sub_menu_list" );
		}
		else
		{
			returnList.add( "Language Unavailable" );
		}
		
		return returnList;
	}
	
	/**
	 * Returns the appropriate response to be sent to the client for the request
	 * received.
	 * <p>
	 * This method receives requests from the client interface and calls the
	 * appropriate local methods in order to create the desired response. Once
	 * an Object List has been built, containing both the response tag and any
	 * appropriate data, it is returned to the client interface so it can be
	 * sent to the client.
	 * 
	 * @param request
	 *            The string that was received from the client
	 * @return A list containing both the tag and any necessary data for the client
	 * 
	 */
	@SuppressWarnings ( "unchecked" )
	public static List<Object> requestMade ( String request )
	{
		List<Object> returnList = new ArrayList();
		
		if ( request != null && request.contains( "~" ) )
		{
			String[] tmp = request.split( "~" );
			
			if ( tmp.length > 1 )
			{
				if ( database == null )
				{
					database = new Tests.DBInterface();
				}
				
				try
				{
					switch ( tmp[0].toLowerCase() )
					{
						case "version":
							returnList = checkVersion( Integer.parseInt( tmp[1] ) );
							break;
						
						case "request":
							returnList = getSubMenu( tmp[1] );
							break;
					}
				}
				catch ( NumberFormatException e )
				{
					returnList.add( "Provided Version Is Not An int" );
				}
				catch ( UnsupportedOperationException e )
				{
					throw e;
				}
			}
			else
			{
				returnList.add( "No Data Provided" );
			}
		}
		else
		{
			returnList.add( "Request Not Recognised" );
		}
		
		return returnList;
	}
}
