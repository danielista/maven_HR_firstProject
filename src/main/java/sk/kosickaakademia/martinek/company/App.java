package sk.kosickaakademia.martinek.company;


import org.json.simple.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.kosickaakademia.martinek.company.database.Databaza;
import sk.kosickaakademia.martinek.company.entity.User;
import sk.kosickaakademia.martinek.company.enumartoris.Gender;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;


@SpringBootApplication
public class App 
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException {


        SpringApplication.run(App.class,args);

        /*    System.out.println( "Hello World!" );
        Databaza db = new Databaza();

       // db.insertNewUser(new User("Kristián","Smolko",20, 0));

        //List<User> list = db.getMales();
        List<User> list = db.getUsersByAge(11,55);
        for(User u:list )
        System.out.println(u.toString());
*/



    }
}
