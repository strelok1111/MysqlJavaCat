package mysqljavacat.databaseobjects;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author strelok
 */
public class FieldObj{
    private Icon icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/textfield.png"));
    private TableObj table;
    private String name;
    private DefaultMutableTreeNode node;
    public FieldObj(String name_f,TableObj t,DefaultMutableTreeNode treenode){
        node = treenode;
        name = name_f;
        table = t;
        table.addField(this);
    }
    public Icon getIcon(){
        return icon;
    }
    public TableObj getTable(){
        return table;
    }
    public void setTable(TableObj t){
        table = t;
    }
    @Override
    public String toString(){
        return name;
    }
    public DefaultMutableTreeNode getNode(){
        return node;
    }
}
