package com.meizitian.transer_1204;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Administrator on 2016/12/8.
 */
//解析过程
public class iciba_out{
    String real_res;
    String english_ps;
    String american_ps;
    String english_v;
    String american_v;
    List<String> examples=new ArrayList<>();

    public void outxml(String string_q) {
        try {
            Reader xml_change = new StringReader(string_q);//把string类型转换成reader
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(xml_change));//最后得到文档结点 document
            //翻译
            NodeList result = document.getElementsByTagName("acceptation");
            real_res = result.item(0).getFirstChild().getNodeValue();
            Log.d("翻译结果：", real_res);
            //音标
            NodeList ps = document.getElementsByTagName("ps");
            english_ps = ps.item(0).getFirstChild().getNodeValue();
            american_ps = ps.item(1).getFirstChild().getNodeValue();
            //发音
            NodeList voice = document.getElementsByTagName("pron");
            english_v = voice.item(0).getFirstChild().getNodeValue();
            american_v = voice.item(1).getFirstChild().getNodeValue();
            /**
            Log.d("翻译结果：", english_ps);
            Log.d("翻译结果：", american_ps);
            Log.d("翻译结果：", english_v);
            Log.d("翻译结果：", american_v);
             **/
            //例句及其翻译
            NodeList sent = document.getElementsByTagName("sent");
            for (int i = 0; i < sent.getLength(); i++) {
                String w=sent.item(i).getFirstChild().getFirstChild().getNodeValue();
                String e=sent.item(i).getLastChild().getFirstChild().getNodeValue();
                //System.out.println("sent:" + i +w);
                //System.out.println("sent:" + i +e);
                examples.add(w);
                examples.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    public String get_res(){
        return real_res;
    }
    public String get_english_ps(){
        return english_ps;
    }
    public String get_american_ps(){
        return american_ps;
    }
    public String get_english_v(){
        return english_v;
    }
    public String ge_american_v(){
        return american_v;
    }
    public List get_examples(){
        return examples;
    }
}
