package com.sun.colornotetaking.data.local;

import android.os.AsyncTask;

import com.sun.colornotetaking.data.OnDataLoadingCallback;

public class LocalGetDataAsync<T> extends AsyncTask<Void, Void, T> {
    private GetDataHandler<T> mGetDataHandler;
    private Exception mException;
    private OnDataLoadingCallback<T> mOnDataLoadingCallback;

    public LocalGetDataAsync(GetDataHandler<T> getDataHandler, OnDataLoadingCallback<T> callback) {
        mGetDataHandler = getDataHandler;
        mOnDataLoadingCallback = callback;
    }

    @Override
    protected T doInBackground(Void... voids) {
        T data = null;
        try {
            data = mGetDataHandler.getData();
        } catch (Exception e) {
            mException = e;
        }
        return data;
    }

    @Override
    protected void onPostExecute(T t) {
        if (mException == null) {
            if (t != null) mOnDataLoadingCallback.onDataLoaded(t);
            else mOnDataLoadingCallback.onDataNotAvailable(null);
        }
        else mOnDataLoadingCallback.onDataNotAvailable(mException);
    }
}
