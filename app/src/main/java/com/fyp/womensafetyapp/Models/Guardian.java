package com.fyp.womensafetyapp.Models;

public class Guardian {
    private String id;
    private String firstContact;
    private String secondContact;

    public Guardian(){

    }
    public Guardian(String firstContact, String secondContact) {
        this.firstContact = firstContact;
        this.secondContact = secondContact;
    }

    public Guardian(String id, String firstContact, String secondContact) {
        this.id = id;
        this.firstContact = firstContact;
        this.secondContact = secondContact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstContact() {
        return firstContact;
    }

    public void setFirstContact(String firstContact) {
        this.firstContact = firstContact;
    }

    public String getSecondContact() {
        return secondContact;
    }

    public void setSecondContact(String secondContact) {
        this.secondContact = secondContact;
    }
}
