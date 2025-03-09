package com.by.sdk.ands.ks.intertitial;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;

import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.intertitial.GCustomIntertitialAdapter;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsInterstitialAd;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsVideoPlayConfig;

import java.util.List;

public class KSIntertitialAdapter extends GCustomIntertitialAdapter {
    private static final String TAG = "KSIntertitialAdapter";
    private KsInterstitialAd ksInterstitialAd;

    @Override
    public void showAd(Activity activity) {
        try {
            if (ksInterstitialAd!=null){
                KsVideoPlayConfig videoPlayConfig = new KsVideoPlayConfig.Builder()
                        .videoSoundEnable(false)
                        .build();
                ksInterstitialAd.showInterstitialAd(activity,videoPlayConfig);
            }
        } catch (Exception e) {
            callRenderError(ErrorCodeUtil.AD_RENDER_ERR,"ks render err");
            e.printStackTrace();
        }
    }

    @Override
    public void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info) {
        KsScene scene = new KsScene.Builder(Long.parseLong(info.getPid())).build();

        KsAdSDK.getLoadManager().loadInterstitialAd(scene, new KsLoadManager.InterstitialAdListener() {

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"onError,code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onRequestResult(int i) {

            }

            @Override
            public void onInterstitialAdLoad(@Nullable List<KsInterstitialAd> list) {
                LogUtil.d(TAG,"KS onInterstitialAdLoad");
                if (list!=null && !list.isEmpty()){
                    ksInterstitialAd = list.get(0);

                    if (ksInterstitialAd!=null){
                        ksInterstitialAd.setAdInteractionListener(new KsInterstitialAd.AdInteractionListener() {
                            @Override
                            public void onAdClicked() {
                                LogUtil.d(TAG,"KS onAdClicked");
                                callAdClicked();
                            }

                            @Override
                            public void onAdShow() {
                                LogUtil.d(TAG,"KS onAdShow");
                                callAdExposure();
                            }

                            @Override
                            public void onAdClosed() {
                                LogUtil.d(TAG,"KS onAdClosed");
                                callAdClosed();
                            }

                            @Override
                            public void onPageDismiss() {

                            }

                            @Override
                            public void onVideoPlayError(int i, int i1) {

                            }

                            @Override
                            public void onVideoPlayEnd() {

                            }

                            @Override
                            public void onVideoPlayStart() {

                            }

                            @Override
                            public void onSkippedAd() {

                            }
                        });
                    }
                }

                callLoadedSuccess();
                callRenderSuccess();
            }
        });
    }

    @Override
    public void destroy() {

    }
}
