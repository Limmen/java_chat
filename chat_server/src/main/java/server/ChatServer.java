/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package server;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import util.Message;
import util.RegularMessage;

/**
 *
 * @author kim
 */
public class ChatServer{
    
    private final ArrayList<ChatId> users = new ArrayList();
    private final ChatId id;
    
    /**
     *
     */
    public ChatServer(){
        this.id = new ChatId(null, "Server");
        new Thread(new Listener(this)).start();
    }
    
    /**
     *
     * @param msg
     * @param id
     */
    public void broadcast(Message msg, ChatId id){
        msg.setName(id.getName());
        System.out.println("Broadcasting, name: " + msg.getName() + " text: " + msg.getString());
        for(ChatId user : users){
            user.getHandler().respond(msg);
        }
    }
    
    /**
     *
     * @param msg
     */
    public void serverBroadcast(Message msg){
        msg.setName("Server");
        for(ChatId user : users){
            user.getHandler().respond(msg);
        }
    }

    public DefaultListModel getUsers(){
        DefaultListModel listModel=new DefaultListModel();
        for(ChatId user : users){
            listModel.addElement(user.getName());
        }
        return listModel;
    }

    /**
     *
     * @param handler
     * @param name
     * @return
     */
    public ChatId regUser(ClientHandler handler, String name){
        ChatId id = new ChatId(handler,name);
        users.add(id);
        serverBroadcast(new RegularMessage(name + " joined the chat", "",getUsers()));        
        return id;
    }

    /**
     *
     */
    public void deRegUser(ChatId id){
        users.remove(id);
        serverBroadcast(new RegularMessage(id.getName() + " left the chat"));
    }
    
    
}
