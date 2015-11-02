/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import util.Message;

/**
 *
 * @author kim
 */
public class ReadWorker extends SwingWorker <Boolean, Integer> {
    
    private final JTextArea area;
    private final ObjectInputStream in;
    private boolean running;
    private final MainWindow window;
    private Message msg;
    
    /**
     *
     * @param area
     * @param in
     * @param window
     */
    public ReadWorker(JTextArea area,ObjectInputStream in, MainWindow window){
        this.area = area;
        this.in = in;
        this.window = window;
    }
    
    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {
        running = true;
        while(running){
            msg = readMsg();
            if(msg != null){
                
                
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        updateChat();
                        updateUsers();
                    }
                });
            }
        }
        
        return false;
    }
    
    private void updateChat(){
        area.setText(area.getText() + msg.getName() + ": " + msg.getString() + "\n");
        window.requestFocus();
    }
    
    private void updateUsers(){
        window.updateUsers(msg.getUsers());
    }
    
    private Message readMsg(){
        Object input;
        try {
            input = in.readObject();
        } catch (ClassNotFoundException cnfe) {
            running = false;
            return null;
        } catch (OptionalDataException ode) {
            running = false;
            return null;
        } catch (IOException ioe) {
            running = false;
            return null;
        }
        if (input instanceof Message) {
            return (Message) input;
        }
        else{
            return null;
        }
    }
    
}
