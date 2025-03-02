package com.by.sdk.byad.utils;

import android.content.pm.PackageManager;

import com.by.sdk.byad.GAdSdk;

public class SdkVerUtil {
    /**
     * 获取穿山甲SDK版本号
     * @return
     */
    public static String getCSJVersionName() {
        try {
            return com.bytedance.sdk.openadsdk.TTAdSdk.getAdManager().getSDKVersion();
        } catch (Throwable e) {}
        return null;
    }

    /**
     * 获取bd SDK版本号
     * @return
     */
    public static String getBDVersionName() {
        try {
            return com.baidu.mobads.sdk.api.AdSettings.getSDKVersion();
        } catch (Throwable e) {}
        return null;
    }

    /**
     * 获取广点通SDK版本号
     * @return
     */
    public static String getGDTVersionName() {
        try {
            return com.qq.e.comm.managers.status.SDKStatus.getIntegrationSDKVersion();
        } catch (Throwable e) {}
        return null;
    }

    /**
     * 获取快手SDK版本号
     * @return
     */
    public static String getKSVersionName() {
        try {
            return com.kwad.sdk.api.KsAdSDK.getSDKVersion();
        } catch (Throwable e) {}
        return null;
    }
    

}
