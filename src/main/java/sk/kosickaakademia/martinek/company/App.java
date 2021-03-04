package sk.kosickaakademia.martinek.company;


import org.json.simple.*;
import sk.kosickaakademia.martinek.company.database.Databaza;
import sk.kosickaakademia.martinek.company.entity.User;
import sk.kosickaakademia.martinek.company.enumartoris.Gender;

import java.sql.SQLException;
import java.util.List;

/**
 * Hello world! helllo HAAALOO :: NEJDE MI TO :D
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException {

        System.out.println( "Hello World!" );
        Databaza db = new Databaza();

        db.insertNewUser(new User("Kristián","Smolko",20, 0));

        //List<User> list = db.getMales();
        List<User> list = db.getUsersByAge(11,55);
        for(User u:list )
        System.out.println(u.toString());




    }
}
