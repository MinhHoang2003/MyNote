package com.sun.colornotetaking.ui.recycle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.sun.colornotetaking.ui.adapter.TaskDeletedAdapter;

import java.util.List;

public class RecycleBinFragment extends Fragment implements RecycleBinContract.View {

    private RecyclerView mRecyclerViewDeletedTask;
    private RecycleBinContract.Presenter mPresenter;
    private TaskDeletedAdapter mAdapter;

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
    public void showCanNotRemoveTask() {
        Toast.makeText(getContext(), getResources().getString(R.string.msg_remove_task_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCanNotRestoreTask() {
        Toast.makeText(getContext(), getResources().getString(R.string.msg_restore_task_error), Toast.LENGTH_SHORT).show();
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
    public boolean onContextItemSelected(MenuItem item) {
        final int position = item.getGroupId();
        final Task task = mAdapter.getData().get(position);
        Snackbar snackbar = null;
        StringBuilder message = new StringBuilder(task.getTitle());
        switch (item.getItemId()) {
            case TaskDeletedAdapter.ID_DELETE:
                if (mPresenter.removeTask(task.getId())) {
                    mAdapter.deleteOrRestoreTask(position);
                    message.append(R.string.msg_task_has_remove_from_list);
                    snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
                }
                break;
            case TaskDeletedAdapter.ID_RESTORE:
                if (mPresenter.restoreDeletedTask(task)) {
                    mAdapter.deleteOrRestoreTask(position);
                    message.append(R.string.msg_task_has_restore_from_list);
                    snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
                }
                break;
        }
        if (snackbar != null) {
            snackbar.setAction(R.string.action_undo, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.undo(task, position);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
        return super.onContextItemSelected(item);
    }
}
