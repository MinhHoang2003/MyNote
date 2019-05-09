package com.sun.colornotetaking.data;

import android.content.Context;

import com.sun.colornotetaking.data.local.source.LabelDataSource;
import com.sun.colornotetaking.data.local.source.LabelLocalDataSource;
import com.sun.colornotetaking.data.model.Label;

import java.util.List;

public class LabelRepository implements LabelDataSource {

    private static LabelRepository sLabelRepository;
    private LabelDataSource mLabelDataSource;

    public LabelRepository(LabelDataSource labelDataSource) {
        mLabelDataSource = labelDataSource;
    }

    public static LabelRepository getInstance(Context context) {

        if (sLabelRepository == null) {
            sLabelRepository = new LabelRepository(LabelLocalDataSource.getInstance(context));
        }
        return sLabelRepository;
    }

    @Override
    public void getLabels(OnDataLoadingCallback<List<Label>> callback) {
        mLabelDataSource.getLabels(callback);
    }

    @Override
    public void getLabelByListId(List<Integer> labelId, OnDataLoadingCallback<List<Label>> callback) {
        mLabelDataSource.getLabelByListId(labelId, callback);
    }

    @Override
    public boolean addLabel(Label label) {
        return mLabelDataSource.addLabel(label);
    }

    @Override
    public boolean editLabel(Label label) {
        return mLabelDataSource.editLabel(label);
    }

    @Override
    public boolean removeLabel(int id) {
        return mLabelDataSource.removeLabel(id);
    }
}
