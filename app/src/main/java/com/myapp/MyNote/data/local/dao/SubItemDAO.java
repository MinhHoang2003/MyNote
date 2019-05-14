package com.myapp.MyNote.data.local.dao;

import com.myapp.MyNote.data.model.SubItem;

import java.util.List;

public interface SubItemDAO {

    boolean addSubItem(SubItem subItem, long taskId);

    int addSubItems(List<SubItem> subItems, long taskId);

    List<SubItem> getSubItemsByTaskId(int taskId);

    boolean editSubItem(SubItem subItem);

    boolean removeSubItem(int subItemId);

    boolean removeAllSubItemsByTaskId(int taskId);

}
