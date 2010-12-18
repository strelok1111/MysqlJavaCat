package mysqljavacat;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import sun.misc.BASE64Decoder;

/**
 *
 * @author strelok
 */
public class SqlTabbedPane extends JTabbedPane {
    public SqlTabbedPane() {
        String open_tabs = MysqlJavaCatApp.getApplication().getProperties().getProperty("openedTabs","");
        String [] files = open_tabs.split("\\|");
        boolean added = false;
        if(files.length != 0){
            BASE64Decoder dec = new BASE64Decoder();
            for(String f : files){
                if(!f.isEmpty()){

                    try {
                        String file_name = new String(dec.decodeBuffer(f));
                        added = true;
                        createTab(new File(file_name),!new File(file_name).getParentFile().getPath().equals(new File("current").getAbsolutePath()));
                    } catch (IOException ex) {
                        Logger.getLogger(SqlTabbedPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }else{
            File dir = new File("current");
            File[] children = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".sql");
                }
            });           
            for (File f : children) {
                createTab(f,false);
                added = true;
            }
        }
        if(!added)
            createTab();
        
        
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

    public SqlTab createTab(String name) {
        String new_name = castName(name,true);
        return createTab(new File("current",new_name),false);
    }
    public final SqlTab createTab(File f,boolean ext) {
        SqlTab tab = new SqlTab(f);
        tab.setTabLabel(f.getName().replaceAll(".sql", ""));
        tab.setIsExternal(ext);
        tab.setSqlTab(this);
        add(tab);
        setTabComponentAt(this.getTabCount() - 1, tab.getTabHeader());
        setSelectedIndex(this.getTabCount() - 1);
        return tab;
    }
    private String castName(String in,boolean flag){
        for(SqlTab tab : getSqlTabs()){
            if(tab.getFile().getName().equals(in)){
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
        return createTab("Untitled.sql");
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
