package com.example.hendriebeats.remindme;

import java.util.Calendar;

public class Task {
    private int id;
    private String title;
    private Calendar dateTime;
    private String description;
    private String location;

    public Task(int id, String title, Calendar dateTime, String description, String location) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Calendar getDateTime() {
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

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
