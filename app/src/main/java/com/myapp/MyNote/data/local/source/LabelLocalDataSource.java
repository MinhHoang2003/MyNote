package com.myapp.MyNote.data.local.source;

import com.myapp.MyNote.data.OnDataLoadingCallback;
import com.myapp.MyNote.data.local.GetDataHandler;
import com.myapp.MyNote.data.local.LocalGetDataAsync;
import com.myapp.MyNote.data.local.dao.LabelDAO;
import com.myapp.MyNote.data.model.Label;

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
