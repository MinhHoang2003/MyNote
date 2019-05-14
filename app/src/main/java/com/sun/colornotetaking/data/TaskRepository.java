package com.sun.colornotetaking.data;

import com.sun.colornotetaking.data.local.source.TaskDataSource;
import com.sun.colornotetaking.data.model.Task;

import java.util.List;

public class TaskRepository implements TaskDataSource {

    private static TaskRepository sTaskRepository;
    private TaskDataSource mTaskDataSource;

    private TaskRepository(TaskDataSource taskDataSource) {
        mTaskDataSource = taskDataSource;
    }

    public static TaskRepository getInstance(TaskDataSource taskDataSource) {

        if (sTaskRepository == null) {
            sTaskRepository = new TaskRepository(taskDataSource);
        }
        return sTaskRepository;
    }

    @Override
    public void getTasks(boolean isPin, OnDataLoadingCallback<List<Task>> callback) {
        mTaskDataSource.getTasks(isPin, callback);
    }

    @Override
    public void getDeletedTasks(boolean isDelete, OnDataLoadingCallback<List<Task>> callback) {
        mTaskDataSource.getDeletedTasks(isDelete, callback);
    }

    @Override
    public void getReminder(OnDataLoadingCallback<List<Task>> callback) {
        mTaskDataSource.getReminder(callback);
    }

    @Override
    public void getTaskById(int taskId, OnDataLoadingCallback<Task> callback) {
        mTaskDataSource.getTaskById(taskId, callback);
    }

    @Override
    public void getTasksByListId(List<Integer> taskId, OnDataLoadingCallback<List<Task>> callback) {
        mTaskDataSource.getTasksByListId(taskId, callback);
    }

    @Override
    public boolean removeTask(int taskId) {
        return mTaskDataSource.removeTask(taskId);
    }

    @Override
    public boolean addTask(Task task) {
        return mTaskDataSource.addTask(task);
    }

    @Override
    public boolean editTask(Task task) {
        return mTaskDataSource.editTask(task);
    }
}
