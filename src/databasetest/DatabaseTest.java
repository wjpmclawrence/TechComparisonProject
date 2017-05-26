/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
Start up the project and add the above jar file to the library folder
Next connect to the your own my sql server and create a database call "mydb"
Next you must right click and select execute command on the tables folder of mydb
Copy and paste the contents of the file "LanguageDatabaseSQLCommands" into the command window to create the window

set Uname on line 92 to the username of your SQL server
set Pword on line 93 to the password of your SQL server


bobs your uncle it should run
 */
package databasetest;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author conno
 */

/*
JAVA, C, C++, PYTHON, C#, VISUALBASIC.NET, JAVASCRIPT, ASSEMBLY LANGUAGE, PHP, PERL, 
RUBY, VISUALBASIC, SWIFT, R, OBJECTIVE-C, GO, MATLAB, DELPHI/OBJECT PASCAL, PL/SQL, LUA.


IMPERATIVE, OBJECT ORIENTED, FUNCTIONAL, PROCEDURAL, GENERIC, REFLECTIVE, EVENT-DRIVEN

SYNTAX COMPARISONS (if written in same format but with different words then its counted as the same))
FOR, WHILE, ARRAYS, OPERATORS

FOR LOOP
0: other
1: for (initialization; condition; increment/decrement)
2: for counter in range(1, 6):7
3: for value As Integer = 0 To 5
4: for counter in 1..5
5: for(i in 1:10)
6: for n = 1:5 
7: for count := 1 to 100 do 

WHILE LOOP
0: other
1: while (counter > 1)

ARRAYS (size, first, last) )
0: other
1: name.length, 0, name.length-1
2: array[size], 0, size
3: len(name), 0, len(name)-1
4: UBound(name)-LBound(name)+1, LBounf(name), UBound(name)
5: count($name), 0, count($name)-1
6: scalar(@name), $[, $#name
7: [name count], 0, [name count] - 1
8: length(name), 1, end
9: #name, 1, #name

OPERATORS
0: other
1: () [] -> . ! ~ ++ -- + - * & / % << >> < <= > <= == != ^ | && || ?: = += -= *= /= %= &= ^= |= <<= >>=
2: ^ + - * / \ MOD = <> > < >= <= AND OR NOT XOR ANDALSO ORELSE ISFALSE ISTRUE & | ^ ~ = += -= *= /= \= ^= <<= >>= &=
3: ] ** ++ -- ~ @! * / % + - . << >> < <= > >= == != === !== <> <=> & ^ | && || ?? ?: = += -= *= **= /= .= %= &= |= ^= <<= >>=
4: -> ++ -- ** ! ~ \ + - . =~ !~ * / % < > <= >= == != <=> ~~ & | ^ && || '
5: + - * / % ** == != > < >= <= <=> === .eql? equal? = += -= *= /= %= **= & | ^ ~ << >>  and or && || ! not
6: + - * / %% %/% ^ > < == <= >= != & | ! && || <- -> : %in% %*%
7: + - * / ** = != <> ~= > < >= <= LIKE BETWEEN IN IS_NULL and or not ** + - * / ||
8: not # - .. * / % + - < > <= >= == ~= and or

USES,PARADIGMS
 */
