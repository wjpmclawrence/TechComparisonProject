package com.mycompany.androidappmaven;

import com.example.benbody.client.TCPClient;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    private TCPClient tCPClient;
    private Socket socket;
    
    @Before
    public void setUp()
    {
        String requestString = null;
        request = request(requestString);
        tCPClient = mock(TCPClient.class);
        socket = tCPClient.getSocket();
    }
    
    @Test
    public void clientReceivesResponseAfterSendingRequestToServer()
    {
        assertNotNull(doInBackground());
    }
    
    @Test
    public void testObjectOutputStream() throws IOException
    {
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        when(socket.getOutputStream()).thenReturn(objectOutputStream);
    }
    
    @Test
    public void clientConnectionToServer()
    {        
        assertEquals(socket.isConnected(), true);
    }
    
    @Test
    public void checkStreamsHaveClosed()
    {
        assertEquals(socket.isInputShutdown(), true);
        assertEquals(socket.isOutputShutdown(), true);
    }
    
    @After
    public void tearDown()
    {
        
    }
    
    public ArrayList request(String request)
    {
        return null;
    }

    private Object doInBackground() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
