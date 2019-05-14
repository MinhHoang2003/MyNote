package com.myapp.MyNote.data;

import com.myapp.MyNote.data.local.source.TaskAndLabelDataSource;

import java.util.List;

public class TaskAndLabelRepository implements TaskAndLabelDataSource {

    private static TaskAndLabelRepository sTaskAndLabelRepository;
    private TaskAndLabelDataSource mTaskAndLabelDataSource;

    public TaskAndLabelRepository(TaskAndLabelDataSource taskAndLabelDataSource) {
        mTaskAndLabelDataSource = taskAndLabelDataSource;
    }

    public static TaskAndLabelRepository getInstance(TaskAndLabelDataSource taskAndLabelDataSource) {
        if (sTaskAndLabelRepository == null) {
            sTaskAndLabelRepository = new TaskAndLabelRepository(taskAndLabelDataSource);
        }
        return sTaskAndLabelRepository;
    }

    @Override
    public void getLabelIdsByTask(int taskId, OnDataLoadingCallback<List<Integer>> callback) {
        mTaskAndLabelDataSource.getLabelIdsByTask(taskId, callback);
    }

    @Override
    public void getTaskIdByLabel(int labelId, OnDataLoadingCallback<List<Integer>> callback) {
        mTaskAndLabelDataSource.getTaskIdByLabel(labelId, callback);
    }

    @Override
    public boolean addItem(int taskId, int labelId) {
        return mTaskAndLabelDataSource.addItem(taskId, labelId);
    }
}
