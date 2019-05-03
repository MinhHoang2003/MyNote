package com.sun.colornotetaking.data.model;

import java.util.List;

public class Task {

    private final int mId;
    private String mTitle;
    private String mDescription;
    private List<SubItem> mSubItems;
    private String mDate;
    private String mTime;
    private boolean mIsPin;
    private boolean mIsDelete;

    private Task(TaskBuilder taskBuilder) {
        mId = taskBuilder.mId;
        mTitle = taskBuilder.mTitle;
        mDescription = taskBuilder.mDescription;
        mSubItems = taskBuilder.mSubItems;
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

    public List<SubItem> getSubItems() {
        return mSubItems;
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

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setSubItems(List<SubItem> subItems) {
        mSubItems = subItems;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public void setPin(boolean pin) {
        mIsPin = pin;
    }

    public void setDelete(boolean delete) {
        mIsDelete = delete;
    }

    public String getDoneItemsCount() {
        if (mSubItems == null || mSubItems.size() == 0) return "0/0";
        int size = mSubItems.size();
        int progess = 0;
        for (SubItem item : mSubItems) {
            if (item.isDone()) progess++;
        }
        return progess + "/" + size;
    }

    public boolean isSearchedItem(String searchPattern) {
        if (searchPattern != null) {
            return (mTitle.contains(searchPattern));
        }
        return false;
    }

    public static class TaskBuilder {
        private String mTitle;
        private int mId;
        private String mDescription;
        private List<SubItem> mSubItems;
        private String mDate;
        private String mTime;
        private boolean mIsPin;
        private boolean mIsDelete;

        public TaskBuilder(String title) {
            mTitle = title;
        }

        public TaskBuilder setId(int id) {
            mId = id;
            return this;
        }

        public TaskBuilder setDescription(String description) {
            mDescription = description;
            return this;
        }

        public TaskBuilder setSubItems(List<SubItem> subItems) {
            mSubItems = subItems;
            return this;
        }

        public TaskBuilder setDate(String date) {
            mDate = date;
            return this;
        }

        public TaskBuilder setTime(String time) {
            mTime = time;
            return this;
        }

        public TaskBuilder setIsPin(boolean isPin) {
            mIsPin = isPin;
            return this;
        }

        public TaskBuilder setIsDelete(boolean isDelete) {
            mIsDelete = isDelete;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
