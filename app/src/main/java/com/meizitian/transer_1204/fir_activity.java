package com.meizitian.transer_1204;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by meizitian on 2016/12/5.
 */

public class fir_activity extends AppCompatActivity {
    private Button start_button;
    private EditText input_edittexr;
    private TextView show_textview;
    private Button top_button;
    //最开始为数据测试页面
    //private Button totest_sql_button;
    //单双击同时监听
    private boolean waitDouble = true;
    private static final int DOUBLE_CLICK_TIME = 350; //两次单击的时间间隔
    //数据库
    private static SQLiteDatabase mydata=null;
// protected boolean qq=false;
// 此处qq提前是因为最开始是将qq定义为全局变量 后来发现局部变量一样可以准确应用 查询 功能才将其改回局部变量
// 以防出错 提前qq相关注释代码方便改动
    private Context fircontext; //翻译过程可能会用到的context
    //从已有数据库中查取数据并调用线程完成输出message.what
    private static final int OUTPUT_NOTHING=0;
    private static final int OUTPUT_ALREADY=1;
    //iciba
    private TextView showtext_aciba_example;
    //judge_net
    private IntentFilter intentFilter;
    private judge_net judgeNet;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fir_activity);
        //final Intent intent=new Intent(fir_activity.this,MainActivity.class);
        //翻译过程可能会用到的context
        fircontext=getApplicationContext();
        initview();
        //翻译按钮监听
        start_button.setOnClickListener(listener);
        //监听输入法回车键
        input_edittexr.setOnKeyListener(keyListener);
        //左上角监听
        top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder d =new AlertDialog.Builder(fir_activity.this);
                d.setTitle("关于");
                d.setCancelable(false);
                d.setMessage(R.string.dialog);
                d.setPositiveButton("还是这边", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT,"推荐你使用transer 可以在阅读过程中通过分享功能快速翻译而无需打断你的阅读过程 获取内测应用以及更多交流可以加QQ群：2059267763");
                        startActivity(Intent.createChooser(intent,"分享给好友~【^_^】"));
                    }
                });
                d.setNegativeButton("这边", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(fircontext,"( ^_^ )/~~",Toast.LENGTH_SHORT).show();
                    }
                });
                d.show();
            }
        });
        /** 最开始为数据测试页面跳转**/
        /** 后改为iciba的测试 **/
        /**
        totest_sql_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent2=new Intent(fir_activity.this,test_sql.class);
                //startActivity(intent2);
                iciba test=new iciba();
                Log.d("按钮有反应","ss");
                test.iciba_ser("we", new threa_back_data() {
                    @Override
                    public void succ(String string1) {
                        iciba_out out=new iciba_out();
                        out.outxml(string1);
                        System.out.println(out.get_res());
                        System.out.println(out.get_english_ps());
                        System.out.println(out.get_american_ps());
                        System.out.println(out.get_english_v());
                        System.out.println(out.ge_american_v());
                        System.out.println(out.get_examples());
                    }
                    @Override
                    public void succ2(String List) {
                    }
                    @Override
                    public void fail() {
                    }
                });
            }
        });
         **/
        //正式创建数据表
        test_sql_mysqliteopenhelper mysqlhelper=new test_sql_mysqliteopenhelper(this,"Dic.db",null,8);
        mysqlhelper.getWritableDatabase();
        mydata=mysqlhelper.getWritableDatabase();
        }
