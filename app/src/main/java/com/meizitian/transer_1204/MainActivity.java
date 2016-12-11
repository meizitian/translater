package com.meizitian.transer_1204;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//2016.12.8.22.46 最开始后台翻译需要跳转到此活动 后改为hsdui+my_service 实现纯后台操作翻译
public class MainActivity extends AppCompatActivity {

    private Button trans_button;
    private TextView main_show_textview;
    private TextView show_textview2;
    private EditText inputtext;
    private static int i=0;//放在别处会出错为什么？
    private static SQLiteDatabase mydata=null;
    private String data=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        data=intent.getStringExtra(Intent.EXTRA_TEXT);
        //final String input=inputtext.getText().toString();
        //edittext.setText(data);
        initview();
        //正式创建数据表
        test_sql_mysqliteopenhelper mysqlhelper=new test_sql_mysqliteopenhelper(this,"Dic.db",null,6);
        mysqlhelper.getWritableDatabase();
        mydata=mysqlhelper.getWritableDatabase();


        trans_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        //实际翻译部分
                        Log.d("开始翻译","sss");
                        singleClick_ben();
                        /**
                        if (!TextUtils.isEmpty(data)) {
                            //判断中英文
                            Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                            Matcher m = p.matcher(data);
                            if (m.find()) {
                                transer_ben(data,"zh","en");
                            } else {
                                transer_ben(data,"en","zh");
                            }
                        }
                         **/
            }
        });
    }

    View.OnClickListener ss=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("单独","有用");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    baidu baidula=new baidu();
                    //判断中英文
                    Log.d("即将判断","sss");
                    Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
                    Matcher m=p.matcher(data);
                    if(m.find()){
                        //transer(this,a,"zh","en");
                        baidula.baudi_transe(MainActivity.this, data, "zh", "en", new threa_back_data() {
                            @Override
                            public void succ(final String string) {
                                Log.d("回调函数正常，翻译结果：",string);
                                //main_show_textview.setText("dd");
                                Message message=new Message();
                                message.what=1;
                                message.obj=string;
                                handler.sendMessage(message);
                            }
                            @Override
                            public void succ2(List list) {
                            }
                            @Override
                            public void fail() {
                            }
                        });
                    }else {
                        baidula.baudi_transe(MainActivity.this, data, "en", "zh", new threa_back_data() {
                            @Override
                            public void succ(String string) {
                                Log.d("可以",string);
                            }
                            @Override
                            public void succ2(List list) {
                            }
                            @Override
                            public void fail() {
                            }
                        });
                        //transer(this,a,"en","zh");
                    }
                }
            }).start();
        }
    };
    private void initview(){
        trans_button= (Button) findViewById(R.id.trans_button);
        main_show_textview= (TextView) findViewById(R.id.showtext_textview);
        //show_textview2= (TextView) findViewById(R.id.showtext_textview2);
        //inputtext= (EditText) findViewById(R.id.input_text);
    }
    /**
    private void transer_ben(final String q, final String from, final String to){
        new AsyncTask<Void,Integer,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                HttpURLConnection connection = null;
                String text = null;
                try {
                    String appId = "20161021000030523";
                    String token = "wpaw29KtT1HfEddnkHIz";
                    String url_1 = "http://api.fanyi.baidu.com/api/trans/vip/translate";
                    //String from = "en";
                    //String to = "zh";

                    Random random = new Random();
                    int salt = random.nextInt(10000);
                    StringBuilder md5String = new StringBuilder();
                    md5String.append(appId).append(q).append(salt).append(token);
                    String md5 = MD5Encoder.encode(md5String.toString());

                    URL url = new URL(url_1 + "?" + "q=" + URLEncoder.encode(q, "utf-8") + "&from=" + from + "&to=" + to + "&appid=" + appId + "&salt=" + salt + "&sign=" + md5);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JSONObject resultJson = new JSONObject(response.toString());

                    //获取返回翻译结果
                    JSONArray array = (JSONArray) resultJson.get("trans_result");
                    JSONObject dst = (JSONObject) array.get(0);
                    String showtext = dst.getString("dst");
                    text = URLDecoder.decode(showtext, "utf-8");


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return text;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                show_textview.setText(s);
            }
        }.execute();
    }
    **/

    protected void singleClick_ben(){
        baidu baidula=new baidu();
        //判断中英文
        Log.d("即将判断","sss");
        Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m=p.matcher(data);
        if(m.find()){
            //transer(this,a,"zh","en");
            baidula.baudi_transe(this, data, "zh", "en", new threa_back_data() {
                @Override
                public void succ(final String string) {
                    Log.d("回调函数正常，翻译结果：",string);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=new Message();
                            message.what=1;
                            message.obj=string;
                            handler.sendMessage(message);
                        }
                    }).start();
                }

                @Override
                public void succ2(List list) {

                }

                @Override
                public void fail() {
                }
            });
        }else {
            baidula.baudi_transe(this, data, "en", "zh", new threa_back_data() {
                @Override
                public void succ(final String string) {
                    Log.d("可以",string);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=new Message();
                            message.what=1;
                            message.obj=string;
                            handler.sendMessage(message);
                        }
                    }).start();
                }

                @Override
                public void succ2(List list) {

                }

                @Override
                public void fail() {
                }
            });
            //transer(this,a,"en","zh");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //后台toast
        /**
        new Thread(){
            @Override
            public void run(){
                // Looper.prepare();
                new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message msg){
                        Toast.makeText(getApplicationContext(),"test2",Toast.LENGTH_SHORT).show();
                    }
                }.obtainMessage().sendToTarget();
            }
        }.start();
         **/
        trans_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        //实际翻译部分
                        Log.d("开始翻译","sss");
                        singleClick_ben();
                        /**
                         if (!TextUtils.isEmpty(data)) {
                         //判断中英文
                         Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                         Matcher m = p.matcher(data);
                         if (m.find()) {
                         transer_ben(data,"zh","en");
                         } else {
                         transer_ben(data,"en","zh");
                         }
                         }
                         **/
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Log.d("子线程上线",msg.obj.toString());
                main_show_textview.setText(msg.obj.toString());
            }
        }
    };
}
