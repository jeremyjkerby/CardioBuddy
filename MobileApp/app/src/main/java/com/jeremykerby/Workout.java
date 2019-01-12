package com.jeremykerby;

public class Workout {

    String w_id, w_date, w_type, w_resistance, w_duration, w_distance, w_calories, w_note;

    public Workout (String w_date, String w_type, String w_resistance, String w_duration, String w_distance, String w_calories, String w_note){
        this.w_date = w_date;
        this.w_type = w_type;
        this.w_resistance = w_resistance;
        this.w_duration = w_duration;
        this.w_distance = w_distance;
        this.w_calories = w_calories;
        this.w_note = w_note;
    }

    public Workout (String w_id, String w_date, String w_type, String w_resistance, String w_duration, String w_distance, String w_calories, String w_note){
        this.w_id = w_id;
        this.w_date = w_date;
        this.w_type = w_type;
        this.w_resistance = w_resistance;
        this.w_duration = w_duration;
        this.w_distance = w_distance;
        this.w_calories = w_calories;
        this.w_note = w_note;
    }

    public String getID() {
        return this.w_id;
    }

    public String getDate() {
        return this.w_date;
    }

    public String getType() {
        return this.w_type;
    }

    public String getResistance() {
        return this.w_resistance;
    }

    public String getDuration() {
        return this.w_duration;
    }

    public String getDistance() {
        return this.w_distance;
    }

    public String getCalories() {
        return this.w_calories;
    }

    public String getNote() {
        return this.w_note;
    }

}

