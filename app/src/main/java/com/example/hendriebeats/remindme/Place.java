package com.example.hendriebeats.remindme;

import android.content.Context;

public class Place {
    private int id;
    private String title;
    private String latitude;
    private String longitude;
    private String address;
    private String locale;

    public DatabaseHandler db;

    public Place(String title, String latitude, String longitude, String address, String locale) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.locale= locale;
    }

    public Place() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCoords() { return ""+latitude+", "+longitude+""; }

    public String getAddress() {
        return address;
    }

    public String getLocale() {
        return locale;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
