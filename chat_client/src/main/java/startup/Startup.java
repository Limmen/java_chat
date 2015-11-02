/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startup;

import view.MainWindow;

/**
 *
 * @author kim
 */
public class Startup {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args){
        //new Thread(new Client()).start();
        new MainWindow();
    }
}
