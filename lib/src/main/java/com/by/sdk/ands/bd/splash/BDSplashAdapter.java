package com.by.sdk.ands.bd.splash;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.baidu.mobads.sdk.api.RequestParameters;
import com.baidu.mobads.sdk.api.SplashAd;
import com.baidu.mobads.sdk.api.SplashInteractionListener;
import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.splash.GCustomSplashAdapter;

public class BDSplashAdapter extends GCustomSplashAdapter {
    private static final String TAG = "BDSplashAdapter";
    private SplashAd splashAd;

    @Override
    public void showAd(ViewGroup container) {
        if (splashAd!=null){
            splashAd.show(container);
        }
    }

    @Override
    public void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info) {
        RequestParameters.Builder parameters = new RequestParameters.Builder();
        parameters.addExtra(SplashAd.KEY_POPDIALOG_DOWNLOAD, "true");

        splashAd = new SplashAd(context.getApplicationContext(), info.getPid(), parameters.build(), new SplashInteractionListener() {
            @Override
            public void onLpClosed() {

            }

            @Override
            public void onAdPresent() {

            }

            @Override
            public void onAdExposed() {
                callAdExposure();
            }

            @Override
            public void onAdDismissed() {
                callAdClosed();
            }

            @Override
            public void onAdSkip() {
                callAdSkip();
            }

            @Override
            public void onAdClick() {
                callAdClicked();
            }

            @Override
            public void onAdCacheSuccess() {

            }

            @Override
            public void onAdCacheFailed() {

            }

            @Override
            public void onADLoaded() {
                Log.e(TAG, "onADLoaded: bd" );
                LogUtil.d(TAG,"BD onADLoaded");
                callLoadedSuccess();
                callRenderSuccess();
            }

            @Override
            public void onAdFailed(String s) {
                LogUtil.d(TAG,"BD onAdFailed:"+s);
                callLoadedError(ErrorCodeUtil.BD_LOAD_ERR,s);
            }
        });

        splashAd.load();
    }

    @Override
    public void destroy() {

    }
}
