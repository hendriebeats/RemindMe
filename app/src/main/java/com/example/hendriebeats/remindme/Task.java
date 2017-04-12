package com.example.hendriebeats.remindme;

import java.util.Calendar;

public class Task {
    private int id;
    private String title;
    private String dateTime;
    private String description;
    private String location;

    public Task(String title, String dateTime, String description, String location) {
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.location = location;
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
}
