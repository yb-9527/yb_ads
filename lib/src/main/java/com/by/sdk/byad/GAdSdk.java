package com.by.sdk.byad;

import android.content.Context;
import android.util.Log;

public class GAdSdk {
    private static Context context;
    private static GAdConfig config;
    public static void init(Context context,GAdConfig config){
        if (GAdSdk.config!=null){
            return;
        }
        GAdSdk.context = context;
        GAdSdk.config = config;
    }

    public static GAdConfig getConfig(){
        if(GAdSdk.config==null){
            Log.e("G_ad","adConfig is null");
        }
        return config;
    }

    public static Context getContext() {
        return context;
    }
}
