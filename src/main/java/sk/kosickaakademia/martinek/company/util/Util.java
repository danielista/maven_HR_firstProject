package sk.kosickaakademia.martinek.company.util;

import org.json.simple.*;
import sk.kosickaakademia.martinek.company.entity.User;
import sk.kosickaakademia.martinek.company.entity.ockovanie.Persons;
import sk.kosickaakademia.martinek.company.enumartoris.Gender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Util {

    public String getJson(List<User> list){


        if(list.isEmpty()) return "{}";

        JSONObject object = new JSONObject();
        object.put("datetime",getCurrentDateTime());
        object.put("size",list.size());
        JSONArray jsonArray=new JSONArray();
        for(User u : list ) {
            JSONObject userJson = new JSONObject();
            userJson.put("id", u.getId());
            userJson.put("fname", u.getFname());
            userJson.put("lname", u.getLname());
            userJson.put("age", u.getAge());
            userJson.put("gender", u.getGender().toString());
            jsonArray.add(userJson);
        }
        object.put("users",jsonArray);

        return object.toJSONString();
    }

    public String getJsonForPersons(List<Persons> list){


        if(list.isEmpty()) return "{}";

        JSONObject object = new JSONObject();
        object.put("datetime",getCurrentDateTime());
        object.put("size",list.size());
        JSONArray jsonArray=new JSONArray();
        for(Persons persons : list ) {
            JSONObject personsJson = new JSONObject();
            personsJson.put("meno", persons.getFirstname());
            personsJson.put("priezvisko", persons.getLastname());
            personsJson.put("stav", persons.getState());
            personsJson.put("vek", persons.getAge());
            jsonArray.add(personsJson);
        }
        object.put("persons",jsonArray);

        return object.toJSONString();
    }

    public String getJson(User user){


        if(user==null) return "{}";
        JSONObject object = new JSONObject();
        object.put("datetime",getCurrentDateTime());
        object.put("size",1);
        JSONArray jsonArray=new JSONArray();
        JSONObject userJson = new JSONObject();
        userJson.put("id",user.getId()) ;
        userJson.put("fname",user.getFname()) ;
        userJson.put("lname",user.getLname()) ;
        userJson.put("age",user.getAge()) ;
        userJson.put("gender",user.getGender().toString()) ;   // ? overit ?
        jsonArray.add(userJson);
        object.put("users",jsonArray);

        return object.toJSONString();
    }

    public String getCurrentDateTime(){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        return date;  // "2021-03-05 15:07:23"
    }

    public String normalizeName(String name){
        if(name==null || name.equals(""))
            return "";

        name=name.trim();

        return Character.toUpperCase(name.charAt(0))+name.substring(1).toLowerCase();
    }

    public String getOverview(List<User> list) {
        int count = list.size();
        int male = 0;
        int female = 0;
        int minAge = count > 0 ? list.get(0).getAge() : 0;
        int maxAge = count > 0 ? list.get(0).getAge() : 0;
        int sumAge = 0;
        for(User user : list){
            if(user.getGender() == Gender.MALE) male++;
            if(user.getGender() == Gender.FEMALE) female++;
            sumAge = sumAge + user.getAge();
            if(minAge > user.getAge()) minAge = user.getAge();
            if(maxAge < user.getAge()) maxAge = user.getAge();
        }
        double avgAge = (double) sumAge/count;

        JSONObject object = new JSONObject();
        object.put("totalNumber",count);
        object.put("min",minAge);
        object.put("max",maxAge);
        object.put("countMale",male);
        object.put("countFemale",female);
        object.put("average",avgAge);

        return object.toJSONString();
    }

    // generovanie TOKENU .. nahodný string ;) :D
    // 4O znakovy token pismena  a cislice
    public String generateToken(){
        String token = "";
        Random rnd = new Random();
        for(int i=0; i<40; i++){
            int help = rnd.nextInt(3); // ak bude 0  chcem capital letter.., 1 lowercase, 2-number
            switch(help){
                case 0: token = token + (char)(rnd.nextInt(26) + 65) ;break;
                case 1: token = token + (char)(  rnd.nextInt(26) + 97); break;
                case 2: token = token + (char)(rnd.nextInt(10) + 48); break;
            }
        }
        return token;
    }

}