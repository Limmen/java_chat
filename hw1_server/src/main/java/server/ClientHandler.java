/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;
import util.Message;
import util.RegularMessage;

/**
 *
 * @author kim
 */
public class ClientHandler implements Runnable {

    private ObjectInputStream in;
    private ObjectOutputStream out = null;
    private final Socket clientSocket;
    private final ChatServer server;
    private boolean running;
    
    /**
     *
     * @param clientSocket
     * @param server
     */
    public ClientHandler(Socket clientSocket, ChatServer server){
        this.clientSocket = clientSocket;        
        this.server = server;
    }

    /**
     *
     */
    @Override
    public void run() {        
        running = true;
        ChatId id;
        setup();
        id = regUser();
        
        while(running){
            Message msg = readMsg();
            System.out.println("Server received: " + msg.getString());
            server.broadcast(msg,id);
        }
                                     
    }
    
    ChatId regUser(){
        RegularMessage msg = new RegularMessage("Welcome to the chat \nwhat is your username?", "Server");
        respond(msg);
        Message message = readMsg();
        return server.regUser(this, message.getString());
    }
    
    void setup(){
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.toString());
            cleanUp();
            terminate();
            return;
        }
    }
    
    Message readMsg(){
        Object msg;
        try {
            msg = in.readObject();
        } catch (ClassNotFoundException cnfe) {
            cleanUp();
            terminate();
            return null;
        } catch (OptionalDataException ode) {
            cleanUp();
            terminate();
            return null;
        } catch (IOException ioe) {
            cleanUp();
            terminate();
            return null;
        }
        
        if (msg instanceof Message) {
            Message message = (Message) msg;
            return message;
        }
        else{
            return null;
        }
    }
    
    /**
     *
     * @param msg
     */
    public void respond(Message msg){
        try {            
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.toString());
            cleanUp();
            terminate();
        }          
    }
    
    /**
     *
     */
    public void cleanUp(){
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException ioe) {
            
        }
    }
    
    /**
     *
     */
    public void terminate(){
        this.running = false;
    }
    
}
