package com.myapp.MyNote.data.model;

public class Label {

    private int mId;
    private String mName;
    private int mColor;

    public Label(int color) {
        mColor = color;
    }

    public Label(String name, int color) {
        mName = name;
        mColor = color;
    }

    public Label(int id, String name, int Color) {
        mId = id;
        mName = name;
        mColor = Color;
    }

    public int getId() {
        return mId;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
