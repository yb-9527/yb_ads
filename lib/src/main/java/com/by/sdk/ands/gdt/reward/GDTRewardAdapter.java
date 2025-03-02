package com.by.sdk.ands.gdt.reward;

import android.app.Activity;
import android.content.Context;

import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.reward.GCustomRewardAdapter;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.comm.util.AdError;

import java.util.Map;

public class GDTRewardAdapter extends GCustomRewardAdapter {

    private static final String TAG = "GDTRewardAdapter";
    private RewardVideoAD rewardVideoAd;

    @Override
    public void showAd(Activity activity) {
        if (rewardVideoAd!=null){
            rewardVideoAd.showAD(activity);
        }
    }

    @Override
    public void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info) {
        rewardVideoAd = new RewardVideoAD(adLoader.getContext(), info.getPid(), new RewardVideoADListener() {
            @Override
            public void onADLoad() {
                LogUtil.d(TAG,"GDT onADLoad");
                callLoadedSuccess();
            }

            @Override
            public void onVideoCached() {

            }

            @Override
            public void onADShow() {

            }

            @Override
            public void onADExpose() {
                LogUtil.d(TAG,"GDT onADExpose");
                callAdExposure();
            }

            @Override
            public void onReward(Map<String, Object> map) {
                LogUtil.d(TAG,"GDT onReward");
                callReward(map);
            }

            @Override
            public void onADClick() {
                LogUtil.d(TAG,"GDT onADClick");
                callAdClicked();
            }

            @Override
            public void onVideoComplete() {

            }

            @Override
            public void onADClose() {
                LogUtil.d(TAG,"GDT onADClose");
                callAdClosed();
            }

            @Override
            public void onError(AdError adError) {
                try {
                    LogUtil.d(TAG,"GDT onError code="+adError.getErrorCode()+",msg="+adError.getErrorMsg());
                    callLoadedError(adError.getErrorCode(),adError.getErrorMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },!adSlot.getIsMute());

        rewardVideoAd.loadAD();
    }

    @Override
    public void destroy() {

    }
}
