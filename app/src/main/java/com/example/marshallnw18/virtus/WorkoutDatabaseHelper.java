package com.example.marshallnw18.virtus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.marshallnw18.virtus.supportingClasses.WeekOne;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private static final String COL_1RM_PERCENTAGE = "one_rm_percentage";

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
    private static final String TABLE_WEEK_FIVE = "week_five";
    private static final String WEEK_FIVE_ID = "_id";

    //Week Six
    private static final String TABLE_WEEK_SIX = "week_six";
    private static final String WEEK_SIX_ID = "_id";

    //Week Seven
    private static final String TABLE_WEEK_SEVEN = "week_seven";
    private static final String WEEK_SEVEN_ID = "_id";

    //Week Eight
    private static final String TABLE_WEEK_EIGHT = "week_eight";
    private static final String WEEK_EIGHT_ID = "_id";


    public WorkoutDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    //================================================================================
    // Record Creation for Week One
    //================================================================================

    public static final String DATABASE_CREATE_WEEK_ONE = "create table "
            + TABLE_WEEK_ONE + "(" + WEEK_ONE_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static String INSERT_DATA_WEEK_ONE = "INSERT INTO " + TABLE_WEEK_ONE
            + " (" + COL_DAY + "," + COL_LIFT + "," + COL_SETS_REPS + "," + COL_1RM_PERCENTAGE + ")"
            + " VALUES "
            + "('Day 1', 'Squat', '3x8', 0.7), "
            + "('Day 1', 'Deadlift', '2x8', 0.7), "
            + "('Day 1', 'Bicep Exercise', '3x8', null), "
            + "('Day 1', 'Shrugs', '3x10', null), "

            + "('Day 2', 'Bench Press', '3x8', 0.7), "
            + "('Day 2', 'Weighted Pull-Up', '3x6', null), "
            + "('Day 2', 'Overhead Press', '3x6', null), "
            + "('Day 2', 'Tricep Exercise', '3x8', null), "

            + "('Day 3', 'Squat', '3x6', 0.75), "
            + "('Day 3', 'Deadlift', '2x6', 0.75), "
            + "('Day 3', 'Bicep Exercise', '3x8', null), "
            + "('Day 3', 'Rear Delt Exercise', '3x10', null), "

            + "('Day 4', 'Bench Press', '3x6', 0.75), "
            + "('Day 4', 'Pendlay Row', '3x8', null), "
            + "('Day 4', 'Incline Dumbbell Bench Press', '3x10', null), "
            + "('Day 4', 'Tricep Exercise', '3x8', null), "

            + "('Day 5', 'Squat', '3x4', 0.775), "
            + "('Day 5', 'Deadlift', '2x4', 0.775), "
            + "('Day 5', 'Bicep Exercise', '3x5', null), "
            + "('Day 5', 'Core Exercise', '3x5 / 3x12', null), "

            + "('Day 6', 'Bench Press', '3x4', 0.775), "
            + "('Day 6', 'Close-Grip Bench Press', '3x8', null), "
            + "('Day 6', 'Weighted Pull-Up', '3x5', null), "
            + "('Day 6', 'Side Lateral Raises', '3x10', null); ";


    //================================================================================
    // Record Creation for Week Two
    //================================================================================

    public static final String DATABASE_CREATE_WEEK_TWO = "create table "
            + TABLE_WEEK_TWO + "(" + WEEK_TWO_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static String INSERT_DATA_WEEK_TWO = "INSERT INTO " + TABLE_WEEK_TWO
            + " (" + COL_DAY + "," + COL_LIFT + "," + COL_SETS_REPS + "," + COL_1RM_PERCENTAGE + ")"
            + " VALUES "
            + "('Day 1', 'Squat', '4x8', 0.725), "
            + "('Day 1', 'Deadlift', '2x8', 0.75), "
            + "('Day 1', 'Bicep Exercise', '4x8', null), "
            + "('Day 1', 'Shrugs', '4x10', null), "

            + "('Day 2', 'Bench Press', '4x8', 0.72), "
            + "('Day 2', 'Weighted Pull-Up', '4x6', null), "
            + "('Day 2', 'Overhead Press', '4x6', null), "
            + "('Day 2', 'Tricep Exercise', '4x8', null), "

            + "('Day 3', 'Squat', '5x6', 0.775), "
            + "('Day 3', 'Deadlift', '2x6', 0.775), "
            + "('Day 3', 'Bicep Exercise', '4x8', null), "
            + "('Day 3', 'Rear Delt Exercise', '3x12', null), "

            + "('Day 4', 'Bench Press', '5x6', 0.775), "
            + "('Day 4', 'Pendlay Row', '4x8', null), "
            + "('Day 4', 'Incline Dumbbell Bench Press', '4x10', null), "
            + "('Day 4', 'Tricep Exercise', '4x8', null), "

            + "('Day 5', 'Squat', '6x4', 0.8), "
            + "('Day 5', 'Deadlift', '2x4', 0.825), "
            + "('Day 5', 'Bicep Exercise', '4x5', null), "
            + "('Day 5', 'Core Exercise', '3x5 / 3x12', null), "

            + "('Day 6', 'Bench Press', '6x4', 0.825), "
            + "('Day 6', 'Close-Grip Bench Press', '4x8', null), "
            + "('Day 6', 'Weighted Pull-Up', '4x5', null), "
            + "('Day 6', 'Side Lateral Raises', '3x12', null); ";

    //================================================================================
    // Record Creation for Week Three
    //================================================================================

    public static final String DATABASE_CREATE_WEEK_THREE = "create table "
            + TABLE_WEEK_THREE + "(" + WEEK_THREE_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static String INSERT_DATA_WEEK_THREE = "INSERT INTO " + TABLE_WEEK_THREE
            + " (" + COL_DAY + "," + COL_LIFT + "," + COL_SETS_REPS + "," + COL_1RM_PERCENTAGE + ")"
            + " VALUES "
            + "('Day 1', 'Squat', '4x8, Week One + 10lb', null), "
            + "('Day 1', 'Deadlift', '2x8, Week One + 10lb', null), "
            + "('Day 1', 'Bicep Exercise', '5x8', null), "
            + "('Day 1', 'Shrugs', '5x10', null), "

            + "('Day 2', 'Bench Press', '4x8, Week One + 5lb', null), "
            + "('Day 2', 'Weighted Pull-Up', '5x6', null), "
            + "('Day 2', 'Overhead Press', '5x6', null), "
            + "('Day 2', 'Tricep Exercise', '5x8', null), "

            + "('Day 3', 'Squat', '5x6, Week One + 10lb', null), "
            + "('Day 3', 'Deadlift', '2x6, Week One + 10lb', null), "
            + "('Day 3', 'Bicep Exercise', '5x8', null), "
            + "('Day 3', 'Rear Delt Exercise', '4x10', null), "

            + "('Day 4', 'Bench Press', '5x6, Week One + 5lb', null), "
            + "('Day 4', 'Pendlay Row', '5x8', null), "
            + "('Day 4', 'Incline Dumbbell Bench Press', '5x10', null), "
            + "('Day 4', 'Tricep Exercise', '5x8', null), "

            + "('Day 5', 'Squat', '6x4, Week One + 10lb', null), "
            + "('Day 5', 'Deadlift', '3x4, Week One + 10lb', null), "
            + "('Day 5', 'Bicep Exercise', '5x5', null), "
            + "('Day 5', 'Core Exercise', '3x5 / 3x12', null), "

            + "('Day 6', 'Bench Press', '6x4, Week One + 5lb', null), "
            + "('Day 6', 'Close-Grip Bench Press', '5x8', null), "
            + "('Day 6', 'Weighted Pull-Up', '5x5', null), "
            + "('Day 6', 'Side Lateral Raises', '4x10', null); ";

    //================================================================================
    // Record Creation for Week Four
    //================================================================================

    public static final String DATABASE_CREATE_WEEK_FOUR = "create table "
            + TABLE_WEEK_FOUR + "(" + WEEK_FOUR_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static String INSERT_DATA_WEEK_FOUR;

    //================================================================================
    // Record Creation for Week Five
    //================================================================================

    public static final String DATABASE_CREATE_WEEK_FIVE = "create table "
            + TABLE_WEEK_FIVE + "(" + WEEK_FIVE_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static String INSERT_DATA_WEEK_FIVE;

    //================================================================================
    // Record Creation for Week Six
    //================================================================================

    public static final String DATABASE_CREATE_WEEK_SIX = "create table "
            + TABLE_WEEK_SIX + "(" + WEEK_SIX_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static String INSERT_DATA_WEEK_SIX;

    //================================================================================
    // Record Creation for Week Seven
    //================================================================================

    public static final String DATABASE_CREATE_WEEK_SEVEN = "create table "
            + TABLE_WEEK_SEVEN + "(" + WEEK_SEVEN_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static String INSERT_DATA_WEEK_SEVEN;

    //================================================================================
    // Record Creation for Week Eight
    //================================================================================

    public static final String DATABASE_CREATE_WEEK_EIGHT = "create table "
            + TABLE_WEEK_EIGHT + "(" + WEEK_EIGHT_ID + " integer primary key autoincrement, "
            + COL_DAY + " text, "
            + COL_LIFT + " text, "
            + COL_SETS_REPS + " text, "
            + COL_1RM_PERCENTAGE + " real)";

    public static String INSERT_DATA_WEEK_EIGHT;

    //TODO: Write Pull Queries for the Database


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

        try {
            db.execSQL(INSERT_DATA_WEEK_ONE);
            db.execSQL(INSERT_DATA_WEEK_TWO);
            db.execSQL(INSERT_DATA_WEEK_THREE);
            db.execSQL(INSERT_DATA_WEEK_FOUR);
            db.execSQL(INSERT_DATA_WEEK_FIVE);
            db.execSQL(INSERT_DATA_WEEK_SIX);
            db.execSQL(INSERT_DATA_WEEK_SEVEN);
            db.execSQL(INSERT_DATA_WEEK_EIGHT);
            Log.d(TAG, "Workout database succesfully built!");
        } catch (SQLiteException e){
            Log.d(TAG, "Workout database failed insertion");
        }

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
