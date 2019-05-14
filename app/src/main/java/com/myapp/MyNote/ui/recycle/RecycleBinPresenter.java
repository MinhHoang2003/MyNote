package com.myapp.MyNote.ui.recycle;

import com.myapp.MyNote.data.OnDataLoadingCallback;
import com.myapp.MyNote.data.TaskRepository;
import com.myapp.MyNote.data.model.Task;

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
    public void removeTask(int id) {
        if (!mTaskRepository.removeTask(id)) mView.showCanNotRemoveTask();
    }

    @Override
    public void restoreDeletedTask(Task task) {
        task.setDelete(false);
        if (mTaskRepository.editTask(task)) {
            mView.showRestoreTaskDone();
        } else {
            mView.showCanNotRestoreTask();
        }
    }

    @Override
    public void undoRestoreTask(Task task) {
        task.setDelete(true);
        if (!mTaskRepository.editTask(task)) mView.showCanNotUndoRestore();
    }

    @Override
    public void start() {
        if (mView != null) getDeletedTask();
    }
}
