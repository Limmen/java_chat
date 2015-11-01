/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;

/**
 *
 * @author kim
 */
public class RegularMessage implements Message, Serializable {

    private String msg;
    private String name;
    
    /**
     *
     * @param msg
     * @param name
     */
    public RegularMessage(String msg, String name){
        this.msg = msg;
        this.name = name;
    }

    /**
     *
     * @param msg
     */
    public RegularMessage(String msg){
        this.msg = msg;
        this.name = "";
    }

    /**
     *
     * @param name
     */
    @Override
    public void setName(String name){
        this.name=name;
    }

    /**
     *
     * @param msg
     */
    @Override
    public void setString(String msg){
        this.msg=msg;
    }    

    /**
     *
     * @return
     */
    @Override
    public String getString() {
        return this.msg;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }
    
}
