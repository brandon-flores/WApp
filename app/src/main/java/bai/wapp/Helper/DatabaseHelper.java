package bai.wapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bai.wapp.Models.Category;
import bai.wapp.Models.User;
import bai.wapp.Models.Video;

/**
 * Created by next on 4/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // Table names
    private static final String TABLE_USER = "user";
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_VIDEO = "video";
    private static final String TABLE_USERCATEGORYVIDEO = "usercategoryvideo";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_USERNAME = "user_username";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    //Category Table Column Names
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_CATEGORY_NAME = "category_name";

    //Video Table Column Names
    private static final String COLUMN_VIDEO_ID = "video_id";
    private static final String COLUMN_VIDEO_NAME = "video_name";

    //UserCategoryVideo Table Column Names
    private static final String COLUMN_ALL_VIDEO_NOTES = "usercategoryvideo_videoNotes";
    private static final String COLUMN_ALL_USER_CATEGORY = "usercategoryvideo_category";
    private static final String COLUMN_ALL_USER_VIDEO = "usercategoryvideo_video";
    private static final String COLUMN_ALL_USER_ID = "usercategoryvideo_id";

    // create user table sql query
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            //+ COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_USERNAME + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // create category table sql query
    private static final String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
            //+ COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_CATEGORY_NAME + " TEXT"
            + ")";

    //video create
    private static final String CREATE_VIDEO_TABLE = "CREATE TABLE " + TABLE_VIDEO + "("
            //+ COLUMN_VIDEO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_VIDEO_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_VIDEO_NAME + " TEXT" + ")";

    //user+category+video create
    private static final String CREATE_ALL_TABLE = "CREATE TABLE " + TABLE_USERCATEGORYVIDEO + "("
            + COLUMN_ALL_USER_ID + " INTEGER, "
            + COLUMN_ALL_USER_CATEGORY + " INTEGER, "
            + COLUMN_ALL_USER_VIDEO + " INTEGER, "
            + COLUMN_ALL_VIDEO_NOTES + " TEXT "
            //+ "FOREIGN KEY (" + COLUMN_ALL_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), "
            //+ "FOREIGN KEY (" + COLUMN_ALL_USER_CATEGORY + ") REFERENCES " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_ID + "), "
            //+ "FOREIGN KEY (" + COLUMN_ALL_USER_VIDEO + ") REFERENCES " + TABLE_VIDEO + "(" + COLUMN_VIDEO_ID + "), "
            //+ "PRIMARY KEY (" + COLUMN_ALL_USER_ID + ", " + COLUMN_ALL_USER_CATEGORY + ", " + COLUMN_ALL_USER_VIDEO + ")"
            + ");";


    //+ TABLE_VIDEO + "(" + COLUMN_VIDEO_ID + ") ON DELETE CASCADE ON UPDATE CASCADE" + ");";

    // drop table sql query
    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private static final String DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + TABLE_CATEGORY;
    private static final String DROP_VIDEO_TABLE = "DROP TABLE IF EXISTS " + TABLE_VIDEO;
    private static final String DROP_ALL_TABLE = "DROP TABLE IF EXISTS " + TABLE_USERCATEGORYVIDEO;

    /**
     * Constructor
     *
     * @param context
     */

    private static DatabaseHelper db;

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (db == null) {
            db = new DatabaseHelper(context.getApplicationContext());
        }
        return db;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_VIDEO_TABLE);
        db.execSQL(CREATE_ALL_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_CATEGORY_TABLE);
        db.execSQL(DROP_VIDEO_TABLE);
        db.execSQL(DROP_ALL_TABLE);

        // Create tables again
        onCreate(db);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

