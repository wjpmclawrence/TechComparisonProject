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

        //Serached Language
        String Language = "assembly language";
        if (Language == null) {
            System.out.println("No Language Entered");
            System.exit(0);
        }

        try {
            //load driver
            Class.forName("com.mysql.jdbc.Driver");

            //Connect to database
            Connection con = DriverManager.getConnection(host, Uname, Pword);

            //Create statement
            Statement stmt = con.createStatement();

            //call Load CLass
            load(Language, stmt);

        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(err.getMessage());
        }
    }

    public static void load(String CheckLanguage, Statement stmt) throws SQLException {
        //initailise variables
        String[][] CompArray;
        String Test = "*";
        String Condition = "";

        String SQL = "SELECT " + Test + " FROM mydb.languages " + Condition;

        //Execute Query on statement
        ResultSet rs = stmt.executeQuery(SQL);

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
        //call Compare
        Compare(CompArray, CheckLanguage);
    }

    public static void Compare(String[][] Array, String Lang) {
        //initialise Variables
        double PercentageMatch;
        String[] ChosenLang;
        ChosenLang = new String[Array[0].length];
        int LANG = 0;

        String[] TopPercentage;
        double[] TopPerce;

        TopPercentage = new String[Array.length - 1];
        TopPerce = new double[Array.length - 1];

        for (int populate = 0; populate < (Array.length - 1); populate++) {
            TopPercentage[populate] = "null";
            TopPerce[populate] = 0;
        }

        double Para = 7.1428;
        double Syntax = 12.5;
        int Wrong = 0;

        //Check for Language chosen and prepare to remove from comparison
        for (int j = 0; j < Array.length; j++) {
            if (Lang.equalsIgnoreCase(Array[j][1])) {
                System.arraycopy(Array[j], 0, ChosenLang, 0, 15);
                LANG = j;
                break;
            } else {
                Wrong++;
            }
        }

        if (Wrong == Array.length) {
            System.out.println("'" + Lang.toUpperCase() + "' IS NOT A VALID LANGUAGE NAME");
        } else {

            //PERFORM Comparison
            for (int x = 0; x < Array.length; x++) {
                PercentageMatch = 0;

                //remove chosen language
                if (x == LANG) {
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

                int R = (TopPerce.length - 1);

                //Order Percentages
                while (PercentageMatch >= TopPerce[R]) {
                    if (R < (TopPerce.length - 1)) {
                        TopPercentage[R + 1] = TopPercentage[R];
                        TopPerce[R + 1] = TopPerce[R];
                    }

                    if (PercentageMatch >= TopPerce[R]) {
                        TopPercentage[R] = Array[x][1];
                        TopPerce[R] = PercentageMatch;
                    }

                    if (R == 0) {
                        break;
                    }
                    R--;
                }

            }
            for (int p = 0; p < (Array.length - 1); p++) {
                System.out.printf(TopPercentage[p] + " has a similarity of %.0f", TopPerce[p]);
                System.out.print("%\n");
            }
        }
    }

}
