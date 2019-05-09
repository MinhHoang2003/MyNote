package com.sun.colornotetaking.data.local.source;

import android.content.Context;

import com.sun.colornotetaking.data.OnDataLoadingCallback;
import com.sun.colornotetaking.data.local.GetDataHandler;
import com.sun.colornotetaking.data.local.LocalGetDataAsync;
import com.sun.colornotetaking.data.local.dao.TaskAndLabelDAO;
import com.sun.colornotetaking.data.local.dao.TaskAndLabelDAOImpl;

import java.util.List;

public class TaskAndLabelLocalDataSource implements TaskAndLabelDataSource {

    private static TaskAndLabelLocalDataSource sTaskAndLabelLocalDataSource;
    private TaskAndLabelDAO mTaskAndLabelDAO;

    private TaskAndLabelLocalDataSource(TaskAndLabelDAO taskAndLabelDAO) {
        mTaskAndLabelDAO = taskAndLabelDAO;
    }

    public static TaskAndLabelLocalDataSource getInstance(Context context) {
        if (sTaskAndLabelLocalDataSource == null) {
            sTaskAndLabelLocalDataSource = new TaskAndLabelLocalDataSource(TaskAndLabelDAOImpl.getInstance(context));
        }
        return sTaskAndLabelLocalDataSource;
    }

    @Override
    public void getLabelIdsByTask(final int taskId, OnDataLoadingCallback<List<Integer>> callback) {
        LocalGetDataAsync<List<Integer>> listLocalGetDataAsync = new LocalGetDataAsync<>(new GetDataHandler<List<Integer>>() {
            @Override
            public List<Integer> getData() throws Exception {
                return mTaskAndLabelDAO.getListLabelsId(taskId);
            }
        }, callback);
        listLocalGetDataAsync.execute();
    }

    @Override
    public void getTaskIdByLabel(final int labelId, OnDataLoadingCallback<List<Integer>> callback) {
        LocalGetDataAsync<List<Integer>> listLocalGetDataAsync = new LocalGetDataAsync<>(new GetDataHandler<List<Integer>>() {
            @Override
            public List<Integer> getData() throws Exception {
                return mTaskAndLabelDAO.getListTasksId(labelId);
            }
        }, callback);
        listLocalGetDataAsync.execute();
    }

    @Override
    public boolean addItem(int taskId, int labelId) {
        return mTaskAndLabelDAO.addItem(taskId, labelId);
    }
}
