package com.sun.colornotetaking.ui.adapter.recycle_bin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.colornotetaking.R;
import com.sun.colornotetaking.data.model.Task;
import com.sun.colornotetaking.ui.adapter.BaseAdapter;

import java.util.List;

public class TaskDeletedAdapter extends BaseAdapter<Task, TaskDeletedAdapter.ViewHolder> {

    private OnContextMenuClickListener mOnContextMenuClickListener;

    public TaskDeletedAdapter(Context context, List<Task> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new ViewHolder(view, mOnContextMenuClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(getData().get(i));
    }

    public void deleteTask(int position) {
        getData().remove(position);
        notifyItemRemoved(position);
    }

    public void insertTask(Task task, int position) {
        getData().add(position, task);
        notifyItemInserted(position);
    }

    public void setOnContextMenuClickListener(OnContextMenuClickListener listener) {
        mOnContextMenuClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        //itemId in context menu
        private static final int MENU_ITEM_DELETE = 0;
        private static final int MENU_ITEM_RESTORE = 1;

        private TextView mTextTitle;
        private TextView mTextDateTime;
        private ImageView mImageCheck;
        private TextView mTextProgress;
        private OnContextMenuClickListener mOnContextMenuClickListener;

        ViewHolder(@NonNull View itemView, OnContextMenuClickListener listener) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.text_task_title);
            mTextDateTime = itemView.findViewById(R.id.text_date_time);
            mImageCheck = itemView.findViewById(R.id.image_check_items);
            mTextProgress = itemView.findViewById(R.id.text_check_items);
            mOnContextMenuClickListener = listener;
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem itemDelete = menu.add(Menu.NONE, MENU_ITEM_DELETE, Menu.NONE, R.string.title_delete_item);
            MenuItem itemRestore = menu.add(Menu.NONE, MENU_ITEM_RESTORE, Menu.NONE, R.string.title_restore_item);
            itemDelete.setOnMenuItemClickListener(this);
            itemRestore.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case MENU_ITEM_DELETE:
                    mOnContextMenuClickListener.onDeleteItem(getAdapterPosition());
                    break;
                case MENU_ITEM_RESTORE:
                    mOnContextMenuClickListener.onRestoreItem(getAdapterPosition());
                    break;
            }
            return true;
        }

        private void bindView(Task task) {
            mTextTitle.setText(task.getTitle());
            String date = task.getDate();
            if (date == null || date.equals("")) mTextDateTime.setVisibility(View.GONE);
            else mTextDateTime.setText(task.getDate());
            mTextProgress.setText(task.getDoneItemsCount());
        }
    }

}
