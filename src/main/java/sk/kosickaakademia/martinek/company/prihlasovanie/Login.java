package sk.kosickaakademia.martinek.company.prihlasovanie;

import sk.kosickaakademia.martinek.company.util.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Login {
    // hashmapa do ktorej davame userovlogin a jeho cas prihlasovania
    private static Map<String, Date> blocked;
    private static Map<String, Integer> attempt;

    public Login() {
        blocked = new HashMap<>();
        attempt = new HashMap<>();
    }

    Util util = new Util();
    private String heslo = "Kosice2021!";
    public String loginUser(String username, String psw){
        for (Map.Entry<String, Date> entry : blocked.entrySet()) {
            if (entry.getKey().equals(username)){

            }
        }

        if (psw.equals(heslo)){
            for (Map.Entry<String, Integer> entry : attempt.entrySet()) {
                if (entry.getKey().equals(username))
                    attempt.remove(username);
            }
            return util.generateToken();
        } else {
            for (Map.Entry<String, Integer> entry : attempt.entrySet()) {
                if (entry.getKey().equals(username)){
                    if (entry.getValue() < 3)
                        attempt.put(username, entry.getValue() + 1);
                    else {
                        attempt.remove(username);
                        Date today = new Date();
                        blocked.put(username, today);
                    }
                }
            }
            attempt.put(username, 1);
        }


        return null;
    }
}
