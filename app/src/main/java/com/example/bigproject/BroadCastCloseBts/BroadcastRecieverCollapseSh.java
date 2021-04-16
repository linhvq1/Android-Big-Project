package com.example.bigproject.BroadCastCloseBts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastRecieverCollapseSh extends BroadcastReceiver {
    private BroadcastCloseListener listenerB;
    public static final String ACTION_CLOSE_ACTION  = "CLOSE_BOTTOM_SHEET";

    public BroadcastRecieverCollapseSh(BroadcastCloseListener listenerB){
        this.listenerB = listenerB;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Kiểm tra Action của Intent nhận được có tên irst-broadcastintent
        //listenerB =(BroadcastCloseListener)context;
        if (intent.getAction().equals(BroadcastRecieverCollapseSh.ACTION_CLOSE_ACTION)) {
            //Đọc dữ liệu trong Intent
            String d = intent.getStringExtra("dataname");
            Toast.makeText(context,"meo"+ d, Toast.LENGTH_SHORT).show();

            listenerB.doSomething(d);
        }

    }
}
