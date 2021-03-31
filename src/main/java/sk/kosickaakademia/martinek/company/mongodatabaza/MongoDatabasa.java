package sk.kosickaakademia.martinek.company.mongodatabaza;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sk.kosickaakademia.martinek.company.entity.User;
import sk.kosickaakademia.martinek.company.log.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class MongoDatabasa {


    MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<org.bson.Document> test;
    Log log = new Log();

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

    public void insertUser(JSONObject object){
        database = mongoClient.getDatabase("myFirstDb");
        test = database.getCollection("users");
        object.put("gender",
                object.get("gender") == null ? 2 : object.get("gender").equals("female") ? 1 : 0);
        Document doc = Document.parse(object.toString());
        test.insertOne(doc);
        log.print("User added to mongodb");
    }


    public List<User> getUsers(){
        List<User> list = new ArrayList<>();
        database = mongoClient.getDatabase("myFirstDb");
        test = database.getCollection("users");
        MongoCollection<Document> table = test;
        for (Document doc : table.find()){
            try {
                JSONObject object = (JSONObject) new JSONParser().parse(doc.toJson());
                list.add(new User((String) object.get("fname"),
                        (String) object.get("lname"),
                        Integer.parseInt(String.valueOf(object.get("age"))),
                        Integer.parseInt(String.valueOf(object.get("gender")))
                ));
            } catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
        }
        return list;
    }
    
    
    
}
