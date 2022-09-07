package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CountryTable {
    JTable jTable = new JTable();
    DefaultTableModel defaultTableModel = new DefaultTableModel();
    TableColorCellRenderer tableColorCellRenderer = new TableColorCellRenderer();
    String startPath = "data/";
    String endPath = ".jpg";

    public CountryTable(String countriesFileName) throws IOException {
       FileReader fileReader = new FileReader(countriesFileName);
       BufferedReader bf = new BufferedReader(fileReader);
       String firstLine = bf.readLine();
       String[] columnNames = firstLine.split("\t");
       defaultTableModel.setColumnIdentifiers(columnNames);
       ArrayList<String> lines = new ArrayList<>();
       String currentLine;
       while ((currentLine = bf.readLine()) !=null){
           lines.add(currentLine);
        }
        for (int i = 0; i < lines.size(); i++) {
            String[] data = lines.get(i).split("\t");
           defaultTableModel.addRow(data);
            jTable.setModel(defaultTableModel);
            jTable.setValueAt(Integer.parseInt(data[2]), defaultTableModel.getRowCount()-1, defaultTableModel.getColumnCount()-2);
            jTable.setValueAt(new MyJLable(startPath+data[0]+endPath), defaultTableModel.getRowCount()-1, defaultTableModel.getColumnCount()-1);
        }
        jTable.getColumn("Flag").setCellRenderer(new LabelRenderer());
        jTable.setDefaultRenderer(Object.class, tableColorCellRenderer);
    }

    public JTable create(){
        return jTable;
    }
}
