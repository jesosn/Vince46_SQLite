package com.codingke.vince46_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * descreption:
 * company:
 * Created by vince on 15/3/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "pet.db";
    private static final int VERSION = 1;

    private static final String CREATE_TABLE_DOG = "CREATE TABLE dog(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT,age INTEGER)";
    private static final String DROP_TABLE_DOG = "DROP TABLE IF EXISTS dog";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    //如果数据库表不存在，那么会调用该方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQLiteDatabase 用于操作数据库的工具类
        db.execSQL(CREATE_TABLE_DOG);
    }

    //升级
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_DOG);
        db.execSQL(CREATE_TABLE_DOG);
    }
}
