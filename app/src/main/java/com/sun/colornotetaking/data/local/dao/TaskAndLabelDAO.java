package com.sun.colornotetaking.data.local.dao;

import java.util.List;

public interface TaskAndLabelDAO {

    boolean addItem(int taskId,int labelId);

    List<Integer> getListLabelsId(int taskId);

    List<Integer> getListTasksId(int labelId);
}