public class DatabaseTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        //connect
        connect();
    }

    public static void connect() {
        //Database Info
        String host = "jdbc:mysql://localhost:3306/mydb";
        String uName = "root";
        String pWord = "password";

        try {
            //load driver
            Class.forName("com.mysql.jdbc.Driver");

            //Connect to database
            Connection con = DriverManager.getConnection(host, uName, pWord);

            //Create statement
            Statement stmt = con.createStatement();

            //create empty table
            String dummy = "CREATE TABLE IF NOT EXISTS `percentages` (`Dummy` varchar(69))";
            stmt.executeUpdate(dummy);

            //call Load CLass
            load(stmt, con);

        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(err.getMessage());
        }
    }

    public static void load(Statement stmt, Connection con) throws SQLException {
        //initailise variables
        String[][] compArray;
        String test = "*";
        String condition = "";
        
        //Select statement to find variables
        String Get = "SELECT " + test + " FROM mydb.languages " + condition;

        //Execute Query on statement
        ResultSet rs = stmt.executeQuery(Get);

        int databaseSize = 0;
        int columnNum = 15;

        //find last row
        if (rs.last()) {
            databaseSize = rs.getRow();
            rs.beforeFirst();
        }
        
                
        compArray = new String[databaseSize][columnNum];
        int x = 0;

        while (rs.next()) {
            //Load Array
            compArray[x][0] = rs.getString("ID");
            compArray[x][1] = rs.getString("Name");
            compArray[x][2] = rs.getString("Imperative");
            compArray[x][3] = rs.getString("Object-Oriented");
            compArray[x][4] = rs.getString("Functional");
            compArray[x][5] = rs.getString("Procedural");
            compArray[x][6] = rs.getString("Generic");
            compArray[x][7] = rs.getString("Reflective");
            compArray[x][8] = rs.getString("Event-Driven");
            compArray[x][9] = rs.getString("For Loop");
            compArray[x][10] = rs.getString("While Loop");
            compArray[x][11] = rs.getString("Arrays");
            compArray[x][12] = rs.getString("Operators");
            compArray[x][13] = rs.getString("Uses");
            compArray[x][14] = rs.getString("Paradigms");
            x++;
        }
        
        //create percentages 
        create(compArray, con);
        
        //call Compare
        compare(compArray, con);
    }

    public static void compare(String[][] compArray, Connection con) {
        //initialise Variables
        double percentageMatch;
        String[] chosenLang;
        chosenLang = new String[compArray[0].length];
        int language = 0;

        String[] topPercentageName;
        double[] topPercentageValue;

        topPercentageName = new String[compArray.length];
        topPercentageValue = new double[compArray.length];

        //populate compArray
        for (int populate = 0; populate < (compArray.length - 1); populate++) {
            topPercentageName[populate] = "null";
            topPercentageValue[populate] = 0;
        }

        //similarity values
        double paradigm = 7.1428;
        double syntax = 12.5;
        
        int wrong = 0;
        int start = Integer.parseInt(compArray[0][0]);

        //Check for Language chosen and prepare to remove from comparison7
        for (int Z = 0; Z < compArray.length; Z++) {
            String check = "" + (Z + start) + "";
            for (int j = 0; j < compArray.length; j++) {
                if (check.equalsIgnoreCase(compArray[j][0])) {
                    System.arraycopy(compArray[j], 0, chosenLang, 0, 15);
                    language = j;
                    break;
                } else {
                    wrong++;
                }
            }

            //PERFORM Comparison
            for (int x = 0; x < compArray.length; x++) {
                percentageMatch = 0;

                //remove chosen language
                if (x == language) {
                    topPercentageName[x] = compArray[x][1];
                    topPercentageValue[x] = 100;
                    x++;
                }
                if (x == compArray.length) {
                    break;
                }
                
                //cycle through languages
                for (int y = 0; y < compArray[x].length; y++) {

                    if (y <= 12) {
                        //cycle though variables
                        if (compArray[x][y].equalsIgnoreCase(chosenLang[y])) {
                            if (y >= 9 && y <= 12) {
                                //ignore 0 as it equals OTHER
                                if (chosenLang[y].equalsIgnoreCase("0")) {
                                
                                } else {
                                    //if match add 12.5
                                    percentageMatch = percentageMatch + syntax;
                                }

                            } else if (y >= 2 && y <= 8) {
                                //if match add 7.1428
                                percentageMatch = percentageMatch + paradigm;

                            }
                        }

                    }

                }

                //Percentage multiplier
                percentageMatch = percentageMatch * 0.8;
                
                //load names and percentges into arrays
                topPercentageName[x] = compArray[x][1];
                topPercentageValue[x] = percentageMatch;

            }
            
            //insert percentages into new table
            insert(compArray[Z][1], topPercentageValue, con);
        }

    }

    public static void create(String[][] Name, Connection con) {
        String rows = "`Name` varchar(45)";
        for (int row = 0; row < Name.length; row++) {
            //cycle through languages to get names for columns
            rows = rows + ", `" + Name[row][1] + "` tinyint(4)";
        }

        try {
            Statement stmt = con.createStatement();
            
            //delete old table
            stmt.executeUpdate("DROP TABLE `percentages`");

            //create new table with all the languages as columns
            String construct = "CREATE TABLE `percentages` (" + rows + ")";
            stmt.executeUpdate(construct);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }

    public static void insert(String Name, double[] Percentage, Connection con) {
        String values = "'" + Name + "'";
        for (int row = 0; row < Percentage.length; row++) {
            //cycle through languages and percentages to insert into table
            double stringValue = Percentage[row];
            values = values + ", " + stringValue;
        }

        try {
            Statement stmt = con.createStatement();
            
            //insert row into percentages table
            String insert = "INSERT INTO `percentages` VALUES (" + values + ")";
            stmt.executeUpdate(insert);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
}
