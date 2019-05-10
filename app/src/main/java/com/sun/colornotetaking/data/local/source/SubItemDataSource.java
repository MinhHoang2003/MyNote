package com.sun.colornotetaking.data.local.source;

import com.sun.colornotetaking.data.OnDataLoadingCallback;
import com.sun.colornotetaking.data.model.SubItem;

import java.util.List;

public interface SubItemDataSource {

    boolean addSubItems(SubItem subItem, int taskId);

    boolean editSubItems(SubItem subItem);

    boolean removeSubItem(int id);

}
