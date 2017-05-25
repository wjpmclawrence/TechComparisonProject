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
        Connect();
    }

    public static void Connect() {
        //Database Info
        String host = "jdbc:mysql://localhost:3306/mydb";
        String Uname = "root";
        String Pword = "password";

        try {
            //load driver
            Class.forName("com.mysql.jdbc.Driver");

            //Connect to database
            Connection con = DriverManager.getConnection(host, Uname, Pword);

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
        String[][] CompArray;
        String Test = "*";
        String Condition = "";

        String Get = "SELECT " + Test + " FROM mydb.languages " + Condition;

        //Execute Query on statement
        ResultSet rs = stmt.executeQuery(Get);

        int DatabaseSize = 0;
        int ColumnNum = 15;

        if (rs.last()) {
            DatabaseSize = rs.getRow();
            rs.beforeFirst();
        }
        
                
        CompArray = new String[DatabaseSize][ColumnNum];
        int X = 0;

        while (rs.next()) {
            //Load Array
            CompArray[X][0] = rs.getString("ID");
            CompArray[X][1] = rs.getString("Name");
            CompArray[X][2] = rs.getString("Imperative");
            CompArray[X][3] = rs.getString("Object-Oriented");
            CompArray[X][4] = rs.getString("Functional");
            CompArray[X][5] = rs.getString("Procedural");
            CompArray[X][6] = rs.getString("Generic");
            CompArray[X][7] = rs.getString("Reflective");
            CompArray[X][8] = rs.getString("Event-Driven");
            CompArray[X][9] = rs.getString("For Loop");
            CompArray[X][10] = rs.getString("While Loop");
            CompArray[X][11] = rs.getString("Arrays");
            CompArray[X][12] = rs.getString("Operators");
            CompArray[X][13] = rs.getString("Uses");
            CompArray[X][14] = rs.getString("Paradigms");
            X++;
        }
        //create percentages 
        Create(CompArray, con);
        //call Compare
        Compare(CompArray, con);
    }

    public static void Compare(String[][] Array, Connection con) {
        //initialise Variables
        double PercentageMatch;
        String[] ChosenLang;
        ChosenLang = new String[Array[0].length];
        int LANG = 0;

        String[] TopPercentageName;
        double[] TopPercentageValue;

        TopPercentageName = new String[Array.length];
        TopPercentageValue = new double[Array.length];

        for (int populate = 0; populate < (Array.length - 1); populate++) {
            TopPercentageName[populate] = "null";
            TopPercentageValue[populate] = 0;
        }

        double Para = 7.1428;
        double Syntax = 12.5;
        int Wrong = 0;
        int Start = Integer.parseInt(Array[0][0]);

        //Check for Language chosen and prepare to remove from comparison7
        for (int Z = 0; Z < Array.length; Z++) {
            String check = "" + (Z + Start) + "";
            for (int j = 0; j < Array.length; j++) {
                if (check.equalsIgnoreCase(Array[j][0])) {
                    System.arraycopy(Array[j], 0, ChosenLang, 0, 15);
                    LANG = j;
                    break;
                } else {
                    Wrong++;
                }
            }

            //PERFORM Comparison
            for (int x = 0; x < Array.length; x++) {
                PercentageMatch = 0;

                //remove chosen language
                if (x == LANG) {
                    TopPercentageName[x] = Array[x][1];
                    TopPercentageValue[x] = 100;
                    x++;
                }
                if (x == Array.length) {
                    break;
                }

                for (int y = 0; y < Array[x].length; y++) {

                    if (y <= 12) {
                        if (Array[x][y].equalsIgnoreCase(ChosenLang[y])) {
                            if (y >= 9 && y <= 12) {
                                if (ChosenLang[y].equalsIgnoreCase("0")) {

                                } else {
                                    PercentageMatch = PercentageMatch + Syntax;
                                }

                            } else if (y >= 2 && y <= 8) {
                                PercentageMatch = PercentageMatch + Para;

                            }
                        }

                    }

                }

                //Percentage multiplier
                PercentageMatch = PercentageMatch * 0.8;

                TopPercentageName[x] = Array[x][1];
                TopPercentageValue[x] = PercentageMatch;

            }

            insert(Array[Z][1], TopPercentageValue, con);
        }

    }

    public static void Create(String[][] Name, Connection con) {
        String Rows = "`Name` varchar(45)";
        for (int row = 0; row < Name.length; row++) {
            Rows = Rows + ", `" + Name[row][1] + "` tinyint(4)";
        }

        try {
            Statement stmt = con.createStatement();

            stmt.executeUpdate("DROP TABLE `percentages`");

            String construct = "CREATE TABLE `percentages` (" + Rows + ")";

            stmt.executeUpdate(construct);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }

    public static void insert(String Name, double[] Percentage, Connection con) {
        String Values = "'" + Name + "'";
        for (int row = 0; row < Percentage.length; row++) {
            double StringValue = Percentage[row];
            Values = Values + ", " + StringValue;
        }

        try {
            Statement stmt = con.createStatement();

            String insert = "INSERT INTO `percentages` VALUES (" + Values + ")";
            stmt.executeUpdate(insert);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
}
