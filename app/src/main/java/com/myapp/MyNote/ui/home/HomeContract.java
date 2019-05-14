package com.myapp.MyNote.ui.home;

import com.myapp.MyNote.data.model.Task;
import com.myapp.MyNote.ui.BaseView;
import com.myapp.MyNote.ui.BasePresenter;

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

        void showEmptyTasks();

        void showLoadTasksError(Exception e);

        void showAddTaskDone();

        void showCanNotAddTask();

        void showEditTaskDone();

        void showCanNotEditTask();

        void showRemoveTaskDone();

        void showCanNotRemoveTask();

    }
}
