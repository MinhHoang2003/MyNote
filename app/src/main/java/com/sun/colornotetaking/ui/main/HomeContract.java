package com.sun.colornotetaking.ui.main;

import com.sun.colornotetaking.data.model.Task;
import com.sun.colornotetaking.ui.BasePresenter;
import com.sun.colornotetaking.ui.BaseView;

import java.util.List;

public interface HomeContract {

    interface Presenter extends BasePresenter {
        void getPinedTasks();

        void getOtherTasks();

        void addNewTask(Task task);

        void editTask(Task task);

        void removeTask(int id);
    }

    interface View extends BaseView<Presenter> {

        void showPinedTasks(List<Task> PinedTasks);

        void showOtherTasks(List<Task> OtherTasks);

        void showGetDataError(Exception e);

        void showAddTaskDone();

        void showCantAddTask();

        void showEditTaskDone();

        void showCantEditTask();

        void showRemoveTaskDone();

        void showCantRemoveTask();

    }
}
