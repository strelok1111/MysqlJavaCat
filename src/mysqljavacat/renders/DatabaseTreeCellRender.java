/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mysqljavacat.renders;

import java.awt.Component;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import mysqljavacat.databaseobjects.DatabaseObj;
import mysqljavacat.databaseobjects.FieldObj;
import mysqljavacat.databaseobjects.TableObj;
/**
 *
 * @author strelok
 */
public class DatabaseTreeCellRender extends DefaultTreeCellRenderer {
    Icon databases_icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/databases.png"));

    @Override
    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
        if(node.isRoot()){
            setIcon(databases_icon);
            setDisabledIcon(databases_icon);
        } else if(node.getUserObject().getClass() == DatabaseObj.class){
            setIcon(((DatabaseObj)node.getUserObject()).getIcon());
            setDisabledIcon(((DatabaseObj)node.getUserObject()).getIcon());
        } else if(node.getUserObject().getClass() == TableObj.class){
            setIcon(((TableObj)node.getUserObject()).getIcon());
            setDisabledIcon(((TableObj)node.getUserObject()).getIcon());
        } else if(node.getUserObject().getClass() == FieldObj.class){
            setIcon(((FieldObj)node.getUserObject()).getIcon());
            setDisabledIcon(((FieldObj)node.getUserObject()).getIcon());
        }

        return this;
    }
}