package mysqljavacat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author strelok
 */
public class SqlTabbedPane extends JTabbedPane {
    
    private static Preferences tabsPref = Preferences.userRoot().node("MysqlJavaCat").node("tabs"); 
    
    final public SqlTab createTab(String name){
        SqlTab tab = new SqlTab(name);
        add(tab);
        tab.setSqlTab(this);
        setTabComponentAt(this.getTabCount() - 1, tab.getTabHeader());
        setSelectedIndex(this.getTabCount() - 1);
        return tab;
    }
    final public SqlTab createTab(File f){
        SqlTab tab = createTab(f.getName());
        tab.setFile(f);
        return tab;
    }
    final public void createTab(){
        createTab(castName("Untitled",true));        
    }
    public SqlTabbedPane() { 
        String[] names = null;
        try {
            names = tabsPref.childrenNames();
        } catch (BackingStoreException ex) {

        }

        if(names.length != 0){
            for(String f : names){
                createTab(f);
            }
        }else{
            createTab();
        }

        
        addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                MysqlJavaCatView view = MysqlJavaCatApp.getApplication().getView();
                if (getSelectedtab().getResultColModel() != null){
                    view.getResultTable().setModel(getSelectedtab().getResultColModel());
                    TableColumnModel columns = view.getResultTable().getColumnModel();
                    for (int i = columns.getColumnCount() - 1; i >= 0; --i){
                        columns.getColumn(i).setPreferredWidth(200);
                    }
                }else {
                    view.getResultTable().setModel(new DefaultTableModel());
                    view.getResultTable().setColumnModel(new DefaultTableColumnModel());
                }
                if(getSelectedtab().getRequestTime() != null){
                    Float sec = getSelectedtab().getRequestTime().floatValue() / 1000;
                    view.getResTimeLabel().setText("Request time: " + sec.toString() + " sec");
                }else{
                    view.getResTimeLabel().setText(null);
                }
                if(getSelectedtab().getRowCount() != null)
                    view.getRowCountLabel().setText( "Row count: " + getSelectedtab().getRowCount().toString());
                else
                    view.getRowCountLabel().setText(null);
            }
        });
    }
    
    private String castName(String in,boolean flag){
        for(SqlTab tab : getSqlTabs()){
            if(tab.getTabName().equals(in)){                
                if(flag){
                    return castName(in + " 1", false);
                }else{
                    Pattern pattern = Pattern.compile("(.+?) (\\d+)$");
                    Matcher match = pattern.matcher(in);
                    match.find();
                    Integer num = new Integer(match.group(2));
                    num = num + 1;
                    return castName(match.group(1) + " " + num, false);
                }
            }
        }
        return in;
    }
    
    public JEditorPane getCurrEditorPane(){
        return ((SqlTab)getSelectedComponent()).getEditPane();
    }
    public List<SqlTab> getSqlTabs(){
        ArrayList<SqlTab> out = new ArrayList<SqlTab>();
        for(int i = 0;i < getTabCount();i = i + 1)
             out.add((SqlTab) getComponentAt(i));
        return out;
    }
    public SqlTab getSelectedtab(){
        if(getTabCount() == 0)
            return null;
        else
            return getSqlTabs().get(getSelectedIndex());
    }
}
