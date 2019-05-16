package com.myapp.MyNote.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.myapp.MyNote.R;
import com.myapp.MyNote.data.TaskRepository;
import com.myapp.MyNote.data.local.dao.SubItemDAOImpl;
import com.myapp.MyNote.data.local.dao.TaskDAO;
import com.myapp.MyNote.data.local.dao.TaskDAOImpl;
import com.myapp.MyNote.data.local.source.TaskDataSource;
import com.myapp.MyNote.data.local.source.TaskLocalDataSource;
import com.myapp.MyNote.data.model.Task;
import com.myapp.MyNote.ui.adapter.recycle_bin.OnContextMenuClickListener;
import com.myapp.MyNote.ui.adapter.task.ItemClickListener;
import com.myapp.MyNote.ui.adapter.task.TaskAdapter;
import com.myapp.MyNote.ui.task_info.TaskInfoActivity;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements HomeContract.View {

    public static final int REQUEST_CODE_EDIT = 0;
    public static final int REQUEST_CODE_CREATE = 1;
    public static final String KEY_RESULT = "task";

    private RecyclerView mPinedTaskRecyclerView;
    private RecyclerView mNormalTaskRecyclerView;
    private TextView mTextOtherTaskTitle;
    private TextView mTextPinedTaskTitle;
    private HomeContract.Presenter mPresenter;
    private TaskAdapter mPinedAdapter;
    private TaskAdapter mOtherAdapter;
    private FloatingActionButton mActionButton;
    SearchView searchView;

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
        searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (mPinedAdapter != null) mPinedAdapter.getFilter().filter(s);
                if (mOtherAdapter != null) mOtherAdapter.getFilter().filter(s);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showPinedTasks(List<Task> tasks) {
        if (tasks.size() != 0) mTextPinedTaskTitle.setVisibility(View.VISIBLE);
        mPinedAdapter = new TaskAdapter(getContext(), tasks);
        mPinedAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                int id = mPinedAdapter.getData().get(position).getId();
                Intent intent = new Intent(getContext(), TaskInfoActivity.class);
                intent.putExtra(KEY_RESULT, id);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
        mPinedTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPinedTaskRecyclerView.setAdapter(mPinedAdapter);
        mPinedAdapter.setOnContextMenuClickListener(new OnContextMenuClickListener() {
            @Override
            public void onDeleteItem(int position) {
                mPresenter.editTask(mPinedAdapter.getData().get(position));
                mPinedAdapter.deleteTask(position);
            }

            @Override
            public void onRestoreItem(int position) {

            }
        });
    }

    @Override
    public void showOtherTasks(List<Task> tasks) {
        if (tasks.size() != 0) mTextOtherTaskTitle.setVisibility(View.VISIBLE);
        mOtherAdapter = new TaskAdapter(getContext(), tasks);
        mOtherAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                int id = mOtherAdapter.getData().get(position).getId();
                Intent intent = new Intent(getContext(), TaskInfoActivity.class);
                intent.putExtra(KEY_RESULT, id);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
        mNormalTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNormalTaskRecyclerView.setAdapter(mOtherAdapter);
        mOtherAdapter.setOnContextMenuClickListener(new OnContextMenuClickListener() {
            @Override
            public void onDeleteItem(int position) {
                mPresenter.editTask(mOtherAdapter.getData().get(position));
                mOtherAdapter.deleteTask(position);
            }

            @Override
            public void onRestoreItem(int position) {

            }
        });
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
        mActionButton = view.findViewById(R.id.fab);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TaskInfoActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (mPinedAdapter != null) mPinedAdapter.getData().clear();
            if (mOtherAdapter != null) mOtherAdapter.getData().clear();
            mPresenter.start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
