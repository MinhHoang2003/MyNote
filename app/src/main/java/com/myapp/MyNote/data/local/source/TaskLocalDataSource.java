package com.myapp.MyNote.data.local.source;

import com.myapp.MyNote.data.model.Task;
import com.myapp.MyNote.data.OnDataLoadingCallback;
import com.myapp.MyNote.data.local.GetDataHandler;
import com.myapp.MyNote.data.local.LocalGetDataAsync;
import com.myapp.MyNote.data.local.dao.TaskDAO;

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
    public void getReminder(OnDataLoadingCallback<List<Task>> callback) {
        LocalGetDataAsync<List<Task>> listLocalGetDataAsync = new LocalGetDataAsync<>(new GetDataHandler<List<Task>>() {
            @Override
            public List<Task> getData() throws Exception {
                return mTaskDAO.getReminder();
            }
        },callback);
        listLocalGetDataAsync.execute();
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
