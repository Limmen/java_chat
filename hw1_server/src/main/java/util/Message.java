package util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kim
 */
public interface Message {
    
    /**
     *
     * @return
     */
    public String getString();    

    /**
     *
     * @return
     */
    public String getName();

    /**
     *
     * @param name
     */
    public void setName(String name);

    /**
     *
     * @param msg
     */
    public void setString(String msg);
    
}
