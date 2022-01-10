package com.fyp.womensafetyapp.Models;

public class UserModel {
    public UserModel(String uID,String name,String number,String age,String email)
    {
        this.uID=uID;
        this.name=name;
        this.number=number;
        this.age=age;
        this.email=email;
    }
    public String uID;
    public String name;
    public String number;
    public String age;
    public String email;
}
