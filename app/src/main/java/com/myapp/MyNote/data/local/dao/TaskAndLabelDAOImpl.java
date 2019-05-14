package com.myapp.MyNote.data.local.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.myapp.MyNote.data.local.AppDatabase;
import com.myapp.MyNote.data.local.entry.LabelEntry;
import com.myapp.MyNote.data.local.entry.TaskAndLabelEntry;
import com.myapp.MyNote.data.local.entry.TaskEntry;

import java.util.ArrayList;
import java.util.List;

public class TaskAndLabelDAOImpl extends AppDatabase implements TaskAndLabelDAO {

    private static TaskAndLabelDAOImpl sTaskAndLabelDAO;

    private TaskAndLabelDAOImpl(Context context) {
        super(context);
    }

    public static TaskAndLabelDAOImpl getInstance(Context context) {
        if (sTaskAndLabelDAO == null) {
            sTaskAndLabelDAO = new TaskAndLabelDAOImpl(context);
        }
        return sTaskAndLabelDAO;
    }

    @Override
    public boolean addItem(int taskId, int labelId) {
        mSQLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskAndLabelEntry.TASK_ID, taskId);
        values.put(TaskAndLabelEntry.LABEL_ID, labelId);
        long result = mSQLiteDatabase.insert(TaskAndLabelEntry.TABLE_NAME, null, values);
        return result > 0;
    }

    @Override
    public List<Integer> getListLabelsId(int taskId) {
        List<Integer> labelId = new ArrayList<>();
        mSQLiteDatabase = getReadableDatabase();
        String[] column = {TaskAndLabelEntry.LABEL_ID};
        String selection = TaskAndLabelEntry.TASK_ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskId)};
        Cursor cursor = mSQLiteDatabase.query(TaskAndLabelEntry.TABLE_NAME, column, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(TaskEntry.ID));
                labelId.add(id);
                cursor.moveToNext();
            }
        }
        mSQLiteDatabase.close();
        cursor.close();
        return labelId;
    }

    @Override
    public List<Integer> getListTasksId(int labelId) {
        List<Integer> taskId = new ArrayList<>();
        mSQLiteDatabase = getReadableDatabase();
        String[] column = {TaskAndLabelEntry.TASK_ID};
        String selection = TaskAndLabelEntry.LABEL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(labelId)};
        Cursor cursor = mSQLiteDatabase.query(TaskAndLabelEntry.TABLE_NAME, column, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(LabelEntry.ID));
                taskId.add(id);
            } while (cursor.moveToNext());
        }
        mSQLiteDatabase.close();
        cursor.close();
        return taskId;
    }
}
