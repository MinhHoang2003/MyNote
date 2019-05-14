package com.myapp.MyNote.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myapp.MyNote.data.local.entry.LabelEntry;
import com.myapp.MyNote.data.local.entry.SubItemEntry;
import com.myapp.MyNote.data.local.entry.TaskAndLabelEntry;
import com.myapp.MyNote.data.local.entry.TaskEntry;

public class AppDatabase extends SQLiteOpenHelper {

    private static AppDatabase sAppDatabase;

    private static final String DATABASE_NAME = "task_database";
    private static final int VERSION = 1;

    private static final String CREATE_TASK_TABLE = "CREATE TABLE " + TaskEntry.TABLE_NAME + " ("
            + TaskEntry.ID + " integer primary key autoincrement, "
            + TaskEntry.TITLE + " text not null, "
            + TaskEntry.DESCRIPTION + " text, "
            + TaskEntry.DATE + " text, "
            + TaskEntry.TIME + " text, "
            + TaskEntry.IS_PIN + " boolean default 0 not null, "
            + TaskEntry.IS_DELETE + " boolean default 0 not null);";

    private static final String CREATE_CHECK_ITEM_TABLE = "CREATE TABLE " + SubItemEntry.TABLE_NAME + "("
            + SubItemEntry.ID + " integer primary key, "
            + SubItemEntry.NAME + " text not null, "
            + SubItemEntry.IS_DONE + " boolean not null, "
            + SubItemEntry.TASK_ID + " integer not null, "
            + " foreign key (" + SubItemEntry.TASK_ID + ") references "
            + TaskEntry.TABLE_NAME + "(" + TaskEntry.ID + "));";

    private static final String CREATE_TAG_TABLE = "CREATE TABLE " + LabelEntry.TABLE_NAME + " ("
            + LabelEntry.ID + " integer primary key autoincrement, "
            + LabelEntry.LABEL_NAME + " text not null, "
            + LabelEntry.COLOR + " integer default 0 not null );";

    private static final String CREATE_TASK_AND_TAG_TABLE = "CREATE TABLE " + TaskAndLabelEntry.TABLE_NAME + " ("
            + TaskAndLabelEntry.TASK_ID + " int not null, "
            + TaskAndLabelEntry.LABEL_ID + " int not null, "
            + "primary key (" + TaskEntry.ID + "," + LabelEntry.ID + "));";

    private static final String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME;
    private static final String DROP_CHECK_ITEM_TABLE = "DROP TABLE IF EXISTS " + SubItemEntry.TABLE_NAME;
    private static final String DROP_TAG_TABLE = "DROP TABLE IF EXISTS " + LabelEntry.TABLE_NAME;
    private static final String DROP_TASK_AND_TAG_TABLE = "DROP TABLE IF EXISTS " + TaskAndLabelEntry.TABLE_NAME;

    protected SQLiteDatabase mSQLiteDatabase;

    protected AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static AppDatabase getInstance(Context context) {
        if (sAppDatabase == null) {
            sAppDatabase = new AppDatabase(context);
        }
        return sAppDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_CHECK_ITEM_TABLE);
        db.execSQL(CREATE_TAG_TABLE);
        db.execSQL(CREATE_TASK_AND_TAG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_CHECK_ITEM_TABLE);
        db.execSQL(DROP_TASK_TABLE);
        db.execSQL(DROP_TAG_TABLE);
        db.execSQL(DROP_TASK_AND_TAG_TABLE);
        onCreate(db);
    }
}
