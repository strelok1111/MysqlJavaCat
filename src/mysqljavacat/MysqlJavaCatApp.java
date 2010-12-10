/*
 * MysqlJavaCatApp.java
 */

package mysqljavacat;

import mysqljavacat.dialogs.ErrorDialog;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The main class of the application.
 */
public class MysqlJavaCatApp extends SingleFrameApplication {
    private Connection dbConnection;
    private ErrorDialog errordialog;
    private Properties prop = new Properties();
    public void saveToFile(String str,File file){
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(file);
            fw.write(str);
        } catch (IOException ex) {
            Logger.getLogger(MysqlJavaCatApp.class.getName()).log(Level.SEVERE, null, ex);
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
            FileReader input = new FileReader(file.getPath());
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
                Logger.getLogger(MysqlJavaCatApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MysqlJavaCatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outline;
    }
    public Connection getConnection(){
        return dbConnection;
    }
    public Properties getProperties(){
        return prop;
    }

    public void showError(String s){
        if (errordialog == null) {
            JFrame mainFrame = getMainFrame();
            errordialog = new ErrorDialog(mainFrame,true);
            errordialog.setLocationRelativeTo(mainFrame);
        }

        errordialog.getLabel().setText(s);
        show(errordialog);
    }

    public void closeConnection(){
        try {
            dbConnection.close();
            dbConnection = null;
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void connectToDb() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MysqlJavaCatApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        String dsn = "jdbc:mysql://" + prop.getProperty("host") + ":3306";
        if(prop.getProperty("useSSH","0").equals("1")){
            dsn = dsn
            + "?socketFactory=SSHSocketFactory"
            + "&SSHHost=" + prop.getProperty("SSHhost")
            + "&SSHUser=" + prop.getProperty("SSHuser")
            + "&SSHPassword=" + prop.getProperty("SSHpass");
        }
        dbConnection = DriverManager.getConnection(dsn,prop.getProperty("user"), prop.getProperty("password"));
        
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return out_array;
    }



    /**
     * At startup create and show the main frame of the application.
     */
    public void loadProp(){
        try {
            prop.load(new FileInputStream("dbProperties.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     public void saveProp(){
        try {
            prop.store(new FileOutputStream("dbProperties.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        loadProp();
        MysqlJavaCatView view = new MysqlJavaCatView(this);
        show(view);
        if(getProperties().getProperty("connectOnStartUp","0").equals("1")){
            view.proxyRunConnect();
        }
    }
     @Override protected void configureWindow(java.awt.Window root) {
        root.addWindowListener(new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
                for(SqlTab tab : ((MysqlJavaCatView)getMainView()).getTabsMain().getSqlTabs()){
                    File f = new File("current",tab.getFilename());
                    saveToFile(tab.getEditPane().getText(), f);
                }
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
