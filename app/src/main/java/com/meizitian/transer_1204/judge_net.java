package com.meizitian.transer_1204;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/9.
 */

public class judge_net extends BroadcastReceiver {

    private ConnectivityManager mConnectivityManager;
    private NetworkInfo netInfo;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = mConnectivityManager.getActiveNetworkInfo();
            if(netInfo != null && netInfo.isAvailable()) {
                /////////////网络连接
                String name = netInfo.getTypeName();
                if(netInfo.getType()==ConnectivityManager.TYPE_WIFI){
                    System.out.println("wifi");
                    /////WiFi网络
                }else if(netInfo.getType()==ConnectivityManager.TYPE_ETHERNET){
                    System.out.println("etheriet");
                    /////有线网络
                }else if(netInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                    /////////3g网络
                    System.out.println("3g");
                }
            } else {
                System.out.println("null");
                Toast.makeText(context,"真幸运，没有网络。",Toast.LENGTH_SHORT).show();
                ////////网络断开
            }
        }
    }
}
