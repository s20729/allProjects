package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableColorCellRenderer implements TableCellRenderer {
    private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column == 2){
            Object rezult = table.getModel().getValueAt(row, column);
            int population = (int)rezult;
            if(population >20000){
                c.setForeground(Color.RED);
            }else {
                c.setForeground(Color.BLACK);
            }
        }else {
            c.setForeground(Color.BLACK);
        }
        return c;
    }
}
