package com.by.sdk.byad;

import android.content.Context;
import android.util.Log;

public class BYAdSdk {
    private static Context context;
    private static BYAdConfig config;
    public static void init(Context context, BYAdConfig config){
        if (BYAdSdk.config!=null){
            return;
        }
        BYAdSdk.context = context;
        BYAdSdk.config = config;
    }

    public static BYAdConfig getConfig(){
        if(BYAdSdk.config==null){
            Log.e("G_ad","adConfig is null");
        }
        return config;
    }

    public static Context getContext() {
        return context;
    }
}
