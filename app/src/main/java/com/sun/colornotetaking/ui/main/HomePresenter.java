package com.sun.colornotetaking.ui.main;

import com.sun.colornotetaking.data.OnDataLoadingCallback;
import com.sun.colornotetaking.data.TaskRepository;
import com.sun.colornotetaking.data.model.Task;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter {

    private TaskRepository mTaskRepository;
    private HomeContract.View mView;

    public HomePresenter(TaskRepository taskRepository, HomeContract.View view) {
        mTaskRepository = taskRepository;
        mView = view;
    }

    @Override
    public void getPinedTasks() {
        mTaskRepository.getTasks(true, new OnDataLoadingCallback<List<Task>>() {
            @Override
            public void onDataLoaded(List<Task> data) {
                if (data.size() == 0) mView.showGetDataError(null);
                else mView.showPinedTasks(data);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                mView.showGetDataError(e);
            }
        });
    }

    @Override
    public void getOtherTasks() {

        mTaskRepository.getTasks(false, new OnDataLoadingCallback<List<Task>>() {
            @Override
            public void onDataLoaded(List<Task> data) {
                if (data.size() == 0) mView.showGetDataError(null);
                else mView.showOtherTasks(data);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                mView.showGetDataError(e);
            }
        });

    }

    @Override
    public void addNewTask(Task task) {
        if (mTaskRepository.addTask(task)) mView.showAddTaskDone();
        else mView.showCantAddTask();
    }

    @Override
    public void editTask(Task task) {
        if (mTaskRepository.editTask(task)) mView.showEditTaskDone();
        else mView.showCantEditTask();
    }

    @Override
    public void removeTask(int id) {
        if (mTaskRepository.removeTask(id)) mView.showRemoveTaskDone();
        else mView.showCantRemoveTask();
    }


    @Override
    public void start() {
        if (mView != null) {
            getOtherTasks();
            getPinedTasks();
        }
    }
}
