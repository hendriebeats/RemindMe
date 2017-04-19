package com.example.hendriebeats.remindme;

import android.content.Context;

import java.util.Calendar;

public class Task {
    private int id;
    private String title;
    private String dateTime;
    private String description;
    private String location;
    private int owner;

    public DatabaseHandler db;

    public Task(String title, String dateTime, String description, String location, int owner) {
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.location = location;
        this.owner = owner;
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Integer getOwner() { return owner; }

    // This method requires context when called blame Peter if doesnt work
    public String getOwnerName(Context context) {
        db = new DatabaseHandler(context);
        return db.getUserById(getOwner()).getName();}

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
