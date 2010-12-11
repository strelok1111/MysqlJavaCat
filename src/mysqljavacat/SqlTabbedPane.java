package mysqljavacat;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
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
    public SqlTabbedPane() {
        if ((new File("current")).exists()) {
            File dir = new File("current");
            File[] children = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".sql");
                }
            });
            if(children.length != 0){               
                for (int i=0; i<children.length; i++) {                                       
                    SqlTab tab = createTab(children[i].getName());
                    tab.getEditPane().setText(MysqlJavaCatApp.getApplication().readFromFile(children[i]));
                }
            }else{
                createTab();
            }

        } else {
            (new File("current")).mkdir();
            createTab();
        }

        if (!(new File("closed")).exists()) {
            (new File("closed")).mkdir();
        }

        addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                MysqlJavaCatView view = (MysqlJavaCatView)MysqlJavaCatApp.getApplication().getMainView();
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

    public SqlTab createTab(String name) {
        SqlTab tab = new SqlTab();
        String new_name = castName(name,true);
        tab.setFilename(new_name);
        tab.setTabLabel(new_name);
        tab.setSqlTab(this);
        this.add(tab);
        this.setTabComponentAt(this.getTabCount() - 1, tab.getTabHeader());
        this.setSelectedIndex(this.getTabCount() - 1);        
        return tab;
    }
    private String castName(String in,boolean flag){
        for(SqlTab tab : getSqlTabs()){
            if(tab.getFilename().equals(in)){
                String[] file = in.split("\\.");
                if(flag){
                    return castName(file[0] + "_1.sql", false);
                }else{
                    Pattern pattern = Pattern.compile("(.+?)_(\\d+)$");
                    Matcher match = pattern.matcher(file[0]);
                    match.find();
                    Integer num = new Integer(match.group(2));
                    num = num + 1;
                    return castName(match.group(1) + "_" + num + ".sql", false);
                }
            }
        }
        return in;
    }
    public SqlTab createTab() {
        SqlTab tab = createTab("Untitled.sql");
        return tab;
    }
    public JEditorPane getCurrEditorPane(){
        return ((SqlTab)this.getSelectedComponent()).getEditPane();
    }
    public List<SqlTab> getSqlTabs(){
        ArrayList<SqlTab> out = new ArrayList<SqlTab>();
        for(int i = 0;i < this.getTabCount();i = i + 1)
             out.add((SqlTab) this.getComponentAt(i));
        return out;
    }
    public SqlTab getSelectedtab(){
        return getSqlTabs().get(getSelectedIndex());
    }
}
