/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotlight;

import view.FlipVisibility;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mobject.MPanel;
import view.MainFrame;

/**
 *
 * @author Andrea
 */
public class Spotlight {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new Thread(new FlipVisibility()).start();
        
    }
}
