package com.sun.colornotetaking.ui.recycle;

import com.sun.colornotetaking.data.model.Task;
import com.sun.colornotetaking.ui.BasePresenter;
import com.sun.colornotetaking.ui.BaseView;

import java.util.List;

public interface RecycleBinContract {
    interface Presenter extends BasePresenter {

        void getDeletedTask();

        boolean removeTask(int id);

        boolean restoreDeletedTask(Task task);

    }

    interface View extends BaseView<Presenter> {

        void showDeletedTask(List<Task> tasks);

        void showEmptyTask();

        void showGetTaskError(Exception e);

        void showCanNotRemoveTask();

        void showCanNotRestoreTask();
    }
}
