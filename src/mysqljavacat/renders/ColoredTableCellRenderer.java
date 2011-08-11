package mysqljavacat.renders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author strelok
 */
public class ColoredTableCellRenderer extends DefaultTableCellRenderer
{  
    private Font deffont = getFont();
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        setEnabled(table == null || table.isEnabled()); // see question above

        if ((row % 2) == 0)
            setBackground(new Color(242, 247, 247));
        else
            setBackground(null);
        
        Font f = deffont;

        if(value == null){
            value = "NULL";       
            f = new Font(deffont.getName(),Font.ITALIC,deffont.getSize());
            setForeground(new Color(77,128,112));
        } else if(value.getClass() == Integer.class || value.getClass() == Float.class || value.getClass() == Long.class || value.getClass() == BigDecimal.class){
            setForeground(Color.blue);
        } else {
            setForeground(Color.black);
        }
        Component comp = super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        comp.setFont(f);
        return comp;
    }
}
