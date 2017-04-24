package com.example.hendriebeats.remindme;

import android.content.Context;

import java.util.Calendar;

public class Task {
    private int id;
    private String title;
    private String date;
    private String time;
    private String description;
    private String location;
    private int ownerId;

    public DatabaseHandler db;

    public Task(String title, String date, String time, String description, String location, int ownerId) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.location = location;
        this.ownerId = ownerId;
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getOwnerId() { return ownerId; }

    // This method requires context when called blame Peter if doesnt work
    public String getOwnerName(Context context) {
        db = new DatabaseHandler(context);
        return db.getUserById(getOwnerId())
                .getName();}

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String dateTime) {
        this.date = date;
    }

    public void setTime(String dateTime) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
