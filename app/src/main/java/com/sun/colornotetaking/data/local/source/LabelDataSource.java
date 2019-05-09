package com.sun.colornotetaking.data.local.source;

import com.sun.colornotetaking.data.OnDataLoadingCallback;
import com.sun.colornotetaking.data.model.Label;

import java.util.List;

public interface LabelDataSource {

    void getLabels(OnDataLoadingCallback<List<Label>> callback);

    void getLabelByListId(List<Integer> labelId, OnDataLoadingCallback<List<Label>> callback);

    boolean addLabel(Label label);

    boolean editLabel(Label label);

    boolean removeLabel(int id);

}
