package com.sun.colornotetaking.data.local.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.sun.colornotetaking.data.local.AppDatabase;
import com.sun.colornotetaking.data.local.entry.TaskEntry;
import com.sun.colornotetaking.data.model.CheckItem;
import com.sun.colornotetaking.data.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabase extends AppDatabase implements TaskDAO{

    private  static  TaskDatabase sTaskDatabase;
    private CheckItemDAO mCheckItemDatabase;
    public TaskDatabase(Context context) {
        super(context);
        mCheckItemDatabase = CheckItemDatabase.getInstance(context);
    }

    public static TaskDatabase getInstance(Context context){
        if(sTaskDatabase==null){
            sTaskDatabase = new TaskDatabase(context);
        }
        return sTaskDatabase;
    }

    @Override
    public boolean addNewTask(Task task) {
        mSQLiteDatabase = getWritableDatabase();
        mSQLiteDatabase.beginTransaction();
        long rowId = addInfoToTaskTable(task);
        if(rowId>0) {
            mCheckItemDatabase.addCheckItems(task.getCheckItemList(),task.getId());
            mSQLiteDatabase.setTransactionSuccessful();
            return true;
        }
        mSQLiteDatabase.endTransaction();
        mSQLiteDatabase.close();
        return false;

    }

    @Override
    public List<Task> getTasks() {
        List<Task> listTask = new ArrayList<>();
        mSQLiteDatabase = getReadableDatabase();
        mCursor = mSQLiteDatabase.query(TaskEntry.TABLE_NAME, null, null, null, null, null, null);
        if (mCursor.moveToFirst()) {
            do {
                int id = mCursor.getInt(mCursor.getColumnIndex(TaskEntry.ID));
                Task task = createTask(mCursor, id);
                listTask.add(task);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        mSQLiteDatabase.close();
        return listTask;
    }


    @Override
    public Task getTaskById(int taskId) {
        Task task = null;
        mSQLiteDatabase = getReadableDatabase();
        String selection = TaskEntry.ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskId)};
        mCursor = mSQLiteDatabase.query(TaskEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (mCursor.moveToFirst()) {
            task = createTask(mCursor, taskId);
        }
        mCursor.close();
        mSQLiteDatabase.close();
        return task;
    }

    @Override
    public boolean editTask(Task task) {
        mSQLiteDatabase = getWritableDatabase();
        String where = TaskEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(task.getId())};
        ContentValues values = new ContentValues();
        values.put(TaskEntry.ID, task.getId());
        values.put(TaskEntry.TITLE, task.getTitle());
        values.put(TaskEntry.DESCRIPTION, task.getDescription());
        values.put(TaskEntry.DATE, task.getDate());
        values.put(TaskEntry.TIME, task.getTime());
        values.put(TaskEntry.IS_PIN, task.isPin());
        values.put(TaskEntry.IS_DELETE, task.isDelete());
        int result = mSQLiteDatabase.update(TaskEntry.TABLE_NAME, values, where, whereArgs);
        mSQLiteDatabase.close();
        return result>0;
    }

    @Override
    public boolean removeTask(int task_id) {
        mSQLiteDatabase = getWritableDatabase();
        if (mCheckItemDatabase.removeAllCheckItemsByTaskId(task_id)) {//remove checkitems have this task_id
            String taskWhere = TaskEntry.ID + " = ?";
            String[] taskWhereArg = {Integer.toString(task_id)};
            int result = mSQLiteDatabase.delete(TaskEntry.TABLE_NAME, taskWhere, taskWhereArg);
            if (result > 0) return true;
        }
        mSQLiteDatabase.close();
        return false;
    }


    private long addInfoToTaskTable(Task task) {

        mSQLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.TITLE, task.getTitle());
        values.put(TaskEntry.DESCRIPTION, task.getDescription());
        values.put(TaskEntry.DATE, task.getDate());
        values.put(TaskEntry.TIME, task.getTime());
        values.put(TaskEntry.IS_PIN, task.isPin());
        values.put(TaskEntry.IS_DELETE, task.isDelete());
        long rowId = mSQLiteDatabase.insert(TaskEntry.TABLE_NAME, null, values);
        mSQLiteDatabase.close();
        return rowId;
    }


    private Task createTask(Cursor cursor, int taskId) {
        List<CheckItem> checkItems = mCheckItemDatabase.getCheckItemsByTaskId(taskId);
        return new Task.TaskBuilder(cursor.getString(cursor.getColumnIndex(TaskEntry.TITLE)))
                .setId(taskId)
                .setDescription(cursor.getString(cursor.getColumnIndex(TaskEntry.DESCRIPTION)))
                .setDate(cursor.getString(cursor.getColumnIndex(TaskEntry.DATE)))
                .setTime(cursor.getString(cursor.getColumnIndex(TaskEntry.TIME)))
                .setIsPin(cursor.getInt(cursor.getColumnIndex(TaskEntry.IS_PIN)) == 1)
                .setIsDelete(cursor.getInt(cursor.getColumnIndex(TaskEntry.IS_DELETE)) == 1)
                .setCheckItemList(checkItems)
                .build();
    }

}
