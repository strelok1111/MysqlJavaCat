package mysqljavacat.renders;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author strelok
 */
public class ColoredTableCellRenderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        setEnabled(table == null || table.isEnabled()); // see question above

        if ((row % 2) == 0)
            setBackground(new Color(230, 255, 255));
        else
            setBackground(null);

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        return this;
    }
}