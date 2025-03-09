package com.by.sdk.ad.reward;

import android.content.Context;
import android.text.TextUtils;

import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.bean.BYAdInfo;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.byad.utils.PlatfromFactory;
import com.by.sdk.byad.adpaster.BaseAdLoader;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public class RewardAdLoader extends BaseAdLoader<RewardAdListener> {
    private static final String TAG = "RewardAdLoader";
    public RewardAdLoader(Context context, BYAdSlot adSlot, RewardAdListener loaderListener) {
        super(context,adSlot, loaderListener);
    }

    @Override
    protected IPlatformLoader<RewardAdLoader> createPlatformLoader(Context context, BYAdInfo jAdInfo) {
        try {
            if (jAdInfo!=null){
                if (!TextUtils.isEmpty(jAdInfo.getCls())){
                    IPlatformLoader<RewardAdLoader> rewardLoader = PlatfromFactory.getCustomPlatfrom().rewardLoader(this, jAdInfo);
                    rewardLoader.init(this,jAdInfo);
                    return rewardLoader;
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
