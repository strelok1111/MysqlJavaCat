package mysqljavacat.databaseobjects;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.lang.reflect.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection{
    private static Preferences conPref = Preferences.userRoot().node("MysqlJavaCat").node("connections");    
    private boolean saved = false;
    public String name;    
    public String dbHost;
    public String dbUser;
    public String dbPort = "3306";
    public String dbPassword;
    public String SSHPass;
    public String SSHHost;
    public String SSHUser;
    public boolean UseSSH = false;
    
    
    public static ArrayList<Connection> getConnections(){
        ArrayList<Connection> list = new ArrayList<Connection>(); 
        String[] names = null;
        try {
            names = conPref.childrenNames();
        } catch (BackingStoreException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
       

        for (int i=0; i<names.length; i++) {
            list.add(new Connection(names[i]));
        }
        return list;
    }
    
    public Connection(String inname){
        name = inname;
        try {
            if(conPref.nodeExists(inname)){
                load();
                saved = true;
            }
        } catch (BackingStoreException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection(ArrayList<Connection> connections){       
        Pattern p = Pattern.compile("^New connection( (\\d+))?$");
        Integer max = 0;
        boolean find = false;
        for(Connection con : connections){
            Matcher m = p.matcher(con.name);
            if(m.matches()){
                find = true;
                Integer curr = 0;
                if(m.group(2) != null)
                    curr = Integer.parseInt(m.group(2));
                else
                    curr = 0;
                if(max < curr)
                    max = curr;
            }
        }
        
        if(!find)
            name = "New connection";
        else{  
            max = max + 1;
            name = "New connection " + max.toString();
        }
    }
    
    public void remove(){
        try {
            if(conPref.nodeExists(name))
                conPref.node(name).removeNode();
        } catch (BackingStoreException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void load(){
        try {
            Preferences conn = conPref.node(name);
            Class cls = Class.forName("mysqljavacat.databaseobjects.Connection");
            Field fieldlist[] = cls.getDeclaredFields();
            for (int i = 0; i < fieldlist.length; i++) {
               Field fld = fieldlist[i];
               int mod = fld.getModifiers();
               if(Modifier.isPublic(mod) && !Modifier.isStatic(mod)){
                   if(fld.getType().isPrimitive() && fld.getType().toString().equalsIgnoreCase("boolean")){
                        try {
                            fld.setBoolean(this, conn.getBoolean(fld.getName(), false));
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }else{
                        try {
                            fld.set(this, conn.get(fld.getName(), ""));
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }
               }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isSaved(){
        return saved;
    }
    public void save(){
        try {
            Preferences conn = conPref.node(name);
            Class cls = Class.forName("mysqljavacat.databaseobjects.Connection");
            Field fieldlist[] = cls.getDeclaredFields();
            for (int i = 0; i < fieldlist.length; i++) {
               Field fld = fieldlist[i];
               int mod = fld.getModifiers();
               if(Modifier.isPublic(mod) && !Modifier.isStatic(mod)){
                   if(fld.getType().isPrimitive() && fld.getType().toString().equalsIgnoreCase("boolean")){
                        try {
                            conn.putBoolean(fld.getName(), fld.getBoolean(this)); 
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }else{
                        try {
                            if(fld.get(this) != null)
                                conn.put(fld.getName(), fld.get(this).toString());
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }
               }
            }
            saved = true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @Override
    public String toString(){
        return name;
    }
  
}
