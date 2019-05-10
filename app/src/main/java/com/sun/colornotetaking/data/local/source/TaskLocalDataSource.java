package com.sun.colornotetaking.data.local.source;

import com.sun.colornotetaking.data.OnDataLoadingCallback;
import com.sun.colornotetaking.data.local.GetDataHandler;
import com.sun.colornotetaking.data.local.LocalGetDataAsync;
import com.sun.colornotetaking.data.local.dao.TaskDAO;
import com.sun.colornotetaking.data.model.Task;

import java.util.List;

public class TaskLocalDataSource implements TaskDataSource {

    private static TaskLocalDataSource sTaskLocalDataSource;
    private TaskDAO mTaskDAO;

    private TaskLocalDataSource(TaskDAO taskDAO) {
        mTaskDAO = taskDAO;
    }

    public static TaskLocalDataSource getInstance(TaskDAO taskDAO) {
        if (sTaskLocalDataSource == null) {
            sTaskLocalDataSource = new TaskLocalDataSource(taskDAO);
        }
        return sTaskLocalDataSource;
    }

    @Override
    public void getTasks(final boolean isPin, OnDataLoadingCallback<List<Task>> callback) {
        LocalGetDataAsync<List<Task>> localGetDataAsync = new LocalGetDataAsync<>(new GetDataHandler<List<Task>>() {
            @Override
            public List<Task> getData() throws Exception {
                return mTaskDAO.getTasks(isPin);
            }
        }, callback);
        localGetDataAsync.execute();
    }

    @Override
    public void getDeletedTasks(final boolean isDelete, OnDataLoadingCallback<List<Task>> callback) {

        LocalGetDataAsync<List<Task>> localGetDataAsync = new LocalGetDataAsync<>(new GetDataHandler<List<Task>>() {
            @Override
            public List<Task> getData() throws Exception {
                return mTaskDAO.getDeletedTasks(isDelete);
            }
        }, callback);
        localGetDataAsync.execute();
    }

    @Override
    public void getTaskById(final int taskId, OnDataLoadingCallback<Task> callback) {
        LocalGetDataAsync<Task> localGetDataAsync = new LocalGetDataAsync<>(new GetDataHandler<Task>() {
            @Override
            public Task getData() throws Exception {
                return mTaskDAO.getTaskById(taskId);
            }
        }, callback);
        localGetDataAsync.execute();
    }

    @Override
    public void getTasksByListId(final List<Integer> taskId, OnDataLoadingCallback<List<Task>> callback) {
        LocalGetDataAsync<List<Task>> localGetDataAsync = new LocalGetDataAsync<>(new GetDataHandler<List<Task>>() {
            @Override
            public List<Task> getData() throws Exception {
                return mTaskDAO.getTasks(taskId);
            }
        }, callback);
        localGetDataAsync.execute();
    }

    @Override
    public boolean removeTask(int taskId) {
        return mTaskDAO.removeTask(taskId);
    }

    @Override
    public boolean addTask(Task task) {
        return mTaskDAO.addNewTask(task);
    }

    @Override
    public boolean editTask(Task task) {
        return mTaskDAO.editTask(task);
    }
}
