
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

public class Converter extends FileChooser {

    public static void start(String source) {
        try {

            //check for file extension
            String extension = source.substring(source.length() - 3);

            //creates a text file if one does not exist, at the designated area
            PrintWriter writer = new PrintWriter(source + ".txt", "UTF-8");

            //set to the same flile path as above in the writer
            String destination = source + ".txt";

            //call conversion method
            convertWordToText(source, destination, extension);

            //connect to the database
            connectToDatabase(destination);

        } catch (ArrayIndexOutOfBoundsException aiobe) {
            System.out.println("Usage:java WordToTextConverter <word_file> <text_file>");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void convertWordToText(String src, String dest, String ext) {
        try {
            //create file inputstream object to read data from file 
            FileInputStream fs = new FileInputStream(src);

            //initialise filewriter
            FileWriter fw = null;

            if (ext.equalsIgnoreCase("ocx")) {

                //create document object to wrap the file inputstream object
                XWPFDocument docx = new XWPFDocument(fs);
                //create text extractor object to extract text from the document
                XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
                //create file writer object to write text to the output file
                fw = new FileWriter(dest);
                //write text to the output file  
                fw.write(extractor.getText());

            }

            if (ext.equalsIgnoreCase("doc")) {
                //initialise doc extractor
                POITextExtractor extractor = null;
                POIFSFileSystem fileSystem = new POIFSFileSystem(fs);
                try {
                    //extract text
                    extractor = ExtractorFactory.createExtractor(fileSystem);
                } catch (OpenXML4JException ex) {
                    Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (XmlException ex) {
                    Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
                }
                String extractedText = extractor.getText();
                //write text to the output file  
                fw = new FileWriter(dest);
                //write text to the output file  
                fw.write(extractedText);

            }

            if (ext.equalsIgnoreCase("pdf")) {

                try {
                    //creater pdfreader
                    PdfReader reader = new PdfReader(src);
                    //find amount of pages
                    int n = reader.getNumberOfPages();
                    for (int i = 1; i <= n; i++) {
                        //Extracting the content from a particular page.
                        String cv = PdfTextExtractor.getTextFromPage(reader, i); //create file writer object to write text to the output file
                        fw = new FileWriter(dest);
                        //write text to the output file  
                        fw.write(cv);
                        //close reader
                        reader.close();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            //clear data from memory
            fw.flush();
            //close inputstream and file writer
            fs.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compareSkills(String dest, String[] compArray, Statement stmt, Connection con) {
        //initialise variables
        Scanner scanner = null;
        boolean skills = false;
        String merge = null;

        //start 3 word array
        String[] wordString = {"one", "two", "three"};

        //open scanner
        try {
            scanner = new Scanner(new File(dest));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //while there is still lines in the file
        while (scanner.hasNextLine()) {
            //open second scanner
            Scanner scanner2 = new Scanner(scanner.nextLine());
            //while ther are still words on the line
            while (scanner2.hasNext()) {
                String s = scanner2.next();
                merge = "";

                //3 word array cycle
                wordString[2] = wordString[1];
                wordString[1] = wordString[0];
                wordString[0] = s;

                //set permutations of specific languages
                if (wordString[1].equalsIgnoreCase("Visual") && wordString[0].equalsIgnoreCase("Basic")) {
                    merge = wordString[1] + wordString[0];
                }
                if (wordString[1].equalsIgnoreCase("Assembly") && wordString[0].equalsIgnoreCase("Language")) {
                    merge = wordString[1] + " " + wordString[0];
                }
                if (wordString[1].equalsIgnoreCase("Visual") && wordString[0].equalsIgnoreCase("Basic.net")) {
                    merge = wordString[1] + wordString[0];
                }
                if (wordString[1].equalsIgnoreCase("Delphi/Object") && wordString[0].equalsIgnoreCase("Pascal")) {
                    merge = wordString[1] + wordString[0];
                }
                if (wordString[1].equalsIgnoreCase("Object") && wordString[0].equalsIgnoreCase("Pascal")) {
                    merge = "Delphi/Object-Pascal";
                }
                if (wordString[0].equalsIgnoreCase("Delphi")) {
                    merge = "Delphi/Object-Pascal";
                }

                //check for start of skills setcion
                if (wordString[2].equalsIgnoreCase("qualifications") && wordString[1].equalsIgnoreCase("and") && wordString[0].equalsIgnoreCase("skills")) {
                    skills = true;
                }

                //while in skills section search for known languages
                if (skills == true) {
                    //remove punctuation at the end of words
                    if (s.endsWith(".") || s.endsWith(",") || s.endsWith("(") || s.endsWith(")")) {
                        s = s.substring(0, s.length() - 1);
                    }
                    //compare words to know languages to check for match
                    for (int i = 0; i < compArray.length; i++) {
                        //if the languages match, go to percentages method
                        if (s.equalsIgnoreCase(compArray[i])) {

                            try {
                                percentage(s, stmt, con);
                            } catch (SQLException ex) {
                                Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        }
                        //if permutation match then go to percentages method
                        if (merge.equalsIgnoreCase(compArray[i])) {

                            try {
                                percentage(merge, stmt, con);
                            } catch (SQLException ex) {
                                Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        }
                    }
                }
                //check for end of skills section
                if (wordString[2].equalsIgnoreCase("interests") && wordString[1].equalsIgnoreCase("and") && wordString[0].equalsIgnoreCase("hobbies")) {
                    skills = false;
                }
            }
        }
    }

    public static void percentage(String language, Statement stmt, Connection con) throws SQLException {
        //initilaise
        String[] percArray;

        String input = language.toLowerCase();

        String output = input.substring(0, 1).toUpperCase() + input.substring(1);

        //Select statement to find variables
        String get = "SELECT * FROM percentages";

        //Execute Query on statement
        ResultSet rs = stmt.executeQuery(get);

        ResultSetMetaData metaData = rs.getMetaData();
        int count = metaData.getColumnCount(); //get number of columns
        String columnName[] = new String[count];

        //get column names
        for (int i = 1; i <= count; i++) {
            columnName[i - 1] = metaData.getColumnLabel(i);
        }

        percArray = new String[count];

        while (rs.next()) {
            if (output.equalsIgnoreCase(rs.getString(columnName[0]))) {
                for (int x = 1; x <= count; x++) {
                    //Load Array
                    percArray[x - 1] = rs.getString(columnName[x - 1]);
                    //print percentages to text area
                    getInfoCv().append("\n" + columnName[x - 1] + ":" + percArray[x - 1]);
                }
            }
        }

        getInfoCv().append("\n");
    }

    public static void connectToDatabase(String destination) {
        //Database Info
        String host = "jdbc:mysql://192.168.1.124:3306/languages";
        String uName = "root";
        String pWord = "whatpassword?";
        String[] compArray;

        try {
            //load driver
            Class.forName("com.mysql.jdbc.Driver");

            //Connect to database
            Connection con = DriverManager.getConnection(host, uName, pWord);

            //Create statement
            Statement stmt = con.createStatement();

            //call Load Class
            compArray = load(stmt, con);

            //call comparison class
            compareSkills(destination, compArray, stmt, con);

        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(err.getMessage());

        }

    }

    public static String[] load(Statement stmt, Connection con) throws SQLException {
        //initailise variables
        String[] compArray;
        String test = "Name";
        String condition = "";

        //Select statement to find variables
        String get = "SELECT " + test + " FROM languages.languages " + condition;

        //Execute Query on statement
        ResultSet rs = stmt.executeQuery(get);

        int databaseSize = 0;

        //find last row
        if (rs.last()) {
            databaseSize = rs.getRow();
            rs.beforeFirst();
        }

        compArray = new String[databaseSize];
        int x = 0;

        while (rs.next()) {
            //Load Array
            compArray[x] = rs.getString("Name");
            x++;
        }
        return compArray;
    }
}
