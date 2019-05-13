package com.sun.colornotetaking.data.local.source;

import com.sun.colornotetaking.data.OnDataLoadingCallback;
import com.sun.colornotetaking.data.model.Task;

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
