package com.example.marshallnw18.virtus.supportingClasses;

/**
 * Created by marshallnw18 on 2/22/2018.
 */

public class Exercise {
    String text1, text2, text3;
    int image;

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Exercise(String text1, String text2, String text3, int image) {
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.image = image;
    }

    public String getText3() {
        return text3;
    }
}
