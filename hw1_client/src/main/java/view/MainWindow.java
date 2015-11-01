/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class MainWindow {
    
    private JFrame frame;
    private JPanel container;
    private JPanel connect;
    private JPanel chatpanel;
    private JPanel inputpanel;
    private JScrollPane chat;
    private JScrollPane messagePane;
    private JTextField host;
    private JTextField port;
    private JTextArea message;
    private JButton send;
    private JTextArea chatarea;
    private JButton connectButton;
    private JButton disconnect;
    private boolean connected;
    private String hostname;
    private int portnr;
    private Socket clientSocket = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private final Font Plain = new Font("Serif", Font.PLAIN, 12);
    private final Font Title = new Font("Serif", Font.PLAIN, 14);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private ReadWorker readWorker;
    private ConnectWorker connectWorker;
    private WriteWorker writeWorker;
    private DisconnectWorker disconnectWorker;
    
    /**
     *
     */
    public MainWindow(){
        connected = false;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                showGUI();
            }
        });
    }
    
    private void showGUI(){
        frame = new JFrame("HomeWork 1 ID2212");
        frame.setLayout(new MigLayout());
        container = new JPanel(new MigLayout("wrap 2"));
        connect = new JPanel(new MigLayout("wrap 2"));
        chatpanel = new JPanel(new MigLayout("wrap 1"));
        createConnectPanel();
        createChatPanel();
        container.add(connect, "span 1, gapright 40, gapleft 20");
        if(connected){
            container.add(chatpanel, "span 1");
        }
        frame.add(container, BorderLayout.CENTER);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                exit();
                System.exit(0);
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);    // centers on screen
        frame.setVisible(true);
    }
    
    private void createConnectPanel(){
        connect.removeAll();
        JLabel lbl;
        
        if(connected == false){
            lbl = new JLabel("Connect to a server");
            lbl.setFont(Title);
            connect.add(lbl, "span 2, gapbottom 10, align center");
            lbl = new JLabel("Host");
            lbl.setFont(PBold);
            host = new JTextField(25);
            host.setFont(Plain);
            connect.add(lbl, "span 1");
            connect.add(host, "span 1");
            lbl = new JLabel("Port");
            lbl.setFont(PBold);
            port = new JTextField(25);
            port.setFont(Plain);
            connect.add(lbl, "span 1");
            connect.add(port,"span 1");
            connectButton = new JButton("Connect");
            connectButton.setFont(Title);
            connectButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    try {
                        connect(host.getText(), Integer.parseInt(port.getText()));
                    }
                    catch(Exception e)
                    {
                        
                    }
                    
                }
            });
            
            connect.add(connectButton, "span 2, gaptop 5");
            
        }
        else{
            lbl = new JLabel("Connected to: ");
            lbl.setFont(Title);
            connect.add(lbl, "span 2, gapbottom 10, align center");
            lbl = new JLabel("Host: ");
            lbl.setFont(PBold);
            connect.add(lbl, "span 1");
            lbl = new JLabel(this.hostname);
            lbl.setFont(Plain);
            connect.add(lbl, "span 1");
            lbl = new JLabel("Port: ");
            lbl.setFont(PBold);
            connect.add(lbl, "span 1");
            lbl = new JLabel(Integer.toString(this.portnr));
            lbl.setFont(Plain);
            connect.add(lbl, "span 1");
            disconnect = new JButton("Disconnect");
            disconnect.setFont(Title);
            disconnect.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    try {
                        disconnect();
                    }
                    catch(Exception e)
                    {
                        
                    }
                    
                }
            });
            connect.add(disconnect, "span 2, gaptop 5");
        }
        
        
    }
    
    private void createChatPanel(){
        chatpanel.removeAll();
        chatarea = new JTextArea("");
        chatarea.setLineWrap(true);
        chatarea.setEditable(false);
        chatarea.setFont(Plain);
        chat = new JScrollPane(chatarea);
        chat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chat.setPreferredSize(new Dimension(250, 250));
        chatpanel.add(chat, "span 1");
        message = new JTextArea("");
        message.setLineWrap(true);
        message.setFont(Plain);
        messagePane = new JScrollPane(message);
        messagePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messagePane.setPreferredSize(new Dimension(250, 50));
        send = new JButton("Send");
        send.setFont(Title);
        send.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    send(message.getText());
                    message.setText("");
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        chatpanel.add(messagePane, "span 1");
        chatpanel.add(send, "span 1");
        
    }
    
    
    private void send(String text){
        writeWorker = new WriteWorker(text, out);
        writeWorker.execute();
        requestFocus();
    }
    
    private void connect(String host, int port){
        connectWorker = new ConnectWorker(this, port, host);
        connectWorker.execute();
    }
    
    /**
     *
     * @param host
     * @param port
     * @param clientSocket
     * @param in
     * @param out
     */
    public void connected(String host, int port, Socket clientSocket, ObjectInputStream in, ObjectOutputStream out){
        this.hostname = host;
        this.portnr = port;
        this.connected = true;
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createConnectPanel();
                container.add(chatpanel, "span 1");
                message.requestFocus();
                frame.pack();
            }
        });
        readWorker = new ReadWorker(chatarea,in, this);
        readWorker.execute();
    }

    /**
     *
     */
    public void requestFocus(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                message.requestFocus();
            }
        });
    }
    
    private void disconnect(){
        connectWorker.cancel(true);
        readWorker.cancel(true);
        writeWorker.cancel(true);
        connected = false;
        disconnectWorker = new DisconnectWorker(clientSocket, out, in);
        disconnectWorker.execute();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createConnectPanel();
                container.remove(chatpanel);
                chatarea.setText("");
                message.setText("");
                frame.pack();
            }
        });
    }
    
    private void exit(){
        if(connectWorker != null)
            connectWorker.cancel(true);
        if(readWorker != null)
            readWorker.cancel(true);
        if(writeWorker != null)
            writeWorker.cancel(true);
        if(connected)
        {
            connected = false;
            disconnectWorker = new DisconnectWorker(clientSocket, out, in);
            disconnectWorker.execute();
            try {
                disconnectWorker.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }        
    }
    
}
