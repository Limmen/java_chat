/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.SwingWorker;
import util.Message;
import util.RegularMessage;

/**
 *
 * @author kim
 */
public class WriteWorker extends SwingWorker <Boolean, Integer> {
    
    private ObjectOutputStream out = null;
    private final String message;
    
    /**
     *
     * @param message
     * @param out
     */
    public WriteWorker(String message, ObjectOutputStream out){
        this.message = message;
        this.out = out;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {             
        Message msg = new RegularMessage(message);
        respond(msg);
        return true;
    }
    
    private void respond(Message msg){
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    
}
