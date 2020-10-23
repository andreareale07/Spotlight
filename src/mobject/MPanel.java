/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Andrea
 */
public class MPanel extends JPanel {
    private String path;
    private Shape shape;
    ImageIcon sfondo ;
    public MPanel(String s){
            this.path = s;    
            sfondo = new ImageIcon(path);
    }
    
    public MPanel(){
    }
    protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
         g.setColor(getForeground());
         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
    public JPanel getPanel(String s){  
        this.path = s;
        JPanel pannello = new JPanel(){
            protected void paintComponent(Graphics g)
            {
                
                // Dispaly image at at full size
                g.drawImage(sfondo.getImage(), 0, 0, null);

                // Scale image to size of component
                Dimension d = getSize();
                g.drawImage(sfondo.getImage(), 0, 0, d.width, d.height, null);

                super.paintComponent(g);
            }
        };
        return pannello;
    }
    
    public JPanel getPanel(){  
        if(isPathSet()){
            JPanel pannello = new JPanel(){
                protected void paintComponent(Graphics g)
                {
                    ImageIcon sfondo = new ImageIcon(path);
                    // Dispaly image at at full size
                    g.drawImage(sfondo.getImage(), 0, 0, null);
                    // Scale image to size of component
                    Dimension d = getSize();
                    g.drawImage(sfondo.getImage(), 0, 0, d.width, d.height, null);
                    super.paintComponent(g);
                }
            };
            return pannello;
        }
        return new JPanel();
    }
   public boolean isPathSet(){
       if(path == null) return false;
       return true;
   }
   
   public void setPath(String s){
       this.path = s;
       sfondo = new ImageIcon(path);
   }
}
