package sk.kosickaakademia.martinek.company.mongodatabaza;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import javax.swing.text.Document;

public class MongoDatabasa {


    MongoClient mongoClient;

    public MongoClient getConnectionToMongo() {
        try {
          mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017"));

            //Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("SAXES connectedto mongo db on your localhost ;) :D");
            return mongoClient;
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return null;
    }


    
    
    
    
}
