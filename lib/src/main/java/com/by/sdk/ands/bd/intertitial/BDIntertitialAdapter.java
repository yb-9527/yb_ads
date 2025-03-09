package com.by.sdk.ands.bd.intertitial;

import android.app.Activity;
import android.content.Context;

import com.baidu.mobads.sdk.api.ExpressInterstitialAd;
import com.baidu.mobads.sdk.api.ExpressInterstitialListener;
import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.intertitial.GCustomIntertitialAdapter;

public class BDIntertitialAdapter extends GCustomIntertitialAdapter {
    private static final String TAG = "BDIntertitialAdapter";
    private ExpressInterstitialAd interstitialAd;

    @Override
    public void showAd(Activity activity) {
        if (interstitialAd!=null){
            interstitialAd.show(activity);
        }
    }

    @Override
    public void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info) {
        interstitialAd = new ExpressInterstitialAd(context,info.getPid());
        interstitialAd.setLoadListener(new ExpressInterstitialListener() {
            @Override
            public void onADLoaded() {
                LogUtil.d(TAG, "onADLoaded: ");
                callLoadedSuccess();
            }

            @Override
            public void onAdClick() {
                LogUtil.d(TAG, "onAdClick: ");
                callAdClicked();
            }

            @Override
            public void onAdClose() {
                LogUtil.d(TAG, "onAdClose: ");
                callAdClosed();
            }

            @Override
            public void onAdFailed(int i, String s) {
                LogUtil.d(TAG, "onAdFailed: i="+i+",s="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onNoAd(int i, String s) {
                LogUtil.d(TAG,"onNoAd code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onADExposed() {
                LogUtil.d(TAG, "onADExposed: ");
                callAdExposure();
            }

            @Override
            public void onADExposureFailed() {
                LogUtil.d(TAG,"onNoAd code="+ErrorCodeUtil.AD_RENDER_ERR+",msg="+"onADExposureFailed");
                callRenderError(ErrorCodeUtil.AD_RENDER_ERR,"onADExposureFailed");
            }

            @Override
            public void onAdCacheSuccess() {

            }

            @Override
            public void onAdCacheFailed() {

            }

            @Override
            public void onLpClosed() {

            }

            @Override
            public void onVideoDownloadSuccess() {

            }

            @Override
            public void onVideoDownloadFailed() {

            }
        });
        interstitialAd.setDialogFrame(true);
        interstitialAd.load();
    }

    @Override
    public void destroy() {

    }
}
