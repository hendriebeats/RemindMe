package com.example.hendriebeats.remindme;

import android.content.Context;

/**
 * Place
 *
 * This class establishes the Place object that will hold
 * all of the location data used within the application
 * along with all of the associated methods that may be
 * used to obtain or change information.
 *
 * @since
 */
public class Place {
    private int id;
    private String title;
    private String latitude;
    private String longitude;
    private String address;
    private String locale;
    private String radius;

    public DatabaseHandler db;

    /**
     * Place()
     *
     * This is the constructor for the place object that
     * is used to keep track of places and their associated
     * values
     *
     * @param title (String)
     * @param latitude (String)
     * @param longitude (String)
     * @param address (String)
     * @param locale (String)
     * @param radius (String)
     *
     * @since
     */
    public Place(String title, String latitude, String longitude, String address, String locale, String radius) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.locale= locale;
        this.radius= radius;
    }

    public Place() {

    }

    /**
     * getId()
     *
     * Returns the id of the current place object
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
     * Returns the title of the current place object
     * referenced in the database.
     *
     * @return title (String)
     * @since
     */
    public String getTitle() {
        return title;
    }

    /**
     * getLatitude()
     *
     * Returns the latitude of the current place object
     * referenced in the database.
     *
     * @return latitude (String)
     * @since
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * getLongitude()
     *
     * Returns the longitude of the current place object
     * referenced in the database.
     *
     * @return longitude (String)
     * @since
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * getCoords()
     *
     * Returns the combined latitude and longitude
     * of the current place object referenced in
     * the database.
     *
     * @return ""+latitude+", "+longitude+""
     *         latitude, longitude (String)
     * @since
     */
    public String getCoords() { return ""+latitude+", "+longitude+""; }

    /**
     * getAddress()
     *
     * Returns the address of the current place object
     * referenced in the database.
     *
     * @return address (String)
     * @since
     */
    public String getAddress() {
        return address;
    }

    /**
     * getLocale()
     *
     * Returns the locale of the current place object
     * referenced in the database.
     *
     * @return locale (String)
     * @since
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Returns the radius of the current place object
     * referenced in the database.
     *
     * @return radius
     * @since
     */
    public String getRadius() {
        return radius;
    }

    /**
     * setId()
     *
     * sets the Id of the current place object.
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
     * sets the title of the current place object.
     *
     * @param title (String)
     * @since
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * setLatitude()
     *
     * sets the latitude of the current place object.
     *
     * @param latitude (String)
     * @since
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * setLongitude()
     *
     * sets the longitude of the current place object.
     *
     * @param longitude (String)
     * @since
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * setAddress()
     *
     * sets the address of the current place object.
     *
     * @param address (String)
     * @since
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * setLocale()
     *
     * sets the locale of the current place object.
     *
     * @param locale (String)
     * @since
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * sets the radius of the current place object.
     *
     * @param radius
     * @since
     */
    public void setRadius(String radius) {
        this.radius = radius;
    }
}
