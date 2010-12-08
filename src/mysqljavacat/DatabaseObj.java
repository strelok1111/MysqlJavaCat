/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mysqljavacat;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
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
        setName(s);
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
}
