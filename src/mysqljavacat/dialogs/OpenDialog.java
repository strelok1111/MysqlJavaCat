/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OpenDialog.java
 *
 * Created on Dec 18, 2010, 7:31:32 PM
 */

package mysqljavacat.dialogs;

import mysqljavacat.MysqlJavaCatApp;
import mysqljavacat.SqlTab;
import mysqljavacat.SqlTabbedPane;

/**
 *
 * @author strelok
 */
public class OpenDialog extends javax.swing.JDialog {

    /** Creates new form OpenDialog */
    public OpenDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        jFileChooser1.setName("jFileChooser1"); // NOI18N
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        if(evt.getActionCommand().equals("CancelSelection")){
            dispose();
        }else if(evt.getActionCommand().equals("ApproveSelection")){
            try{
                SqlTabbedPane tabs = MysqlJavaCatApp.getApplication().getView().getTabsMain();
                boolean is_ex = false;
                for(SqlTab tab : tabs.getSqlTabs()){
                    if(tab.getFile().getAbsolutePath().equals(jFileChooser1.getSelectedFile().getAbsolutePath())){
                        tabs.setSelectedComponent(tab);
                        tab.getEditPane().setText(MysqlJavaCatApp.getApplication().readFromFile(jFileChooser1.getSelectedFile()));
                        is_ex = true;
                        break;
                    }
                }
                if(!is_ex){
                    MysqlJavaCatApp.getApplication().getView().getTabsMain().createTab(jFileChooser1.getSelectedFile());                    
                }
                dispose();
            }catch(Exception e){
                MysqlJavaCatApp.getApplication().showError(e.getMessage());
            }
        }
    }//GEN-LAST:event_jFileChooser1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables

}
