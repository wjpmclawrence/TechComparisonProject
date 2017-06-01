import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CopyOfDB_Interface {
	
	//DB CONNECTION VARIABLES
	static String db_connection = "jdbc:mysql://localhost:6800/languages";
	static String db_user = "root";
	static String db_password = "whatpassword?";
	
	//CLIENT REQUEST TYPE
	static String client_request = "main_menu";
	static String lookup_lang = "C++";
	

	public static void main(String[] args) {
		
		try {
			//GET CONNECTION
			Connection myConn = DriverManager.getConnection(db_connection,db_user,db_password);
			
			//CREATE A STATEMENT
			Statement myStmt = myConn.createStatement();
			
			//GET MENU LIST
			List<String> mainMenu = GET_MENU(myStmt);			//returns list of languages (rows)
			
			//show each element in the list
			for(int i=0; i<mainMenu.size(); i++){
				System.out.println("main_menu: " + mainMenu.get(i));
			}
			
			
			//GET SUBMENU LIST
			String[][] subMenu = GET_SUB_MENU(myStmt, lookup_lang);		//returns list of percentages for selected language
			
			//show each element in the list
			for(int i=0; i<subMenu.length; i++){
				System.out.println("column_name: " + subMenu[i][0] + " " + subMenu[i][1]);
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	private static List<String> GET_MENU(Statement myStmt) throws Exception{
		List<String> list = new ArrayList<String>();
		ResultSet rs = myStmt.executeQuery("SELECT `Known_Language` FROM language_percent");
		
		while(rs.next()){
			list.add(rs.getString("Known_Language"));	
		}
		return list;
	}
	
	
	
	
	
	private static String[][] GET_SUB_MENU(Statement myStmt, String language) throws SQLException
	{
		
		//query single row relative to client's language selection
		ResultSet rs = myStmt.executeQuery("SELECT * FROM language_percent "
				+ "WHERE Known_Language = '" + language + "'");
		
		int columnCount = rs.getMetaData().getColumnCount();
		String[][] subMenu = new String[columnCount-2][2];				//-2 column count to omit the 1st column and start from 0 (columnC starts at 1)
		
		System.out.println("\nClient Selection: " + language);
		
		//populate array
		while(rs.next())
		{
					
			for (int i = 2; i<columnCount; i++)								//start at column 2 as 1 is primary key (Known_Language)
			{
				String columnName = rs.getMetaData().getColumnLabel(i);		//get column name in order of position in the table					
				
				subMenu[i-2][0] = columnName;
				subMenu[i-2][1] = rs.getString(i);					//get row data from particular column
			
			}
		}
		
		return subMenu;
	}

}
