package com.myapp.MyNote.ui.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.myapp.MyNote.R;
import com.myapp.MyNote.data.model.Task;
import com.myapp.MyNote.ui.task_info.TaskInfoActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class DateTimePickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    public static final String NONE = "none";

    private TextView mTextDate;
    private TextView mTextTime;
    private Button mButtonOk;
    private Button mButtonReset;
    private Button mButtonCancel;
    private OnDateTimePicked mOnDateTimePicked;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity) context;
            mOnDateTimePicked = (OnDateTimePicked) activity;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_date_time_picker_fragment, container, false);
        initView(view);
        Bundle bundle = getArguments();
        if(bundle!=null) {
            mTextDate.setText(bundle.getString(TaskInfoActivity.BUNDLE_DATE,NONE));
            mTextTime.setText(bundle.getString(TaskInfoActivity.BUNDLE_TIME,NONE));
        }
        setOnClick();
        return view;
    }

    private void initView(View view) {
        mTextDate = view.findViewById(R.id.text_date);
        mTextTime = view.findViewById(R.id.text_time);
        mButtonOk = view.findViewById(R.id.button_ok);
        mButtonReset = view.findViewById(R.id.button_reset);
        mButtonCancel = view.findViewById(R.id.button_cancel);
        resetDateTime();
    }

    private void setOnClick() {
        mTextDate.setOnClickListener(this);
        mTextTime.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
        mButtonReset.setOnClickListener(this);
        mButtonOk.setOnClickListener(this);
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        String dateFormat = DateFormat.getDateInstance().format(calendar.getTime());
        mTextDate.setText(dateFormat);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_date:
                openDatePicker();
                break;
            case R.id.text_time:
                openTimePicker();
                break;
            case R.id.button_cancel:
                dismiss();
                break;
            case R.id.button_reset:
                resetDateTime();
                break;
            case R.id.button_ok:
                mOnDateTimePicked.onDateTimePickListener(mTextDate.getText().toString(), mTextTime.getText().toString());
                dismiss();
                break;
        }

    }

    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), this, hour, minute, true);
        timePickerDialog.show();
    }

    private void resetDateTime() {
        String reset = "None";
        mTextTime.setText(reset);
        mTextDate.setText(reset);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        DateFormat dateFormat = DateFormat.getTimeInstance();
        String time = dateFormat.format(calendar.getTime());
        mTextTime.setText(time);
    }

    public void setOnDateTimePickedListener(OnDateTimePicked listener) {
        mOnDateTimePicked = listener;
    }
}
