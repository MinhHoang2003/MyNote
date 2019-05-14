package com.myapp.MyNote.data;

import com.myapp.MyNote.data.local.source.SubItemDataSource;
import com.myapp.MyNote.data.model.SubItem;

public class SubItemRepository implements SubItemDataSource {

    private static SubItemRepository sSubItemRepository;
    private SubItemDataSource mSubItemDataSource;

    private SubItemRepository(SubItemDataSource subItemDataSource) {
        mSubItemDataSource = subItemDataSource;
    }

    public static SubItemRepository getInstance(SubItemDataSource subItemDataSource) {
        if (sSubItemRepository == null) {
            sSubItemRepository = new SubItemRepository(subItemDataSource);
        }
        return sSubItemRepository;
    }

    @Override
    public boolean addSubItems(SubItem subItem, int taskId) {
        return mSubItemDataSource.addSubItems(subItem, taskId);
    }

    @Override
    public boolean editSubItems(SubItem subItem) {
        return mSubItemDataSource.editSubItems(subItem);
    }

    @Override
    public boolean removeSubItem(int id) {
        return mSubItemDataSource.removeSubItem(id);
    }
}
