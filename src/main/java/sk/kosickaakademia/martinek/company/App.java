package sk.kosickaakademia.martinek.company;


import org.json.simple.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.kosickaakademia.martinek.company.database.Databaza;
import sk.kosickaakademia.martinek.company.database.OckovaciaBaza;
import sk.kosickaakademia.martinek.company.entity.User;
import sk.kosickaakademia.martinek.company.entity.ockovanie.Persons;
import sk.kosickaakademia.martinek.company.enumartoris.Gender;
import sk.kosickaakademia.martinek.company.mongodatabaza.MongoDatabasa;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;


@SpringBootApplication
public class App 
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException {


      //SpringApplication.run(App.class,args);

        MongoDatabasa db = new MongoDatabasa();
        db.getConnectionToMongo();
        JSONObject obj = new JSONObject();
        obj.put("meno","Daniel");
        obj.put("priezvisko","Riciardo");
        db.insertUser(obj);
      // OckovaciaBaza db = new OckovaciaBaza();
       // db.insertNewPerson(new Persons("Bohdan","Neviemaky",false,30));
       // db.changeState("Jozef","Lipsic",88,false);
       // db.deletePerson("Jozef","Lipsic", 88);

       /* OckovaciaBaza db = new OckovaciaBaza();
        List<Persons> zoznam = db.getAllPersons();
        for(Persons p:zoznam) System.out.println(p.toString());
*/

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
