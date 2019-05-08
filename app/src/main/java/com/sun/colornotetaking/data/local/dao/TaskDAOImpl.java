package com.sun.colornotetaking.data.local.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.sun.colornotetaking.data.local.AppDatabase;
import com.sun.colornotetaking.data.local.entry.TaskEntry;
import com.sun.colornotetaking.data.model.SubItem;
import com.sun.colornotetaking.data.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl extends AppDatabase implements TaskDAO {

    private static TaskDAOImpl sTaskDAOImpl;
    private SubItemDAO mSubItemDAOImpl;

    private TaskDAOImpl(Context context, SubItemDAO subItemDAO) {
        super(context);
        mSubItemDAOImpl = subItemDAO;
    }

    public static TaskDAOImpl getInstance(Context context, SubItemDAO subItemDAO) {
        if (sTaskDAOImpl == null) {
            sTaskDAOImpl = new TaskDAOImpl(context, subItemDAO);
        }
        return sTaskDAOImpl;
    }

    @Override
    public boolean addNewTask(Task task) {
        mSQLiteDatabase = getWritableDatabase();
        mSQLiteDatabase.beginTransaction();
        long rowId = addInfoToTaskTable(task);
        if (rowId > 0 && mSubItemDAOImpl.addSubItems(task.getSubItems(), task.getId()) >= 0) {
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
        Cursor cursor = mSQLiteDatabase.query(TaskEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(TaskEntry.ID));
                Task task = createTask(cursor, id);
                listTask.add(task);
                cursor.moveToNext();
            }
        }
        cursor.close();
        mSQLiteDatabase.close();
        return listTask;
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = null;
        mSQLiteDatabase = getReadableDatabase();
        String selection = TaskEntry.ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskId)};
        Cursor cursor = mSQLiteDatabase.query(TaskEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            task = createTask(cursor, taskId);
        }
        cursor.close();
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
        return result > 0;
    }

    @Override
    public boolean removeTask(int taskId) {
        mSQLiteDatabase = getWritableDatabase();
        if (mSubItemDAOImpl.removeAllSubItemsByTaskId(taskId)) {//remove sub item have this taskId
            String taskWhere = TaskEntry.ID + " = ?";
            String[] taskWhereArg = {Integer.toString(taskId)};
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
        List<SubItem> subItems = mSubItemDAOImpl.getSubItemsByTaskId(taskId);
        return new Task.TaskBuilder(cursor.getString(cursor.getColumnIndex(TaskEntry.TITLE)))
                .setId(taskId)
                .setDescription(cursor.getString(cursor.getColumnIndex(TaskEntry.DESCRIPTION)))
                .setDate(cursor.getString(cursor.getColumnIndex(TaskEntry.DATE)))
                .setTime(cursor.getString(cursor.getColumnIndex(TaskEntry.TIME)))
                .setIsPin(cursor.getInt(cursor.getColumnIndex(TaskEntry.IS_PIN)) == 1)
                .setIsDelete(cursor.getInt(cursor.getColumnIndex(TaskEntry.IS_DELETE)) == 1)
                .setSubItems(subItems)
                .build();
    }

}
