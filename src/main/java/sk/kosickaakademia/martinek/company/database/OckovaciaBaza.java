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





}
