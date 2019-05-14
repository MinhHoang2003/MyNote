package com.myapp.MyNote.data.local.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.myapp.MyNote.data.local.AppDatabase;
import com.myapp.MyNote.data.local.entry.LabelEntry;
import com.myapp.MyNote.data.model.Label;

import java.util.ArrayList;
import java.util.List;

public class LabelDAOImpl extends AppDatabase implements LabelDAO {

    private static LabelDAOImpl sLabelDAO;

    private LabelDAOImpl(Context context) {
        super(context);
    }

    public static LabelDAOImpl getInstance(Context context) {
        if (sLabelDAO == null) {
            sLabelDAO = new LabelDAOImpl(context);
        }
        return sLabelDAO;
    }

    @Override
    public boolean addLabel(Label label) {
        mSQLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LabelEntry.LABEL_NAME, label.getName());
        long result = mSQLiteDatabase.insert(LabelEntry.TABLE_NAME, null, values);
        mSQLiteDatabase.close();
        return result > 0;
    }

    @Override
    public List<Label> getLabels() {
        List<Label> labels = new ArrayList<>();
        mSQLiteDatabase = getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.query(LabelEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Label label = createLabel(cursor);
                labels.add(label);
            } while (cursor.moveToNext());
        }
        cursor.close();
        mSQLiteDatabase.close();
        return labels;
    }

    @Override
    public List<Label> getLabels(List<Integer> labelId) {
        List<Label> labels = new ArrayList<>();
        mSQLiteDatabase = getReadableDatabase();
        String selection = LabelEntry.ID + " IN (" + toString(labelId) + ") ";
        Cursor cursor = mSQLiteDatabase.query(LabelEntry.TABLE_NAME, null, selection, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(createLabel(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        mSQLiteDatabase.close();
        return labels;
    }

    private String toString(List<Integer> labelId) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < labelId.size(); i++) {
            if (i != labelId.size() - 1) {
                builder.append(labelId.get(i));
                builder.append(", ");
            } else builder.append(labelId.get(i));
        }
        return builder.toString();
    }

    @Override
    public Label getLabelById(int labelId) {
        Label label = null;
        mSQLiteDatabase = getReadableDatabase();
        String selection = LabelEntry.ID + " = ?";
        String[] selectionArgs = {String.valueOf(labelId)};
        Cursor cursor = mSQLiteDatabase.query(LabelEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            label = createLabel(cursor);
        }
        cursor.close();
        mSQLiteDatabase.close();
        return label;
    }


    @Override
    public boolean editLabel(Label label) {
        mSQLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LabelEntry.ID, label.getId());
        values.put(LabelEntry.LABEL_NAME, label.getName());
        values.put(LabelEntry.COLOR, label.getColor());
        String where = LabelEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(label.getId())};
        int result = mSQLiteDatabase.update(LabelEntry.TABLE_NAME, values, where, whereArgs);
        mSQLiteDatabase.close();
        return result > 0;
    }

    @Override
    public boolean removeLabelById(int labelId) {
        String where = LabelEntry.ID + " = ?";
        String[] whereArg = {String.valueOf(labelId)};
        mSQLiteDatabase = getWritableDatabase();
        int result = mSQLiteDatabase.delete(LabelEntry.TABLE_NAME, where, whereArg);
        mSQLiteDatabase.close();
        return result >= 0;
    }

    private Label createLabel(Cursor cursor) {
        return new Label(cursor.getInt(cursor.getColumnIndex(LabelEntry.ID)),
                cursor.getString(cursor.getColumnIndex(LabelEntry.LABEL_NAME)),
                cursor.getInt(cursor.getColumnIndex(LabelEntry.COLOR)));
    }
}
