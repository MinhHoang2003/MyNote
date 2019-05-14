package com.myapp.MyNote.data.local.dao;

import com.myapp.MyNote.data.model.Label;

import java.util.List;

public interface LabelDAO {

    boolean addLabel(Label label);

    List<Label> getLabels();

    List<Label> getLabels(List<Integer> labelId);

    Label getLabelById(int labelId);

    boolean editLabel(Label label);

    boolean removeLabelById(int labelId);

}
