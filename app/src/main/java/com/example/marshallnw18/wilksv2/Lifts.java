package com.example.marshallnw18.wilksv2;

/**
 * Created by Nick Marshall on 1/22/2018.
 */

public class Lifts {
    private int _id;
    private int _squat;
    private int _bench;
    private int _deadlift;
    private double _wilks;
    private String _date;

    //TODO: May need additional constructors, dependent on inputs
    public Lifts(){}


    public Lifts(int _id, int _squat, int _bench, int _deadlift, double _wilks, String _date) {
        this._id = _id;
        this._squat = _squat;
        this._bench = _bench;
        this._deadlift = _deadlift;
        this._wilks = _wilks;
        this._date = _date;
    }

    //Setters
    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_squat(int _squat) {
        this._squat = _squat;
    }

    public void set_bench(int _bench) {
        this._bench = _bench;
    }

    public void set_deadlift(int _deadlift) {
        this._deadlift = _deadlift;
    }

    public void set_wilks(double _wilks) {
        this._wilks = _wilks;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    //Getters
    public int get_id() {
        return _id;
    }

    public int get_squat() {
        return _squat;
    }

    public int get_bench() {
        return _bench;
    }

    public int get_deadlift() {
        return _deadlift;
    }

    public double get_wilks() {
        return _wilks;
    }

    public String get_date() {
        return _date;
    }
}
