package com.by.sdk.byad.utils;

import android.util.Log;

import com.by.sdk.byad.GAdSdk;

public class LogUtil {
    private static final String TAG = "G_ad";
    public static void d(String tag, String msg) {
        if (GAdSdk.getConfig()!=null && GAdSdk.getConfig().enableDebug()) {
            Log.d(getTag(tag), ""+msg);
        }
    }

    public static void e(String tag, String msg) {
        if (GAdSdk.getConfig()!=null && GAdSdk.getConfig().enableDebug()) {
            Log.e(getTag(tag), ""+msg);
        }
    }

    private static String getTag(String tag) {
        return new StringBuffer(TAG).append("_").append(tag).toString();
    }
}
