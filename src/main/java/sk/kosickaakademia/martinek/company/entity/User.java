package sk.kosickaakademia.martinek.company.entity;

import sk.kosickaakademia.martinek.company.enumartoris.Gender;

public class User {
    public User(int id, String fname, String lname, int age, int gender) {
        this(fname,lname,age,gender);  // tento konštruktor si vola toho druheho koli skratenemu zapisu ;)
        this.id = id;
    }

    private int id;
    private String fname;
    private String lname;
    private int age;

    public Gender getGender() {
        return gender;
    }

    private Gender gender;

    public User(String fname, String lname, int age, int gender) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.gender = gender==0?Gender.MALE:gender==1?Gender.FEMALE:Gender.OTHER;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public int getAge() {
        return age;
    }

    public Gender isGender() {
        return gender;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
