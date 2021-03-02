package sk.kosickaakademia.martinek.company;

import org.json.simple.*;
import sk.kosickaakademia.martinek.company.database.Databaza;

import java.sql.SQLException;

/**
 * Hello world! helllo
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        Databaza db = new Databaza();
        try {
            db.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }
}
