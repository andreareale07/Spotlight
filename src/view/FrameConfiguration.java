/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import data.DataBase;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import mobject.MPanel;
import mobject.MTextField;

/**
 *
 * @author Andrea
 */
public class FrameConfiguration extends JFrame implements ActionListener{
    DataBase db;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JScrollPane tablePane;
    private JTable table;
    private DefaultTableModel mTable;
    private MPanel mainPanel;
    private TableMenu tMenu;
    private MTextField textAlias;
    private MTextField textUrl;
    private MPanel newVoice;
    private JLabel labelAlias;
    private JLabel labelUrl;
    private JButton addAlias;
    public FrameConfiguration(){
        db = new DataBase();
       // db.loadPreference();
        
        tMenu = new TableMenu();
        mainPanel = new MPanel();
        mTable = new DefaultTableModel();
        table = new JTable(mTable);
        tablePane = new JScrollPane(table);
        textAlias = new MTextField(20);
        textUrl = new MTextField(20);
        newVoice = new MPanel();
        newVoice.setLayout(null);
        labelAlias = new JLabel("Alias");
        labelUrl = new JLabel("Url");
        addAlias = new JButton("Aggiungi");
        addAlias.addActionListener(this);
        addAlias.setBackground(new Color(235, 235, 235));
               
        mTable.addColumn("Alias");
        mTable.addColumn("url");
        
        this.mainPanel.setOpaque(true);
        this.mainPanel.setBackground(new Color(191, 191, 191, 100));
        //this.newVoice.setOpaque(true);
        //this.newVoice.setBackground(new Color(191, 191, 191, 100));
        
        
        updateModel();
       
        mainPanel.setLayout(null);
        labelAlias.setBounds(10,10,100,10);
        textAlias.setBounds(5, 25, 200, 20);
        labelUrl.setBounds(10,50,100,20);
        textUrl.setBounds(5, 70, 200, 20);
        addAlias.setBounds(55,105,100,23);
        newVoice.setBounds(400,20,210,135);
        mainPanel.setBounds(0, 0, 800, 400);
        tablePane.setBounds(10, 20, 350, 345);
        table.addMouseListener(new MouseList());
        tablePane.addMouseListener(new MouseList());
     
        table.add(tMenu);
        this.newVoice.add(addAlias);
        this.newVoice.add(labelAlias);
        this.newVoice.add(textAlias);
        this.newVoice.add(labelUrl);
        this.newVoice.add(textUrl);
        this.mainPanel.add(newVoice);
        this.mainPanel.add(tablePane);
        this.add(mainPanel);
        this.setLocation((int)(screenSize.getWidth()*0.5-410),(int)(screenSize.getHeight()*0.1)+50 );
        this.setUndecorated(true);
        this.setBackground(new Color(255,255,255,0));
        this.setSize(800, 400);
        this.setVisible(false);
        //this.setVisible(false);
    }
    public void setShow(){
        if(isVisible()){
            setVisible(false);
            this.tMenu.setVisible(false);
        }
        else{
            updateModel();
            setVisible(true);
        }
    }
    
    private void updateModel(){
        mTable.setRowCount(0);
        for(int i=0; i<db.size(); i++){
            String tmp [] = new String[2];
            tmp[0] = db.getAlias(i);
            tmp[1] = db.getUrl(i);
            mTable.addRow(tmp);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("Aggiungi")){
            if(textAlias.getText().length()>1){
                if(textUrl.getText().length()>1) {
                    db.add(textAlias.getText(), textUrl.getText());
                    updateModel();
                }
            }
            else JOptionPane.showMessageDialog(null, "Alias non aggiunto ! Campi obbligatori!");
        }
    }
    private class MouseList implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public void mouseReleased(MouseEvent evt) {
            table.changeSelection(table.rowAtPoint(new Point(evt.getX(), evt.getY())), 0, false, false);
            int i = table.getSelectedRow();

            if(SwingUtilities.isRightMouseButton(evt)) {
                Component source = evt.getComponent();
                //System.out.println("oggetto "+source.toString());
            tMenu.show(source, evt.getXOnScreen(), evt.getYOnScreen());
            } else
            if(SwingUtilities.isMiddleMouseButton(evt)) {
            // azioni con il tasto medio
            } else
            if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {
            // azioni con doppio click
            } else
            if(SwingUtilities.isLeftMouseButton(evt) ) {
            // azioni con click
                if(tMenu.isVisible()) tMenu.setVisible(false);
            }
        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }
    }
    private class TableMenu extends JPopupMenu{
        private JMenuItem elimina;
        private JMenuItem salva;
        private JMenuItem carica;
        private JTable tbl;
        private int select;
        public TableMenu(){
            mouseList ml = new mouseList();
            elimina = new JMenuItem("Elimina");
            elimina.addActionListener(new listener());
            elimina.addMouseListener(ml);
            salva = new JMenuItem("Salva");
            salva.addActionListener(new listener());
            salva.addMouseListener(ml);
            carica = new JMenuItem("Carica");
            carica.addActionListener(new listener());
            carica.addMouseListener(ml);
            this.add(elimina);
            this.add(salva); 
            this.add(carica);
        }
        public void show(Component t, int i, int x){
            //tbl = (JTable) t;
            
            this.setLocation(i, x);
            this.setVisible(true);
        }
        private class mouseList implements MouseListener{

            @Override
            public void mouseClicked(MouseEvent me) {
                
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                Color color = UIManager.getColor ( "JPopupMenu.background" );
                me.getComponent().setBackground(color);
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                me.getComponent().setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                Color color = UIManager.getColor ( "JPopupMenu.background" );
                me.getComponent().setBackground(color);
            }  
        }
        private class listener implements ActionListener{
            public listener(){
            }
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(ae.getActionCommand().equals("Elimina")){
                    int i = table.getSelectedRow();
                    if(i>=0){
                        String alias = (String) mTable.getValueAt(i, 0);
                        System.out.println("alias : "+alias);
                        db.remove(alias);
                        db.savePreference();
                        updateModel();
                    }
                    table.repaint();
                    setVisible(false);
                }
                else if(ae.getActionCommand().equals("Salva")){
                    db.savePreference();
                    setVisible(false);
                }
                else if(ae.getActionCommand().equals("Carica")){
                    db.loadPreference();
                    updateModel();
                    table.repaint();
                    setVisible(false);
                }
            }
        
        }
    }
}
