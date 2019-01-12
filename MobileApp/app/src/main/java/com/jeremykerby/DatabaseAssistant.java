package com.jeremykerby;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseAssistant extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CardioBuddy.db";
    private static final int DATABASE_VERSION = 1;

    private static final String WORKOUTS_TABLE_NAME = "workouts";
    private static final String WORKOUTS_COLUMN_ID = "w_id";
    private static final String WORKOUTS_COLUMN_DATE = "w_date";
    private static final String WORKOUTS_COLUMN_TYPE = "w_type";
    private static final String WORKOUTS_COLUMN_RESISTANCE = "w_resistance";
    private static final String WORKOUTS_COLUMN_DURATION = "w_duration";
    private static final String WORKOUTS_COLUMN_DISTANCE = "w_distance";
    private static final String WORKOUTS_COLUMN_CALORIES = "w_calories";
    private static final String WORKOUTS_COLUMN_NOTE = "w_note";

    String TAG = "DatabaseAssistant";

    public DatabaseAssistant(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + WORKOUTS_TABLE_NAME + "(" +
                WORKOUTS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORKOUTS_COLUMN_DATE + " TEXT, " +
                WORKOUTS_COLUMN_TYPE + " TEXT, " +
                WORKOUTS_COLUMN_RESISTANCE + " TEXT, " +
                WORKOUTS_COLUMN_DURATION + " TEXT, " +
                WORKOUTS_COLUMN_DISTANCE + " TEXT, " +
                WORKOUTS_COLUMN_CALORIES + " TEXT," +
                WORKOUTS_COLUMN_NOTE + " TEXT)"
        );

        Log.d(TAG, "workouts table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUTS_TABLE_NAME);
        onCreate(db);
    }

    public void saveSingleWorkout(Workout w) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(WORKOUTS_COLUMN_DATE, w.getDate());
        cv.put(WORKOUTS_COLUMN_TYPE, w.getType());
        cv.put(WORKOUTS_COLUMN_RESISTANCE, w.getResistance());
        cv.put(WORKOUTS_COLUMN_DURATION, w.getDuration());
        cv.put(WORKOUTS_COLUMN_DISTANCE, w.getDistance());
        cv.put(WORKOUTS_COLUMN_CALORIES, w.getCalories());
        cv.put(WORKOUTS_COLUMN_NOTE, w.getNote());

        db.insert(WORKOUTS_TABLE_NAME, WORKOUTS_COLUMN_DATE, cv);
        db.close();

        Log.d(TAG, "Workout Added");
    }

    public ArrayList<Workout> getAllWorkouts() {
        ArrayList<Workout> workouts = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(WORKOUTS_TABLE_NAME, new String[]{"w_id", "w_date", "w_type", "w_resistance", "w_duration", "w_distance", "w_calories", "w_note"},
                null, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            String w_id = cursor.getString(0);
            String w_date = cursor.getString(1);
            String w_type = cursor.getString(2);
            String w_resistance = cursor.getString(3);
            String w_duration = cursor.getString(4);
            String w_distance = cursor.getString(5);
            String w_calories = cursor.getString(6);
            String w_note = cursor.getString(7);

            Log.d(TAG, "DATA " + w_id + " " + w_date + " " + w_type + " " + w_resistance + " " + w_duration + " " + w_distance + " " + w_calories + " " + w_note);
            Workout workout = new Workout(w_id, w_date, w_type, w_resistance, w_duration, w_distance, w_calories, w_note);
            workouts.add(workout);
            cursor.moveToNext();
        }

        cursor.close();

        return workouts;
    }

    public void deleteSingleWorkout(Workout w) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(WORKOUTS_TABLE_NAME, WORKOUTS_COLUMN_ID
                + " = " + w.getID(), null);
    }

    public void deleteAllWorkouts() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(WORKOUTS_TABLE_NAME, null, null);
        db.close();
    }

    public void updateSingleWorkout(Workout w) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(WORKOUTS_COLUMN_DATE, w.getDate());
        cv.put(WORKOUTS_COLUMN_TYPE, w.getType());
        cv.put(WORKOUTS_COLUMN_RESISTANCE, w.getResistance());
        cv.put(WORKOUTS_COLUMN_DURATION, w.getDuration());
        cv.put(WORKOUTS_COLUMN_DISTANCE, w.getDistance());
        cv.put(WORKOUTS_COLUMN_CALORIES, w.getCalories());
        cv.put(WORKOUTS_COLUMN_NOTE, w.getNote());

        db.update(WORKOUTS_TABLE_NAME, cv, WORKOUTS_COLUMN_ID + "=" + w.getID(), null);
        db.close();
    }

}

