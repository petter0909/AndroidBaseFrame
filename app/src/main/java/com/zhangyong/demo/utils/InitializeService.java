package com.zhangyong.demo.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ZY on 2018/6/11.
 */

public class InitializeService extends IntentService {


    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.zhangyong.demo.service.action.INIT";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        //initSDK
    }

}
