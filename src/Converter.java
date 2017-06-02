
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class Converter {

    public static void start(String source) {
        try {

            //check for file extension
            String extension = source.substring(source.length() - 3);

            //creates a text file if one does not exist, at the designated area
            PrintWriter writer = new PrintWriter("C:\\Users\\conno\\OneDrive\\Documents\\Capita\\CompareTheLanguage.com\\TESTCONVERSION_" + extension + ".txt", "UTF-8");

            //set to the same flile path as above in the writer
            String destination = "C:\\Users\\conno\\OneDrive\\Documents\\Capita\\CompareTheLanguage.com\\TESTCONVERSION_" + extension + ".txt";

            //call conversion method
            convertWordToText(source, destination, extension);

            //performs comparison 9Not being tested
            //compareSkills(destination);
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

                //create buffer
                StringBuffer buffer = new StringBuffer();
                try {
                    //read input stream
                    InputStreamReader isr = new InputStreamReader(fs, "ASCII");
                    Reader in = new BufferedReader(isr);
                    int ch;
                    while ((ch = in.read()) > -1) {
                        buffer.append((char) ch);
                    }
                    in.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    //output to txt file
                    FileOutputStream fos = new FileOutputStream(dest);
                    Writer out = new OutputStreamWriter(fos, "ASCII");
                    fw = new FileWriter(dest);

                    //write text to the output file  
                    fw.write(buffer.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public static void compareSkills(String dest) {
        Scanner scanner = null;
        boolean skills = false;
        boolean additionalInfo = false;
        try {
            scanner = new Scanner(new File(dest));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            Scanner scanner2 = new Scanner(scanner.nextLine());
            while (scanner2.hasNext()) {
                String s = scanner2.next();

                if (s.equalsIgnoreCase("skills") || s.equalsIgnoreCase("qualifications")) {
                    skills = true;
                }
                if (skills == true) {
                    if (s.endsWith(".") || s.endsWith(",")) {
                        s = s.substring(0, s.length() - 1);
                    }
                    if (s.equalsIgnoreCase("java") || s.equalsIgnoreCase("oracle") || s.equalsIgnoreCase("ruby")) {
                        System.out.println(s);
                    }
                }
                if (s.equalsIgnoreCase("Additional") || s.equalsIgnoreCase("interests") || s.equalsIgnoreCase("hobbies")) {
                    skills = false;
                    additionalInfo = true;
                }
                if (additionalInfo == true) {
                    System.out.print(s + " ");
                }
            }
        }
    }
}
