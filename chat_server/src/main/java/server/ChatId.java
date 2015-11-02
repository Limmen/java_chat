/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author kim
 */
public class ChatId {
    private final ClientHandler handler;
    private final String name;
    
    /**
     *
     * @param handler
     * @param name
     */
    public ChatId(ClientHandler handler, String name){
        this.handler = handler;
        this.name = name;        
    }
    
    /**
     *
     * @return
     */
    public ClientHandler getHandler(){
        return this.handler;
    }

    /**
     *
     * @return
     */
    public String getName(){
        return this.name;
    }
    
}
