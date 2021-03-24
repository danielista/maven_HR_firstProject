package sk.kosickaakademia.martinek.company.controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.martinek.company.log.Log;
import sk.kosickaakademia.martinek.company.util.Util;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SecretController {

    private final String PASSWORD = "Kosice2021!";
    Map<String, String> map = new HashMap<>();
    Log log = new Log();


    @GetMapping("/secret")
    public String secret(@RequestHeader("token") String header){
        System.out.println(header);
        String token = header.substring(7);

        for(Map.Entry<String, String> entry : map.entrySet()){
            if(entry.getValue().equals(token)){
                return "secret";
            }
        }


        return "401";
    }

    // niečo metoda post lebo posielame meno a heslo
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String auth){

        JSONObject object = null;
        try {
            object = (JSONObject) new JSONParser().parse(auth);
            String login = ((String)object.get("login")).trim();
            String password = ((String)object.get("password")).trim();

            System.out.println(login + " " + password);

            if(login == null || password == null){
                log.error("chyba meno abo heslo ;)");
                return ResponseEntity.status(400).body("");
            }

            // if password is correct
            if(password.equals(PASSWORD)){
                String token = new Util().generateToken();
                map.put(login,token);
                log.print("OK všetko okej user je prihlaseny");

                // tu si idem vyskladať JSON :D
                JSONObject obj = new JSONObject();
                obj.put("login",login);
                obj.put("token","Bearer "+token);

                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(obj.toJSONString());
            }else{
                log.error("Wrong password");
                return ResponseEntity.status(401).body("");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("token") String header){
        String token = header.substring(7);
        String login = null;
        for (Map.Entry<String, String> entry : map.entrySet()){
            if(entry.getValue().equalsIgnoreCase(token)){
                login = entry.getKey();
                break;
            }
        }

        if(login != null){
            map.remove(login);
            log.info("Logout user: " + login);
        }else{
            log.error("logout FAILED. User " + login + " neexistuje... ;)");
        }
        return  ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("");

    }



}
