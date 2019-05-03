package com.sun.colornotetaking.data.model;

public class CheckItem {

    private int mId;
    private String mName;
    private boolean mIsDone;

    public CheckItem(String name, boolean isDone) {
        mName = name;
        this.mIsDone = isDone;
    }

    public CheckItem(int Id, String name, boolean isDone) {
        mId = Id;
        mName = name;
        this.mIsDone = isDone;
    }



    public CheckItem() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setDone(boolean done) {
        mIsDone = done;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

}
