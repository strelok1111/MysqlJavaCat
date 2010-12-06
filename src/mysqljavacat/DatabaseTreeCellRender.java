/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mysqljavacat;

import java.awt.Component;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 *
 * @author strelok
 */
class DatabaseTreeCellRender extends DefaultTreeCellRenderer {
    Icon database_icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/database.png"));
    Icon databases_icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/databases.png"));
    Icon table_icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/table.png"));
    Icon field_icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/textfield.png"));

    @Override
    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
        if(node.isRoot()){
            setIcon(databases_icon);
            setDisabledIcon(databases_icon);
        } else if(((DefaultMutableTreeNode)node.getParent()).isRoot()){
            setIcon(database_icon);
            setDisabledIcon(database_icon);
        } else if(((DefaultMutableTreeNode)((DefaultMutableTreeNode)node.getParent()).getParent()).isRoot()){
            setIcon(table_icon);
            setDisabledIcon(table_icon);
        } else if (        ((DefaultMutableTreeNode)(       (DefaultMutableTreeNode)((DefaultMutableTreeNode)node.getParent()).getParent()       ).getParent()).isRoot()         ){
            setIcon(field_icon);
            setDisabledIcon(field_icon);
        }

        return this;
    }
}