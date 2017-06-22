//package DatabaseInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Sam Travers
 *
 */
public class DB_Interface {
	
	//DB CONNECTION VARIABLES
	static String db_connection = "jdbc:mysql://93.97.129.217:3306/languages?verifyServerCertificate=false&useSSL=true";		//languages/
	static String db_user = "root";
	static String db_password = "whatpassword?";
	static Connection myConn;
	static Statement myStmt;
	
	static String[][] subMenu;

	public static void main(String[] args) throws Exception
	{
		
	}
	
	
	/**
	 * Called to initialise the DB connection. First creates a connection to the DB using
	 * connection path, username and password. 
	 * Then uses the connection to create a blank statement.   
	 * @throws SQLException
	 */
	
	private static void setup_connections() throws SQLException
	{
		//GET CONNECTION
		myConn = DriverManager.getConnection(db_connection,db_user,db_password);
				
		//CREATE A STATEMENT
		myStmt = myConn.createStatement();
	
	}
	
	
	/**
	 * Is called to find the language that was called in the request
	 * @return Boolean true/false depending if the the language matches the request
	 * @throws SQLException
	 */
	public static boolean langAvailable(String language) throws SQLException
    {
		//assume language is not there until confirmed 
        boolean available = false;
       
        setup_connections();
       
        //query row relative to client's language selection
        ResultSet rs = myStmt.executeQuery("SELECT * FROM percentages "
                + "WHERE Name = '" + language + "'");
       
        while(rs.next())
        {
        	//get language percentage
            String percent = rs.getString(language);
           
            //if languages are the same (ie 100% match)
            if (percent.equals("100"))
            {
               
                available = true;
               
            }
           
        }
        	
        return available;
    }
	
	
	/**
	 * Is called when Client needs to update to a new version/main menu.
	 * @return 1 dimensional String array containing the main menu elements.
	 * @throws Exception
	 */
	
	public static String[] version() throws Exception
	{
		
		setup_connections();
		
		//query row relative to client's language selection
		ResultSet rs = myStmt.executeQuery("SELECT * FROM percentages");			//`Name`
		
		int columnCount = rs.getMetaData().getColumnCount();
		
		/*
		 *count # of languages 
		*# of columns is # of rows - 1
		*/
		String[] list = new String[columnCount - 1];
		
		int i = 0;
		while(rs.next())
		{
					//pull all the row entries of Name
					list[i] = rs.getString("Name");
					i++;
					//System.out.println(list[i]);
					
		}
		
		return list;
		
	}
	
	
	/**
	 * Is called when Client makes a main menu selection. Responds with sub menu elements. 
	 * @param language - The requested language as a String.
	 * @return 2 dimensional String array containing the sub menu elements. First element references the language,
	 * the second specifies the similarity percentage.
	 * @throws SQLException
	 */
	
	public static String[][] request(String language) throws SQLException
	{
		
		setup_connections();
		
		//query row relative to client's language selection
		ResultSet rs = myStmt.executeQuery("SELECT * FROM percentages "
				+ "WHERE Name = '" + language + "'");
		
		int columnCount = rs.getMetaData().getColumnCount();
		
		
		/*
		 * -1 column count to omit the 1st column 
		 * as column index starts at 1
		*/
		subMenu = new String[columnCount-1][3];
		
		//populate array
		while(rs.next())
		{
			
			//column count -1 as 1st column is primary key (Name)
			for (int i = 0; i<columnCount-1; i++)	
			{
				
				/* get column name in order of position in the table
				 * +2 to column label to omit the first column
				 * and to override array index 0
				 */
				String columnName = rs.getMetaData().getColumnLabel(i+2);
				
				//get row column name
				subMenu[i][0] = columnName;
				
				//get row data from particular column
				subMenu[i][1] = rs.getString(i+2);
				
			
			}
			
		}
		
		//amend subMenu to include uses for each language
		subMenu = uses_request(subMenu);
		
		//in event of invalid request
		if(subMenu[0][0] == null)
		{
			//trim array and populate with error message
			subMenu = new String[1][2];
			subMenu[0][0] = "ERROR:";
			subMenu[0][1] = "Invalid request.";
			
		}
		
		return subMenu;
		
	}
	
	
	
	/**
	 * Is called within the request method to add language uses to the subMenu.
	 * @return 2 dimensional String array containing the sub menu elements with uses.
	 * @throws SQLException
	 */
	public static String[][] uses_request(String[][] menuArray) throws SQLException{
		
		//pull uses from languages table
		ResultSet rs = myStmt.executeQuery("SELECT Uses FROM languages.languages");
		
		//array index
		int i = 0;
		
		while(rs.next())
		{
			
			//get uses from result set and put into a string
			String string = rs.getString(1);
			
			//put uses into menuArray element
			menuArray[i][2] = string;
			
			//increment menuArray element
			i++;
		
		}
		
		return menuArray;
	}
}

