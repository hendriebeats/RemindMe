package com.example.hendriebeats.remindme;

/**
 * User
 *
 * This class establishes the User object that will hold
 * all of the user data used within the application
 * along with all of the associated methods that may be
 * used to obtain or change information.
 *
 * @since 4/11/2017
 *
 * Created by Hendrie Beats
 */
public class User {

    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String salt;

    /**
     * User()
     *
     * This is the constructor for the User object that
     * is used to keep track of users and their associated
     * values.
     *
     * This constructor does not require an id as an int.
     *
     * @param name (String)
     * @param phoneNumber (String)
     * @param email (String)
     * @param password (String)
     *
     * @since
     */
    public User(String name, String phoneNumber, String email, String password, String salt) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    /**
     * User()
     *
     * This is the constructor for the User object that
     * is used to keep track of users and their associated
     * values
     *
     * @param id (int)
     * @param name (String)
     * @param phoneNumber (String)
     * @param email (String)
     * @param password (String)
     *
     * @since
     */
    public User(int id, String name, String phoneNumber, String email, String password, String salt) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.salt = salt;

    }

    /**
     * getId()
     *
     * Returns the id of the current User object
     * referenced in the database.
     *
     * @return id (int)
     * @since
     */
    public int getId() {
        return id;
    }

    /**
     * getName()
     *
     * Returns the name of the current User object
     * referenced in the database.
     *
     * @return name (String)
     * @since
     */
    public String getName() {
        return name;
    }

    /**
     * getPhoneNumber()
     *
     * Returns the phone number of the current User object
     * referenced in the database.
     *
     * @return phoneNumber (String)
     * @since
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * getEmail()
     *
     * Returns the email of the current User object
     * referenced in the database.
     *
     * @return email (String)
     * @since
     */
    public String getEmail() {
        return email;
    }

    /**
     * getPassword()
     *
     * Returns the password of the current User object
     * referenced in the database.
     *
     * Note: Currently returns password in clear text
     *
     * @return password (String)
     * @since
     */
    public String getPassword() {
        return password;
    }

    /**
     * setId()
     *
     * sets the Id of the current User object.
     *
     * @param id (int)
     * @since
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * setName()
     *
     * sets the name of the current User object.
     *
     * @param name (String)
     * @since
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setPhoneNumber()
     *
     * sets the phone number of the current User object.
     *
     * @param phoneNumber (String)
     * @since
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * setEmail()
     *
     * sets the email of the current User object.
     *
     * @param email (String)
     * @since
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * setPassword()
     *
     * sets the password of the current User object.
     *
     * @param password (String)
     * @since
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

