package com.meizitian.transer_1204;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/8.
 */

public class hsdui extends Activity {
    private String data=null;
    private Intent ser;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent=getIntent();
        data=intent.getStringExtra(Intent.EXTRA_TEXT);
        ser=new Intent(this,my_service.class);
        ser.putExtra("gg",data); //只调用到此处 貌似
        startService(ser);
        //Toast.makeText(this,"oncreat",Toast.LENGTH_SHORT).show();
        Log.d("TAG","1");

        onDestroy();
        //finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG","2");
    }

    @Override
    public void finish() {
        super.finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        finish();
        Intent ser=new Intent(this,my_service.class);
        ser.putExtra("gg",data);
        //startService(ser);
        //Toast.makeText(this,"onresume",Toast.LENGTH_SHORT).show();
        Log.d("TAG","3");

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG","4");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG","5");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG","6");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        stopService(ser);
        Log.d("TAG","7");
    }
}
