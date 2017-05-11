package DataManagement;

public class VersionManager
{
	private int versionNum = 0;
	
	public int getVersion()
	{
		return versionNum;
	}
	
	public void updateVersion()
	{
		versionNum++;
	}
}
