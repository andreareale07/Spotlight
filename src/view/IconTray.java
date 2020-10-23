/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 *
 * @author Andrea
 */
public class IconTray {
    private TrayIcon trayIcon;
    private SystemTray tray;
    private Image image;
    private PopupMenu popup;
    private ArrayList <MenuItem> menuItem;
    public IconTray(){
        try{
            //System.out.println("setting look and feel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            System.out.println("Unable to set LookAndFeel");
        }
        if(SystemTray.isSupported()){
            tray=SystemTray.getSystemTray();
            image=Toolkit.getDefaultToolkit().getImage("image/silver/preferenze.png");
            menuItem = new ArrayList();
            popup = new PopupMenu();
            this.addMenuItem(new MenuItem("Open"));
            this.addMenuItem(new MenuItem("Hide"));
            this.addMenuItem(new MenuItem("Preference"));
            this.addMenuItem(new MenuItem("Exit"));
            trayIcon=new TrayIcon(image, "WSpot", popup);
            trayIcon.setImageAutoSize(true);
            try {
                tray.add(trayIcon);
            } catch (AWTException ex) {
                Logger.getLogger(IconTray.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public void addActionListener(ActionListener al){
        for(int i = 0; i<menuItem.size(); i++){
            this.addActionListener(al, i);
        }
    }
    public void addActionListener(ActionListener al, int i){
        menuItem.get(i).addActionListener(al);
        if(menuItem.get(i).getLabel().equals("Open")){
            trayIcon.setActionCommand("Open");
            this.trayIcon.addActionListener(al);
        }
    }
    public void addMenuItem(MenuItem mt){
        this.menuItem.add(mt);
        this.popup.add(menuItem.get(menuItem.size()-1));  
    }
    public void close(){
        this.tray.remove(trayIcon);
    }
}