/** 最开始的仅仅单击事件监听
        start_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String a=input_edittexr.getText().toString();
                if (TextUtils.isEmpty(a)){
                    Toast.makeText(fir_activity.this,"没有输入文字",Toast.LENGTH_SHORT).show();
                }else {
                    //判断中英文
                    Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
                    Matcher m=p.matcher(a);
                    if(m.find()){
                        transer(a,"zh","en");
                    }else {
                        transer(a,"en","zh");
                    }
                }
                //textView.setText(a);
                //startActivity(intent);
            }
        });
 **/
    //初始化控件
    private void initview(){
        start_button= (Button) findViewById(R.id.start_button);
        input_edittexr= (EditText) findViewById(R.id.input_text);
        show_textview= (TextView) findViewById(R.id.showtext_textview2);
        top_button= (Button) findViewById(R.id.top_button);
        //最开始为数据测试页面
        //totest_sql_button= (Button) findViewById(R.id.totest_sql_button);
        //iciba
        showtext_aciba_example= (TextView) findViewById(R.id.showtext_aciba_example);
    }
    //输入法回车键监听
    View.OnKeyListener keyListener =new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.KEYCODE_ENTER ) {
                Log.d("输入法事件","确认");
                singleClick();
                return true;
            }
            return false;
        }
    };
    //单双击同时监听
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (waitDouble == true) {
                waitDouble = false;
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(DOUBLE_CLICK_TIME);
                            if (waitDouble == false) {
                                waitDouble = true;

                                intentFilter=new IntentFilter();
                                judgeNet=new judge_net();
                                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                                //registerReceiver(judgeNet,intentFilter);

                                singleClick();
                                //singleClick_iciba();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            } else {
                waitDouble = true;
                doubleClick();
                show_textview.setText("");
                input_edittexr.setText("");
                showtext_aciba_example.setText("");
            }
        }
    };
    //单击事件 调用爱词霸
    protected void singleClick_iciba(){


        final String b=input_edittexr.getText().toString();
        if (Objects.equals(b,"")){
            final String nothing="啦啦啦，你没有输入任何东西";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message =new Message();
                    message.obj=nothing;
                    message.what=OUTPUT_NOTHING;
                    handler.sendMessage(message);
                }
            }).start();
        }else if (query(b)){
            final String s=get_myresult(b);
            Log.d("有数据，结果为：",s);
            //从已有数据库中查取数据并调用线程完成输出
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message =new Message();
                    message.obj=s;
                    message.what=OUTPUT_ALREADY;
                    handler.sendMessage(message);
                }
            }).start();
        }else {
            Log.d("没有数据",b);
            iciba2 iciba2_2=new iciba2();
            final iciba_out icibaOut=new iciba_out();
            iciba2_2.iciba_ser(b, new threa_back_data() {
                @Override
                public void succ(String string1) {
                    icibaOut.outxml(string1);
                    String res=icibaOut.get_res();
                    List<String> ex=icibaOut.get_examples();
                    String url =icibaOut.ge_american_v();
                    //音乐调用
                    /**
                    System.out.println(url);
                    String mus="http://218.76.94.43/m10.music.126.net/20161209115054/04bffd6a28b069a2e0f199107ccbdb79/ymusic/a19d/0542/3f4a/22f668de5d922babf9720154b6de09bb.mp3?wshc_tag=0&wsts_tag=584a2442&wsid_tag=76fbaab5&wsiphost=ipdbm";
                    String extension = MimeTypeMap.getFileExtensionFromUrl(mus);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                    mediaIntent.setDataAndType(Uri.parse(mus), mimeType);
                    startActivity(mediaIntent);
                    **/
                    System.out.println(res);
                    show_textview.setText(res);
                    int ch=ex.size();
                    //showtext_aciba_example.setText(ex.toString());
                }
                @Override
                public void succ2(List list) {
                }
                @Override
                public void fail() {
                }
            });
        }
    }
    //单击事件
    protected void singleClick(){
        //View view2= View.inflate(fircontext,R.layout.fir_activity,null);
        String a=input_edittexr.getText().toString();
        Log.d("输入框a的值",a);
        //local(a);
        if(Objects.equals(a, "")){
           // Log.d("d","dddd");
            final String nothing="啦啦啦，你没有输入任何东西";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message =new Message();
                    message.obj=nothing;
                    message.what=OUTPUT_NOTHING;
                    handler.sendMessage(message);
                }
            }).start();
            /**
            Looper.prepare();
            Toast.makeText(this,"you input nothing[白眼]",Toast.LENGTH_SHORT).show();
            Looper.loop();
             **/
        }else if ((query(a))){
            System.out.println("匹配成功："+a);
            System.out.println("匹配成功："+get_myresult(a));
//          qq=false;
            final String s=get_myresult(a);

            //从已有数据库中查取数据并调用线程完成输出
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message =new Message();
                    message.obj=s;
                    message.what=OUTPUT_ALREADY;
                    handler.sendMessage(message);
                }
            }).start();
            /**
            Looper.prepare();
            Toast.makeText(this,"从已有库中提取",Toast.LENGTH_SHORT).show();
            Looper.loop();
             **/
        }else {
            Log.d("匹配不成功，数据库的值","GG" );
//          qq=false;
            Looper.prepare();
            baidu baidula=new baidu();
            //判断中英文
            Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m=p.matcher(a);
            if(m.find()){
                //transer(this,a,"zh","en");
                baidula.baudi_transe(this, a, "zh", "en", new threa_back_data() {
                    @Override
                    public void succ(String string) {
                        Log.d("回调函数正常，翻译结果：",string);
                        show_textview.setText(string);
                    }
                    @Override
                    public void succ2(List list) {

                    }
                    @Override
                    public void fail() {
                    }
                });
            }else {
                baidula.baudi_transe(this, a, "en", "zh", new threa_back_data() {
                    @Override
                    public void succ(String string) {
                        Log.d("可以",string);
                        show_textview.setText(string);
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
            Looper.loop();
            //Toast.makeText(this,"d",Toast.LENGTH_SHORT).show();
        }
    }
    //双击事件
    private void doubleClick(){
        String a=input_edittexr.getText().toString();
        if (!TextUtils.isEmpty(a)){
            Toast.makeText(fir_activity.this,"-_-;",Toast.LENGTH_SHORT).show();
        }
    }
    //翻译过程
    //正式退休于2016.12.7 采用外部方法实现 baidu+threa_back_data
    /**
    protected void transer(final Context context, final String q, final String from, final String to){
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
            protected void onPostExecute(final String s) {
                super.onPostExecute(s);
                show_textview.setText(s);

                Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m=p.matcher(q);
                if(m.find()){
                    addmydata(q,s);
                }else {
                   addmydata(s,q);
                }
                //showout(s);
            }
        }.execute();
    }
     **/
    //查询并返回本来的数据
    protected boolean query(String q){
boolean qq=false;
        List<String> nd=new ArrayList<>();
        String chinese;
        String english;
        Cursor cursor=mydata.query("dictionary",null,null,null,null,null,null);
        //判断中英文
        Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m=p.matcher(q);
        if(m.find()){
            if (cursor.moveToFirst()){
                do {
                    chinese=cursor.getString(cursor.getColumnIndex("zh"));
                    nd.add(chinese);
                    //Log.d("查询时情况",chinese);
                    /**
                    if (q==chinese){
                        qq=chinese;
                        break;
                    }
                     **/
                }while (cursor.moveToNext());
                if (nd.contains(q)){
                    qq=true;
                }
            }
        }else {
            if (cursor.moveToFirst()){
                do {
                    english=cursor.getString(cursor.getColumnIndex("en"));
                    nd.add(english);
                    /**
                    if (q==english){
                        qq=english;
                        break;
                    }
                     **/
                }while (cursor.moveToNext());
                if (nd.contains(q)){
                    qq=true;
                }
            }
        }
        cursor.close();
        return qq;
    }
    //查询并返回翻译的数据
    protected String get_myresult(String q){
        String res="预定的值";
        String chinese_res;
        String english_res;
        List<String> che_res=new ArrayList<>();
        List<String> eng_res=new ArrayList<>();
        Cursor cursor=mydata.query("dictionary",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                chinese_res=cursor.getString(cursor.getColumnIndex("zh"));
                che_res.add(chinese_res);
                english_res=cursor.getString(cursor.getColumnIndex("en"));
                eng_res.add(english_res);

                Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m=p.matcher(q);
                if(m.find()){
                    if (che_res.contains(q)){
                        res=eng_res.get(che_res.indexOf(q));
                        break;
                    }
                }else {
                    if (eng_res.contains(q)){
                        res=che_res.get(eng_res.indexOf(q));
                        break;
                    }
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("返回出去的值",res);
        return res;
    }
    //添加数据
    // 调用翻译功能必定添加结果进数据库
    protected void addmydata(String zh, String en){
        Log.d("启用了adddata",zh+en);
        ContentValues values=new ContentValues();
        Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m=p.matcher(zh);
        if(m.find()){
            values.put("zh",zh);
            values.put("en",en);
        }else {
            values.put("zh",en);
            values.put("en",zh);
        }
        mydata.insert("dictionary",null,values);
        values.clear();

    }
    //从已有数据库中查取数据并调用线程完成输出
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case OUTPUT_ALREADY:
                    show_textview.setText(msg.obj.toString());
                    //如果仅仅是msg.toString()那么会打印全部信息
                    break;
                case OUTPUT_NOTHING:
                    show_textview.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };
    //顶部图片监听
    /**
    View.OnClickListener top_listener =new View.OnClickListener() {
        public void onClick(View view) {
            AlertDialog.Builder d=new AlertDialog.Builder(fircontext);
            d.setTitle("关于");
            d.setCancelable(false);
            d.setMessage("在其他应用中 长按文字分享到这个app中 然后点击按钮即可翻译 目前仅支持中英文互译 双击可以清除数据重新开始 谢谢");
            d.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            d.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            d.show();
        }
    };
     **/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
        try{
            fircontext.unregisterReceiver(judgeNet);
        }catch (Exception e){
            e.printStackTrace();
        }
         **/

    }
}