//---------------------------------USERCATEGORYVIDEO TABLE--------------------------------------///
    public boolean userHasCategory(int categoryid, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();

        //int categoryid = getCategory(categoryTitle).getId();

        String query = "SELECT " + COLUMN_ALL_USER_ID
                + " FROM " + TABLE_USERCATEGORYVIDEO
                + " WHERE " + COLUMN_ALL_USER_ID + " = " + userid
                + " AND " + COLUMN_ALL_USER_CATEGORY + " = '"
                + categoryid + "'";

        Cursor c = db.rawQuery(query, null);
        int cursorCount = c.getCount();

        c.close();
        return cursorCount > 0;
    }

    public void addUserCategory(int userid, int categoryid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ALL_USER_ID, userid);
        values.put(COLUMN_ALL_USER_CATEGORY, categoryid);

        db.insert(TABLE_USERCATEGORYVIDEO, null, values);
        Log.wtf("insert", "INSERTED!");
    }

    public boolean userHasVideo(int vidid, int userid){
        SQLiteDatabase db = this.getWritableDatabase();

        //int vidid = getVideo(vidname).getId();
        String query = "SELECT " + COLUMN_ALL_USER_ID
                + " FROM " + TABLE_USERCATEGORYVIDEO
                + " WHERE " + COLUMN_ALL_USER_ID + " = " + userid
                + " AND " + COLUMN_ALL_USER_VIDEO + " = '"
                + vidid + "'";

        Cursor c = db.rawQuery(query, null);
        int cursorCount = c.getCount();

        c.close();
        return cursorCount > 0;
    }

    public void addUserVideo(int userid, int categoryid, int videoid){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "";
        if(userCategoryIsEmpty(userid, categoryid)){
            ContentValues values = new ContentValues();
            values.put(COLUMN_ALL_USER_VIDEO, videoid); //These Fields should be your String values of actual column names
            Log.wtf("AUQNA", "TANGINA!");
            db.update(TABLE_USERCATEGORYVIDEO, values, COLUMN_ALL_USER_ID + " = " + userid
                    + " AND " + COLUMN_ALL_USER_CATEGORY + " = " + categoryid + " AND "
                    + COLUMN_ALL_USER_VIDEO + " is null", null);
        }else{
            ContentValues values = new ContentValues();
            values.put(COLUMN_ALL_USER_ID, userid);
            values.put(COLUMN_ALL_USER_CATEGORY, categoryid);
            values.put(COLUMN_ALL_USER_VIDEO, videoid);

            db.insert(TABLE_USERCATEGORYVIDEO, null, values);
            Log.wtf("insert", "INSERTED!");
        }
    }

    public boolean userCategoryIsEmpty(int userid, int categoryid){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COLUMN_ALL_USER_VIDEO
                + " FROM " + TABLE_USERCATEGORYVIDEO
                + " WHERE " + COLUMN_ALL_USER_ID + " = "
                + userid + " AND " + COLUMN_ALL_USER_CATEGORY
                + " = " + categoryid + " AND " + COLUMN_ALL_USER_VIDEO
                + " is null";

        Cursor c = db.rawQuery(query, null);
        int cursorCount = c.getCount();
        Log.wtf("CURSOR COUNT", "" + cursorCount);
        c.close();
        return cursorCount > 0;
    }

    //----------------------------------USER-------------------------------------//
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, user.getId());
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        Log.wtf("insert", "INSERTED!");
    }

    public User getUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USER
                + " WHERE " + COLUMN_USER_USERNAME + " = '"
                + username + "'";

        Cursor c = db.rawQuery(query, null);

        User user = new User();
        if(c != null && c.moveToFirst()){
            user.setId(c.getInt(c.getColumnIndex(COLUMN_USER_ID)));
            user.setUsername((c.getString(c.getColumnIndex(COLUMN_USER_USERNAME))));
            user.setName((c.getString(c.getColumnIndex(COLUMN_USER_NAME))));
            user.setPassword((c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD))));
            c.close();
        }

        return user;
    }

    public boolean checkUser(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_USER_ID + " FROM "
            + TABLE_USER + " WHERE " + COLUMN_USER_USERNAME + " = '"
            + username + "' AND " + COLUMN_USER_PASSWORD + " = '"
            + password + "'";

        Cursor c = db.rawQuery(query, null);

        int cursorCount = c.getCount();
        c.close();
        return cursorCount > 0;
    }

    public boolean checkUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_USER_ID + " FROM "
                + TABLE_USER + " WHERE " + COLUMN_USER_USERNAME + " = '"
                + username + "'";

        Cursor c = db.rawQuery(query, null);

        int cursorCount = c.getCount();
        c.close();
        return cursorCount > 0;
    }

    public List<User> getAllUser(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USER
                + " ORDER BY " + COLUMN_USER_USERNAME;

        List<User> userList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);

        if(c != null && c.moveToFirst()){
            do{
                User user = new User();
                user.setId(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_USER_ID))));
                user.setName(c.getString(c.getColumnIndex(COLUMN_USER_NAME)));
                user.setUsername(c.getString(c.getColumnIndex(COLUMN_USER_USERNAME)));
                user.setPassword(c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD)));
                userList.add(user);
            }while(c.moveToNext());
            c.close();
        }


        return userList;
    }


    //--------------------------CATEGORY---------------------------//
    public void addCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, category.getId());
        values.put(COLUMN_CATEGORY_NAME, category.getName());

        db.insert(TABLE_CATEGORY, null, values);
        Log.wtf("insert", "INSERTED!");
    }

    public boolean checkCategory(String categoryname){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_CATEGORY_ID + " FROM "
                + TABLE_CATEGORY + " WHERE " + COLUMN_CATEGORY_NAME + " = '"
                + categoryname + "'";

        Cursor c = db.rawQuery(query, null);

        int cursorCount = c.getCount();
        c.close();
        return cursorCount > 0;
    }

    public Category getCategory(String categoryname){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_CATEGORY
                + " WHERE " + COLUMN_CATEGORY_NAME + " = '"
                + categoryname + "'";

        Cursor c = db.rawQuery(query, null);

        Category category = new Category();
        if(c != null && c.moveToFirst()){
            category.setId(c.getInt(c.getColumnIndex(COLUMN_CATEGORY_ID)));
            category.setName((c.getString(c.getColumnIndex(COLUMN_CATEGORY_NAME))));
            c.close();
        }

        return category;
    }

    public List<Category> getAllCategories(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_CATEGORY
                + " ORDER BY " + COLUMN_CATEGORY_NAME;

        List<Category> categoryList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);

        if(c != null && c.moveToFirst()){
            do{
                Category category = new Category();
                category.setId(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_CATEGORY_ID))));
                category.setName(c.getString(c.getColumnIndex(COLUMN_CATEGORY_NAME)));
                categoryList.add(category);
            }while(c.moveToNext());
            c.close();
        }


        return categoryList;
    }

    //--------------------------------VIDEO---------------------------------//
    public void addVideo(Video video){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_VIDEO_ID, video.getId());
        values.put(COLUMN_VIDEO_NAME, video.getName());

        db.insert(TABLE_VIDEO, null, values);
        Log.wtf("insert", "INSERTED!");
    }

    public Video getVideo(String videoname){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_VIDEO
                + " WHERE " + COLUMN_VIDEO_NAME + " = '"
                + videoname + "'";

        Cursor c = db.rawQuery(query, null);

        Video video = new Video();
        if(c != null && c.moveToFirst()){
            video.setId(c.getInt(c.getColumnIndex(COLUMN_VIDEO_ID)));
            video.setName((c.getString(c.getColumnIndex(COLUMN_VIDEO_NAME))));
            c.close();
        }

        return video;
    }

    public List<Video> getAllVideos(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_VIDEO
                + " ORDER BY " + COLUMN_VIDEO_NAME;

        List<Video> videoList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);

        if(c != null && c.moveToFirst()){
            do{
                Video video = new Video();
                video.setId(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_VIDEO_ID))));
                video.setName(c.getString(c.getColumnIndex(COLUMN_VIDEO_NAME)));
                videoList.add(video);
            }while(c.moveToNext());
            c.close();
        }

        return videoList;
    }

    public boolean checkVideo(String videoname){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_VIDEO_ID + " FROM "
                + TABLE_VIDEO + " WHERE " + COLUMN_VIDEO_NAME + " = '"
                + videoname + "'";

        Cursor c = db.rawQuery(query, null);

        int cursorCount = c.getCount();
        c.close();
        return cursorCount > 0;
    }
}