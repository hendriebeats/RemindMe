package com.example.hendriebeats.remindme;

import java.util.Calendar;

public class Task {
    private String title;
    private Calendar dateTime;
    private String description;
    private String location;

    public Task(int title, Calendar dateTime, String description, String location) {
        this.name = name;
        this.author = author;
        this.imageResource = imageResource;
        this.imageUrl = imageUrl;
    }

    public int getName() {
        return name;
    }

    public int getAuthor() {
        return author;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }
    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
