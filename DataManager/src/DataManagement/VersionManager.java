package DataManagement;

public class VersionManager
{
	private static int versionNum = 0;
	
	public static int getVersion ()
	{
		return versionNum;
	}
	
	public static void updateVersion ()
	{
		versionNum++;
	}
}
