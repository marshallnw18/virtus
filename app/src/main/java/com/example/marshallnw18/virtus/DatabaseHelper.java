package com.example.marshallnw18.virtus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nick Marshall on 1/19/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "virtus.db";
    //private static final String COLUMN_ID = "_id";

    /*
    https://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
    https://www.youtube.com/watch?v=q3rhteIierY
     */

    //users Table
    private static final String TABLE_USER = "users";
    private static final String USER_ID = "_id";
    private static final String COL_HEIGHT = "height";
    private static final String COL_WEIGHT = "weight";
    private static final String COL_GENDER = "gender";
    private static final String COL_USER_DATE = "date";

    //lifts table
    private static final String TABLE_LIFTS = "lifts";
    private static final String LIFTS_ID = "_id";
    private static final String COL_BENCH = "bench_one_rep_max";
    private static final String COL_SQUAT = "squat_one_rep_max";
    private static final String COL_DEADLIFT = "deadlift_one_rep_max";
    private static final String COL_WILKS = "wilks_coefficient";
    private static final String COL_LIFTS_DATE = "date";

    //nutrition table
    private static final String TABLE_NUTRITION = "nutrition";
    private static final String NUTRITION_ID = "_id";
    private static final String COL_PROTEINS = "proteins";
    private static final String COL_CARBS = "carbohydrates";
    private static final String COL_FATS = "fats";
    private static final String COL_TDEE = "total_daily_energy_expenditure";
    private static final String COL_NUTRITION_DATE = "date";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    public static final String DATABASE_CREATE_USER = "create table "
            + TABLE_USER + "(" + USER_ID + " integer primary key autoincrement, "
            + COL_HEIGHT + " int, "
            + COL_WEIGHT + " int, "
            + COL_GENDER + " string, "
            + COL_USER_DATE + " text)";

    public static final String DATABASE_CREATE_LIFTS = "create table "
            + TABLE_LIFTS + "(" + LIFTS_ID + " integer primary key autoincrement, "
            + COL_SQUAT + " int, "
            + COL_BENCH + " int, "
            + COL_DEADLIFT + " int, "
            + COL_WILKS + " double, "
            + COL_LIFTS_DATE + " text)";

    public static final String DATABASE_CREATE_NUTRITION = "create table "
            + TABLE_NUTRITION + "(" + NUTRITION_ID + " integer primary key autoincrement, "
            + COL_CARBS + " int, "
            + COL_PROTEINS + " int, "
            + COL_FATS + " int, "
            + COL_TDEE + " int, "
            + COL_NUTRITION_DATE + " text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_USER);
        db.execSQL(DATABASE_CREATE_LIFTS);
        db.execSQL(DATABASE_CREATE_NUTRITION);
    }

    /*
    TODO: Review OnUpdate structuring for future DB implementations
    https://thebhwgroup.com/blog/how-android-sqlite-onupgrade
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Database Helper", "Upgrading database from version " + oldVersion + " to version " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIFTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUTRITION);
        onCreate(db);
    }

    //https://stackoverflow.com/questions/15473325/inserting-current-date-and-time-in-sqlite-database for DATETIME information
    public boolean addDataUsers(int height, int weight, String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_HEIGHT, height);
        contentValues.put(COL_WEIGHT, weight);
        contentValues.put(COL_GENDER, gender);
        //Insert current Date/Time to updated entry
        contentValues.put(COL_USER_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        long result = db.insert(TABLE_USER, null, contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addDataLifts(int squat, int bench, int deadlift, double wilks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SQUAT, squat);
        contentValues.put(COL_BENCH, bench);
        contentValues.put(COL_DEADLIFT, deadlift);
        contentValues.put(COL_WILKS, wilks);
        //Insert current Date/Time to updated entry
        contentValues.put(COL_LIFTS_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        long result = db.insert(TABLE_LIFTS, null, contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addDataNutrition(int carbs, int proteins, int fats, int tdee){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_CARBS, carbs);
        contentValues.put(COL_PROTEINS, proteins);
        contentValues.put(COL_FATS, fats);
        contentValues.put(COL_TDEE, tdee);
        //Insert current Date/Time to updated entry
        contentValues.put(COL_NUTRITION_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        long result = db.insert(TABLE_NUTRITION, null, contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public String populateTDEEData(DatabaseHelper db){
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] columns = new String[]{NUTRITION_ID, COL_CARBS, COL_PROTEINS, COL_FATS, COL_TDEE, COL_NUTRITION_DATE};
        String orderBy = COL_NUTRITION_DATE + " DESC";
        String limit = "1";

        //SQL Query that orders based on the most recent DateTime entry
        Cursor c = SQ.query(TABLE_NUTRITION, columns, null, null, null, null, orderBy, limit);
        String result = "";

        int indexTDEE = c.getColumnIndex(COL_TDEE);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = c.getString(indexTDEE);
        }

        return result;
    }

    public String populateCarbData(DatabaseHelper db){
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] columns = new String[]{NUTRITION_ID, COL_CARBS, COL_PROTEINS, COL_FATS, COL_TDEE, COL_NUTRITION_DATE};
        String orderBy = COL_CARBS + " DESC";
        String limit = "1";

        //SQL Query that orders based on the most recent DateTime entry
        Cursor c = SQ.query(TABLE_NUTRITION, columns, null, null, null, null, orderBy, limit);
        String result = "";

        int indexCarbs = c.getColumnIndex(COL_CARBS);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = c.getString(indexCarbs);
        }

        return result;
    }

    public String populateFatsData(DatabaseHelper db){
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] columns = new String[]{NUTRITION_ID, COL_CARBS, COL_PROTEINS, COL_FATS, COL_TDEE, COL_NUTRITION_DATE};
        String orderBy = COL_FATS + " DESC";
        String limit = "1";

        //SQL Query that orders based on the most recent DateTime entry
        Cursor c = SQ.query(TABLE_NUTRITION, columns, null, null, null, null, orderBy, limit);
        String result = "";

        int indexFats = c.getColumnIndex(COL_FATS);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = c.getString(indexFats);
        }

        return result;
    }

    public String populateProteinData(DatabaseHelper db){
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] columns = new String[]{NUTRITION_ID, COL_CARBS, COL_PROTEINS, COL_FATS, COL_TDEE, COL_NUTRITION_DATE};
        String orderBy = COL_PROTEINS + " DESC";
        String limit = "1";

        //SQL Query that orders based on the most recent DateTime entry
        Cursor c = SQ.query(TABLE_NUTRITION, columns, null, null, null, null, orderBy, limit);
        String result = "";

        int indexProteins = c.getColumnIndex(COL_PROTEINS);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = c.getString(indexProteins);
        }

        return result;
    }

    public String populateWilksData(DatabaseHelper db){
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] columns = new String[]{LIFTS_ID, COL_SQUAT, COL_BENCH, COL_DEADLIFT, COL_WILKS, COL_LIFTS_DATE};
        String orderBy = COL_WILKS + " DESC";
        String limit = "1";

        //SQL Query that orders based on the most recent DateTime entry
        Cursor c = SQ.query(TABLE_LIFTS, columns, null, null, null, null, orderBy, limit);
        String result = "";

        int indexWilks = c.getColumnIndex(COL_WILKS);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = c.getString(indexWilks);
        }

        return result;
    }

    public String populateBenchData(DatabaseHelper db){
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] columns = new String[]{LIFTS_ID, COL_SQUAT, COL_BENCH, COL_DEADLIFT, COL_WILKS, COL_LIFTS_DATE};
        String orderBy = COL_BENCH + " DESC";
        String limit = "1";

        //SQL Query that orders based on the most recent DateTime entry
        Cursor c = SQ.query(TABLE_LIFTS, columns, null, null, null, null, orderBy, limit);
        String result = "";

        int indexBench = c.getColumnIndex(COL_BENCH);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = c.getString(indexBench);
        }

        return result;
    }

    public String populateSquatData(DatabaseHelper db){
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] columns = new String[]{LIFTS_ID, COL_SQUAT, COL_BENCH, COL_DEADLIFT, COL_WILKS, COL_LIFTS_DATE};
        String orderBy = COL_SQUAT + " DESC";
        String limit = "1";

        //SQL Query that orders based on the most recent DateTime entry
        Cursor c = SQ.query(TABLE_LIFTS, columns, null, null, null, null, orderBy, limit);
        String result = "";

        int indexSquat = c.getColumnIndex(COL_SQUAT);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = c.getString(indexSquat);
        }

        return result;
    }

    public String populateDeadliftData(DatabaseHelper db){
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] columns = new String[]{LIFTS_ID, COL_SQUAT, COL_BENCH, COL_DEADLIFT, COL_WILKS, COL_LIFTS_DATE};
        String orderBy = COL_DEADLIFT + " DESC";
        String limit = "1";

        //SQL Query that orders based on the most recent DateTime entry
        Cursor c = SQ.query(TABLE_LIFTS, columns, null, null, null, null, orderBy, limit);
        String result = "";

        int indexDeadlift = c.getColumnIndex(COL_DEADLIFT);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = c.getString(indexDeadlift);
        }

        return result;
    }

}
