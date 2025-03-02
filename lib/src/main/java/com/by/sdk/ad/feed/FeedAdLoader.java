package com.by.sdk.ad.feed;

import android.content.Context;
import android.text.TextUtils;

import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.bean.GAdInfo;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.byad.utils.PlatfromFactory;
import com.by.sdk.byad.adpaster.BaseAdLoader;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public class FeedAdLoader extends BaseAdLoader<FeedAdEventListener> {
    private static final String TAG = "FeedAdLoader";
    public FeedAdLoader(Context context, GAdSlot adSlot, FeedAdListener loaderListener) {
        super(context,adSlot, new FeedAdListenerAdapter(loaderListener));
    }

    @Override
    protected IPlatformLoader<FeedAdLoader> createPlatformLoader(Context context, GAdInfo jAdInfo) {
        try {
            if (jAdInfo!=null){
                if (!TextUtils.isEmpty(jAdInfo.getCls())){
                    IPlatformLoader<FeedAdLoader> feedLoader = PlatfromFactory.getCustomPlatfrom().feedLoader(this, jAdInfo);
                    feedLoader.init(this,jAdInfo);
                    return feedLoader;
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
