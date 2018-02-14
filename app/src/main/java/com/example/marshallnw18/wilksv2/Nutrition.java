package com.example.marshallnw18.wilksv2;

/**
 * Created by Nick Marshall on 1/22/2018.
 */

public class Nutrition {
    private int _id;
    private int _proteins;
    private int _carbs;
    private int _fats;
    private int _tdee;
    private String _date;

    //TODO: May need additional constructors
    public Nutrition(){
    }

    public Nutrition(int _id, int _proteins, int _carbs, int _fats, int _tdee, String _date) {
        this._id = _id;
        this._proteins = _proteins;
        this._carbs = _carbs;
        this._fats = _fats;
        this._tdee = _tdee;
        this._date = _date;
    }

    //Setters

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_proteins(int _proteins) {
        this._proteins = _proteins;
    }

    public void set_carbs(int _carbs) {
        this._carbs = _carbs;
    }

    public void set_fats(int _fats) {
        this._fats = _fats;
    }

    public void set_tdee(int _tdee) {
        this._tdee = _tdee;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    //Getters

    public int get_id() {
        return _id;
    }

    public int get_proteins() {
        return _proteins;
    }

    public int get_carbs() {
        return _carbs;
    }

    public int get_fats() {
        return _fats;
    }

    public int get_tdee() {
        return _tdee;
    }

    public String get_date() {
        return _date;
    }
}
