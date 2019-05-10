package com.sun.colornotetaking.data.local.source;

import com.sun.colornotetaking.data.OnDataLoadingCallback;
import com.sun.colornotetaking.data.local.GetDataHandler;
import com.sun.colornotetaking.data.local.LocalGetDataAsync;
import com.sun.colornotetaking.data.local.dao.LabelDAO;
import com.sun.colornotetaking.data.model.Label;

import java.util.List;

public class LabelLocalDataSource implements LabelDataSource {

    private static LabelLocalDataSource sLabelLocalDataSource;
    private LabelDAO mLabelDAO;

    private LabelLocalDataSource(LabelDAO labelDAO) {
        mLabelDAO = labelDAO;
    }

    public static LabelLocalDataSource getInstance(LabelDAO labelDAO) {
        if (sLabelLocalDataSource == null) {
            sLabelLocalDataSource = new LabelLocalDataSource(labelDAO);
        }
        return sLabelLocalDataSource;
    }

    @Override
    public void getLabels(OnDataLoadingCallback<List<Label>> callback) {
        LocalGetDataAsync<List<Label>> listLocalGetDataAsync = new LocalGetDataAsync<>(new GetDataHandler<List<Label>>() {
            @Override
            public List<Label> getData() throws Exception {
                return mLabelDAO.getLabels();
            }
        }, callback);
        listLocalGetDataAsync.execute();
    }

    @Override
    public void getLabelByListId(final List<Integer> labelId, OnDataLoadingCallback<List<Label>> callback) {
        LocalGetDataAsync<List<Label>> listLocalGetDataAsync = new LocalGetDataAsync<>(new GetDataHandler<List<Label>>() {
            @Override
            public List<Label> getData() throws Exception {
                return mLabelDAO.getLabels(labelId);
            }
        }, callback);
        listLocalGetDataAsync.execute();
    }

    @Override
    public boolean addLabel(Label label) {
        return mLabelDAO.addLabel(label);
    }

    @Override
    public boolean editLabel(Label label) {
        return mLabelDAO.editLabel(label);
    }

    @Override
    public boolean removeLabel(int id) {
        return mLabelDAO.removeLabelById(id);
    }
}
