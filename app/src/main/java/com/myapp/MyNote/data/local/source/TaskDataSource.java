package com.myapp.MyNote.data.local.source;

import com.myapp.MyNote.data.model.Task;
import com.myapp.MyNote.data.OnDataLoadingCallback;

import java.util.List;

public interface TaskDataSource {

    void getTasks(boolean isPin, OnDataLoadingCallback<List<Task>> callback);

    void getDeletedTasks(boolean isDelete, OnDataLoadingCallback<List<Task>> callback);

    void getReminder(OnDataLoadingCallback<List<Task>> callback);

    void getTaskById(int taskId, OnDataLoadingCallback<Task> callback);

    void getTasksByListId(List<Integer> taskId, OnDataLoadingCallback<List<Task>> callback);

    boolean removeTask(int taskId);

    boolean addTask(Task task);

    boolean editTask(Task task);

}
