package DataManagement;

import java.util.ArrayList;
import java.util.List;

import Utils.Language;

public class LanguageManager
{
	static List<Language> langList;
	
	public static List<Language> setupLanguages ( String[][] incoming )
	{
		langList = new ArrayList<Language>();
		
		for ( int i = 0; i < incoming.length; i++ )
		{
			int similarity = Integer.parseInt( incoming[i][1] );
			langList.add( new Language( incoming[i][0], similarity, calcWeeks( similarity ) ) );
		}
		
		return langList;
	}
	
	private static String calcWeeks ( int similarity )
	{
		if ( similarity > 75 )
		{
			return "1 Week";
		}
		else if ( similarity > 70 )
		{
			return "1-2 Weeks";
		}
		else if ( similarity > 60 )
		{
			return "2-3 Weeks";
		}
		else if ( similarity > 50 )
		{
			return "3-4 Weeks";
		}
		else if ( similarity > 40 )
		{
			return "4-5 Weeks";
		}
		else if ( similarity > 30 )
		{
			return "5-6 Weeks";
		}
		else if ( similarity > 20 )
		{
			return "6-7 Weeks";
		}
		else if ( similarity > 10 )
		{
			return "7-8 Weeks";
		}

		return "Over 2 Months";
	}
}
