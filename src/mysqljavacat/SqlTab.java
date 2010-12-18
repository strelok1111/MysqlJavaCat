/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mysqljavacat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mysqljavacat.dialogs.ComboDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author strelok
 */
public class SqlTab extends JScrollPane{
    private JLabel tab_label;
    private JEditorPane editor;
    private JPanel tab_header;
    private SqlTabbedPane sqlTab;
    private ComboDialog combo_dialog;
    private DefaultTableModel result_col_model;
    private Long request_time;
    private Integer row_count;
    private File file;
    private Font notsavedfont = new Font("Arial",Font.BOLD | Font.ITALIC,12);
    private Font savedfont = new Font("Arial",Font.PLAIN,12);
    private boolean saved = false;
    private boolean external = false;

    public void setResultColModel(DefaultTableModel model){
        result_col_model = model;
    }

    public void setIsExternal(boolean s){
        external = s;
    }
    public boolean isExternal(){
        return external;
    }
    public boolean isSaved(){
        return saved;
    }
    public void save(){
        try {
            file.createNewFile();
            MysqlJavaCatApp.getApplication().saveToFile(editor.getText(), file);
            saved = true;
            tab_label.setFont(savedfont);
        } catch (IOException ex) {
            Logger.getLogger(SqlTab.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public DefaultTableModel getResultColModel(){
        return result_col_model;
    }
    public void setRequestTime(Long rt){
        request_time = rt;
    }
    public Long getRequestTime(){
        return request_time;
    }
    public void setRowCount(Integer rc){
        row_count = rc;
    }
    public Integer getRowCount(){
        return row_count;
    }
    public void setSqlTab(SqlTabbedPane s){
        sqlTab = s;
    }
    private SqlTab getSqlTab(){
        return this;
    }
    public ComboDialog getComboDialog(){
        return combo_dialog;
    }
    public SqlTab(File file_in) {
        editor = new JEditorPane();
        file = file_in;        
        this.setBorder(null);
        this.setViewportView(editor);
        editor.setContentType("text/sql");        
        combo_dialog = new ComboDialog(editor);
        editor.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {

            }
            public void focusLost(FocusEvent e) {
                if(e.getOppositeComponent() == null || !(e.getOppositeComponent().getClass() == JList.class && (JList)e.getOppositeComponent() == combo_dialog.getComboList())){
                    combo_dialog.hideVithPrepared();
                }
            }
        });
        final CaretListener caretListener = new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                String lastword = "";
                String input = editor.getText().replace("\r\n", "\n").substring(0, e.getDot());
                Matcher m = Pattern.compile("([.\\w]+)\\z").matcher(input);
                if(m.find())
                    lastword = m.group(1);
                if(combo_dialog.isVisible() && lastword.equals(""))
                    combo_dialog.hideVithPrepared();
                if(combo_dialog.getPrepared()){
                    combo_dialog.showFor(lastword);
                }
            }
        };
        editor.addCaretListener(caretListener);
        editor.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                combo_dialog.hideVithPrepared();
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        editor.addKeyListener(new KeyListener() {

            
            public void keyPressed(KeyEvent e) {
                if(!combo_dialog.getPrepared() && !combo_dialog.isVisible()){
                    if(e.getKeyChar() == '.'){
                        combo_dialog.setPrepared(true);
                    }else if(e.isControlDown() && e.getKeyCode() == 32){
                        combo_dialog.setPrepared(true);
                        caretListener.caretUpdate(new CaretEvent(e.getSource()) {
                            @Override
                            public int getDot() {
                                return editor.getCaretPosition();
                            }

                            @Override
                            public int getMark() {
                                return editor.getCaretPosition();
                            }
                        });
                    }                      
                    
                }else if(combo_dialog.isVisible()){
                    if(e.getKeyCode() == 38){
                        JList combo_list = combo_dialog.getComboList();
                        if(combo_list.getSelectedIndex() == 0){
                            combo_list.setSelectedIndex(combo_list.getModel().getSize() - 1 );
                            combo_list.ensureIndexIsVisible(combo_list.getModel().getSize() - 1 );
                        }else{
                            combo_list.setSelectedIndex(combo_list.getSelectedIndex() - 1);
                            combo_list.ensureIndexIsVisible(combo_list.getSelectedIndex() - 1);
                        }
                        e.setKeyCode(0);
                    }else if(e.getKeyCode() == 40){
                        JList combo_list = combo_dialog.getComboList();
                        if(combo_list.getSelectedIndex() == combo_list.getModel().getSize() -1){
                            combo_list.setSelectedIndex(0);
                            combo_list.ensureIndexIsVisible(0);
                        }else{
                            combo_list.setSelectedIndex(combo_list.getSelectedIndex() + 1);
                            combo_list.ensureIndexIsVisible(combo_list.getSelectedIndex() + 1);
                        }
                        e.setKeyCode(0);
                    }else if(e.getKeyCode() == 10 || e.getKeyCode() == 9){
                        e.setKeyCode(0);
                        combo_dialog.setSelectedInEdit();
                    }
                    editor.grabFocus();
                }
            }

            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() != 83 && !e.isControlDown()){
                    saved = false;
                    tab_label.setFont(notsavedfont);
                }
            }

            public void keyReleased(KeyEvent e) {

            }

        });
        TabButton tabCloseButton = new TabButton();
        tabCloseButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
            if(sqlTab.getTabCount() == 1){
                sqlTab.createTab();
            }
            close();
          }
        });
        tab_label = new JLabel();
        tab_header = new JPanel();
        /*
         * TODO edit tab header
        final JTextField tab_edit = new JTextField();
        tab_edit.setSize(100, 8);
        tab_edit.setVisible(false);
        tab_edit.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                tab_edit.setVisible(false);
                tab_label.setVisible(true);
                tab_label.setText(tab_edit.getText());
            }
        });
        tab_label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    tab_label.setVisible(false);
                    tab_edit.setText(tab_label.getText());
                    tab_edit.setVisible(true);
                }
            }
        });
        */
        tab_label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));        
        
        tab_header.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        tab_header.setOpaque(false);
        tab_header.add(tab_label);
        //tab_header.add(tab_edit);
        tab_label.setFocusable(false);
        tab_header.setFocusable(false);
        tab_header.setBorder(null);
        tab_header.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tab_header.add(tabCloseButton);
        if(file.exists()){
            saved = true;
            editor.setText(MysqlJavaCatApp.getApplication().readFromFile(file));
            tab_label.setFont(savedfont);
        }else{
            tab_label.setFont(notsavedfont);
        }
    }
    public void close(){
        sqlTab.remove(getSqlTab());
        if(!external){
            MysqlJavaCatApp.getApplication().deleteFile(file);
        }
    }
    public File getFile(){
        return file;
    }
    public void setFileSave(File f){
        file = f;
        save();
        tab_label.setFont(savedfont);
        tab_label.setText(file.getName().replace(".sql", ""));
    }
    public JEditorPane getEditPane(){
        return editor;
    }
    public JPanel getTabHeader(){
        return tab_header;
    }
    public void setTabLabel(String s){
        tab_label.setText(s);
    }

}
