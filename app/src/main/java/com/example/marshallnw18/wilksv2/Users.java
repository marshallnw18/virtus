package com.example.marshallnw18.wilksv2;

/**
 * Created by Nick Marshall on 1/22/2018.
 */

public class Users {

    private int _id;
    private int _height;
    private int _weight;
    private String _gender;
    private int _tdee;
    private double _wilks;

    //Constructors
    //Possibility for the need of additional constructors
    public Users(){}

    public Users(int _id, int _height, int _weight, String _gender, int _tdee, double _wilks) {
        this._id = _id;
        this._height = _height;
        this._weight = _weight;
        this._gender = _gender;
        this._tdee = _tdee;
        this._wilks = _wilks;
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

    public void set_tdee(int _tdee) {
        this._tdee = _tdee;
    }

    public void set_wilks(double _wilks) {
        this._wilks = _wilks;
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

    public int get_tdee() {
        return _tdee;
    }

    public double get_wilks() {
        return _wilks;
    }
}
