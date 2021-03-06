package com.myapp.MyNote.ui.adapter.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.MyNote.R;
import com.myapp.MyNote.data.model.Task;
import com.myapp.MyNote.ui.adapter.BaseAdapter;
import com.myapp.MyNote.ui.adapter.recycle_bin.OnContextMenuClickListener;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter<Task, TaskAdapter.TaskHolder> implements Filterable {

    private ItemClickListener mItemClickListener;
    private List<Task> mSearchList;
    private OnContextMenuClickListener mOnContextMenuClickListener;
    private Filter mTaskFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Task> filteredList = new ArrayList<>();
            if (constraint.length() == 0) {
                filteredList.addAll(mSearchList);
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
            List<Task> resultList = (ArrayList<Task>) results.values;
            if (resultList != null) {
                getData().clear();
                getData().addAll(resultList);
                notifyDataSetChanged();
            }
        }
    };

    public TaskAdapter(Context context, List<Task> data) {
        super(context, data);
        mSearchList = new ArrayList<>(getData());
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskHolder(view, mOnContextMenuClickListener, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder taskHolder, int i) {
        taskHolder.bindView(getData().get(i));
    }

    public void setOnContextMenuClickListener(OnContextMenuClickListener listener) {
        mOnContextMenuClickListener = listener;
    }

    public void deleteTask(int position) {
        getData().remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public Filter getFilter() {
        return mTaskFilter;
    }

    public void setItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    static class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private TextView mTextTitle;
        private TextView mTextDateTime;
        private ImageView mImageCheck;
        private TextView mTextProgress;
        private OnContextMenuClickListener mOnContextMenuClickListener;
        private ItemClickListener mItemClickListener;

        private TaskHolder(@NonNull View itemView, OnContextMenuClickListener listener, ItemClickListener itemClickListener) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.text_task_title);
            mTextDateTime = itemView.findViewById(R.id.text_date);
            mImageCheck = itemView.findViewById(R.id.image_check_items);
            mTextProgress = itemView.findViewById(R.id.text_check_items);
            mOnContextMenuClickListener = listener;
            mItemClickListener = itemClickListener;
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem itemDelete = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, R.string.title_delete_item);
            itemDelete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            mOnContextMenuClickListener.onDeleteItem(getAdapterPosition());
            return true;
        }

        private void bindView(Task task) {
            mTextTitle.setText(task.getTitle());
            String date = task.getDate();
            if (date == null || date.equals("")) {
                mTextDateTime.setVisibility(View.GONE);
            } else mTextDateTime.setText(date);
            mTextProgress.setText(task.getDoneItemsCount());
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onClick(getAdapterPosition());
        }
    }
}
