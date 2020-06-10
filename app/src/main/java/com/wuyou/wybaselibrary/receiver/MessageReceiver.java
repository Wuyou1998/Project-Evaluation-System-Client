package com.wuyou.wybaselibrary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.wuyou.wybaselibrary.Account;
import com.wuyou.wybaselibrary.application.BaseApplication;
import com.wuyou.wybaselibrary.dao.MessageDao;
import com.wuyou.wybaselibrary.fragment.MessageFragment;
import com.wuyou.wybaselibrary.helper.DeviceHelper;
import com.wuyou.wybaselibrary.model.device.DeviceBindModel;

import java.util.Objects;

public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = "MessageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null)
            return;
        Bundle bundle = intent.getExtras();
        switch (Objects.requireNonNull(bundle).getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:
                //当Id初始化的时候,获取设备ID
                Log.e(TAG, "GET_CLIENT_ID: " + bundle.toString());
                onClientInit(bundle.getString("clientid"));
                break;
            case PushConsts.GET_MSG_DATA:
                //常规消息送达
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String message = new String(payload);
                    onMessageArrived(message);
                    Log.e(TAG, "GET_MSG_DATA: " + message);
                }
                break;
            default:
                Log.e(TAG, "OTHERS: " + bundle.toString());
                break;
        }
    }

    /**
     * 当Id初始化的时候
     *
     * @param cid
     */
    private void onClientInit(String cid) {
        //设置设备ID
        Account.setPushId(cid);
        if (Account.isLogin()) {
            DeviceBindModel model=new DeviceBindModel(Account.getUserName(),cid);
            DeviceHelper.bind(model);
        }
    }

    /**
     * 消息到达时
     *
     * @param message 新消息
     */
    private void onMessageArrived(String message) {
        //交由Factory处理
        Intent intent = new Intent(MessageFragment.ACTION_NEED_RELOAD_DATA);
        MessageDao.insertMessage(message);
        BaseApplication.getBaseApplicationContext().sendBroadcast(intent);

    }
}


