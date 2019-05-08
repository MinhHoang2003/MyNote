package com.sun.colornotetaking.data.local.dao;

import com.sun.colornotetaking.data.model.SubItem;

import java.util.List;

public interface SubItemDAO {

    boolean addSubItem(SubItem subItem, long taskId);

    int addSubItems(List<SubItem> subItems, long taskId);

    List<SubItem> getSubItemsByTaskId(int taskId);

    boolean editSubItem(SubItem subItem);

    boolean removeSubItem(int id);

    boolean removeAllSubItemsByTaskId(int taskId);
}
