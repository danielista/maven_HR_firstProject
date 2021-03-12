package sk.kosickaakademia.martinek.company.controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.kosickaakademia.martinek.company.database.Databaza;
import sk.kosickaakademia.martinek.company.entity.User;
import sk.kosickaakademia.martinek.company.enumartoris.Gender;
import sk.kosickaakademia.martinek.company.log.Log;


@RestController
public class Controller {
    Log log = new Log();



    @PostMapping("/user/new")
    public ResponseEntity<String> insertNewUser(@RequestBody String data){

        try {
            JSONObject object = (JSONObject) new JSONParser().parse(data);

            String fname = ((String)object.get("fname")).trim();
            String lname = ((String)object.get("lname")).trim();
            String gender = (String)object.get("gender");
            //int age = Integer.parseInt((String)object.get("age"));
            int age = Integer.parseInt(String.valueOf(object.get("age")));

            if(fname==null || lname==null || fname.length() ==0 || lname.length()==0 || age < 1){
                log.error("MISSING NAME ;)");
                JSONObject object1 = new JSONObject();
                object1.put("ERROR...", "are u sure that u type your name and age CORRECTLY? :D");

                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(object1.toJSONString());
            }
            Gender g ;
            if(gender==null){
                g=Gender.OTHER;
            }else if(gender.equalsIgnoreCase("male")){
                g=Gender.MALE;
            }else if(gender.equalsIgnoreCase("female")){
                g=Gender.FEMALE;
            }else g=Gender.OTHER;

            //return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(data);

            User user = new User(fname,lname,age,g.getValue());
            new Databaza().insertNewUser(user);

        } catch (Exception e) {
            JSONObject obj = new JSONObject();
            log.error("MISSING NAME ;)");
            obj.put("Error:","INCORRECT BODY...");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(obj.toJSONString());

        }


return null;



        }

}
