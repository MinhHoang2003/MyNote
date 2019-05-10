package com.sun.colornotetaking.data;

public interface OnDataLoadingCallback<T> {

    void onDataLoaded(T data);

    void onDataNotAvailable(Exception e);

}
