package com.myapp.MyNote.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.myapp.MyNote.R;
import com.myapp.MyNote.data.model.SubItem;

import java.util.List;

public class SubItemAdapter extends BaseAdapter<SubItem, SubItemAdapter.ItemHolder> {

    public SubItemAdapter(Context context, List<SubItem> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_sub_items, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.bindView(getData().get(i));
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        CheckBox mCheckBoxIsDone;
        TextView mTextSubItemTitle;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBoxIsDone = itemView.findViewById(R.id.checkbox_isDone);
            mTextSubItemTitle = itemView.findViewById(R.id.text_sub_item_tile);
        }

        void bindView(SubItem subItem) {
            mCheckBoxIsDone.setSelected(subItem.isDone());
            mTextSubItemTitle.setText(subItem.getName());
        }
    }
}
