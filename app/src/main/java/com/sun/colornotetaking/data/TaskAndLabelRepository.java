package com.sun.colornotetaking.data;

import android.content.Context;

import com.sun.colornotetaking.data.local.source.TaskAndLabelDataSource;
import com.sun.colornotetaking.data.local.source.TaskAndLabelLocalDataSource;

import java.util.List;

public class TaskAndLabelRepository implements TaskAndLabelDataSource {

    private static TaskAndLabelRepository sTaskAndLabelRepository;
    private TaskAndLabelDataSource mTaskAndLabelDataSource;

    public TaskAndLabelRepository(TaskAndLabelDataSource taskAndLabelDataSource) {
        mTaskAndLabelDataSource = taskAndLabelDataSource;
    }

    public static TaskAndLabelRepository getInstance(Context context) {
        if (sTaskAndLabelRepository == null) {
            sTaskAndLabelRepository = new TaskAndLabelRepository(TaskAndLabelLocalDataSource.getInstance(context));
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
