package mysqljavacat.databaseobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import mysqljavacat.MysqlJavaCatApp;

public class Connection {
    private File file;
    private Properties prop = new Properties();
    private boolean saved = false;
    public Connection(){
        String file_name = System.currentTimeMillis() + ".con";
        file = new File("connections",file_name);
    }  
    public Connection(File file_in){
        file = file_in;
        saved = true;
        load();
    }
    public boolean isSaved(){
        return saved;
    }
    public void save(){
        try {
            prop.store(new FileOutputStream(file), null);
            saved = true;
        } catch (IOException ex) {
            MysqlJavaCatApp.getApplication().showError(ex.getMessage());
        }
    }
    public final void load(){
        try {
            if(file.exists()){
                prop.load(new FileInputStream(file));
            }
        } catch (IOException ex) {
            MysqlJavaCatApp.getApplication().showError(ex.getMessage());
        }
    }
    public Properties getProperties(){
        return prop;
    }
    @Override
    public String toString(){
        return prop.getProperty("name");
    }

    public void setName(String s){
        prop.setProperty("name", s + "");
    }

    public void remove(){
       file.delete();
    }

    public String getUniqName(){
        return file.getName().replace(".con", "");
    }
    public static ArrayList<Connection> getConnections(){
        ArrayList<Connection> out = new ArrayList<Connection>();
        File dir = new File("connections");
        File[] children = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".con");
            }
        });
        if(children != null)
            for (File f : children) {
                out.add(new Connection(f));
            }
        return out;
    }
}
