package com.waageweb.tides.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.MessageFormat;


public class TidesDbHelper extends SQLiteOpenHelper {

    public TidesDbHelper(Context context) {
        super(context, "tides.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createTideTableSql = MessageFormat.format(
                "create table {0}(" +
                "{1} integer primary key autoincrement" +
                ",{2} text not null" +
                ",{3} integer not null" +
                ",{4} text not null" +
                ",{5} text not null" +
                ",{6} text not null" +
                ");",
                TideStrings.NAME,
                TideStrings.Columns._ID,
                TideStrings.Columns.NAME,
                TideStrings.Columns.CREATED,
                TideStrings.Columns.HIGHLOW,
                TideStrings.Columns.VALUE,
                TideStrings.Columns.UNIT
        );

        db.execSQL(createTideTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
