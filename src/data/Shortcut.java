/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;

/**
 *
 * @author Andrea
 */
public class Shortcut implements Serializable {
    private String alias;
    private String url;
    
    public Shortcut(String a, String u){
        this.alias = a;
        this.url = u;
    }
    public Shortcut(){
        
    }
    public void setAlias(String s){
        this.alias = s;
    }
    public void setUrl(String s){
        this.url = s;
    }
    public String getAlias(){
        return this.alias;
    }
    public String getUrl(){
        return this.url;
    }
}
