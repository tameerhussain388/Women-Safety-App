package com.fyp.womensafetyapp.Utils;

public class Message {

    public static String getCurrentMessage(double latitude,double longitude){
        String message = "Emergency!\nNeed Help\n";
        message += "Current Location:\n";
        message += "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
        return message;
    }

    public static String getLastMessage(double latitude,double longitude){
        String message = "Emergency!\nNeed Help\n";
        message += "Last Known Location:\n";
        message += "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
        return message;
    }
}
