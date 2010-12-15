/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class DatabaseObj {
    private String name;
    private boolean isSet = false;
    private DefaultMutableTreeNode node;
    private Icon icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/database.png"));;
    private HashMap<String,TableObj> tables = new HashMap<String,TableObj>();
    public Icon getIcon(){
        return icon;
    }
    public DatabaseObj(String s,DefaultMutableTreeNode treenode){
        name = s;
        node = treenode;
    }

    public DefaultMutableTreeNode getNode(){
        return node;
    }

    public void setIsSet(boolean t){
        isSet = t;
    }

    public boolean isSet(){
        return isSet;
    }

    public String getName(){
        return name;
    }
    public void setName(String s){
        name = s;
    }
    @Override
    public String toString(){
        return name;
    }
    public ArrayList<TableObj> getTables(){
        ArrayList<TableObj> out = new ArrayList<TableObj>();
        for(TableObj t : tables.values())
            out.add(t);
        return out;
    }
    public TableObj getTable(String s){
        return tables.get(s);
    }
    public void addTable(TableObj t){
        t.setDatabase(this);
        tables.put(t.toString(), t);
    }
    public void refreshDatabase(boolean with_fields,boolean force){
        
        if(isSet && !force) return;

        tables.clear();

        MysqlJavaCatView view = MysqlJavaCatApp.getApplication().getView();
        ArrayList<MutableTreeNode> childs = new ArrayList<MutableTreeNode>();
        for(int i = 0;i < node.getChildCount();i = i + 1)
            childs.add((MutableTreeNode)node.getChildAt(i));
        for(MutableTreeNode n : childs)
            ((DefaultTreeModel)view.getDbTree().getModel()).removeNodeFromParent(n);

        ArrayList<ArrayList<Object>> fields_list;
        if(with_fields){
            isSet = true;
            fields_list = MysqlJavaCatApp.getApplication().getRows(MysqlJavaCatApp.getApplication().executeSql("SELECT TABLE_NAME,COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = '" + name + "'"));
        }else{
            fields_list = MysqlJavaCatApp.getApplication().getRows(MysqlJavaCatApp.getApplication().executeSql("SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = '" + name + "'"));
        }
        int table_index = 0;
        int field_index = 0;
               
        for( ArrayList<Object> field : fields_list){
            TableObj table = getTable(field.get(0).toString());
            DefaultMutableTreeNode table_node;
            if(table == null){
                table_node = new DefaultMutableTreeNode();
                table = new TableObj(field.get(0).toString(), this, table_node);
                table_node.setUserObject(table);
                ((DefaultTreeModel)view.getDbTree().getModel()).insertNodeInto(table_node, node, node.getChildCount());
                table_index = table_index + 1;
                field_index = 0;
            }else{
                table_node = table.getNode();
            }
            if(with_fields){
                DefaultMutableTreeNode field_node = new DefaultMutableTreeNode();
                FieldObj field_obj = new FieldObj(field.get(1).toString(), table, field_node);
                field_node.setUserObject(field_obj);
                ((DefaultTreeModel)view.getDbTree().getModel()).insertNodeInto(field_node, table_node, table_node.getChildCount());
                field_index = field_index + 1;
            }
        }
    }
}
