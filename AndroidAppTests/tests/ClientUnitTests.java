import java.util.ArrayList;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matth
 */
public class ClientUnitTests {
    private ArrayList request;
    
    @Before
    public void setUp()
    {
        String requestString = null;
        request = request(requestString);
    }
    
    @Test
    public void clientReceivesResponseAfterSendingRequestToServer()
    {
        if(request == null)
        {
            fail("Client doesn't retrieve a response from server");
        }
    }
    
    @After
    public void tearDown()
    {
        
    }
    
    public ArrayList request(String request)
    {
        return null;
    }
}
