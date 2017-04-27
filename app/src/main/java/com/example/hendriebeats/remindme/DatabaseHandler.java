package com.example.hendriebeats.remindme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * DatabaseHandler
 *
 * This class establishes the database for the application
 * as well as all of the associated tables and queries that
 * may be used to create, modify, or delete information.
 *
 * This class extends SQLiteOpenHelper
 *
 * @extends SQLiteOpenHelper
 *
 * @since
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "RemindMeDatabase";

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_PLACES = "places";

    // Users Table Columns names
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_PH_NO = "phone_number";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_PASS = "password";

    // Tasks Table Columns names
    private static final String KEY_TASK_ID = "id";
    private static final String KEY_TASK_TITLE = "title";
    private static final String KEY_TASK_DATE = "date";
    private static final String KEY_TASK_TIME = "time";
    private static final String KEY_TASK_DESCRIPTION = "description";
    private static final String KEY_TASK_ISCOMPLETE = "iscomplete";
    private static final String KEY_TASK_OWNER_ID = "ownerId";
    private static final String KEY_TASK_PLACE_ID = "placeId";

    // Places Table Columns names
    private static final String KEY_PLACE_ID = "id";
    private static final String KEY_PLACE_TITLE = "title";
    private static final String KEY_PLACE_LATITUDE = "latitude";
    private static final String KEY_PLACE_LONGITUDE = "longitude";
    private static final String KEY_PLACE_ADDRESS = "address";
    private static final String KEY_PLACE_LOCALE = "locale";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    /**
     * onCreate()
     *
     * This method instantiates the tables needed for the database
     * and creates columns to hold the associated data.
     *
     * Tables are designed as follows:
     *
     * ------------------------------------------------------------------
     * |    TABLE_USERS                                                 |
     * ------------------------------------------------------------------
     * |    KEY_USER_ID             INTEGER     Primary                 |
     * |    KEY_USER_NAME           TEXT                                |
     * |    KEY_USER_PH_NO          TEXT                                |
     * |    KEY_USER_EMAIL          TEXT                                |
     * |    KEY_USER_PASS           TEXT                                |
     * |                                                                |
     * ------------------------------------------------------------------
     *
     * ------------------------------------------------------------------
     * |    TABLE_TASKS                                                 |
     * ------------------------------------------------------------------
     * |    KEY_TASK_ID             INTEGER     Primary                 |
     * |    KEY_TASK_TITLE          TEXT                                |
     * |    KEY_TASK_DATE           TEXT                                |
     * |    KEY_TASK_TIME           TEXT                                |
     * |    KEY_TASK_DESCRIPTION    TEXT                                |
     * |    KEY_TASK_OWNER_ID       INTEGER     Foreign (TABLE_USERS)   |
     * |    KEY_TASK_PLACE_ID       INTEGER     Foreign (TABLE_PLACES)  |
     * |                                                                |
     * ------------------------------------------------------------------
     *
     * ------------------------------------------------------------------
     * |    TABLE_USERS                                                 |
     * ------------------------------------------------------------------
     * |    KEY_PLACE_ID            INTEGER     Primary                 |
     * |    KEY_PLACE_TITLE         TEXT                                |
     * |    KEY_PLACE_LATITUDE      TEXT                                |
     * |    KEY_PLACE_LONGITUDE     TEXT                                |
     * |    KEY_PLACE_ADDRESS       TEXT                                |
     * |    KEY_PLACE_LOCALE        TEXT                                |
     * |                                                                |
     * ------------------------------------------------------------------
     *
     * @param db (SQLiteDatabase)
     *
     * @since
     */
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
                + KEY_TASK_DATE + " TEXT, "
                + KEY_TASK_TIME + " TEXT, "
                + KEY_TASK_DESCRIPTION + " TEXT, "
                + KEY_TASK_ISCOMPLETE + " TEXT, "
                + KEY_TASK_OWNER_ID + " INTEGER, "
                + KEY_TASK_PLACE_ID + " INTEGER, "
                + "FOREIGN KEY (" + KEY_TASK_OWNER_ID + ") "
                + "REFERENCES " + TABLE_USERS + " (" + KEY_USER_ID + ")"
                + "FOREIGN KEY (" + KEY_TASK_PLACE_ID + ") "
                + "REFERENCES " + TABLE_PLACES + " (" + KEY_PLACE_ID + ")"
                + ");";

        String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_PLACES + "("
                + KEY_PLACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_PLACE_TITLE + " TEXT, "
                + KEY_PLACE_LATITUDE + " TEXT, "
                + KEY_PLACE_LONGITUDE + " TEXT, "
                + KEY_PLACE_ADDRESS + " TEXT, "
                + KEY_PLACE_LOCALE + " TEXT"
                + ");";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);
        db.execSQL(CREATE_PLACES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);

        // Create tables again
        onCreate(db);
    }

    /**
     * addUser()
     *
     * This method accepts a User object containing associated information
     * and adds the user into the database.
     *
     * @param user (User)
     * @since
     */
    public void addUser(User user) {

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

    /**
     * addTask()
     *
     * This method accepts a Task object containing associated information
     * and adds the task into the database.
     *
     * @param task (Task)
     * @since
     */
    public void addTask(Task task) {
        Log.d(TAG, "adding Task...");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_TITLE, task.getTitle());
        values.put(KEY_TASK_DATE, task.getDate());
        values.put(KEY_TASK_TIME, task.getTime());
        values.put(KEY_TASK_DESCRIPTION, task.getDescription());
        values.put(KEY_TASK_ISCOMPLETE, task.isComplete()? 1 : 0);
        values.put(KEY_TASK_OWNER_ID, task.getOwnerId()); // This gets the User ID of the current user.
        values.put(KEY_TASK_PLACE_ID, task.getPlaceId());

        // Inserting Row
        db.insert(TABLE_TASKS, null, values);

        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    /**
     * addPlace()
     *
     * This method accepts a Place object containing associated information
     * and adds the place(location) into the database.
     *
     * @param place (Place)
     * @since
     */
    public void addPlace(Place place) {
        Log.d(TAG, "adding Place...");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_TITLE, place.getTitle());
        values.put(KEY_PLACE_LATITUDE , place.getLatitude());
        values.put(KEY_PLACE_LONGITUDE , place.getLongitude());
        values.put(KEY_PLACE_ADDRESS , place.getAddress());
        values.put(KEY_PLACE_LOCALE , place.getLocale());

        // Inserting Row
        db.insert(TABLE_PLACES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    /**
     * getUserById()
     *
     * This method accepts the id of a user object as an int
     * and returns the associated User object.
     *
     * @param id (int)
     * @return user (User)
     * @since
     */
    public User getUserById(int id) {
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

        User user = new User(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        // return user
        return user;
    }

    /**
     * getTaskById()
     *
     * This method accepts the id of a Task object as an int
     * and returns the associated Task object.
     *
     * @param id (int)
     * @return task (Task)
     * @since
     */
    public Task getTaskById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_TASKS,
                new String[] {
                        KEY_TASK_ID,
                        KEY_TASK_TITLE,
                        KEY_TASK_DATE,
                        KEY_TASK_TIME,
                        KEY_TASK_DESCRIPTION,
                        KEY_TASK_ISCOMPLETE,
                        KEY_TASK_OWNER_ID,
                        KEY_TASK_PLACE_ID},
                KEY_TASK_ID + "=?",
                new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5) > 0, //get boolean value
                cursor.getInt(6),
                cursor.getInt(7)
                );
        // return task
        return task;
    }

    public Task getMostRecentTask() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String [] {"MAX(" + KEY_TASK_ID + ")"}, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return getTaskById(cursor.getInt(0));
    }

    /**
     * getPlaceById()
     *
     * This method accepts the id of a Place object as an int
     * and returns the associated Place object.
     *
     * @param id (int)
     * @return place (Place)
     * @since
     */
    public Place getPlaceById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PLACES,
                new String[] {
                        KEY_PLACE_ID,
                        KEY_PLACE_TITLE,
                        KEY_PLACE_LATITUDE,
                        KEY_PLACE_LONGITUDE,
                        KEY_PLACE_ADDRESS,
                        KEY_PLACE_LOCALE},
                KEY_PLACE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Place place = new Place(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
        // return place
        return place;
    }

    public Place getMostRecentPlace() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String [] {"MAX(" + KEY_PLACE_ID + ")"}, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return getPlaceById(cursor.getInt(0));
    }

    /**
     * getUserByEmail()
     *
     * This method accepts the email of a user object as String
     * and returns the associated User object.
     *
     * @param email (String)
     * @return user (User)
     * @since
     */
    public User getUserByEmail(String email) {
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

        User user = new User(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        // return user
        return user;
    }

    /**
     * getAllTaskTitlesByUser()
     *
     * This method accepts the id of a user object as an int
     * and returns all of the titles of the tasks that are
     * associated with that user and returns that list within
     * an ArrayList<String>.
     *
     * @param userId (int)
     * @return titleList (ArrayList<String>)
     * @since
     */
    public ArrayList<String> getAllTaskTitlesByUser(int userId) {
        ArrayList<Task> taskList = getAllTasksByUser(userId);
        ArrayList<String> titleList = new ArrayList<>();

        for(int i = 0; i < taskList.size(); i++){
            titleList.add(taskList.get(i).getTitle());
        }
        return titleList;
    }

    public ArrayList<String> getAllTaskPlaceTitlesByUser(int userId) {
        ArrayList<Task> placeList = getAllTasksByUser(userId);
        ArrayList<String> titleList = new ArrayList<>();

        for(int i = 0; i < placeList.size(); i++){
            titleList.add(getPlaceById(placeList.get(i).getPlaceId()).getTitle());
        }
        return titleList;
    }

    /**
     * getAllTasksByUser()
     *
     * This method accepts the id of a user object as an int
     * and returns all of the tasks that are associated with
     * that user and returns that list of Task objects within
     * an ArrayList<Task>.
     *
     * @param userId (int)
     * @return taskList (ArrayList<String>)
     * @since
     */
    public ArrayList<Task> getAllTasksByUser(int userId) {
        ArrayList<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS
                + " WHERE " + KEY_TASK_OWNER_ID + " = " + userId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(0));
                task.setTitle(cursor.getString(1));
                task.setDate(cursor.getString(2));
                task.setTime(cursor.getString(3));
                task.setDescription(cursor.getString(4));
                task.setComplete(cursor.getInt(5) > 0); //get boolean value
                task.setOwnerId(cursor.getInt(6));
                task.setPlaceId(cursor.getInt(7));
                // Adding task to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        // return task list
        return taskList;
    }

    /**
     * updateUser()
     *
     * This method accepts a User object and updates the associated
     * information within the database.
     *
     * @param user (User)
     * @return db.update(TABLE_USERS, values, KEY_USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) })
     *          success? (int)
     * @since
     */
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

    /**
     * updateTask()
     *
     * This method accepts a Task object and updates the associated
     * information within the database.
     *
     * @param task (Task)
     * @return db.update(TABLE_TASKS, values, KEY_TASK_ID + " = ?",
            new String[] { String.valueOf(task.getId()) })
     *          success? (int)
     * @since
     */
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_TITLE, task.getTitle());
        values.put(KEY_TASK_DATE, task.getDate());
        values.put(KEY_TASK_TIME, task.getTime());
        values.put(KEY_TASK_DESCRIPTION, task.getDescription());
        values.put(KEY_TASK_ISCOMPLETE, task.isComplete()? 1 : 0); //set boolean value

        // updating row
        return db.update(TABLE_TASKS, values, KEY_TASK_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }

    public int updatePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_TITLE, place.getTitle());
        values.put(KEY_PLACE_LATITUDE, place.getLatitude());
        values.put(KEY_PLACE_LONGITUDE, place.getLongitude());
        values.put(KEY_PLACE_ADDRESS, place.getAddress());
        values.put(KEY_PLACE_LOCALE, place.getLocale());

        // updating row
        return db.update(TABLE_PLACES, values, KEY_PLACE_ID + " = ?",
                new String[] { String.valueOf(place.getId()) });
    }

    /**
     * deleteUser()
     *
     * This method accepts a User object and deletes the associated
     * information within the database.
     *
     * @param user (User)
     * @since
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }

    /**
     * deleteTask()
     *
     * This method accepts a Task object and deletes the associated
     * information within the database.
     *
     * @param task (Task)
     * @since
     */
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_TASK_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });

        try{
            db.delete(TABLE_PLACES, KEY_PLACE_ID + " = ?", new String[]
                    { String.valueOf(task.getPlaceId()) }); // IDK if this will work.
        }catch(Exception e){

        }
        db.close();
    }

    /**
     * deletePlace()
     *
     * This method accepts a Place object and deletes the associated
     * information within the database.
     *
     * Note: Using this method may cause the application to crash
     *
     * @param place (Place)
     * @since
     */
    public void deletePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLACES, KEY_PLACE_ID + " = ?",
                new String[] { String.valueOf(place.getId()) });
        db.close();
    }

    /**
     * getTasksCount()
     *
     * This method returns the total number of tasks
     * within the database for all users as an int.
     *
     * @return cursor.getCount() (int)
     * @since
     */
    public int getTasksCount() {
        List<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // return count
        return cursor.getCount();
    }
}
