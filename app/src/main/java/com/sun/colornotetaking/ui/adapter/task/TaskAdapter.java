package com.sun.colornotetaking.ui.adapter.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.colornotetaking.R;
import com.sun.colornotetaking.data.model.Task;
import com.sun.colornotetaking.ui.adapter.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter<Task, TaskAdapter.TaskHolder> implements Filterable {

    public TaskAdapter(Context context, List<Task> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder taskHolder, int i) {
        taskHolder.bindView(getData().get(i));
    }

    @Override
    public Filter getFilter() {
        return mTaskFilter;
    }

    static class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextTitle;
        private TextView mTextDateTime;
        private ImageView mImageCheck;
        private TextView mTextProgress;

        private TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.test_text_task_title);
            mTextDateTime = itemView.findViewById(R.id.test_text_date_time);
            mImageCheck = itemView.findViewById(R.id.test_image_check_items);
            mTextProgress = itemView.findViewById(R.id.test_text_check_items);
        }

        private void bindView(Task task) {
            mTextTitle.setText(task.getTitle());
            String date = task.getDate();
            if (date == null || date.equals("")) {
                mTextDateTime.setVisibility(View.GONE);
            }
            else mTextDateTime.setText(date);
            mTextProgress.setText(task.getDoneItemsCount());
        }
    }

    private Filter mTaskFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Task> filteredList = new ArrayList<>();
            if (constraint.length() == 0) {
                filteredList.addAll(getData());
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Task item : getData()) {
                    if (item.isSearchedItem(filterPattern)) filteredList.add(item);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Task> resultList = (List<Task>) results.values;
            if (resultList != null) {
                getData().clear();
                getData().addAll(resultList);
                notifyDataSetChanged();
            }
        }
    };
}
