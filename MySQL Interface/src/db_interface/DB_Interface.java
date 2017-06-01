package db_interface;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DB_Interface {
	
	//DB CONNECTION VARIABLES
	static String db_connection = "jdbc:mysql://localhost:6800/languages?verifyServerCertificate=false&useSSL=true";		//languages/
	static String db_user = "root";
	static String db_password = "whatpassword?";
	static Connection myConn;
	static Statement myStmt;
	
	static String[][] subMenu;
	
	

	public static void main(String[] args) throws Exception 
	{
		
	}
	
	
	
	private static void setup_connections() throws SQLException
	{
		//GET CONNECTION
		myConn = DriverManager.getConnection(db_connection,db_user,db_password);
				
		//CREATE A STATEMENT
		myStmt = myConn.createStatement();
	
	}
	
	
	
	
	public static String[] version() throws Exception
	{
		
		setup_connections();
		
		
		ResultSet rs = myStmt.executeQuery("SELECT * FROM language_percent");			//`Known_Language`
		
		int columnCount = rs.getMetaData().getColumnCount();
		
		/*
		 *count # of languages 
		*# of columns is # of rows - 1
		*/
		String[] list = new String[columnCount - 1];
		
		int i = 0;
		while(rs.next())
		{
					//pull all the row entries of Known_language
					list[i] = rs.getString("Known_Language");
					i++;
					//System.out.println(list[i]);
					
		}
		
		return list;
		
	}
	
	
	
	public static String[][] request(String language) throws SQLException
	{
		
		setup_connections();
		
		//query row relative to client's language selection
		ResultSet rs = myStmt.executeQuery("SELECT * FROM language_percent "
				+ "WHERE Known_Language = '" + language + "'");
		
		int columnCount = rs.getMetaData().getColumnCount();
		
		/*
		 * -2 column count to omit the 1st column and start from 0 
		 * as column index starts at 1
		*/
		subMenu = new String[columnCount-2][2];
		
		//populate array
		while(rs.next())
		{
			
			//start at column 2 as 1 is primary key (Known_Language)
			for (int i = 2; i<columnCount; i++)	
			{
				
				//get column name in order of position in the table
				String columnName = rs.getMetaData().getColumnLabel(i);					
				
				//get row column name
				subMenu[i-2][0] = columnName;
				
				//get row data from particular column
				subMenu[i-2][1] = rs.getString(i);
			
			}
			
		}
		
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

}
