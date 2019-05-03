package com.sun.colornotetaking.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.colornotetaking.R;
import com.sun.colornotetaking.data.model.Task;

import java.util.List;

public class RecycleAdapter extends BaseAdapter<Task, RecycleAdapter.TaskHolder> {

    //itemId in context menu
    public static final int ID_DELETE = 0;
    public static final int ID_RESTORE = 1;
    private static final int DELETE_ORDER = 0;
    private static final int RESTORE_ORDER = 1;


    public RecycleAdapter(Context context, List<Task> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new RecycleAdapter.TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder taskHolder, int i) {
        taskHolder.bindView(getData().get(i));
    }

    static class TaskHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView mTextTitle;
        private TextView mTextDateTime;
        private ImageView mImageCheck;
        private TextView mTextProgress;

        TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.text_task_title);
            mTextDateTime = itemView.findViewById(R.id.text_date_time);
            mImageCheck = itemView.findViewById(R.id.image_check_items);
            mTextProgress = itemView.findViewById(R.id.text_check_items);
            itemView.setOnCreateContextMenuListener(this);
        }

        private void bindView(Task task) {
            mTextTitle.setText(task.getTitle());
            String date = task.getDate();
            if (date == null || date.equals("")) mTextDateTime.setVisibility(View.GONE);
            else mTextDateTime.setText(task.getDate());
            mTextProgress.setText(task.getDoneItemsCount());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), ID_DELETE, DELETE_ORDER, R.string.title_delete_item);
            menu.add(getAdapterPosition(), ID_RESTORE, RESTORE_ORDER, R.string.title_restore_item);
        }
    }

    public void deleteOrRestoreTask(int position) {
        getData().remove(position);
        notifyItemRemoved(position);
    }

    public void undo(Task task, int position) {
        getData().add(position, task);
        notifyItemInserted(position);
    }
}
