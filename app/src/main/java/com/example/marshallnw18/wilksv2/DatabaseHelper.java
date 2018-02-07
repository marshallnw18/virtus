package com.example.marshallnw18.wilksv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Nick Marshall on 1/19/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "virtus.db";
    private static final String COLUMN_ID = "_id";

    /*
    https://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
    https://www.youtube.com/watch?v=q3rhteIierY
     */

    //users Table
    private static final String TABLE_USER = "users";
    private static final String COL_HEIGHT = "height";
    private static final String COL_WEIGHT = "weight";
    private static final String COL_GENDER = "gender";
    private static final String COL_TDEE = "total_daily_energy_expenditure";

    //private static final String TABLE_LIFTS = "lifts";
    private static final String COL_BENCH = "bench_one_rep_max";
    private static final String COL_SQUAT = "squat_one_rep_max";
    private static final String COL_DEADLIFT = "deadlift_one_rep_max";
    private static final String COL_WILKS = "wilks_coefficient";

    //private static final String TABLE_NUTRITION = "nutrition";
    private static final String COL_PROTEINS = "proteins";
    private static final String COL_CARBS = "carbohydrates";
    private static final String COL_FATS = "fats";
    private static final String COL_DATE = "date";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    public static final String DATABASE_CREATE_USER = "create table "
            + TABLE_USER + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COL_HEIGHT + " int, "
            + COL_WEIGHT + " int, "
            + COL_GENDER + " string, "
            + COL_WILKS + " double, "
            + COL_SQUAT + " int, "
            + COL_BENCH + " int, "
            + COL_DEADLIFT + " int, "
            + COL_CARBS + " int, "
            + COL_PROTEINS + " int, "
            + COL_FATS + " int, "
            + COL_TDEE + " int, "
            + COL_DATE + " text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_USER);
    }

    /*
    TODO: Review OnUpdate structuring for future DB implementations
    https://thebhwgroup.com/blog/how-android-sqlite-onupgrade
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean addData(int height, int weight, String gender,
                           int squat, int bench, int deadlift, int wilks,
                           int carbs, int protein, int fats, int tdee){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_HEIGHT, height);
        contentValues.put(COL_WEIGHT, weight);
        contentValues.put(COL_GENDER, gender);
        contentValues.put(COL_SQUAT, squat);
        contentValues.put(COL_BENCH, bench);
        contentValues.put(COL_DEADLIFT, deadlift);
        contentValues.put(COL_WILKS, wilks);
        contentValues.put(COL_CARBS, carbs);
        contentValues.put(COL_PROTEINS, protein);
        contentValues.put(COL_FATS, fats);
        contentValues.put(COL_TDEE, tdee);
        //contentValues.put(COL_DATE, date);

        long result = db.insert(TABLE_USER, null, contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

}
