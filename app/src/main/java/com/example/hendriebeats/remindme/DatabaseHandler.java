package com.example.hendriebeats.remindme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.text.method.DateTimeKeyListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "RemindMeDatabase";

    // Contacts table name
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TASKS = "tasks";

    // Contacts Table Columns names
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_PH_NO = "phone_number";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_PASS = "password";

    private static final String KEY_TASK_ID = "id";
    private static final String KEY_TASK_TITLE = "password";
    private static final String KEY_TASK_DATETIME = "password";
    private static final String KEY_TASK_DESCRIPTION = "password";
    private static final String KEY_TASK_LOCATION = "password";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_NAME + " TEXT,"
                + KEY_USER_PH_NO + " TEXT"
                + KEY_USER_EMAIL + " TEXT"
                + KEY_USER_PASS + " TEXT"
                + ")";

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY,"
                + KEY_TASK_TITLE + " TEXT,"
                + KEY_TASK_DATETIME + " TEXT"
                + KEY_TASK_DESCRIPTION + " TEXT"
                + KEY_TASK_LOCATION + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);



    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addUser(User user) {                                   //FIX THIS
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getName()); // Contact Name
        values.put(KEY_USER_PH_NO, user.getPhoneNumber()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // Getting single user                                      //Fix this
    Contact getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] {KEY_USER_ID,
                        KEY_USER_NAME, KEY_USER_PH_NO}, KEY_USER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // Getting All Users
    public List<User> getAllContacts() {                                //fix this
        List<User> contactList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setID(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(user);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact                                      //fix this
    public int updateContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getName());
        values.put(KEY_USER_PH_NO, user.getPhoneNumber());

        // updating row
        return db.update(TABLE_USERS, values, KEY_USER_ID + " = ?",
                new String[] { String.valueOf(user.getID()) });
    }

    // Deleting single contact
    public void deleteContact(User user) {                              //fix this
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_USER_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {                                     //fix this
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
