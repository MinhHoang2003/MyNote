package com.sun.colornotetaking.data.local.dao;

import android.content.ContentValues;
import android.content.Context;

import com.sun.colornotetaking.data.local.AppDatabase;
import com.sun.colornotetaking.data.local.entry.CheckItemEntry;
import com.sun.colornotetaking.data.model.CheckItem;

import java.util.ArrayList;
import java.util.List;

public class CheckItemDAOImpl extends AppDatabase implements CheckItemDAO {

    private static CheckItemDAOImpl sCheckItemDAOImpl;

    protected CheckItemDAOImpl(Context context) {
        super(context);
    }

    public static CheckItemDAOImpl getInstance(Context context) {
        if (sCheckItemDAOImpl == null) {
            sCheckItemDAOImpl = new CheckItemDAOImpl(context);
        }
        return sCheckItemDAOImpl;
    }

    @Override
    public boolean addCheckItem(CheckItem checkItem, long task_id) {
        mSQLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CheckItemEntry.NAME, checkItem.getName());
        values.put(CheckItemEntry.IS_DONE, checkItem.isDone());
        values.put(CheckItemEntry.TASK_ID, task_id);
        long result = mSQLiteDatabase.insert(CheckItemEntry.TABLE_NAME, null, values);
        mSQLiteDatabase.close();
        return result > 0;
    }

    @Override
    public boolean addCheckItems(List<CheckItem> checkItems, long task_id) {
        for (CheckItem item : checkItems) {
            if (!addCheckItem(item, task_id)) return false;
        }
        return true;
    }

    @Override
    public List<CheckItem> getCheckItemsByTaskId(int taskId) {
        mSQLiteDatabase = getReadableDatabase();
        List<CheckItem> checkItems = new ArrayList<>();
        String selection = CheckItemEntry.TASK_ID + " = ?";
        String[] selectArgs = {String.valueOf(taskId)};
        mCursor = mSQLiteDatabase.query(CheckItemEntry.TABLE_NAME, null, selection, selectArgs, null, null, null);
        if (mCursor.moveToFirst()) {
            do {
                CheckItem checkItem = new CheckItem(mCursor.getInt(mCursor.getColumnIndex(CheckItemEntry.ID)),
                        mCursor.getString(mCursor.getColumnIndex(CheckItemEntry.NAME)),
                        mCursor.getInt(mCursor.getColumnIndex(CheckItemEntry.IS_DONE)) == 1);

                checkItems.add(checkItem);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        mSQLiteDatabase.close();
        return checkItems;
    }

    @Override
    public boolean editCheckItem(CheckItem checkItem, int taskId) {
        mSQLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CheckItemEntry.ID, checkItem.getId());
        values.put(CheckItemEntry.NAME, checkItem.getName());
        values.put(CheckItemEntry.IS_DONE, checkItem.isDone());
        values.put(CheckItemEntry.TASK_ID, taskId);
        String where = CheckItemEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(checkItem.getId())};
        int result = mSQLiteDatabase.update(CheckItemEntry.TABLE_NAME, values, where, whereArgs);
        mSQLiteDatabase.close();
        return result > 0;
    }

    @Override
    public boolean removeCheckItem(int checkItemId) {
        mSQLiteDatabase = getWritableDatabase();
        String where = CheckItemEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(checkItemId)};
        int result = mSQLiteDatabase.delete(CheckItemEntry.TABLE_NAME, where, whereArgs);
        mSQLiteDatabase.close();
        return result > 0;
    }

    @Override
    public boolean removeAllCheckItemsByTaskId(int taskId) {
        mSQLiteDatabase = getWritableDatabase();
        String where = CheckItemEntry.TASK_ID + " = ?";
        String[] whereArgs = {String.valueOf(taskId)};
        int result = mSQLiteDatabase.delete(CheckItemEntry.TABLE_NAME, where, whereArgs);
        mSQLiteDatabase.close();
        return result >= 0;
    }
}
