package com.example.marshallnw18.wilksv2;

/**
 * Created by Nick Marshall on 1/22/2018.
 */

public class Users {

    private int _id;
    private int _height;
    private int _weight;
    private String _gender;
    private String _date;

    //Constructors
    //Possibility for the need of additional constructors
    public Users(){}

    public Users(int _id, int _height, int _weight, String _gender, String _date) {
        this._id = _id;
        this._height = _height;
        this._weight = _weight;
        this._gender = _gender;
        this._date = _date;
    }

    //Setters
    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_height(int _height) {
        this._height = _height;
    }

    public void set_weight(int _weight) {
        this._weight = _weight;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    //Getters

    public int get_id() {
        return _id;
    }

    public int get_height() {
        return _height;
    }

    public int get_weight() {
        return _weight;
    }

    public String get_gender() {
        return _gender;
    }

    public String get_date() {
        return _date;
    }
}
