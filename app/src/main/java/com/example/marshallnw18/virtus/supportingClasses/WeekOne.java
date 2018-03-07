package com.example.marshallnw18.virtus.supportingClasses;

/**
 * Created by marshallnw18 on 3/6/2018.
 */

public class WeekOne {
    private int _id;
    private String day;
    private String lift;
    private String sets_reps;
    private double one_rm_percentage;

    public WeekOne(){
    }

    public WeekOne(int _id, String day, String lift, String sets_reps, double one_rm_percentage) {
        this._id = _id;
        this.day = day;
        this.lift = lift;
        this.sets_reps = sets_reps;
        this.one_rm_percentage = one_rm_percentage;
    }

    public int get_id() {
        return _id;
    }

    public String getDay() {
        return day;
    }

    public String getLift() {
        return lift;
    }

    public String getSets_reps() {
        return sets_reps;
    }

    public double getOne_rm_percentage() {
        return one_rm_percentage;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setLift(String lift) {
        this.lift = lift;
    }

    public void setSets_reps(String sets_reps) {
        this.sets_reps = sets_reps;
    }

    public void setOne_rm_percentage(double one_rm_percentage) {
        this.one_rm_percentage = one_rm_percentage;
    }
}
