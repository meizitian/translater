package com.meizitian.transer_1204;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by Administrator on 2016/12/7.
 */
//调用翻译必定添加进数据库
public class baidu {
    private String hhhh=null;
    private String hhhh2=null;
    public void baudi_transe(final Context context, final String q, final String from, final String to,final threa_back_data back_data){
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
                hhhh=q;
                hhhh2=s;
                back_data.succ(s);
                //List<String> q_and_s=new ArrayList<String>();
                //q_and_s.add(s);
                //q_and_s.add(q);

                Message message1 =new Message();
                message1.what=1;
                handler.sendMessage(message1);
                //show_textview.setText(s);
//                Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
//                Matcher m=p.matcher(q);
//               if(m.find()){
//                    addmydata(q,s);
//                }else {
//                    addmydata(s,q);
//                }
                //showout(s);
            }
        }.execute();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
//                Log.d("效果",hhhh);
//                Log.d("效果",hhhh2);
                fir_activity fir_adddata=new fir_activity();
                fir_adddata.addmydata(hhhh,hhhh2);
            }
        }
    };

}
