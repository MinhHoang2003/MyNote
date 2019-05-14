package com.myapp.MyNote.data.local.source;

import com.myapp.MyNote.data.OnDataLoadingCallback;
import com.myapp.MyNote.data.model.Label;

import java.util.List;

public interface LabelDataSource {

    void getLabels(OnDataLoadingCallback<List<Label>> callback);

    void getLabelByListId(List<Integer> labelId, OnDataLoadingCallback<List<Label>> callback);

    boolean addLabel(Label label);

    boolean editLabel(Label label);

    boolean removeLabel(int id);

}
