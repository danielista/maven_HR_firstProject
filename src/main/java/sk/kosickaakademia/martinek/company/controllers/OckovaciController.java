package sk.kosickaakademia.martinek.company.controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

}
