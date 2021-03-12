package sk.kosickaakademia.martinek.company.database;


import sk.kosickaakademia.martinek.company.entity.User;
import sk.kosickaakademia.martinek.company.log.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Databaza {
    Tajnosti tj = new Tajnosti();
    Log log = new Log();

    private final String INSERTQUERY = "INSERT INTO user (fname, lname, age, gender) VALUES ( ?,?,?,?)";

    public Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(tj.getUrl(), tj.getUsername(), tj.getPassword());
            //Class.forName("com.mysql.cj.jdbc.Driver");
            log.print("SAXES connected");
            return con;
        }catch(Exception e){
            log.error(e.toString());
        }
        return null;
    }

    public void closeConnection(Connection con){
        if (con!=null){
            try {
                log.print("Connection closed.");
                con.close();
            } catch (SQLException throwables) {
                log.error(throwables.toString());

            }
        }
    }

    public boolean insertNewUser(User user) {


        try( Connection con = getConnection()){

        if(con!= null){

                PreparedStatement ps = con.prepareStatement(INSERTQUERY);
                ps.setString(1,user.getFname());
                ps.setString(2,user.getLname());
                ps.setInt(3,user.getAge());
                ps.setInt(4,user.getGender().getValue());

                int result = ps.executeUpdate();

                //closeConnection(con);
                log.print("New user has been added to the DB");
                return result==1;
        }
            }catch (Exception ex){
                log.error(ex.toString());
            }


        return false;
    }


    public List<User> getFemales(){
        String sqlQuerySelectFemales = "SELECT * FROM user WHERE gender = 1";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sqlQuerySelectFemales);
            return executeSelect(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<User> getMales(){
        String sqlQuerySelectMales = "SELECT * FROM user WHERE gender = 0";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sqlQuerySelectMales);
            return executeSelect(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public List<User> getUsersByAge(int from, int to){
        if (from>to){
            return null;
        }
        String sqlAge = "SELECT * FROM user WHERE age >= ? AND age <= ? ";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sqlAge);
            ps.setInt(1,from);
            ps.setInt(2,to);
            return executeSelect(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return null;
    }


    private List<User> executeSelect(PreparedStatement ps) throws SQLException{
        ResultSet rs = ps.executeQuery();
        List<User> list = new ArrayList<>();

        while (rs.next()){
            //naplnim postupne list
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            int age = rs.getInt("age");
            int id = rs.getInt("id");
            int gender = rs.getInt("gender");
            User u = new User(id,fname,lname,age,gender);
            list.add(u);

        }
        return list;
    }

    public List<User> getAllUsers(){

        String sql = "SELECT * FROM user";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            return executeSelect(ps);
        }catch(Exception ex){
            log.error(ex.toString());
        }

        return null;

    }


}
