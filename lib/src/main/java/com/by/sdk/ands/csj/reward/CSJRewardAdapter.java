package com.by.sdk.ands.csj.reward;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.reward.GCustomRewardAdapter;

import java.util.HashMap;

public class CSJRewardAdapter extends GCustomRewardAdapter {
    private static final String TAG = "CSJRewardAdapter";
    private TTRewardVideoAd rewardVideoAd;
    @Override
    public void showAd(Activity activity) {
        if (rewardVideoAd!=null){
            rewardVideoAd.showRewardVideoAd(activity);
        }
    }

    @Override
    public void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info) {
        AdSlot csjAdSlot = new AdSlot.Builder()
                .setCodeId(info.getPid())
                .setSupportDeepLink(true)
                .build();
        TTAdNative mTTAdNative = TTAdSdk.getAdManager().createAdNative(context);
        mTTAdNative.loadRewardVideoAd(csjAdSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int i, String s) {
                callLoadedError(i,s);
            }

            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {
                try {
                    LogUtil.d(TAG,"CSJ onRewardVideoAdLoad");
                    rewardVideoAd =ttRewardVideoAd;
                    ttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
                        @Override
                        public void onAdShow() {
                            LogUtil.d(TAG,"CSJ reward exposure");
                            callAdExposure();
                        }

                        @Override
                        public void onAdVideoBarClick() {
                            callAdClicked();
                        }

                        @Override
                        public void onAdClose() {
                            LogUtil.d(TAG,"CSJ reward onAdClose");
                            callAdClosed();
                        }

                        @Override
                        public void onVideoComplete() {

                        }

                        @Override
                        public void onVideoError() {

                        }

                        @Override
                        public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName, int code, String msg) {
    //                        LogUtil.d(TAG,"CSJ onRewardVerify");
                        }

                        @Override
                        public void onRewardArrived(boolean isRewardValid, int rewardType,Bundle extraInfo) {
                            LogUtil.d(TAG,"CSJ onRewardArrived");
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("rewardVerify",isRewardValid);
                            map.put("rewardType",rewardType);
                            map.put("extraInfo",extraInfo);

                            callReward(map);
                        }

                        @Override
                        public void onSkippedVideo() {

                        }
                    });
                    callLoadedSuccess();
                    callRenderSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onRewardVideoCached() {

            }

            @Override
            public void onRewardVideoCached(TTRewardVideoAd ttRewardVideoAd) {

            }
        });
    }

    @Override
    public void destroy() {

    }
}
