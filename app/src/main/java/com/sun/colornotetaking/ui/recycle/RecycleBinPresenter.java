package com.sun.colornotetaking.ui.recycle;

import com.sun.colornotetaking.data.OnDataLoadingCallback;
import com.sun.colornotetaking.data.TaskRepository;
import com.sun.colornotetaking.data.model.Task;

import java.util.List;

public class RecycleBinPresenter implements RecycleBinContract.Presenter {

    private TaskRepository mTaskRepository;
    private RecycleBinContract.View mView;

    public RecycleBinPresenter(TaskRepository taskRepository, RecycleBinContract.View view) {
        mTaskRepository = taskRepository;
        mView = view;
    }

    @Override
    public void getDeletedTask() {
        mTaskRepository.getDeletedTasks(true, new OnDataLoadingCallback<List<Task>>() {
            @Override
            public void onDataLoaded(List<Task> data) {
                if (data.size() != 0) {
                    mView.showDeletedTask(data);
                } else {
                    mView.showEmptyTask();
                }
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                mView.showGetTaskError(e);
            }
        });
    }

    @Override
    public boolean removeTask(int id) {
        if (mTaskRepository.removeTask(id)) {
            return true;
        } else {
            mView.showCanNotRemoveTask();
        }
        return false;
    }

    @Override
    public boolean restoreDeletedTask(Task task) {
        task.setDelete(false);
        if (mTaskRepository.editTask(task)) {
            return true;
        } else {
            mView.showCanNotRestoreTask();
        }
        return false;
    }

    @Override
    public void start() {
        if (mView != null) getDeletedTask();
    }
}
