package zad1;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class LabelRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        TableColumn tc = table.getColumn("Flag");
        tc.setMinWidth(200);
        tc.setMaxWidth(200);
        table.setRowHeight(100);
        return (Component) value;
    }
}
