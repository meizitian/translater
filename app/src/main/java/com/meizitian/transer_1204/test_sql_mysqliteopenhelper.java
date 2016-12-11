package com.meizitian.transer_1204;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/6.
 */

public class test_sql_mysqliteopenhelper extends SQLiteOpenHelper {

    public static final String DICTIONARY = "create table dictionary ("
            + "id integer primary key autoincrement,"
            + "zh text,"
            + "en text)";
    private Context mcontext;
    public test_sql_mysqliteopenhelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mcontext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DICTIONARY);
        //Toast.makeText(mcontext,"creat s",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists dictionary");
        onCreate(sqLiteDatabase);
    }
}
