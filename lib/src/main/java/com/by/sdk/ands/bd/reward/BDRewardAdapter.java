package com.by.sdk.ands.bd.reward;

import android.app.Activity;
import android.content.Context;

import com.baidu.mobads.sdk.api.RewardVideoAd;
import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.reward.GCustomRewardAdapter;

import java.util.HashMap;

public class BDRewardAdapter extends GCustomRewardAdapter {
    private static final String TAG = "BDRewardAdapter";
    private RewardVideoAd mRewardVideoAd;

    @Override
    public void showAd(Activity activity) {
        if (mRewardVideoAd !=null){
            mRewardVideoAd.show();
        }
    }

    @Override
    public void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info) {
        mRewardVideoAd = new RewardVideoAd(context, info.getPid(), new RewardVideoAd.RewardVideoAdListener() {
            @Override
            public void onAdShow() {
                LogUtil.d(TAG,"onAdShow");
                callAdExposure();
            }

            @Override
            public void onAdClick() {
                LogUtil.d(TAG,"onAdClick");
                callAdClicked();
            }

            @Override
            public void onAdClose(float v) {
                LogUtil.d(TAG,"onAdClose");
                callAdClosed();
            }

            @Override
            public void onAdFailed(String s) {
                LogUtil.d(TAG,"BD onAdFailed:"+s);
                callLoadedError(ErrorCodeUtil.BD_LOAD_ERR,s);
            }

            @Override
            public void onVideoDownloadSuccess() {

            }

            @Override
            public void onVideoDownloadFailed() {

            }

            @Override
            public void playCompletion() {

            }

            @Override
            public void onAdLoaded() {
                LogUtil.d(TAG,"onAdLoaded");
                callLoadedSuccess();
                callRenderSuccess();
            }

            @Override
            public void onAdSkip(float v) {

            }

            @Override
            public void onRewardVerify(boolean b) {
                LogUtil.d(TAG,"onRewardVerify");
                HashMap<String,Object> map = new HashMap<>();
                map.put("rewardVerify",b);
                callReward(map);
            }
        }, false);

        mRewardVideoAd.load();
    }

    @Override
    public void destroy() {

    }
}
