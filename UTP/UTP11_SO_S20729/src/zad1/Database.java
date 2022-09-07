package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Database {
    private Connection connection;
    private TravelData travelData;
    private static String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
    private String url;
    private Integer id = 1;

    public Database(String url, TravelData travelData) {
        this.url = url;
        this.travelData = travelData;
    }

    public void create() {
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url);
            if(connection != null) {
                System.out.println();
                System.out.println("Connected to derby!");
            }
        } catch (SQLException e) {
            System.err.println("Connection error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection.createStatement().execute("CREATE TABLE Travel("
                    + "id int PRIMARY KEY, "
                    + "kraj varchar(40), "
                    + "data_wyjazdu Date, "
                    + "data_powrotu Date, "
                    + "miejsce varchar(20), "
                    + "cena varchar(20), "
                    + "symbol_waluty varchar(10))"
            );

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Travel VALUES(?,?,?,?,?,?,?)");
            for (String line : travelData.getResList()) {
                String[] tokens = line.split("\\t");
                pstmt.setInt(1, id);
                id++;
                pstmt.setString(2, tokens[0]);
                pstmt.setString(3, tokens[1]);
                pstmt.setString(4, tokens[2]);
                pstmt.setString(5, tokens[3]);
                pstmt.setString(6, tokens[4]);
                pstmt.setString(7, tokens[5]);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("DDL&DML exe");
            e.printStackTrace();
        }
    }

    public void showGui(){
        try {
            Statement statment = connection.createStatement();
            ResultSet resultSet = statment.executeQuery("SELECT * FROM Travel");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount =  resultSetMetaData.getColumnCount();
            Vector columns = new Vector();
            for (int i = 1; i <= columnCount; i++)
                columns.add(resultSetMetaData.getColumnName(i));
            JFrame jFrame = new JFrame();
            JTable jTable = new JTable();
            DefaultTableModel defaultTableModel = new DefaultTableModel(columns,0);
            while (resultSet.next()){
                Vector row = new Vector();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));

                }
                defaultTableModel.addRow(row);
            }
            jTable.setModel(defaultTableModel);
            JScrollPane sp = new JScrollPane(jTable);
            jFrame.add(sp);
            jFrame.setSize(700,700);
            jFrame.setVisible(true);
            resultSet.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}