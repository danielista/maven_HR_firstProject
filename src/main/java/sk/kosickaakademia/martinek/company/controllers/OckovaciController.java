package sk.kosickaakademia.martinek.company.controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.martinek.company.database.Databaza;
import sk.kosickaakademia.martinek.company.database.OckovaciaBaza;
import sk.kosickaakademia.martinek.company.entity.User;
import sk.kosickaakademia.martinek.company.entity.ockovanie.Persons;
import sk.kosickaakademia.martinek.company.enumartoris.Gender;
import sk.kosickaakademia.martinek.company.log.Log;
import sk.kosickaakademia.martinek.company.util.Util;

import java.util.List;


@RestController
public class OckovaciController {

    Log log = new Log();

    @GetMapping("/persons")
    public ResponseEntity<String> getAllUsers(){
        List<Persons> list = new OckovaciaBaza().getAllPersons();
        String json = new Util().getJsonForPersons(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @PostMapping("/persons/new")
    public ResponseEntity<String> insertNewPerson(@RequestBody String data){
        System.out.println(data);
        try {

            JSONObject object = (JSONObject) new JSONParser().parse(data);
            String firstName = ((String)object.get("fname")).trim();
            String lastName = ((String)object.get("lname")).trim();
            Boolean state = object.get("state").toString().contains("1");
            int age = Integer.parseInt(String.valueOf(object.get("age")));


            // tak toto sú podmienky
            if(firstName==null || lastName==null || firstName.length() == 0 || lastName.length() == 0 || age < 1){
                log.error("MISSING NAME ;)");
                JSONObject object1 = new JSONObject();
                object1.put("ERROR...", "are u sure that u type your name and age CORRECTLY? :D");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(object1.toJSONString());
            }
            if(state==null) state = false;

            Persons person = new Persons(firstName,lastName,state,age);
            new OckovaciaBaza().insertNewPerson(person);

        } catch (Exception e) {
            JSONObject obj = new JSONObject();
            log.error("MISSING NAME ;)");
            obj.put("Error:","INCORRECT BODY...");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(obj.toJSONString());

        }
        return null;
    }

    @PutMapping("/persons/{state}")
    public  ResponseEntity<String> changeState(@PathVariable Boolean state, @RequestBody String body) throws ParseException {

        JSONObject object = (JSONObject) new JSONParser().parse(body);
        String firstName = ((String)object.get("fname")).trim();
        String lastName = ((String)object.get("lname")).trim();
        //Boolean state = object.get("state").toString().contains("1");
        int age = Integer.parseInt(String.valueOf(object.get("age")));

        if(firstName==null || lastName==null || firstName.length() == 0 || lastName.length() == 0 || age < 1){
            log.error("MISSING NAME ;)");
            JSONObject object1 = new JSONObject();
            object1.put("ERROR...", "are u sure that u type your name + lastName + age CORRECTLY? :D");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(object1.toJSONString());
        }
        if(state==null) state = false;

        boolean result = new OckovaciaBaza().changeState(firstName,lastName,age,state);
        int status;
        if(result) status = 200;
        else status = 404;
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body("{}");

    }





}
