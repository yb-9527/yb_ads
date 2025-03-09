package com.by.sdk.ands.gdt.intertitial;

import android.app.Activity;
import android.content.Context;

import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.intertitial.GCustomIntertitialAdapter;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.comm.util.AdError;

public class GDTIntertitialAdapter extends GCustomIntertitialAdapter {
    private static final String TAG = "GDTIntertitialAdapter";
    private UnifiedInterstitialAD unifiedInterstitialAD;

    @Override
    public void showAd(Activity activity) {
        if (unifiedInterstitialAD!=null){
            unifiedInterstitialAD.show(activity);
        }
    }

    @Override
    public void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info) {
        unifiedInterstitialAD = new UnifiedInterstitialAD((Activity) context, info.getPid(), new UnifiedInterstitialADListener() {
            @Override
            public void onADReceive() {
                LogUtil.e(TAG, "onADReceive: ");
                callLoadedSuccess();
            }

            @Override
            public void onVideoCached() {

            }

            @Override
            public void onNoAD(AdError adError) {
                try {
                    LogUtil.d(TAG,"onNoAD code ="+adError.getErrorCode()+",msg="+adError.getErrorMsg());
                    callLoadedError(adError.getErrorCode(),adError.getErrorMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onADOpened() {

            }

            @Override
            public void onADExposure() {
                LogUtil.d(TAG,"onADExposure");
                callAdExposure();
            }

            @Override
            public void onADClicked() {
                LogUtil.d(TAG,"onADClicked");
                callAdClicked();
            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADClosed() {
                LogUtil.d(TAG,"onADClosed");
                callAdClosed();
            }

            @Override
            public void onRenderSuccess() {
                LogUtil.d(TAG,"onRenderSuccess");
                callRenderSuccess();
            }

            @Override
            public void onRenderFail() {
                LogUtil.d(TAG,"onRenderFail");
                callRenderError(ErrorCodeUtil.AD_RENDER_ERR,"onRenderFail");
            }
        });

        unifiedInterstitialAD.loadAD();
    }

    @Override
    public void destroy() {

    }
}
