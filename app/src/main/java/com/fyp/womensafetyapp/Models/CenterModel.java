package com.fyp.womensafetyapp.Models;

public class CenterModel {

    private String title;
    private Double lat;
    private Double lng;

    public CenterModel(String title, Double lat, Double lng) {
        this.title = title;
        this.lat = lat;
        this.lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
