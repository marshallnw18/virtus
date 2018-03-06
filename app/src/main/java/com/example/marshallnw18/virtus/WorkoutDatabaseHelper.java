package com.example.marshallnw18.virtus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by marshallnw18 on 3/6/2018.
 */

public class WorkoutDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "virtus_workout.db";
    private final String TAG = "WORKOUT DATABASE HELPER";

    //TODO: In supportingClasses: Create POJOs for each table

    /*
     * Eight Table Database containing ne Table for each week of the program:
     * Each table contains:
     * - Information on the lifts for each day of the week
     * - Sets and Reps for each lift
     * - Percentage of the user's 1RM to accurately display weight for each day
     */

    //Week One
    private static final String TABLE_WEEK_ONE = "week_one";
    private static final String WEEK_ONE_ID = "_id";
    private static final String COL_DAY = "day";
    private static final String COL_LIFT = "lift";
    private static final String COL_SETS_REPS = "sets_reps";
    private static final String COL_1RM_PERCENTAGE = "1rm_percentage";

    //Week Two
    private static final String TABLE_WEEK_TWO = "week_two";
    private static final String WEEK_TWO_ID = "_id";

    //Week Three
    private static final String TABLE_WEEK_THREE = "week_three";
    private static final String WEEK_THREE_ID = "_id";

    //Week Four
    private static final String TABLE_WEEK_FOUR = "week_four";
    private static final String WEEK_FOUR_ID = "_id";

    //Week Five
    private static final String TABLE_WEEK_FIVE = "week_one";
    private static final String WEEK_FIVE_ID = "_id";

    //Week Six
    private static final String TABLE_WEEK_SIX = "week_one";
    private static final String WEEK_SIX_ID = "_id";

    //Week Seven
    private static final String TABLE_WEEK_SEVEN = "week_one";
    private static final String WEEK_SEVEN_ID = "_id";

    //Week Eight
    private static final String TABLE_WEEK_EIGHT = "week_one";
    private static final String WEEK_EIGHT_ID = "_id";


    public WorkoutDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    //SQLite Creation Queries

    public static final String DATABASE_CREATE_WEEK_ONE = "create table "
            + TABLE_WEEK_ONE + "(" + WEEK_ONE_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static final String DATABASE_CREATE_WEEK_TWO = "create table "
            + TABLE_WEEK_TWO + "(" + WEEK_TWO_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static final String DATABASE_CREATE_WEEK_THREE = "create table "
            + TABLE_WEEK_THREE + "(" + WEEK_THREE_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static final String DATABASE_CREATE_WEEK_FOUR = "create table "
            + TABLE_WEEK_FOUR + "(" + WEEK_FOUR_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static final String DATABASE_CREATE_WEEK_FIVE = "create table "
            + TABLE_WEEK_FIVE + "(" + WEEK_FIVE_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static final String DATABASE_CREATE_WEEK_SIX = "create table "
            + TABLE_WEEK_SIX + "(" + WEEK_SIX_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static final String DATABASE_CREATE_WEEK_SEVEN = "create table "
            + TABLE_WEEK_SEVEN + "(" + WEEK_SEVEN_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static final String DATABASE_CREATE_WEEK_EIGHT = "create table "
            + TABLE_WEEK_EIGHT + "(" + WEEK_EIGHT_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_WEEK_ONE);
        db.execSQL(DATABASE_CREATE_WEEK_TWO);
        db.execSQL(DATABASE_CREATE_WEEK_THREE);
        db.execSQL(DATABASE_CREATE_WEEK_FOUR);
        db.execSQL(DATABASE_CREATE_WEEK_FIVE);
        db.execSQL(DATABASE_CREATE_WEEK_SIX);
        db.execSQL(DATABASE_CREATE_WEEK_SEVEN);
        db.execSQL(DATABASE_CREATE_WEEK_EIGHT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading workout database from version " + oldVersion + " to version " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK_ONE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK_TWO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK_THREE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK_FOUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK_FIVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK_SIX);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK_SEVEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK_EIGHT);
        onCreate(db);
    }
}
