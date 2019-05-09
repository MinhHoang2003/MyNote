package com.sun.colornotetaking.data;

import android.content.Context;

import com.sun.colornotetaking.data.local.source.SubItemDataSource;
import com.sun.colornotetaking.data.local.source.SubItemLocalDataSource;
import com.sun.colornotetaking.data.model.SubItem;

import java.util.List;

public class SubItemRepository implements SubItemDataSource {

    private static SubItemRepository sSubItemRepository;
    private SubItemDataSource mSubItemDataSource;

    private SubItemRepository(SubItemDataSource subItemDataSource) {
        mSubItemDataSource = subItemDataSource;
    }

    public static SubItemRepository getInstance(Context context) {
        if (sSubItemRepository == null) {
            sSubItemRepository = new SubItemRepository(SubItemLocalDataSource.getInstance(context));
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
