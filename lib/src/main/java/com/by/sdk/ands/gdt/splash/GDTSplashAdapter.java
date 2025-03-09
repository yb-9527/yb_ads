package com.by.sdk.ands.gdt.splash;

import android.content.Context;
import android.view.ViewGroup;

import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.splash.GCustomSplashAdapter;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;

public class GDTSplashAdapter extends GCustomSplashAdapter {
    private static final String TAG = "GDTSplashAdapter";
    private SplashAD splashAD;
    private BYAdSlot adSlot;

    @Override
    public void showAd(ViewGroup container) {
        try {
            if (splashAD!=null){
                boolean isFullScreen = adSlot != null && adSlot.getIsSplashFullScreen();
                if (isFullScreen){
                    splashAD.showFullScreenAd(container);
                }else {
                    splashAD.showAd(container);
                }
            }
        } catch (Exception e) {
            callRenderError(ErrorCodeUtil.AD_RENDER_ERR,"render err");
            e.printStackTrace();
        }
    }

    @Override
    public void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info) {
        this.adSlot = adSlot;
        splashAD = new SplashAD(context, info.getPid(), new SplashADListener() {
            @Override
            public void onADDismissed() {
                callAdClosed();
            }

            @Override
            public void onNoAD(AdError adError) {
                LogUtil.d(TAG,"GDT  onNoAD,code="+adError.getErrorCode()+",msg="+adError.getErrorMsg());
                callLoadedError(adError.getErrorCode(),adError.getErrorMsg());
            }

            @Override
            public void onADPresent() {

            }

            @Override
            public void onADClicked() {
                callAdClicked();
            }

            @Override
            public void onADTick(long l) {
                callTick(l);
            }

            @Override
            public void onADExposure() {
                callAdExposure();
            }

            @Override
            public void onADLoaded(long l) {
                LogUtil.d(TAG,"GDT loaded success");
                callLoadedSuccess();
                callRenderSuccess();
            }
        },3500);

        if (adSlot.getIsSplashFullScreen()){
            splashAD.fetchFullScreenAdOnly();
        }else {
            splashAD.fetchAdOnly();
        }
    }

    @Override
    public void destroy() {

    }
}
