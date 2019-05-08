package com.sun.colornotetaking.data.local.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.sun.colornotetaking.data.local.AppDatabase;
import com.sun.colornotetaking.data.local.entry.SubItemEntry;
import com.sun.colornotetaking.data.model.SubItem;

import java.util.ArrayList;
import java.util.List;

public class SubItemDAOImpl extends AppDatabase implements SubItemDAO {

    private static SubItemDAOImpl sCheckItemDAOImpl;

    private SubItemDAOImpl(Context context) {
        super(context);
    }

    public static SubItemDAOImpl getInstance(Context context) {
        if (sCheckItemDAOImpl == null) {
            sCheckItemDAOImpl = new SubItemDAOImpl(context);
        }
        return sCheckItemDAOImpl;
    }

    @Override
    public boolean addSubItem(SubItem subItem, long taskId) {
        mSQLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SubItemEntry.NAME, subItem.getName());
        values.put(SubItemEntry.IS_DONE, subItem.isDone());
        values.put(SubItemEntry.TASK_ID, taskId);
        long result = mSQLiteDatabase.insert(SubItemEntry.TABLE_NAME, null, values);
        mSQLiteDatabase.close();
        return result > 0;
    }

    @Override
    public int addSubItems(List<SubItem> subItems, long taskId) {
        int size = subItems.size();
        int result = (size > 0) ? size - 1 : 0;
        for (SubItem item : subItems) {
            if (!addSubItem(item, taskId)) result--;
        }
        return result;
    }

    @Override
    public List<SubItem> getSubItemsByTaskId(int taskId) {
        mSQLiteDatabase = getReadableDatabase();
        List<SubItem> subItems = new ArrayList<>();
        String selection = SubItemEntry.TASK_ID + " = ?";
        String[] selectArgs = {String.valueOf(taskId)};
        Cursor cursor = mSQLiteDatabase.query(SubItemEntry.TABLE_NAME, null, selection, selectArgs, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                SubItem subItem = new SubItem(cursor.getInt(cursor.getColumnIndex(SubItemEntry.ID)),
                        cursor.getString(cursor.getColumnIndex(SubItemEntry.NAME)),
                        cursor.getInt(cursor.getColumnIndex(SubItemEntry.IS_DONE)) == 1);
                subItems.add(subItem);
                cursor.moveToNext();
            }
        }
        cursor.close();
        mSQLiteDatabase.close();
        return subItems;
    }

    @Override
    public boolean editSubItem(SubItem subItem) {
        mSQLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SubItemEntry.ID, subItem.getId());
        values.put(SubItemEntry.NAME, subItem.getName());
        values.put(SubItemEntry.IS_DONE, subItem.isDone());
        String where = SubItemEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(subItem.getId())};
        int result = mSQLiteDatabase.update(SubItemEntry.TABLE_NAME, values, where, whereArgs);
        mSQLiteDatabase.close();
        return result > 0;
    }

    @Override
    public boolean removeSubItem(int id) {
        mSQLiteDatabase = getWritableDatabase();
        String where = SubItemEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        int result = mSQLiteDatabase.delete(SubItemEntry.TABLE_NAME, where, whereArgs);
        mSQLiteDatabase.close();
        return result > 0;
    }

    @Override
    public boolean removeAllSubItemsByTaskId(int taskId) {
        mSQLiteDatabase = getWritableDatabase();
        String where = SubItemEntry.TASK_ID + " = ?";
        String[] whereArgs = {String.valueOf(taskId)};
        int result = mSQLiteDatabase.delete(SubItemEntry.TABLE_NAME, where, whereArgs);
        mSQLiteDatabase.close();
        return result >= 0;
    }
}
