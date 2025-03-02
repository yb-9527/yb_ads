package com.by.sdk.ad.banner;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.bean.GAdInfo;
import com.by.sdk.byad.utils.PlatfromFactory;
import com.by.sdk.byad.adpaster.BaseAdLoader;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public class BannerAdLoader extends BaseAdLoader<BannerAdListener> {
    public BannerAdLoader(Activity activity, GAdSlot adSlot, BannerAdListener loaderListener) {
        super(activity,adSlot, loaderListener);
    }

    @Override
    protected IPlatformLoader<BannerAdLoader> createPlatformLoader(Context context, GAdInfo jAdInfo) {
        try {
            if (jAdInfo!=null){
                if (!TextUtils.isEmpty(jAdInfo.getCls())){
                    IPlatformLoader<BannerAdLoader> bannerLoader = PlatfromFactory.getCustomPlatfrom().bannerLoader(this, jAdInfo);
                    bannerLoader.init(this,jAdInfo);
                    return bannerLoader;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized void loadAd() {
        super.loadAd();
    }
}
