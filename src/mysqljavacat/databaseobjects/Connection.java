package mysqljavacat.databaseobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection {
    private String name;
    private String file_name;
    private Properties prop;    
    public Connection(String n){
        name = n;
        prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("connections", file_name)));
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void save() throws FileNotFoundException{
        try {
            prop.store(new FileOutputStream(new File("connections", file_name)), null);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
