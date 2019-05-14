package com.myapp.MyNote.ui.adapter.recycle_bin;

public interface OnContextMenuClickListener {

    void onDeleteItem(int position);

    void onRestoreItem(int position);
}
