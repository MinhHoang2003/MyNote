package com.sun.colornotetaking.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.colornotetaking.R;
import com.sun.colornotetaking.data.TaskRepository;
import com.sun.colornotetaking.data.local.dao.SubItemDAOImpl;
import com.sun.colornotetaking.data.local.dao.TaskDAO;
import com.sun.colornotetaking.data.local.dao.TaskDAOImpl;
import com.sun.colornotetaking.data.local.source.TaskDataSource;
import com.sun.colornotetaking.data.local.source.TaskLocalDataSource;
import com.sun.colornotetaking.data.model.Task;
import com.sun.colornotetaking.ui.adapter.task.TaskAdapter;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {

    private RecyclerView mPinedTaskRecyclerView;
    private RecyclerView mNormalTaskRecyclerView;
    private TextView mTextOtherTaskTitle;
    private TextView mTextPinedTaskTitle;
    private HomeContract.Presenter mPresenter;
    private TaskAdapter mPinedAdapter;
    private TaskAdapter mOtherAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        //initPresenter
        TaskDAO taskDAO = TaskDAOImpl.getInstance(getContext(), SubItemDAOImpl.getInstance(getContext()));
        TaskDataSource taskDataSource = TaskLocalDataSource.getInstance(taskDAO);
        TaskRepository taskRepository = TaskRepository.getInstance(taskDataSource);
        setPresenter(new HomePresenter(taskRepository, this));
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_layout, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_toolbar_main_item, menu);

        //event search
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(mPinedAdapter!=null) mPinedAdapter.getFilter().filter(s);
                if(mOtherAdapter!=null) mOtherAdapter.getFilter().filter(s);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showPinedTasks(List<Task> tasks) {
        if (tasks.size() != 0) mTextPinedTaskTitle.setVisibility(View.VISIBLE);
        mPinedAdapter = new TaskAdapter(getContext(), tasks);
        mPinedTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPinedTaskRecyclerView.setAdapter(mPinedAdapter);
    }

    @Override
    public void showOtherTasks(List<Task> tasks) {
        if (tasks.size() != 0) mTextOtherTaskTitle.setVisibility(View.VISIBLE);
        mOtherAdapter = new TaskAdapter(getContext(), tasks);
        mNormalTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNormalTaskRecyclerView.setAdapter(mOtherAdapter);
    }

    @Override
    public void showEmptyTasks() {
        Toast.makeText(getContext(), R.string.msg_note_not_existing, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadTasksError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddTaskDone() {
        Toast.makeText(getContext(), R.string.msg_add_task_done, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCanNotAddTask() {
        Toast.makeText(getContext(), R.string.msg_can_not_add_task, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEditTaskDone() {
        Toast.makeText(getContext(), R.string.msg_edit_task_done, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCanNotEditTask() {
        Toast.makeText(getContext(), R.string.msg_can_not_edit_task, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRemoveTaskDone() {
        Toast.makeText(getContext(), R.string.msg_remove_task_done, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCanNotRemoveTask() {
        Toast.makeText(getContext(), R.string.msg_can_not_remove_task, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(@NonNull HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void initView(View view) {
        mPinedTaskRecyclerView = view.findViewById(R.id.recycle_pined);
        mNormalTaskRecyclerView = view.findViewById(R.id.recycle_normal);
        mTextOtherTaskTitle = view.findViewById(R.id.title_normal);
        mTextPinedTaskTitle = view.findViewById(R.id.title_pin);
    }
}
