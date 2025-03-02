package com.by.sdk.ad.intertitial;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.bean.GAdInfo;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.byad.utils.PlatfromFactory;
import com.by.sdk.byad.adpaster.BaseAdLoader;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public class IntertitialAdLoader extends BaseAdLoader<IntertitialAdListener> {
    private static final String TAG = "IntertitialAdLoader";
    public IntertitialAdLoader(Activity context, GAdSlot adSlot, IntertitialAdListener loaderListener) {
        super(context,adSlot, loaderListener);
    }

    @Override
    protected IPlatformLoader<IntertitialAdLoader> createPlatformLoader(Context context, GAdInfo jAdInfo) {
        try {
            if (jAdInfo!=null){
                if (!TextUtils.isEmpty(jAdInfo.getCls())){
                    IPlatformLoader<IntertitialAdLoader> intertitialLoader = PlatfromFactory.getCustomPlatfrom().intertitialLoader(this, jAdInfo);
                    intertitialLoader.init(this,jAdInfo);
                    return intertitialLoader;
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
}
