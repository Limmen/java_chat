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
import javax.swing.SwingWorker;

/**
 *
 * @author kim
 */
public class DisconnectWorker extends SwingWorker <Boolean, Integer> {
  
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Socket clientSocket;
    
    /**
     *
     * @param clientSocket
     * @param out
     * @param in
     */
    public DisconnectWorker(Socket clientSocket, ObjectOutputStream out, ObjectInputStream in){
        this.clientSocket = clientSocket;
        this.out = out;
        this.in = in;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {             
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }

    
}
