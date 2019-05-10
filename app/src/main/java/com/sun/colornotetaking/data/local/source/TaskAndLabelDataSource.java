package com.sun.colornotetaking.data.local.source;

import com.sun.colornotetaking.data.OnDataLoadingCallback;

import java.util.List;

public interface TaskAndLabelDataSource {

    void getLabelIdsByTask(int taskId, OnDataLoadingCallback<List<Integer>> callback);

    void getTaskIdByLabel(int labelId, OnDataLoadingCallback<List<Integer>> callback);

    boolean addItem(int taskId, int labelId);
}
