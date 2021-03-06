package com.myapp.MyNote.ui.task_info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.myapp.MyNote.R;
import com.myapp.MyNote.data.LabelRepository;
import com.myapp.MyNote.data.SubItemRepository;
import com.myapp.MyNote.data.TaskAndLabelRepository;
import com.myapp.MyNote.data.TaskRepository;
import com.myapp.MyNote.data.local.dao.LabelDAO;
import com.myapp.MyNote.data.local.dao.LabelDAOImpl;
import com.myapp.MyNote.data.local.dao.SubItemDAO;
import com.myapp.MyNote.data.local.dao.SubItemDAOImpl;
import com.myapp.MyNote.data.local.dao.TaskAndLabelDAO;
import com.myapp.MyNote.data.local.dao.TaskAndLabelDAOImpl;
import com.myapp.MyNote.data.local.dao.TaskDAO;
import com.myapp.MyNote.data.local.dao.TaskDAOImpl;
import com.myapp.MyNote.data.local.source.LabelDataSource;
import com.myapp.MyNote.data.local.source.LabelLocalDataSource;
import com.myapp.MyNote.data.local.source.SubItemDataSource;
import com.myapp.MyNote.data.local.source.SubItemLocalDataSource;
import com.myapp.MyNote.data.local.source.TaskAndLabelDataSource;
import com.myapp.MyNote.data.local.source.TaskAndLabelLocalDataSource;
import com.myapp.MyNote.data.local.source.TaskDataSource;
import com.myapp.MyNote.data.local.source.TaskLocalDataSource;
import com.myapp.MyNote.data.model.SubItem;
import com.myapp.MyNote.data.model.Task;
import com.myapp.MyNote.ui.adapter.SubItemAdapter;
import com.myapp.MyNote.ui.dialog.DateTimePickerDialog;
import com.myapp.MyNote.ui.dialog.OnDateTimePicked;
import com.myapp.MyNote.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class TaskInfoActivity extends AppCompatActivity implements TaskInfoContract.View, View.OnClickListener, OnDateTimePicked {

    //extra key
    public static final String BUNDLE_DATE = "date";
    public static final String BUNDLE_TIME = "time";

    //view
    private FloatingActionButton mActionButton;
    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private EditText mEditTextAddSubItem;
    private RecyclerView mRecyclerViewSubItems;
    private SubItemAdapter mSubItemAdapter;
    private TextView mTextViewLabel;
    private TextView mTextViewDate;
    private TextView mTextViewTime;
    private Toolbar mToolbar;

    // one task
    private int mTaskId = -1;
    private boolean isPin = false;
    private boolean isDelete = false;
    private String mDate, mTime;
    private List<SubItem> mSubItems = new ArrayList<>();

    //presenter
    private TaskInfoContract.Presenter mPresenter;

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        initView();
        initPresenter();

        //get intent from home
        Intent intent = getIntent();
        mTaskId = intent.getIntExtra(HomeFragment.KEY_RESULT, -1);
        if (mTaskId != -1) mPresenter.getTask(mTaskId);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTaskId == -1) {
                    Task task = getTask();
                    mPresenter.addTask(task);
                    setResult(RESULT_OK);

                } else {
                    mPresenter.editTask(getTask());
                    setResult(RESULT_OK);
                }
                finish();
            }
        });

    }

    private void initPresenter() {
        //DAO
        SubItemDAO subItemDAO = SubItemDAOImpl.getInstance(this);
        TaskDAO taskDAO = TaskDAOImpl.getInstance(this, subItemDAO);
        LabelDAO labelDAO = LabelDAOImpl.getInstance(this);
        TaskAndLabelDAO taskAndLabelDAO = TaskAndLabelDAOImpl.getInstance(this);

        //Local data source
        TaskDataSource taskDataSource = TaskLocalDataSource.getInstance(taskDAO);
        SubItemDataSource subItemDataSource = SubItemLocalDataSource.getInstance(subItemDAO);
        LabelDataSource labelDataSource = LabelLocalDataSource.getInstance(labelDAO);
        TaskAndLabelDataSource taskAndLabelDataSource = TaskAndLabelLocalDataSource.getInstance(taskAndLabelDAO);

        //Repository
        TaskRepository taskRepository = TaskRepository.getInstance(taskDataSource);
        SubItemRepository subItemRepository = SubItemRepository.getInstance(subItemDataSource);
        LabelRepository labelRepository = LabelRepository.getInstance(labelDataSource);
        TaskAndLabelRepository taskAndLabelRepository = TaskAndLabelRepository.getInstance(taskAndLabelDataSource);

        //presenter
        setPresenter(new TaskInfoPresenter(this, taskRepository, labelRepository, taskAndLabelRepository, subItemRepository));
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mActionButton = findViewById(R.id.fab_task_info);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEditTextTitle = mToolbar.findViewById(R.id.edt_title);
        mEditTextDescription = findViewById(R.id.edt_description);
        mEditTextAddSubItem = findViewById(R.id.edt_add_sub_task);
        mTextViewLabel = findViewById(R.id.text_label);
        mTextViewDate = findViewById(R.id.text_date);
        mTextViewTime = findViewById(R.id.text_time);
        mRecyclerViewSubItems = findViewById(R.id.recycle_sub_items);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                onBackPressed();
            }
        });
        mTextViewDate.setOnClickListener(this);
        mTextViewTime.setOnClickListener(this);
        mEditTextAddSubItem.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    SubItem item = new SubItem(mEditTextAddSubItem.getText().toString(), false);
                    mSubItems.add(item);
                    mPresenter.addSubItem(item, mTaskId);
                    hideKeyboardFrom(TaskInfoActivity.this, mEditTextAddSubItem);
                    if (mSubItemAdapter != null)
                        mSubItemAdapter.notifyItemInserted(mSubItems.size() - 1);
                    mEditTextAddSubItem.getText().clear();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_task_info_screen, menu);
        MenuItem itemPin = menu.findItem(R.id.menu_pin);
        MenuItem itemDelete = menu.findItem(R.id.menu_delete);
        changePinIcon(itemPin, isPin);
        changeDeleteIcon(itemDelete, isDelete);
        return true;
    }

    private void changePinIcon(MenuItem item, boolean isPin) {
        if (isPin) item.setIcon(R.drawable.ic_pin_black_48dp);
        else item.setIcon(R.drawable.ic_pin_white_48dp);
    }

    private void changeDeleteIcon(MenuItem item, boolean isDelete) {
        if (isDelete) item.setIcon(R.drawable.ic_delete_black_32dp);
        else item.setIcon(R.drawable.ic_delete_white_32dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pin:
                isPin = !isPin;
                changePinIcon(item, isPin);
                break;
            case R.id.menu_delete:
                isDelete = !isDelete;
                changeDeleteIcon(item, isDelete);
                break;
        }
        return true;
    }

    @Override
    public void showTask(Task task) {
        mEditTextTitle.setText(task.getTitle());
        mSubItems.addAll(task.getSubItems());
        String description = task.getDescription();
        if (description != null) mEditTextDescription.setText(description);
        mDate = task.getDate();
        mTime = task.getTime();
        if (mDate != null) mTextViewDate.setText(mDate);
        if (mTime != null) mTextViewTime.setText(mTime);
        isPin = task.isPin();
        isDelete = task.isDelete();
        mSubItemAdapter = new SubItemAdapter(this, mSubItems);
        mRecyclerViewSubItems.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewSubItems.setAdapter(mSubItemAdapter);
    }

    @Override
    public void showEmptyTask() {

    }

    @Override
    public void showGetTaskError(Exception e) {

    }

    @Override
    public void showAddTaskDone() {

    }

    @Override
    public void showAddTaskError() {

    }

    @Override
    public void showEditTaskDone() {

    }

    @Override
    public void showEditTaskError() {

    }

    @Override
    public void setPresenter(@NonNull TaskInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private Task getTask() {
        Task task = null;
        String title = mEditTextTitle.getText().toString();
        String description = mEditTextDescription.getText().toString();
        if (!title.equals("")) {
            Task.TaskBuilder builder = new Task.TaskBuilder(title);
            if (mTaskId > 0) builder.setId(mTaskId);
            if (description.length() != 0) builder.setDescription(description);
            if (mDate != null) builder.setDate(mDate);
            if (mTime != null) builder.setTime(mTime);
            builder.setSubItems(new ArrayList<SubItem>());
            builder.setIsPin(isPin).setIsDelete(isDelete);
            builder.setSubItems(mSubItems);
            task = builder.build();
        }
        return task;
    }

    @Override
    public void onClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        DateTimePickerDialog dialog = new DateTimePickerDialog();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_DATE, mDate);
        bundle.putString(BUNDLE_TIME, mTime);
        dialog.setArguments(bundle);
        dialog.show(manager, "tag");
    }

    @Override
    public void onDateTimePickListener(String date, String time) {
        if (!date.equals(DateTimePickerDialog.NONE)) {
            mDate = date;
            mTextViewDate.setText(date);
        }
        if (!time.equals(DateTimePickerDialog.NONE)) {
            mTime = time;
            mTextViewTime.setText(time);
        }
    }
}
