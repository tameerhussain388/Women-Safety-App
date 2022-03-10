package com.fyp.womensafetyapp.Models;

public class Center {

    private final String title;
    private final Double lat;
    private final Double lng;

    public Center(String title, Double lat, Double lng) {
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
