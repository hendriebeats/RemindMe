package com.example.hendriebeats.remindme;

import android.content.Context;

import java.util.Calendar;

/**
 * Task
 *
 * This class establishes the Task object that will hold
 * all of the task data used within the application
 * along with all of the associated methods that may be
 * used to obtain or change information.
 *
 * @since
 */
public class Task {
    private int id;
    private String title;
    private String date;
    private String time;
    private String description;
    private String location;
    private int ownerId;
    private int placeId;

    public DatabaseHandler db;

    /**
     * Task()
     *
     * This is the constructor for the task object that
     * is used to keep track of tasks and their associated
     * values
     *
     * @param title (String)
     * @param date (String)
     * @param time (String)
     * @param description (String)
     * @param location (String)
     * @param ownerId (int)
     * @param placeId (int)
     *
     * @since
     */
    public Task(String title, String date, String time, String description, String location, int ownerId, int placeId) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.location = location;
        this.ownerId = ownerId;
        this.placeId = placeId;
    }

    public Task() {

    }

    /**
     * getId()
     *
     * Returns the id of the current task object
     * referenced in the database.
     *
     * @return id (int)
     * @since
     */
    public int getId() {
        return id;
    }

    /**
     * getTitle()
     *
     * Returns the title or name of the current task object
     * referenced in the database.
     *
     * @return title (String)
     * @since
     */
    public String getTitle() {
        return title;
    }

    /**
     * getDate()
     *
     * Returns the date of the current task object
     * referenced in the database.
     *
     * @return date (String)
     * @since
     */
    public String getDate() {
        return date;
    }

    /**
     * getTime()
     *
     * Returns the time of the current task object
     * referenced in the database.
     *
     * @return time (String)
     * @since
     */
    public String getTime() {
        return time;
    }

    /**
     * getDescription()
     *
     * Returns the description of the current task object
     * referenced in the database.
     *
     * @return description (String)
     * @since
     */
    public String getDescription() {
        return description;
    }

    /**
     * getLocation()
     *
     * Returns the location of the current task object
     * referenced in the database.
     *
     * This method and associated variables may become
     * obsolete when place object start to become used.
     *
     * @return location (String)
     * @since
     */
    public String getLocation() {
        return location;
    }

    /**
     * getOwnerId()
     *
     * Returns the Id of the current task object's
     * owner referenced as a foreign key in the database.
     *
     * @return ownerId (int)
     * @since
     */
    public int getOwnerId() { return ownerId; }

    /**
     * getOwnerName()
     *
     * Returns the name of the current task object's
     * owner using the ownerId as a foreign key reference
     * in the database.
     *
     * This method requires context when called blame Peter if doesn't work
     * -Peter
     *
     * @return db.getUserById(getOwnerId()).getName()
     *         name (String)
     * @since
     */
    public String getOwnerName(Context context) {
        db = new DatabaseHandler(context);
        return db.getUserById(getOwnerId())
                .getName();}

    /**
     * getPlaceId()
     *
     * Returns the Id of the current task object's
     * place referenced as a foreign key in the database.
     *
     * @return placeId (int)
     * @since
     */
    public int getPlaceId() { return placeId; }

    /**
     * setId()
     *
     * sets the Id of the current task object.
     *
     * @param id (int)
     * @since
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * setTitle()
     *
     * sets the title of the current task object.
     *
     * @param title (String)
     * @since
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * setDate()
     *
     * sets the date of the current task object.
     *
     * @param dateTime (String)
     * @since
     */
    public void setDate(String dateTime) {
        this.date = date;
    }

    /**
     * setTime()
     *
     * sets the date of the current task object.
     *
     * @param dateTime (String)
     * @since
     */
    public void setTime(String dateTime) {
        this.time = time;
    }

    /**
     * setDescription()
     *
     * sets the description of the current task object.
     *
     * @param description (String)
     * @since
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * setLocation()
     *
     * sets the location of the current task object.
     *
     * @param location (String)
     * @since
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * setTitle()
     *
     * sets the id of the referenced user as a foreign
     * key of the current task object.
     *
     * @param ownerId (int)
     * @since
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
