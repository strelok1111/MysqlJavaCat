package mysqljavacat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import mysqljavacat.renders.ColoredTableCellRenderer;

/**
 *
 * @author strelok
 */
public class ResultTable extends JTable {
    public ResultTable(){        
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setDragEnabled(false);
        setDefaultRenderer(Object.class,new ColoredTableCellRenderer());
    }
    public void loadResult(Object res)throws Exception{
        DefaultTableModel defaultColumnModel = new DefaultTableModel();
        ResultSet r1 = null;
        if(res.getClass() == com.mysql.jdbc.JDBC4ResultSet.class){
            r1 = (ResultSet)res;
            int col_count = r1.getMetaData().getColumnCount();
            for(int i = 1;i <= col_count;i = i + 1){
                defaultColumnModel.addColumn(r1.getMetaData().getColumnLabel(i));
            }
            while(r1.next()){                        
                Object [] arr = new Object[col_count];
                for(int i = 1; i <= col_count ; i = i + 1){
                    Object obj = null;
                    try{
                        obj = r1.getObject(i);
                    }catch(SQLException e){
                        obj = "0000-00-00";
                    }
                    try{
                        byte[] b = (byte[])obj;
                        obj = new String(b);
                    }catch(Exception e){
                    }
                    arr[i-1] = obj;
                }
                defaultColumnModel.addRow(arr);
            }                    
        }else{
            defaultColumnModel.addColumn("Rows Affected");
            ArrayList<Object> single_cell = new ArrayList<Object>();
            single_cell.add(res);
            defaultColumnModel.addRow(single_cell.toArray());
        }
        setModel(defaultColumnModel);
        getTableHeader().setReorderingAllowed(false);
        TableColumnModel columns = getColumnModel();
        for (int i = columns.getColumnCount() - 1; i >= 0; --i){
            columns.getColumn(i).setPreferredWidth(200);
        }
        
    }
}
