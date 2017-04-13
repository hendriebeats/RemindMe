package com.example.hendriebeats.remindme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String KEY_TASK_TITLE = "title";
    private static final String KEY_TASK_DATETIME = "dateTime";
    private static final String KEY_TASK_DESCRIPTION = "description";
    private static final String KEY_TASK_LOCATION = "location";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USER_NAME + " TEXT, "
                + KEY_USER_PH_NO + " TEXT, "
                + KEY_USER_EMAIL + " TEXT, "
                + KEY_USER_PASS + " TEXT"
                + ")";

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TASK_TITLE + " TEXT, "
                + KEY_TASK_DATETIME + " TEXT, "
                + KEY_TASK_DESCRIPTION + " TEXT, "
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
    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getName());
        values.put(KEY_USER_PH_NO, user.getPhoneNumber());
        values.put(KEY_USER_EMAIL, user.getEmail());
        values.put(KEY_USER_PASS, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // Adding new task
    void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_TITLE, task.getTitle()); // Contact Name
        values.put(KEY_TASK_DATETIME, task.getDateTime()); // idk what this will look like
        values.put(KEY_TASK_DESCRIPTION, task.getDescription());
        values.put(KEY_TASK_LOCATION, task.getLocation());

        // Inserting Row
        db.insert(TABLE_TASKS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // Getting single user
    User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[] {
                        KEY_USER_ID,
                        KEY_USER_NAME,
                        KEY_USER_PH_NO,
                        KEY_USER_EMAIL,
                        KEY_USER_PASS},
                KEY_USER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return user
        return user;
    }

    // Getting single user
    User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[] {
                        KEY_USER_ID,
                        KEY_USER_NAME,
                        KEY_USER_PH_NO,
                        KEY_USER_EMAIL,
                        KEY_USER_PASS},
                KEY_USER_EMAIL + "=?",
                new String[] {email},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return user
        return user;
    }
    // Getting single task
    Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_TASKS,
                new String[] {
                        KEY_TASK_ID,
                        KEY_TASK_TITLE,
                        KEY_TASK_DATETIME,
                        KEY_TASK_DESCRIPTION,
                        KEY_TASK_LOCATION},
                KEY_TASK_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return task
        return task;
    }

    // Getting All Users
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setTitle(cursor.getString(1));
                task.setDateTime(cursor.getString(2));
                task.setDescription(cursor.getString(3));
                task.setLocation(cursor.getString(4));
                // Adding task to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return task list
        return taskList;
    }

    // Updating single user
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getName()); // Contact Name
        values.put(KEY_USER_PH_NO, user.getPhoneNumber()); // Contact Phone
        values.put(KEY_USER_EMAIL, user.getEmail());
        values.put(KEY_USER_PASS, user.getPassword());

        // updating row
        return db.update(TABLE_USERS, values, KEY_USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    // Updating single task
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_TITLE, task.getTitle()); // Contact Name
        values.put(KEY_TASK_DATETIME, task.getDateTime()); // idk what this will look like
        values.put(KEY_TASK_DESCRIPTION, task.getDescription());
        values.put(KEY_TASK_LOCATION, task.getLocation());

        // updating row
        return db.update(TABLE_TASKS, values, KEY_TASK_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }

    // Deleting single user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }

    // Deleting single task
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_TASK_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
        db.close();
    }

    // Getting task count
    public int getTasksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
