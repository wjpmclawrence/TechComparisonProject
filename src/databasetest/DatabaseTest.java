/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public static void main(String[] args) {
        // TODO code application logic here
        //Database Info
        String host = "jdbc:derby://localhost:1527/Languages";
        String Uname = "Connor";
        String Pword = "password";

        String[][] CompArray;
        CompArray = new String[20][15];
        int X = 0;

        String Test = "*";
        String Condition = "";

        String SQL = "SELECT " + Test + " FROM APP.LANGUAGES " + Condition;

        try {

            Connection con = DriverManager.getConnection(host, Uname, Pword);

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                String ID = rs.getString("ID");
                CompArray[X][0] = ID;
                String Name = rs.getString("Name");
                CompArray[X][1] = Name;
                String Imp = rs.getString("Imperative");
                CompArray[X][2] = Imp;
                String OOri = rs.getString("ObjectOriented");
                CompArray[X][3] = OOri;
                String Func = rs.getString("Funtional");
                CompArray[X][4] = Func;
                String Proc = rs.getString("Procedural");
                CompArray[X][5] = Proc;
                String Gene = rs.getString("Generic");
                CompArray[X][6] = Gene;
                String Refl = rs.getString("Reflective");
                CompArray[X][7] = Refl;
                String EDri = rs.getString("EventDriven");
                CompArray[X][8] = EDri;
                String Forl = rs.getString("ForLoop");
                CompArray[X][9] = Forl;
                String Whil = rs.getString("WhileLoop");
                CompArray[X][10] = Whil;
                String Arry = rs.getString("Arrays");
                CompArray[X][11] = Arry;
                String Oper = rs.getString("Operators");
                CompArray[X][12] = Oper;
                String Uses = rs.getString("Uses");
                CompArray[X][13] = Uses;
                String Para = rs.getString("Paradigms");
                CompArray[X][14] = Para;

                String Gap = "  ";

                /*String Data = rs.getString(Test);
                        
                
                String Data = (ID + Gap + Name + Gap + Imp + Gap + OOri + Gap + Func 
                        + Gap + Proc + Gap + Gene + Gap + Refl + Gap + EDri 
                        + Gap + Forl + Gap + Whil + Gap + Arry + Gap + Oper 
                        + Gap + Uses + Gap + Para);
                
                System.out.println(Data);*/
                X++;
            }
            String LanguageCheck = "Assembly language";
            Compare(CompArray, LanguageCheck);
            /*for (int j = 0; j < 20; j++) {
                for (int k = 0; k < 15; k++) {

                    System.out.print(CompArray[j][k] + " ");
                }
                System.out.print("\n");
            }*/
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }

    public static void Compare(String[][] Array, String Lang) {
        double PercentageMatch;
        String[] ChosenLang;
        String LearnTime;
        ChosenLang = new String[15];
        int LANG = 0;

        double Para = 7.1428;
        double Syntax = 12.5;
        int Wrong = 0;

        for (int j = 0; j < Array.length; j++) {
            if (Lang.equalsIgnoreCase(Array[j][1])) {
                System.arraycopy(Array[j], 0, ChosenLang, 0, 15);
                LANG = j;
                break;
            } else {
                Wrong++;
            }
        }
        //System.out.printf("%d   %d \n\n", Array.length, Array[0].length);
        if (Wrong == 20) {
            System.out.println("NOT A VALID LANGUAGE NAME");
        } else {

            for (int x = 0; x < Array.length; x++) {
                PercentageMatch = 0;
                if (x == LANG) {
                    x++;
                }
                //System.out.print(Array[x][1] + "   ");
                for (int y = 0; y < Array[x].length; y++) {
                    if (ChosenLang[y].equalsIgnoreCase(Array[x][y])) {
                        if (y >= 2 && y <= 8) {
                            PercentageMatch = PercentageMatch + Para;
                            //System.out.printf("%.5f", PercentageMatch);
                            //System.out.print("%    ");
                        } else if (y >= 9 && y <= 12) {
                            PercentageMatch = PercentageMatch + Syntax;
                            //System.out.printf("%.5f", PercentageMatch);
                            //System.out.print("%    ");
                        }
                    }
                }
                if (PercentageMatch >= 85 && PercentageMatch < 100) {
                    LearnTime = "1 Week";

                } else if (PercentageMatch >= 65 && PercentageMatch < 85) {
                    LearnTime = "2 Week";

                } else if (PercentageMatch >= 50 && PercentageMatch < 65) {
                    LearnTime = "3 Week";

                } else if (PercentageMatch >= 35 && PercentageMatch < 50) {
                    LearnTime = "1 Month ";

                } else {
                    LearnTime = "2 Month +";
                }

                System.out.printf(Array[x][1] + "      %.0f", PercentageMatch);
                System.out.print("%     " + LearnTime);
                System.out.print("\n\n");
            }
        }
    }

}
