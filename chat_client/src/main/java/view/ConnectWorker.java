/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import javax.swing.SwingWorker;

/**
 *
 * @author kim
 */
public class ConnectWorker extends SwingWorker<Socket, Socket> {
    
    private final int port;
    private final String host;
    private Socket clientSocket;
    private final MainWindow window;
    private ObjectInputStream in;
    private ObjectOutputStream out = null; 
    
    /**
     *
     * @param window
     * @param port
     * @param host
     */
    public ConnectWorker(MainWindow window, int port, String host){
        this.port = port;
        this.host = host;
        this.window = window;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Socket doInBackground() throws Exception {        
        try
        {
            clientSocket = new Socket(host,port);
        } catch (UnknownHostException e)
        {
            System.err.println("Don't know about host: " + host + ".");
            return null;
        } catch (IOException e)
        {
            System.err.println("Couldn't get I/O for " +
                    "the connection to: " + port + "");
            return null;
        }
        try{
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch(Exception e){
        }
        return clientSocket;
    }
    
    /**
     *
     */
    @Override
    protected void done()
    {
        window.connected(this.host, this.port, this.clientSocket, this.in, this.out);        
    }        
}
