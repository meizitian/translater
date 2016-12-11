package com.meizitian.transer_1204;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2016/12/9.
 */

public class iciba2 {
    public void iciba_ser(final String q, final threa_back_data threaBackData){
        System.out.println("没有进来iciba2？");
        new AsyncTask<Void,Integer,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                System.out.println("里面？");
                HttpURLConnection connection = null;
                String text = null;
                try {
                    URL url=new URL("http://dict-co.iciba.com/api/dictionary.php?w="+q+"&type=xml&key=4690BA3FB1BCC3D519CCD9D4A236223F");
                    //URL url1=new URL("http://www.baidu.com");
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    String ss=URLDecoder.decode(in.toString(), "UTF-8");
                    Log.d("服务器端数据1",ss);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Log.d("服务器端数据2",response.toString());
                    String xml_orign=response.toString();
                    text=xml_orign; //解析留到外部做
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
                return text;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                threaBackData.succ(s);
            }
        }.execute();
    }
}
