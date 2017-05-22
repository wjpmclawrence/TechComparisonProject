package Tests;

import java.util.Random;

public class DBInterface
{
	private String[][] array = new String[6][];
	
	public DBInterface()
	{
		for ( int i = 0; i < 6; i++ )
		{
			array[i] = new String[2];
		}
		
		array[0][0] = "Java";
		array[1][0] = "C#";
		array[2][0] = "JavaScript";
		array[3][0] = "Python";
		array[4][0] = "HTML";
		array[5][0] = "C++";
	}
	
	public String[] request ()
	{
		String[] returnArray = new String[6];
		
		for ( int i = 0; i < 6; i++ )
		{
			returnArray[i] = array[i][0];
		}
		
		return returnArray;
	}
	
	public String[][] request ( String lang )
	{
		Random rand = new Random();
		
		for ( int i = 0; i < 6; i++ )
		{
			array[i][1] = Integer.toString( rand.nextInt( 80 ) + 0 );
		}
		
		return array;
	}
}
