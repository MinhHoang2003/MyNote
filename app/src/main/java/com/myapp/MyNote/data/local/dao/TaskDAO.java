package com.myapp.MyNote.data.local.dao;

import com.myapp.MyNote.data.model.Task;

import java.util.List;

public interface TaskDAO {

    boolean addNewTask(Task task);

    List<Task> getTasks(boolean isPin);

    List<Task> getReminder();

    List<Task> getDeletedTasks(boolean isDelete);

    List<Task> getTasks(List<Integer> tasksId);

    Task getTaskById(int taskId);

    boolean editTask(Task task);

    boolean removeTask(int taskId);

}
