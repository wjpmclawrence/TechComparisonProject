import java.sql.SQLException;
import junit.framework.Assert;
import static junit.framework.Assert.fail;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author matth
 */
public class AndroidAppTests {

    // These instance variables need to be replaced by the actual variables in other classes when code is written
    private String connectionString;
    private int lowerBoundForNoOfCharsInConnString = 10; //Assign here recommended no. chars for case
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    @Test
    public void isConnectionStringValid()
    {
        assert connectionString instanceof String;
        assert connectionString.length() >= lowerBoundForNoOfCharsInConnString;
    }
    
    @Test
    public void isConnectionSuccessful()
    {
        try 
        {
            executeSql();
            printToTables();
            
            //Either of these may throw SQL Exception if you implement it wrongly, throw SQLException to demonstrate this example
            throw new SQLException();
        }
        catch(SQLException e)
        {
            fail();
        }
    }

    
    // Assume these methods are in main code, these are ways SQL can fail
    private void executeSql() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void printToTables() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
