/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.DefaultListModel;

/**
 *
 * @author kim
 */
public interface Message {
    
    public String getString();
    public String getName();
    public DefaultListModel getUsers();
    public void setUsers(DefaultListModel users);
    public void setString(String string);
    public void setName(String name);
    
}
