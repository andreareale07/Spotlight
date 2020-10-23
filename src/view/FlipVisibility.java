/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.MainFrame;
import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyAdapter;
import de.ksquared.system.keyboard.KeyEvent;

/**
 *
 * @author Andrea
 */
public class FlipVisibility implements Runnable{
    private boolean control = false, space = false;
    private int ctrl = 162, barspace = 32, enter = 0;
    public MainFrame f;
    public FlipVisibility(){
        f = new MainFrame();
    }
    @Override
    public void run() {
        //System.out.println("inizio ");
        new GlobalKeyListener().addKeyListener(new KeyAdapter() {
                @Override public void keyPressed(KeyEvent event) {
                    //System.out.println(event);
                    if(ctrl==event.getVirtualKeyCode()) control=true;
                    if(barspace==event.getVirtualKeyCode())space=true;
                    if(control == true && space == true) f.setShow();
                }
                @Override public void keyReleased(KeyEvent event) {
                    if(ctrl==event.getVirtualKeyCode()) control=false;
                    if(barspace==event.getVirtualKeyCode()) space=false;
                }
            });
        while(true)
        try { Thread.sleep(100); }
        catch(InterruptedException e) { }
    }
}
