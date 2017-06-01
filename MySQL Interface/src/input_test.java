
public class input_test {

	public static void main(String[] args) throws Exception {
		
		String[] mainMenu = DB_Interface.version();
		
		String[][] subMenu = DB_Interface.request("C#");
		
		for (int i = 0; i<mainMenu.length; i++)
		{
			
			System.out.println("main_menu: " + mainMenu[i]);
			
		}
		
		System.out.println("\n\n");
		
		for (int i = 0; i<subMenu.length; i++)
		{
			
			System.out.println("sub_menu: " + subMenu[i][0] + " " + subMenu[i][1]);
		
		}
		
	}

}
