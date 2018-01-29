package com.example.marshallnw18.wilksv2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nick Marshall on 1/19/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "virtus.db";
    private static final String COLUMN_ID = "_id";

    /*
    TODO: Create model classes for each table listed below.
    https://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
    https://www.youtube.com/watch?v=q3rhteIierY

    USERS done
    LIFTS done
    NUTRITION done
     */

    //users Table
    private static final String TABLE_USERS = "users";
    private static final String COL_HEIGHT = "height";
    private static final String COL_WEIGHT = "weight";
    private static final String COL_GENDER = "gender";
    private static final String COL_TDEE = "total_daily_energy_expenditure";

    //lifts Table
    private static final String TABLE_LIFTS = "lifts";
    private static final String COL_BENCH = "bench_one_rep_max";
    private static final String COL_SQUAT = "squat_one_rep_max";
    private static final String COL_DEADLIFT = "deadlift_one_rep_max";
    private static final String COL_WILKS = "wilks_coefficient";

    //nutrition Table
    private static final String TABLE_NUTRITION = "nutrition";
    private static final String COL_PROTEINS = "proteins";
    private static final String COL_CARBS = "carbohydrates";
    private static final String COL_FATS = "fats";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    //TODO: Update Database creation based on the newest tables made in Google Docs
    public static final String DATABASE_CREATE_USERS = "create table "
            + TABLE_USERS + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COL_HEIGHT + " int, "
            + COL_WEIGHT + " int, "
            + COL_GENDER + " string, "
            + COL_WILKS + " double, "
            + COL_TDEE+ " int)";

    public static final String DATABASE_CREATE_LIFTS = "create table "
            + TABLE_LIFTS + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COL_SQUAT + " int, "
            + COL_BENCH + " int, "
            + COL_DEADLIFT + " int, "
            + COL_WILKS + " double, "
            + "foreign key (" + COL_WILKS + ") references " + TABLE_USERS + "(" + COL_WILKS + ")"; //FK to User

    public static final String DATABASE_CREATE_NUTRITION = "create table "
            + TABLE_NUTRITION + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COL_CARBS + " int, "
            + COL_PROTEINS + " int, "
            + COL_FATS + " int, "
            + COL_TDEE + " int, "
            + "foreign key (" + COL_TDEE + ") references " + TABLE_USERS + "(" + COL_TDEE + ")"; //Foreign Key to User

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_USERS);
        db.execSQL(DATABASE_CREATE_LIFTS);
        db.execSQL(DATABASE_CREATE_NUTRITION);
    }

    /*
    TODO: Review OnUpdate structuring for future DB implementations
    https://thebhwgroup.com/blog/how-android-sqlite-onupgrade
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUTRITION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIFTS);
        onCreate(db);
    }

}
