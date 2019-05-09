package com.sun.colornotetaking.data.local.source;

import android.content.Context;

import com.sun.colornotetaking.data.local.dao.SubItemDAO;
import com.sun.colornotetaking.data.local.dao.SubItemDAOImpl;
import com.sun.colornotetaking.data.model.SubItem;

public class SubItemLocalDataSource implements SubItemDataSource {

    private static SubItemLocalDataSource sSubItemLocalDataSource;
    private SubItemDAO mSubItemDAO;

    private SubItemLocalDataSource(SubItemDAO subItemDAO) {
        mSubItemDAO = subItemDAO;
    }

    public static SubItemLocalDataSource getInstance(Context context) {
        if (sSubItemLocalDataSource == null) {
            sSubItemLocalDataSource = new SubItemLocalDataSource(SubItemDAOImpl.getInstance(context));
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
