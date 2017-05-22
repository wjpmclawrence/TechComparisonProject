package DataManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
	
	@SuppressWarnings ( { "unchecked", "rawtypes" } )
	private static List<Object> getSubMenu ( String langName )
	{
		List<Object> returnList = new ArrayList();
		String[][] array = database.request( langName );
		
		for ( int i = 0; i < array.length; i++ )
		{
			returnList.add( new Language( array[i][0], Integer.parseInt( array[i][1] ) ) );
		}
		
		Collections.sort( (List<Language>) (Object) returnList/*,
				(Comparator<Language>) ( l1, l2 ) -> l1.compareTo( l2 )*/ );
		returnList.add( 0, "sub_menu_list" );
		
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
	 * <p>
	 * STILL IN DEVELOPMENT... Currently the method will throw not implemented
	 * exceptions when a sub menu is requested, or if the version number is out
	 * of date.
	 * 
	 * @param request	The string that was received from the client
	 * @return			A list containing both the tag and any necessary data for the client
	 * 
	 */
	@SuppressWarnings ( "unchecked" )
	public static List<Object> requestMade ( String request )
	{
		String[] tmp = request.split( "~" );
		List<Object> returnList = new ArrayList();
		
		if ( database == null )
		{
			database = new Tests.DBInterface();
		}
		
		try
		{
			switch ( tmp[0] )
			{
				case "version":
					returnList = checkVersion( Integer.parseInt( tmp[1] ) );
					break;
				
				case "request":
					returnList = getSubMenu( tmp[1] );
					break;
			}
		}
		catch ( UnsupportedOperationException e )
		{
			throw e;
		}
		
		return returnList;
	}
}
