package zad5;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class KsiazkiController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter =  resp.getWriter();
        String respNazwa = req.getParameter("one");
        StringBuilder stringBuilder;
        String userName = "root";
        String password = "root";
        String connectionUrl = "jdbc:mysql://localhost:3306/mydb";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            if (respNazwa!=null && !respNazwa.isEmpty()){
                Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
                PreparedStatement select = connection.prepareStatement("Select * from ksiazki WHERE Nazwa Like ? ");
                select.setString(1,respNazwa);
                ResultSet resultSet = select.executeQuery();
                stringBuilder = new StringBuilder();
                while (resultSet.next()){
                    stringBuilder.append(resultSet.getInt(1) + " ");
                    stringBuilder.append(resultSet.getString(2)+ " ");
                    stringBuilder.append(resultSet.getString(3)+ " ");
                    stringBuilder.append(resultSet.getString(4)+ " ");
                    stringBuilder.append("<br>");
                }
                respNazwa=stringBuilder.toString();
                resultSet.close();
                select.close();
            }else if (respNazwa==null || respNazwa.isEmpty()){
                Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet =statement.executeQuery("select * from ksiazki");
                stringBuilder = new StringBuilder();
                while (resultSet.next()){
                    stringBuilder.append(resultSet.getInt(1) + " ");
                    stringBuilder.append(resultSet.getString(2)+ " ");
                    stringBuilder.append(resultSet.getString(3)+ " ");
                    stringBuilder.append(resultSet.getString(4)+ " ");
                    stringBuilder.append("<br>");
                }
                respNazwa=stringBuilder.toString();
                resultSet.close();
                statement.close();
            }
        }catch (Exception e){
            respNazwa = "Wystapil blad";
        }
        printWriter.write("<html>" +
                "<head></head>" +
                "<body>" +
                "<form action = 'ksiazki' method = 'post'>" +
                "Nazwa : <input type ='text' name = 'one' />" +
                "<input type = 'submit' name = 'submit'>" +
                "</form>" +
                "<p>" + respNazwa + "</p>" +
                "</body>" +
                "</html>");

        printWriter.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}