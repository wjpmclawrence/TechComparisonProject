package Utils;

public class ErrorHandler
{
	private static String[] errorList = new String[]
			{
					"Request Not Recognised",
					"No Data Provided",
					"Provided Version Is Not An int",
					"Language Unavailable",
					"Profile Does Not Exist",
					"Failed To Create Profile"
			};
	
	public static String getError ( int code )
	{
		return errorList[code];
	}
}
