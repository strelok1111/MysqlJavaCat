package mysqljavacat.databaseobjects;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import mysqljavacat.MysqlJavaCatApp;
import mysqljavacat.MysqlJavaCatView;

/**
 *
 * @author strelok
 */
public class TableObj  implements CompleteObj{
    private Icon icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/table.png"));
    private DatabaseObj database;
    private HashMap<String,FieldObj> fields = new HashMap<String,FieldObj>();
    private String name;
    private DefaultMutableTreeNode node;
    private TableObj alias_from;

    public TableObj(String name_t,DatabaseObj db,DefaultMutableTreeNode treenode){
        node = treenode;
        name = name_t;
        database = db;
        database.addTable(this);
    }
    private TableObj(String name_t){
        name = name_t;
    }
       
    public Icon getIcon(){
        return icon;
    }
    public void setDatabase(DatabaseObj d){
        database = d;
    }
    public DatabaseObj getDatabase(){
        return database;
    }
    public ArrayList<FieldObj> getFields(){
        ArrayList<FieldObj> out= new ArrayList<FieldObj>();
        for(FieldObj t : fields.values())
            out.add(t);
        return out;
    }
    private FieldObj getField(String s){
        return fields.get(s);
    }
    public void addField(FieldObj t){
        t.setTable(this);
        fields.put(t.toString(), t);
    }
    @Override
    public String toString(){
        return name;
    }
    public DefaultMutableTreeNode getNode(){
        return node;
    }
    public void refereshTable(){
        MysqlJavaCatView view = MysqlJavaCatApp.getApplication().getView();
        fields.clear();
        ArrayList<MutableTreeNode> childs = new ArrayList<MutableTreeNode>();
        for(int i = 0;i < node.getChildCount();i = i + 1)
            childs.add((MutableTreeNode)node.getChildAt(i));
        for(MutableTreeNode n : childs)
            ((DefaultTreeModel)view.getDbTree().getModel()).removeNodeFromParent(n);
        ArrayList<ArrayList<Object>> fields_list = MysqlJavaCatApp.getApplication().getRows(MysqlJavaCatApp.getApplication().executeSql("SELECT COLUMN_NAME,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,NUMERIC_PRECISION FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = '" + getDatabase() + "' AND TABLE_NAME = '" + name + "'"));
        for( ArrayList<Object> field : fields_list){
            DefaultMutableTreeNode field_node = new DefaultMutableTreeNode();
            field_node.setUserObject(new FieldObj(field.get(0).toString(),field.get(1).toString(), this, field_node));
            ((DefaultTreeModel)view.getDbTree().getModel()).insertNodeInto(field_node, node, node.getChildCount());
        }
        
    }
    public String getName(){
        return name;
    }
    public TableObj createAlias(String name){
        TableObj al = new TableObj(name);
        al.alias_from = this;
        al.fields = this.fields;
        return al;
    }
}
