package com.by.sdk.ad.splash;

import android.content.Context;
import android.text.TextUtils;

import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.bean.BYAdInfo;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.byad.utils.PlatfromFactory;
import com.by.sdk.byad.adpaster.BaseAdLoader;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public class SplashAdLoader extends BaseAdLoader<SplashAdListener> {
    private static final String TAG = "SplashAdLoader";
    private final int fetchTimeout;

    public SplashAdLoader(Context context, BYAdSlot adSlot, SplashAdListener loaderListener, int fetchTimeout) {
        super(context,adSlot, loaderListener);
        this.fetchTimeout = fetchTimeout;
    }

    @Override
    protected IPlatformLoader<SplashAdLoader> createPlatformLoader(Context context, BYAdInfo jAdInfo) {
        try {
            if (jAdInfo!=null){
                if (!TextUtils.isEmpty(jAdInfo.getCls())){
                    IPlatformLoader<SplashAdLoader> splashLoader = PlatfromFactory.getCustomPlatfrom().splashLoader(this, jAdInfo);
                    splashLoader.init(this,jAdInfo);
                    return splashLoader;
                }
            }
        } catch (Exception e) {
            LogUtil.d(TAG,e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized void loadAd() {
        super.loadAd();
    }

    public int getFetchTimeout() {
        return fetchTimeout;
    }

}
