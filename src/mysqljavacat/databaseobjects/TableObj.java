package mysqljavacat.databaseobjects;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author strelok
 */
public class TableObj{
    private  Icon icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/table.png"));
    private DatabaseObj database;
    private HashMap<String,FieldObj> fields = new HashMap<String,FieldObj>();
    private String name;
    private DefaultMutableTreeNode node;

    public TableObj(String name_t,DatabaseObj db,DefaultMutableTreeNode treenode){
        node = treenode;
        name = name_t;
        database = db;
        database.addTable(this);
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
}
