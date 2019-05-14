package com.myapp.MyNote.data.local.source;

import com.myapp.MyNote.data.model.SubItem;
import com.myapp.MyNote.data.local.dao.SubItemDAO;

public class SubItemLocalDataSource implements SubItemDataSource {

    private static SubItemLocalDataSource sSubItemLocalDataSource;
    private SubItemDAO mSubItemDAO;

    private SubItemLocalDataSource(SubItemDAO subItemDAO) {
        mSubItemDAO = subItemDAO;
    }

    public static SubItemLocalDataSource getInstance(SubItemDAO subItemDAO) {
        if (sSubItemLocalDataSource == null) {
            sSubItemLocalDataSource = new SubItemLocalDataSource(subItemDAO);
        }
        return sSubItemLocalDataSource;
    }

    @Override
    public boolean addSubItems(SubItem subItem, int taskId) {
        return mSubItemDAO.addSubItem(subItem, taskId);
    }

    @Override
    public boolean editSubItems(SubItem subItem) {
        return mSubItemDAO.editSubItem(subItem);
    }

    @Override
    public boolean removeSubItem(int id) {
        return mSubItemDAO.removeSubItem(id);
    }

}
