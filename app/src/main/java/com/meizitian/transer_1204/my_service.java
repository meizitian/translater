package com.meizitian.transer_1204;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/8.
 */

public class my_service extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String tt=intent.getStringExtra("gg");
        use_baidu(tt);
        /**
        final fir_activity firActivity=new fir_activity();
        if (Objects.equals(tt,"")){
            new Thread(){
                @Override
                public void run(){
                    // Looper.prepare();
                    new Handler(Looper.getMainLooper()){
                        @Override
                        public void handleMessage(Message msg){
                            Toast.makeText(getApplicationContext(),"你没有输入任何值",Toast.LENGTH_SHORT).show();
                        }
                    }.obtainMessage().sendToTarget();
                }
            }.start();
        }else if (firActivity.query(tt)){
            new Thread(){
                @Override
                public void run(){
                    // Looper.prepare();
                    new Handler(Looper.getMainLooper()){
                        @Override
                        public void handleMessage(Message msg){
                            Toast.makeText(getApplicationContext(),firActivity.get_myresult(tt),Toast.LENGTH_SHORT).show();
                        }
                    }.obtainMessage().sendToTarget();
                }
            }.start();
            Log.d("dd","步骤2");
        }else {
            baidu service_baidu=new baidu();
            Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m=p.matcher(tt);
            if(m.find()){
                service_baidu.baudi_transe(this, tt, "zh", "en", new threa_back_data() {
                    @Override
                    public void succ(final String string) {
                        new Thread(){
                            @Override
                            public void run(){
                                // Looper.prepare();
                                new Handler(Looper.getMainLooper()){
                                    @Override
                                    public void handleMessage(Message msg){
                                        Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                                    }
                                }.obtainMessage().sendToTarget();
                            }
                        }.start();
                    }
                    @Override
                    public void succ2(String List) {
                    }
                    @Override
                    public void fail() {
                    }
                });
            }else {
                service_baidu.baudi_transe(this, tt, "en", "zh", new threa_back_data() {
                    @Override
                    public void succ(final String string) {
                        new Thread(){
                            @Override
                            public void run(){
                                // Looper.prepare();
                                new Handler(Looper.getMainLooper()){
                                    @Override
                                    public void handleMessage(Message msg){
                                        Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                                    }
                                }.obtainMessage().sendToTarget();
                            }
                        }.start();
                    }
                    @Override
                    public void succ2(String List) {
                    }
                    @Override
                    public void fail() {
                    }
                });
            }
            Log.d("dd","步骤3");
        }
         **/
        /**
        new Thread(){
            @Override
            public void run(){
                // Looper.prepare();
                new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message msg){
                        Toast.makeText(getApplicationContext(),tt,Toast.LENGTH_SHORT).show();
                    }
                }.obtainMessage().sendToTarget();
            }
        }.start();
         **/
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    protected void use_baidu(final String tt){
        final fir_activity firActivity=new fir_activity();
        if (Objects.equals(tt,"")){
            new Thread(){
                @Override
                public void run(){
                    // Looper.prepare();
                    new Handler(Looper.getMainLooper()){
                        @Override
                        public void handleMessage(Message msg){
                            Toast.makeText(getApplicationContext(),"你没有输入任何值",Toast.LENGTH_SHORT).show();
                        }
                    }.obtainMessage().sendToTarget();
                }
            }.start();
        }else if (firActivity.query(tt)){
            new Thread(){
                @Override
                public void run(){
                    // Looper.prepare();
                    new Handler(Looper.getMainLooper()){
                        @Override
                        public void handleMessage(Message msg){
                            Toast.makeText(getApplicationContext(),firActivity.get_myresult(tt),Toast.LENGTH_SHORT).show();
                        }
                    }.obtainMessage().sendToTarget();
                }
            }.start();
            Log.d("dd","步骤2");
        }else {
            baidu service_baidu=new baidu();
            Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m=p.matcher(tt);
            if(m.find()){
                service_baidu.baudi_transe(this, tt, "zh", "en", new threa_back_data() {
                    @Override
                    public void succ(final String string) {
                        new Thread(){
                            @Override
                            public void run(){
                                // Looper.prepare();
                                new Handler(Looper.getMainLooper()){
                                    @Override
                                    public void handleMessage(Message msg){
                                        Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                                    }
                                }.obtainMessage().sendToTarget();
                            }
                        }.start();
                    }
                    @Override
                    public void succ2(List list) {
                    }
                    @Override
                    public void fail() {
                    }
                });
            }else {
                service_baidu.baudi_transe(this, tt, "en", "zh", new threa_back_data() {
                    @Override
                    public void succ(final String string) {
                        new Thread(){
                            @Override
                            public void run(){
                                // Looper.prepare();
                                new Handler(Looper.getMainLooper()){
                                    @Override
                                    public void handleMessage(Message msg){
                                        Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                                    }
                                }.obtainMessage().sendToTarget();
                            }
                        }.start();
                    }
                    @Override
                    public void succ2(List list) {
                    }
                    @Override
                    public void fail() {
                    }
                });
            }
            Log.d("dd","步骤3");
        }
    }
}
