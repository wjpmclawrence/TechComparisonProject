import java.sql.SQLException;
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    private Thread sut; //sut = system under test
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    @Before
    public void setUp()
    {
        sut = new Thread(new ServerThread());
        sut.start();
        connectionString = "Insert here a connection string";
    }
    
    
    // I am not sure yet clear how to test these yet.
    @Test
    public void httpRequestTest()
    {
        
    }
    
    @Test
    public void httpResponseTest()
    {
        
    }
    
    @Test
    public void isServerThreadAlive()
    {
        sut.checkAccess();
        boolean sutIsAlive = sut.isAlive();
        assertEquals(sutIsAlive, true);
    }
    
    @Test
    public void doesServerThreadHaveRightPermissions()
    {
        try {
            sut.checkAccess();
        } catch (SecurityException e) {
            System.out.println("Security exception thrown");
            fail("Test failed, exception thrown");
        }
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
            
            boolean throwException = false;
            //Set above to true if throw SQLException
            //Either of these may throw SQL Exception if you implement it wrongly, throw SQLException to demonstrate this example
            if(throwException)
            {
                throw new SQLException();
            }
        }
        catch(SQLException e)
        {
            fail("SQLException thrown, isConnectionSuccessful() test fails");
        }
    }
    
    @After
    public void tearDown()
    {
        sut.stop();
    }
    
    // Assume these methods are in main code, these are ways SQL can fail
    private void executeSql() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void printToTables() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
