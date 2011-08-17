/*
 * MysqlJavaCatApp.java
 */

package mysqljavacat;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import mysqljavacat.dialogs.ConfigDialog;
import org.jdesktop.application.Task;
/**
 * The main class of the application.
 */
public class MysqlJavaCatApp extends SingleFrameApplication {
    private Connection dbConnection;
    private mysqljavacat.databaseobjects.Connection est_connection;
    private ConfigDialog config_dialog;
    private MysqlJavaCatView view;
    public String VERSION;
    private Preferences prop = Preferences.userRoot().node("MysqlJavaCat");

    public MysqlJavaCatView getView(){        
        return view;
    }
    public ConfigDialog getConfigDialog(){
        return config_dialog;
    }
    public void saveToFile(String str,File file){
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(file);
            fw.write(str);
        } catch (IOException ex) {
            showError(ex.getMessage());
        } finally {
            fw.close();
        }

    }
    public void deleteFile(File file){
        file.delete();
    }

    public String readFromFile(File file){
        String outline = null;
        try {
            FileReader input = new FileReader(file);
            BufferedReader bufRead = new BufferedReader(input);
            outline = "";
            try {
                String line = bufRead.readLine();
                while (line != null){
                    outline = outline + line + "\n";
                    line = bufRead.readLine();
                }
                bufRead.close();
            } catch (IOException ex) {
                showError(ex.getMessage());
            }
        } catch (FileNotFoundException ex) {
            showError(ex.getMessage());
        }
        return outline;
    }
    public Connection getConnection(){
        return dbConnection;
    }
    public Preferences getProperties(){
        return prop;
    }

    public void showError(String s){
        JOptionPane.showMessageDialog(null, s, "Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public void closeConnection(){
        try {
            dbConnection.close();
            dbConnection = null;
        } catch(Exception e){
            showError(e.getMessage());
        }
    }
    public Connection setupConnection(mysqljavacat.databaseobjects.Connection con) throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");        
        String dsn = "jdbc:mysql://" + con.dbHost + ":" + con.dbPort;
        if(con.UseSSH){
            dsn = dsn
            + "?socketFactory=SSHSocketFactory"
            + "&SSHHost=" + con.SSHHost
            + "&SSHUser=" + con.SSHUser
            + "&SSHPassword=" + con.SSHPass;
        }
        return DriverManager.getConnection(dsn,con.dbUser, con.dbPassword);
    }
    public void connectToDb() throws SQLException{        
        try{
            dbConnection = setupConnection(config_dialog.getCurConnect());
            est_connection = config_dialog.getCurConnect();
        }catch(ClassNotFoundException e){
            showError(e.getMessage());
        }
    }
    public mysqljavacat.databaseobjects.Connection getEstablishedConnection(){
        return est_connection;
    }

    public Object executeCustom(String sql){
        Object rs = null;
        try{
            Statement stmt = dbConnection.createStatement();
            if(stmt.execute(sql)){
                rs = stmt.getResultSet();
            }else{
                rs = stmt.getUpdateCount();
            }
        }catch(SQLException e){
            showError(e.toString());
        }
        return rs;
    }

    public ResultSet executeSql(String sql){
        ResultSet rs = null;
        try{
            Statement stmt = dbConnection.createStatement();
            rs = stmt.executeQuery(sql);
        }catch(SQLException e){
            showError(e.toString());
        }finally{
            return rs;
        }
    }
    public ArrayList<Object> getCol(ResultSet res,String col){
        ArrayList<Object> out_array = new ArrayList<Object>();
        if(res == null)
            return out_array;
        try{
            while(res.next())
                out_array.add(res.getObject(col));
        }catch(Exception e){
            showError(e.getMessage());
        }
        return out_array;
    }
    public ArrayList<String> getCols(ResultSet res){
        ArrayList<String> out_cols = new ArrayList<String>();
        if(res == null)
            return out_cols;
        try{
            for(int i = 1;i <= res.getMetaData().getColumnCount();i = i + 1){
                out_cols.add(res.getMetaData().getColumnLabel(i));
            }
        }catch(Exception e){
            showError(e.getMessage());
        }
        return out_cols;
    }
    public ArrayList<ArrayList<Object>> getRows(ResultSet res){
        ArrayList<ArrayList<Object>> out_array = new ArrayList<ArrayList<Object>>();
        if(res == null)
            return out_array;
        try{
            while(res.next()){
                ArrayList<Object> row = new ArrayList<Object>();
                for(int i = 1; i <= res.getMetaData().getColumnCount() ; i = i + 1){
                    row.add(res.getObject(i));
                }
                out_array.add(row);
            }
        }catch(Exception e){
            showError(e.getMessage());
        }
        return out_array;
    }



    /**
     * At startup create and show the main frame of the application.
     */
     public void saveTabs(){
        for(SqlTab tab : view.getTabsMain().getSqlTabs()){
            tab.save();
        }      
     }
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {                       
        view = new MysqlJavaCatView(this);
        config_dialog = new ConfigDialog(view.getFrame(),true);
        config_dialog.setLocationRelativeTo(view.getFrame());
        show(view);
        VERSION = getContext().getResourceMap(MysqlJavaCatApp.class).getString("Application.version");
        if(getProperties().getBoolean("connectOnStartUp",false)){
            Task task = new Task(this) {
                 
                 @Override
                 protected Object doInBackground() throws Exception { 
                   view.proxyRunConnect(); 
                   return null;
                }  
            };
            getContext().getTaskService().execute(task);
            getContext().getTaskMonitor().setForegroundTask(task);
            
        }
    }
     @Override protected void configureWindow(java.awt.Window root) {
        root.addWindowListener(new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
               saveTabs();
        }

    });
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */

    /**
     * A convenient static getter for the application instance.
     * @return the instance of MysqlJavaCatApp
     */
    public static MysqlJavaCatApp getApplication() {
        return Application.getInstance(MysqlJavaCatApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(MysqlJavaCatApp.class, args);
    }
    public static Frame getFrameFor(Component comp) {
            Window w = SwingUtilities.getWindowAncestor(comp);
            if (w != null && w instanceof Frame) {
                    Frame frame = (Frame) w;
                    return frame;
            }
            return null;
    }
}
