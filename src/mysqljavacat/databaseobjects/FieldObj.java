package mysqljavacat.databaseobjects;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author strelok
 */
public class FieldObj  implements CompleteObj{
    private Icon icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/textfield.png"));
    private TableObj table;
    private String name;
    private String type;
    private Integer length;
    private DefaultMutableTreeNode node;
    public FieldObj(String name_f,String type_f,TableObj t,DefaultMutableTreeNode treenode){
        node = treenode;
        name = name_f;
        type = type_f;
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
    public void setLength(Integer l){
        length = l;
    }
    @Override
    public String toString(){
        if(length == null)
            return name + " (" + type + ")";
        else
            return name + " (" + type + " ," + length + ")";
    }
    public String getName(){
        return name;
    }
    public DefaultMutableTreeNode getNode(){
        return node;
    }
}
