package com.myapp.MyNote.data.local.source;

import com.myapp.MyNote.data.model.SubItem;

public interface SubItemDataSource {

    boolean addSubItems(SubItem subItem, int taskId);

    boolean editSubItems(SubItem subItem);

    boolean removeSubItem(int id);

}
