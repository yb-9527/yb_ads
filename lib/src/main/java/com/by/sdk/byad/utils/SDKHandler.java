package com.by.sdk.byad.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class SDKHandler {
    private static final String TAG = "SdkHandler";
    private Handler mHandler;

    private SDKHandler(){
        mHandler = new Handler(Looper.getMainLooper());
    }

    private static class SdkHandlerHolder {
        private static final SDKHandler instance = new SDKHandler();
    }

    public static SDKHandler getInstance(){
        return SdkHandlerHolder.instance;
    }


    public void postDelay(Runnable runnable,long time){
        mHandler.postDelayed(runnable,time);
    }


    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    public static void runOnMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            Message message = Message.obtain(MAIN_HANDLER,  runnable);
            message.sendToTarget();

        }
    }
}
