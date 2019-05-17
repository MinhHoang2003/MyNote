package com.sun.colornotetaking.ui.recycle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sun.colornotetaking.R;
import com.sun.colornotetaking.data.TaskRepository;
import com.sun.colornotetaking.data.local.dao.SubItemDAOImpl;
import com.sun.colornotetaking.data.local.dao.TaskDAO;
import com.sun.colornotetaking.data.local.dao.TaskDAOImpl;
import com.sun.colornotetaking.data.local.source.TaskDataSource;
import com.sun.colornotetaking.data.local.source.TaskLocalDataSource;
import com.sun.colornotetaking.data.model.Task;
import com.sun.colornotetaking.ui.adapter.recycle_bin.OnContextMenuClickListener;
import com.sun.colornotetaking.ui.adapter.recycle_bin.TaskDeletedAdapter;

import java.util.List;

public class RecycleBinFragment extends Fragment implements RecycleBinContract.View, OnContextMenuClickListener {

    private RecyclerView mRecyclerViewDeletedTask;
    private RecycleBinContract.Presenter mPresenter;
    private TaskDeletedAdapter mAdapter;
    private int mPosition = -1;
    private Task mCachedTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.recycle_bin_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewDeletedTask = view.findViewById(R.id.recycler_deleted);
        TaskDAO taskDAO = TaskDAOImpl.getInstance(getContext(), SubItemDAOImpl.getInstance(getContext()));
        TaskDataSource taskDataSource = TaskLocalDataSource.getInstance(taskDAO);
        TaskRepository taskRepository = TaskRepository.getInstance(taskDataSource);
        setPresenter(new RecycleBinPresenter(taskRepository, this));
        mPresenter.start();
    }

    @Override
    public void showDeletedTask(List<Task> tasks) {
        mAdapter = new TaskDeletedAdapter(getContext(), tasks);
        mAdapter.setOnContextMenuClickListener(this);
        mRecyclerViewDeletedTask.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewDeletedTask.setAdapter(mAdapter);
    }

    @Override
    public void showEmptyTask() {
        Toast.makeText(getContext(), getResources().getString(R.string.msg_recycle_bin_empty), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGetTaskError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCanNotUndoRestore() {
        Toast.makeText(getContext(), R.string.msg_can_not_undo_restore, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCanNotRemoveTask() {
        Toast.makeText(getContext(), R.string.msg_remove_task_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRestoreTaskDone() {
        Snackbar snackbar = null;
        StringBuilder message = new StringBuilder(mCachedTask.getTitle());
        if (mPosition != -1) {
            mAdapter.deleteTask(mPosition);
            message.append(getResources().getString(R.string.msg_task_has_restore_from_list));
            snackbar = Snackbar.make(getView(), message.toString(), Snackbar.LENGTH_LONG);
        }
        if (snackbar != null) {
            snackbar.setAction(R.string.action_undo, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCachedTask.setDelete(true);
                    mAdapter.insertTask(mCachedTask, mPosition);
                    mPresenter.undoRestoreTask(mCachedTask);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void showCanNotRestoreTask() {
        Toast.makeText(getContext(), R.string.msg_restore_task_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(@NonNull RecycleBinContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_recycle_bin, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDeleteItem(int position) {
        setCachedTask(position);
        StringBuilder message = new StringBuilder(mCachedTask.getTitle());
        //soft delete
        mAdapter.deleteTask(mPosition);
        message.append(getResources().getString(R.string.msg_task_has_remove_from_list));
        Snackbar snackbar = Snackbar.make(getView(), message.toString(), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.action_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.insertTask(mCachedTask, mPosition);
            }
        }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (event != DISMISS_EVENT_ACTION) {
                    //delete on database when user miss undo action
                    mPresenter.removeTask(mCachedTask.getId());
                }
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    public void onRestoreItem(int position) {
        setCachedTask(position);
        mPresenter.restoreDeletedTask(mCachedTask);
    }

    private void setCachedTask(int position) {
        mPosition = position;
        mCachedTask = mAdapter.getData().get(position);
    }
}
