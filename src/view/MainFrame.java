/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import de.ksquared.system.keyboard.GlobalKeyListener;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mobject.MPanel;
import mobject.MTextField;
import data.Operation;
import static javax.swing.UIManager.getIcon;


/**
 *
 * @author Andrea
 */
public class MainFrame extends JFrame implements KeyListener, ActionListener {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private MPanel mainPanel; 
    private MTextField inputSearch;
    private JButton exit, preference;
    private Desktop desktop;
    private FrameConfiguration config;
    private IconTray iconTray;
    private boolean live = true;
    Operation op = new Operation();
    @SuppressWarnings("empty-statement")
    public MainFrame(){
        //if (AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.TRANSLUCENT));
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
        
        Mouse mouseList = new Mouse();
        int width = (int)(screenSize.getWidth()*0.7);
        int height = (int)(screenSize.getHeight()*0.1);
        int x1 = (int)(width*0.05);
        int x2 = (int)(width*0.75);
        int y1 = (int)(height*0.1);
        //int y2 = (int)(height*0.7);
        int y2 = 50;
        /***********external component******************/
        config = new FrameConfiguration();
        config.db.loadPreference();
        iconTray = new IconTray();
        iconTray.addActionListener(this);
        /***********************************************/
        mainPanel = new MPanel();
        inputSearch = new MTextField();
        exit = new JButton(new ImageIcon("image/silver/cancel.png"));
        preference = new JButton(new ImageIcon("image/silver/preferenze.png"));
        preference.setToolTipText("Prefenrenze");
        exit.setToolTipText("Chiudi Applicazione");
        
        this.setLayout(null);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        this.inputSearch.setFont( new Font("Courier", Font.BOLD,20));
        this.inputSearch.setText("Ricerca...");
        
        //this.mainPanel.setBackground(new Color(72,113,246,100));
        this.mainPanel.setOpaque(true);
        this.mainPanel.setBackground(new Color(191, 191, 191, 100));
        
        
        //this.exit.setBackground(new Color(143,167,243));
        //this.preference.setBackground(new Color(143,167,243));
       // this.inputSearch.setBackground(new Color(143,167,243));
        this.inputSearch.setBackground(new Color(235, 235, 235));
        this.exit.setBackground(new Color(235, 235, 235));
        this.preference.setBackground(new Color(235, 235, 235));
                
        //this.mainPanel.setBounds(x1, y1, x2, y2);
        this.mainPanel.setBounds(0, 0, 800, 50);
        Dimension dimSearch = new Dimension(700,40);
        this.inputSearch.setPreferredSize(dimSearch);
        
        Dimension dimButton = new Dimension(40,40);
        this.exit.setPreferredSize(dimButton);
        this.preference.setPreferredSize(dimButton);
                
        //inputSearch.setBounds((int)(x1*0.2),(int)(y1*0.2),(int)(x2*0.8),(int)(y2*0.85));
        this.mainPanel.add(inputSearch);
        this.mainPanel.add(preference);
        this.mainPanel.add(exit);
        this.add(mainPanel);
        
        this.inputSearch.addKeyListener(this);
        
        this.exit.addActionListener(this);
        this.exit.setActionCommand("Exit");
        this.preference.addActionListener(this);
        this.preference.setActionCommand("pref");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("image/preferenze.png"));
        this.setSize(820, 50);
        this.setUndecorated(true);
        this.setBackground(new Color(255,255,255,0));
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setLocation((int)(screenSize.getWidth()*0.5-410),(int)(screenSize.getHeight()*0.1) );
        this.setSize(width, height);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.addKeyListener(this);
        this.setVisible(false);
    }
    public boolean isAlive(){
        return live;
    }
    
    public void setShow(){
        if(isVisible())
            this.setVisibleFalse();
        else
            this.setVisibleTrue();        
    }
    private void setVisibleFalse(){
        setVisible(false);
        if(this.config.isVisible()){
            this.config.setShow();
        }
    }
    private void setVisibleTrue(){
        setVisible(true);
        this.inputSearch.setText("Ricerca...");
        this.inputSearch.requestFocusInWindow();
    }
    
    public void keyReleased(KeyEvent e) {
        if(KeyEvent.VK_ENTER == e.getKeyCode()){
            try {
                searchFormat();
            } catch (URISyntaxException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String s = inputSearch.getText();
        if(s.length()>1 && s.charAt(s.length()-1) == '=')
            inputSearch.setText(s.substring(0, s.length()-1));
    }
    
    //esegue un parsing del testo decidendo quale operazione eseguire
    private void searchFormat() throws URISyntaxException, IOException{
        String str = inputSearch.getText();
        boolean found = false;
        String tmp = formatString(str, '+');
        if(str.toLowerCase().equals("exit")) this.close();
        
        else if(!tmp.equals("") && !tmp.equals("Ricerca...")){
            String s = inputSearch.getText();
            if(op.validate(this.inputSearch.getText())) {
                op.removeResult(s);
                this.inputSearch.setText(s+" = "+op.expToVal(s));
            }else {
            for(int i = 0; i<config.db.size(); i++){
                if(inputSearch.getText().equals(config.db.getAlias(i))){
                    if(config.db.getUrl(i).toLowerCase().substring(0, 3).equals("www"))
                        openBrowser(""+config.db.getUrl(i));
                    else
                         openBrowser("www."+config.db.getUrl(i));
                    i = config.db.size();
                    found = true;
                }
            }
            if(str.length()> 2 && !found){
                if(str.toLowerCase().substring(0, 3).equals("www")) openBrowser(str);
                else openBrowser("www.google.it/#q="+tmp);
            }
            else if(!found) openBrowser("www.google.it/#q="+tmp);
            setShow();
            }
        }
    }
   
    private void openBrowser(String url) throws URISyntaxException, IOException{
        desktop.browse(new URI(url));
    }
    //formatta la stringa per consentire la ricerca del testo su google
    private String formatString(String str, char c){
        int i = 0;
        String tmp = "";
        while(i<str.length()){
            if(str.charAt(i) == ' ')
                tmp = tmp+c;
            else    
                tmp = tmp+str.charAt(i);
            i++;
        }
        return tmp;
    }
    public void keyTyped(KeyEvent e) {
        // System.out.println(e.getKeyChar());
       // String s = inputSearch.getText();
        
        
    }
    private void close(){
        this.iconTray.close();
        System.exit(0);
    }
    @Override
    public void keyPressed(KeyEvent e) {
         String s = this.inputSearch.getText();
        if(e.getKeyChar()>=32 && e.getKeyCode()<= 126 ){
            if(inputSearch.getText().equals("Ricerca..."))
                inputSearch.setText("");
            if(op.validate(this.inputSearch.getText())&& e.getKeyChar() == '=') {
                s = op.removeResult(s);
                this.inputSearch.setText(s+" = "+op.expToVal(s));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("Exit"))
                close();
        else if(ae.getActionCommand().equals("pref"))
                config.setShow();
        else if(ae.getActionCommand().equals("Open")){
            this.setVisibleTrue();
        }
        else if(ae.getActionCommand().equals("Preference")){
            this.setVisibleTrue();
            this.config.setShow();
        }
        else if(ae.getActionCommand().equals("Hide")){
            this.setVisibleFalse();
        }
    }
    
    private class Mouse implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            
        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public void mouseReleased(MouseEvent me) {
        }

        @Override
        public void mouseEntered(MouseEvent me) {

        }

        @Override
        public void mouseExited(MouseEvent me) {
            //exit.setVisible(false);
        }
        
    }
    
}
