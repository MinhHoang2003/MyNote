package com.sun.colornotetaking.ui.home;

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
                if (data.size() == 0) {
                    mView.showEmptyTasks();
                } else {
                    mView.showPinedTasks(data);
                }
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                mView.showLoadTasksError(e);
            }
        });
    }

    @Override
    public void getOtherTasks() {

        mTaskRepository.getTasks(false, new OnDataLoadingCallback<List<Task>>() {
            @Override
            public void onDataLoaded(List<Task> data) {
                if (data.size() == 0) {
                    mView.showEmptyTasks();
                } else {
                    mView.showOtherTasks(data);
                }
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                mView.showLoadTasksError(e);
            }
        });

    }

    @Override
    public void addNewTask(Task task) {
        if (mTaskRepository.addTask(task)) {
            mView.showAddTaskDone();
        } else {
            mView.showCanNotAddTask();
        }
    }

    @Override
    public void editTask(Task task) {
        if (mTaskRepository.editTask(task)) {
            mView.showEditTaskDone();
        } else {
            mView.showCanNotEditTask();
        }
    }

    @Override
    public void removeTask(int id) {
        if (mTaskRepository.removeTask(id)) {
            mView.showRemoveTaskDone();
        } else {
            mView.showCanNotRemoveTask();
        }
    }

    @Override
    public void start() {
        if (mView != null) {
            getOtherTasks();
            getPinedTasks();
        }
    }
}
