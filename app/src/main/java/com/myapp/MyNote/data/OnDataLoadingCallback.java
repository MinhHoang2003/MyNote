package com.myapp.MyNote.data;

public interface OnDataLoadingCallback<T> {

    void onDataLoaded(T data);

    void onDataNotAvailable(Exception e);

}
