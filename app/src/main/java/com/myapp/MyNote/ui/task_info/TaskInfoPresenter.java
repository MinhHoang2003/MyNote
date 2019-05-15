package com.myapp.MyNote.ui.task_info;


import com.myapp.MyNote.data.LabelRepository;
import com.myapp.MyNote.data.OnDataLoadingCallback;
import com.myapp.MyNote.data.SubItemRepository;
import com.myapp.MyNote.data.TaskAndLabelRepository;
import com.myapp.MyNote.data.TaskRepository;
import com.myapp.MyNote.data.model.SubItem;
import com.myapp.MyNote.data.model.Task;

public class TaskInfoPresenter implements TaskInfoContract.Presenter {

    private TaskInfoContract.View mView;
    private TaskRepository mTaskRepository;
    private LabelRepository mLabelRepository;
    private TaskAndLabelRepository mTaskAndLabelRepository;
    private SubItemRepository mSubItemRepository;

    TaskInfoPresenter(TaskInfoContract.View view, TaskRepository taskRepository, LabelRepository labelRepository, TaskAndLabelRepository taskAndLabelRepository, SubItemRepository subItemRepository) {
        mView = view;
        mTaskRepository = taskRepository;
        mLabelRepository = labelRepository;
        mTaskAndLabelRepository = taskAndLabelRepository;
        mSubItemRepository = subItemRepository;
    }

    @Override
    public void getTask(int id) {
        mTaskRepository.getTaskById(id, new OnDataLoadingCallback<Task>() {
            @Override
            public void onDataLoaded(Task data) {
                if (data != null) mView.showTask(data);
                else mView.showEmptyTask();
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                mView.showGetTaskError(e);
            }
        });
    }

    @Override
    public void addTask(Task task) {
        if (mTaskRepository.addTask(task)) {
            mView.showAddTaskDone();
        } else {
            mView.showAddTaskError();
        }
    }

    @Override
    public void editTask(Task task) {
        if (mTaskRepository.editTask(task)) {
            mView.showEditTaskDone();
        } else {
            mView.showEditTaskError();
        }
    }

    @Override
    public void editSubItem(SubItem subItem) {
        mSubItemRepository.editSubItems(subItem);
    }

    @Override
    public void addSubItem(SubItem subItem, int taskId) {
        if (taskId != -1) {
            mSubItemRepository.addSubItems(subItem, taskId);
        }
    }

    @Override
    public void removeSubItem(int id) {
        mSubItemRepository.removeSubItem(id);
    }

    @Override
    public void start() {

    }
}
