package mysqljavacat.renders;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.border.Border;
import mysqljavacat.databaseobjects.DatabaseObj;
import mysqljavacat.databaseobjects.FieldObj;
import mysqljavacat.databaseobjects.FuncObj;
import mysqljavacat.databaseobjects.TableObj;

/**
 *
 * @author strelok
 */
public class ComboCompleteRender extends DefaultListCellRenderer{
    @Override
    public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
        super.getListCellRendererComponent(list,value,index,isSelected, cellHasFocus);
        //if(value == null){
        //    setBackground(Color.black);
        //    setEnabled(false);
        //}else
        if(value.getClass() == DatabaseObj.class) {
            setIcon(((DatabaseObj)value).getIcon());
        }else if(value.getClass() == TableObj.class){
            setIcon(((TableObj)value).getIcon());
        }else if(value.getClass() == FieldObj.class){
            setIcon(((FieldObj)value).getIcon());
        }else if(value.getClass() == FuncObj.class){
            setIcon(((FuncObj)value).getIcon());
        }
        return this;
    }

}
