package com.sun.colornotetaking.data.local.dao;

import com.sun.colornotetaking.data.model.CheckItem;

import java.util.List;

public interface CheckItemDAO {

    boolean addCheckItem(CheckItem checkItem, long task_id);

    boolean addCheckItems(List<CheckItem> checkItems,long task_id);

    List<CheckItem> getCheckItemsByTaskId(int taskId);

    boolean editCheckItem(CheckItem checkItem, int taskId);

    boolean removeCheckItem(int checkItemId);

    boolean removeAllCheckItemsByTaskId(int taskId);
}
