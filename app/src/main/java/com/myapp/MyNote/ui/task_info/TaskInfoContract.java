package com.myapp.MyNote.ui.task_info;

import com.myapp.MyNote.data.model.SubItem;
import com.myapp.MyNote.data.model.Task;
import com.myapp.MyNote.ui.BasePresenter;
import com.myapp.MyNote.ui.BaseView;


public interface TaskInfoContract {

    interface View extends BaseView<Presenter> {

        void showTask(Task task);

        void showEmptyTask();

        void showGetTaskError(Exception e);

        void showAddTaskDone();

        void showAddTaskError();

        void showEditTaskDone();

        void showEditTaskError();

    }

    interface Presenter extends BasePresenter {

        void getTask(int id);

        void addTask(Task task);

        void editTask(Task task);

        void editSubItem(SubItem subItem);

        void addSubItem(SubItem subItem, int taskId);

        void removeSubItem(int id);

        //TODO: label add and edit.
    }
}
