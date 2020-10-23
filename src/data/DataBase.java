/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Andrea
 */
public class DataBase {
    private ArrayList <Shortcut>db;
    
    public DataBase(){
        db = new ArrayList<Shortcut>();
    }
    public int size(){
        return db.size();
    }
    public String getAlias(int i){
        return db.get(i).getAlias();
    }
    public String getUrl(int i){
        return db.get(i).getUrl();
    }
    
    public String getAlias(String s){
        for(Shortcut sh : db)
            if(sh.getUrl().equals(s)) return sh.getAlias();
       return null;
    }
    
    public String getUrl(String s){
        for(Shortcut sh : db)
            if(sh.getAlias().equals(s)) return sh.getUrl();
        return null;
    }
    
    public int add(String alias, String url){
        for(Shortcut sh : db)
            if(sh.getAlias().equals(alias)){
                JOptionPane.showMessageDialog(null, "Alias già presente !");
                return -1;
            }
        db.add(new Shortcut(alias, url));
        this.savePreference();
        return 0;
    }
    public int remove(String alias){
        for(int i = 0; i<db.size(); i++)
            if(db.get(i).getAlias().equals(alias)) db.remove(i);
        return -1;
    }
    /*****************SALVATAGGIO SU FILE**************************************/
    private void savedb(ArrayList <Shortcut> al){
        try {
            File f = new File("./Config/preferenze.pref");
            if(!f.getParentFile().exists())f.getParentFile().mkdirs();
            ObjectOutputStream OUT = new ObjectOutputStream(new FileOutputStream("./Config/preferenze.pref"));
            OUT.writeObject(al);
            OUT.flush();
            OUT.close();	
        } catch (IOException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }
    public void savePreference(){
        if(db == null) db = new ArrayList<Shortcut>();
        this.savedb(db);
    }
    public void savePreference(ArrayList <Shortcut> al){
        this.savedb(al);
    }
    /*******************FINE SALVATAGGIO***************************************/
    /*********************CARICA PREFERENZE************************************/
    private ArrayList<Shortcut> caricadb(){
        ArrayList <Shortcut> ogg = new ArrayList <Shortcut>();
        try {
            ObjectInputStream  IN = new ObjectInputStream(new FileInputStream("./Config/preferenze.pref"));
            ogg=(ArrayList) IN.readObject();  //CAST su array
            IN.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Nessun configurazione presente !");
        } catch(IOException ex){}   
          catch(ClassNotFoundException ex){}
        return ogg;
    }
    public void loadPreference(){
        this.db = this.caricadb();
    }
    /*********************FINE CARICA PREFERENZE*******************************/
    public Vector getAllAlias(){
        Vector<String> vett = new Vector<String>();
        for(int i = 0; this.db.size()<i; i++){
            vett.add(db.get(i).getAlias());
        }
        return vett;
    }
    public Vector getAllUrl(){
        Vector<String> vett = new Vector<String>();
        for(int i = 1; this.db.size()<i; i++){
            vett.add(db.get(i).getUrl());
        }
        return vett;
    }
}
