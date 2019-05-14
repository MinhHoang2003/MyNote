package com.myapp.MyNote.data.local.source;

import com.myapp.MyNote.data.OnDataLoadingCallback;

import java.util.List;

public interface TaskAndLabelDataSource {

    void getLabelIdsByTask(int taskId, OnDataLoadingCallback<List<Integer>> callback);

    void getTaskIdByLabel(int labelId, OnDataLoadingCallback<List<Integer>> callback);

    boolean addItem(int taskId, int labelId);
}
