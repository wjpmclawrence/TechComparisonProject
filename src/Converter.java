
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class Converter {

    public static void main(String[] args) {
        try {
            String Source = "C:\\Users\\conno\\OneDrive\\Documents\\Capita\\CompareTheLanguage.com\\TEST.docx";
            String Destination = "C:\\Users\\conno\\OneDrive\\Documents\\Capita\\CompareTheLanguage.com\\TESTCONVERSION.txt";
            PrintWriter writer = new PrintWriter("C:\\Users\\conno\\OneDrive\\Documents\\Capita\\CompareTheLanguage.com\\TESTCONVERSION.txt", "UTF-8");
            convertWordToText(Source, Destination);
            compareSkills (Destination);
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            System.out.println("Usage:java WordToTextConverter <word_file> <text_file>");

        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void convertWordToText(String src, String dest) {
        try {
            //create file inputstream object to read data from file 
            FileInputStream fs = new FileInputStream(src);
            //create document object to wrap the file inputstream object
            XWPFDocument docx = new XWPFDocument(fs);
            //create text extractor object to extract text from the document
            XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
            //create file writer object to write text to the output file
            FileWriter fw = new FileWriter(dest);
            //write text to the output file  
            fw.write(extractor.getText());
            //clear data from memory
            fw.flush();
            //close inputstream and file writer
            fs.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void compareSkills (String dest){
        
    }
}
