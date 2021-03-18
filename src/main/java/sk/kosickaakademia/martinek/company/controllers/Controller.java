package sk.kosickaakademia.martinek.company.controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.martinek.company.database.Databaza;
import sk.kosickaakademia.martinek.company.entity.User;
import sk.kosickaakademia.martinek.company.enumartoris.Gender;
import sk.kosickaakademia.martinek.company.log.Log;
import sk.kosickaakademia.martinek.company.util.Util;

import java.util.List;


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


    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers(){
        List<User> list = new Databaza().getAllUsers();
        String json = new Util().getJson(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    // /users/age?from=A&to=B
    @GetMapping("users/age")
    public ResponseEntity<String> getUserByAge(@RequestParam(value="from") int from,@RequestParam(value = "to") int to){

        if (from>to || from<1 ) {
            JSONObject obj = new JSONObject();
            log.error("years are not correct");
            obj.put("Error:", "INCORRECT year...");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(obj.toJSONString());
        }
            Databaza db = new Databaza();
            List<User> list = db.getUsersByAge(from,to);
            String json = new Util().getJson(list);
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(json);
    }


    @PutMapping("/user/{id}")
    public  ResponseEntity<String> changeAge(@PathVariable Integer id, @RequestBody String body) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(body);
        String data = String.valueOf(object.get("newage"));
            int newage = Integer.parseInt(data);
            if(newage < 1) return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("{}");
            boolean result = new Databaza().changeAge(id,newage);
            int status;
                if(result) status = 200;
                else status = 404;
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body("{}");
    }

    @GetMapping("/")
    public ResponseEntity<String> overview(){
        //tu si vytiahnem z db všetkych userov v LISTE
        List<User> list = new Databaza().getAllUsers();
        String jsonOvervieew = new Util().getOverview(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(jsonOvervieew.toString());
    }


}
