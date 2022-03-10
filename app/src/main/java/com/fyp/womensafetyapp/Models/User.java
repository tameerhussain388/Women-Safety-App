package com.fyp.womensafetyapp.Models;

public class User {

    private String id;
    private String name;
    private String number;
    private String age;
    private String email;

    public User(){

    }

    public User(String name,String number,String age,String email) {
        this.name=name;
        this.number=number;
        this.age=age;
        this.email=email;
    }

    public User(String id,String name,String number,String age,String email) {
        this.id = id;
        this.name=name;
        this.number=number;
        this.age=age;
        this.email=email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
