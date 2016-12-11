package com.meizitian.transer_1204;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/5.
 */

//最开始用来测试数据储存的,还有一个test_sql.xml文件未删除
public class test_sql extends AppCompatActivity {

    private EditText inputtext;
    private Button button;
    private Button button2;
    private Button button3;
    private TextView textView;

    private test_sql_mysqliteopenhelper mysqliteopenhelper;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_sql);
        initview();
        mysqliteopenhelper=new test_sql_mysqliteopenhelper(this,"Dic.db",null,5);
        //create
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mysqliteopenhelper.getWritableDatabase();

            }
        });
        //add data
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase data=mysqliteopenhelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("zh","我们");
                values.put("en","we");
                data.insert("dictionary",null,values);
                values.clear();
                values.put("zh","他们");
                values.put("en","they");
                data.insert("dictionary",null,values);
                values.clear();
            }
        });
        //read data
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase read_data=mysqliteopenhelper.getWritableDatabase();
                Cursor cursor=read_data.query("dictionary",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do {
                        String chinese=cursor.getString(cursor.getColumnIndex("zh"));
                        String english=cursor.getString(cursor.getColumnIndex("en"));
                        Log.d("kkk",chinese);
                        //textView.setText("chinese:"+chinese+"english:"+english);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }

    private void initview(){
        inputtext= (EditText) findViewById(R.id.test_sql_inputtext);
        button= (Button) findViewById(R.id.test_sql_button);
        button2= (Button) findViewById(R.id.test_sql_button2);
        button3= (Button) findViewById(R.id.test_sql_button3);
        textView= (TextView) findViewById(R.id.test_sql_showtext);
    }

}
