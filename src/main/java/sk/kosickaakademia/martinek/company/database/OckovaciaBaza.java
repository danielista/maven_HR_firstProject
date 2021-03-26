package sk.kosickaakademia.martinek.company.database;

import sk.kosickaakademia.martinek.company.entity.User;
import sk.kosickaakademia.martinek.company.entity.ockovanie.Persons;
import sk.kosickaakademia.martinek.company.log.Log;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OckovaciaBaza {
    Tajnosti tj = new Tajnosti();
    Log log = new Log();

    public Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(tj.getUrl2(), tj.getUsername2(), tj.getPassword2());
            //jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
            //Class.forName("com.mysql.cj.jdbc.Driver");
            log.print("SAXES connected");
            return con;
        }catch(Exception e){
            log.error(e.toString());
        }
        return null;
    }

    private List<Persons> executeSelect(PreparedStatement ps) throws SQLException{
        ResultSet rs = ps.executeQuery();
        List<Persons> listOfAllPersons = new ArrayList<>();

        while (rs.next()){
            String fname = rs.getString("meno");
            String lname = rs.getString("priezvisko");
            Boolean state = rs.getBoolean("stav");
            int age = rs.getInt("Vek");
            Persons persona = new Persons(fname,lname,state,age);
            listOfAllPersons.add(persona);
        }
        return listOfAllPersons;
    }

    public List<Persons> getAllPersons(){

        String sql = "SELECT * FROM osoby";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            return executeSelect(ps);
        }catch(Exception ex){
            log.error(ex.toString());
        }

        return null;

    }



    //public void insertRecord(String firstname, String lastname, String stav, Date datum){
    public boolean insertNewPerson(Persons person) {
            String INSERTQUERY = "INSERT INTO osoby (meno, priezvisko, stav, vek) VALUES ( ?,?,?,?)";

            try( Connection con = getConnection()){

                if(con!= null){
                    PreparedStatement ps = con.prepareStatement(INSERTQUERY);
                    ps.setString(1,person.getFirstname());
                    ps.setString(2,person.getLastname());
                    ps.setBoolean(3,person.getState());
                    ps.setInt(4,person.getAge());

                    int result = ps.executeUpdate();


                    log.print("New person has been added to the DB");
                    return result == 1;
                }
            }catch (Exception ex){
                log.error(ex.toString());
            }


            return false;
        }

        // UPDATE osoby SET stav = true WHERE meno = 'Jozef' AND priezvisko = 'Lipsic' AND Vek = '88'
    private String updateState = "UPDATE osoby SET stav = ? WHERE meno = ? AND priezvisko = ? AND Vek = ?";

    public boolean changeState(String name, String lastName,int age , Boolean state){

        if (age < 1 || age >= 100) return false;

        try (Connection connection = getConnection()){
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(updateState);
                ps.setBoolean(1, state);
                ps.setString(2, name);
                ps.setString(3, lastName);
                ps.setInt(4, age);


                int update = ps.executeUpdate();
                log.print("Updated state for: " + name + " "+ lastName + " ("+age+")" + " to: " + state);
                return update == 1;
            }
        } catch (Exception e) { log.error(e.toString()); }
        return false;
    }


    public Persons getPersonByName(String name, String lastName, int age){
        if (name == null || lastName == null || age < 5) {
            log.error("INCORET name or lastname or age");
            return null;
        }

        String sql2 = "SELECT * FROM osoby WHERE meno = ? AND priezvisko = ? AND Vek = ? ";

        try (Connection connection = getConnection()){
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(sql2);
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setInt(3, age);

                List<Persons> list =  executeSelect(ps);
                if (list.isEmpty())
                    return null;
                else {
                    return list.get(0);
                }

            }
        } catch (Exception e) { log.error(e.toString()); }
        return null;
    }


    private String deletePerson = "DELETE FROM osoby WHERE meno = ? AND priezvisko = ? AND Vek = ? ";
    public boolean deletePerson(String name, String lastName, int age ){


        if (getPersonByName(name,lastName,age) == null){
            log.error("No Persons found");
            return false;
        }

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(deletePerson);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);

            if (ps.executeUpdate() == 1){
                log.print("Deleted person: " + name + " " + lastName + " (" +age+ ")");
                return true;
            }
        } catch (Exception e) { log.error(e.toString()); }
        return false;
    }

}
