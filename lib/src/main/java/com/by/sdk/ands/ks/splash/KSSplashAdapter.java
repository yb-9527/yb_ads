package com.by.sdk.ands.ks.splash;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.splash.GCustomSplashAdapter;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsSplashScreenAd;

public class KSSplashAdapter extends GCustomSplashAdapter {
    private static final String TAG = "KSSplashAdapter";
    private KsSplashScreenAd splashAd;

    @Override
    public void showAd(ViewGroup container) {
        try {
            if (container==null || splashAd==null){
                callRenderError(ErrorCodeUtil.AD_RENDER_ERR,"container is null");
                return;
            }
            View view = splashAd.getView(container.getContext(), new KsSplashScreenAd.SplashScreenAdInteractionListener() {
                @Override
                public void onAdClicked() {
                    callAdClicked();
                }

                @Override
                public void onAdShowError(int i, String s) {
                    callRenderError(i,s);
                }

                @Override
                public void onAdShowEnd() {
                    callAdClosed();
                }

                @Override
                public void onAdShowStart() {
                    callAdExposure();
                }

                @Override
                public void onSkippedAd() {
                    callAdClosed();
                }

                @Override
                public void onDownloadTipsDialogShow() {

                }

                @Override
                public void onDownloadTipsDialogDismiss() {

                }

                @Override
                public void onDownloadTipsDialogCancel() {

                }
            });

            if (view !=null){
                container.removeAllViews();
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            }
        } catch (Exception e) {
            callRenderError(ErrorCodeUtil.AD_RENDER_ERR,"container is null");
            e.printStackTrace();
        }
    }

    @Override
    public void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info) {
        KsScene scene   = new KsScene.Builder(Long.parseLong(info.getPid())).build();
        KsAdSDK.getLoadManager().loadSplashScreenAd(scene, new KsLoadManager.SplashScreenAdListener() {
            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"KS onError code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onRequestResult(int i) {

            }

            @Override
            public void onSplashScreenAdLoad(@Nullable KsSplashScreenAd ksSplashScreenAd) {
                LogUtil.d(TAG,"KS onSplashScreenAdLoad");
                splashAd = ksSplashScreenAd;
                callLoadedSuccess();
                callRenderSuccess();
            }
        });
    }

    @Override
    public void destroy() {

    }
}
