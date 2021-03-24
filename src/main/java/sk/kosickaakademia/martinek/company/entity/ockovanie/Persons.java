package sk.kosickaakademia.martinek.company.entity.ockovanie;

import java.sql.Date;

public class Persons {


    public Persons(String firstname, String lastname, Boolean state, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.state = state;
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Boolean getState() {
        return state;
    }

    public int getAge() {
        return age;
    }

    private String firstname;
    private String lastname;
    private Boolean state;
    private int age;


    @Override
    public String toString() {
        return "Persons{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", state=" + state +
                ", age=" + age +
                '}';
    }
}
