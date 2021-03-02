package sk.kosickaakademia.martinek.company.database;

import sk.kosickaakademia.martinek.company.log.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Databaza {
    Tajnosti tj = new Tajnosti();
    Log log = new Log();

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(tj.getUrl(), tj.getUsername(), tj.getPassword())) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            log.print("SAXES connected");
            return conn;
        }catch(SQLException e){
            log.error(e.toString());
        }
        return null;
    }

    public void closeConnesction(Connection con){
        if (con!=null){
            try {
                con.close();
                log.print("Connection closed.");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }



}
