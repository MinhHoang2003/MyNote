package com.myapp.MyNote.ui.recycle;

import com.myapp.MyNote.data.model.Task;
import com.myapp.MyNote.ui.BaseView;
import com.myapp.MyNote.ui.BasePresenter;

import java.util.List;

public interface RecycleBinContract {
    interface Presenter extends BasePresenter {

        void getDeletedTask();

        void removeTask(int id);

        void restoreDeletedTask(Task task);

        void undoRestoreTask(Task task);

    }

    interface View extends BaseView<Presenter> {

        void showDeletedTask(List<Task> tasks);

        void showEmptyTask();

        void showGetTaskError(Exception e);

        void showCanNotUndoRestore();

        void  showCanNotRemoveTask();

        void showRestoreTaskDone();

        void showCanNotRestoreTask();
    }
}
