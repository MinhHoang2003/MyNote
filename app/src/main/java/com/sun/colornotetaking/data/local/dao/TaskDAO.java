package com.sun.colornotetaking.data.local.dao;

import com.sun.colornotetaking.data.model.Task;

import java.util.List;

public interface TaskDAO {

    boolean addNewTask(Task task);

    List<Task> getTasks();

    Task getTaskById(int taskId);

    boolean editTask(Task task);

    boolean removeTask(int taskId);

}
