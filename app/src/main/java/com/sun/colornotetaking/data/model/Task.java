package com.sun.colornotetaking.data.model;


import java.util.List;

public class Task {

    private final int mId;
    private final String mTitle;
    private final String mDescription;
    private final List<CheckItem> mCheckItemList;
    private final String mDate;
    private final String mTime;
    private final boolean mIsPin;
    private final boolean mIsDelete;


    public Task(TaskBuilder taskBuilder) {
        mId = taskBuilder.mId;
        mTitle = taskBuilder.mTitle;
        mDescription = taskBuilder.mDescription;
        mCheckItemList = taskBuilder.mCheckItemList;
        mDate = taskBuilder.mDate;
        mTime = taskBuilder.mTime;
        mIsPin = taskBuilder.mIsPin;
        mIsDelete = taskBuilder.mIsDelete;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public List<CheckItem> getCheckItemList() {
        return mCheckItemList;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public boolean isPin() {
        return mIsPin;
    }

    public boolean isDelete() {
        return mIsDelete;
    }

    public static class TaskBuilder {

        private int mId;
        private final String mTitle;
        private String mDescription;
        private List<CheckItem> mCheckItemList;
        private String mDate;
        private String mTime;
        private boolean mIsPin;
        private boolean mIsDelete;

        public TaskBuilder(String title){
            this.mTitle = title;
        }
        public TaskBuilder setId(int id){
            this.mId = id;
            return this;
        }

        public TaskBuilder setDescription(String description){
            this.mDescription = description;
            return this;
        }
        public TaskBuilder setCheckItemList(List<CheckItem> checkItemList){
            this.mCheckItemList = checkItemList;
            return this;
        }
        public TaskBuilder setDate(String date){
            this.mDate = date;
            return this;
        }
        public TaskBuilder setTime(String time){
            this.mTime = time;
            return this;
        }
        public TaskBuilder setIsPin(boolean isPin){
            this.mIsPin = isPin;
            return this;
        }
        public TaskBuilder setIsDelete(boolean isDelete){
            this.mIsDelete = isDelete;
            return this;
        }

        public Task build(){
            Task task = new Task(this);
            return task;
        }


    }
}
