package com.by.sdk.ands.csj.intertitial;

import android.app.Activity;
import android.content.Context;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.intertitial.GCustomIntertitialAdapter;

public class CSJIntertitialAdapter extends GCustomIntertitialAdapter {
    private static final String TAG = "CSJIntertitialAdapter";
    private TTFullScreenVideoAd intertitialAd;
    @Override
    public void showAd(Activity activity) {
        if (intertitialAd!=null){
            intertitialAd.showFullScreenVideoAd(activity);
        }
    }

    @Override
    public void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info) {
        AdSlot csjAdSlot = new AdSlot.Builder()
                .setCodeId(info.getPid())
                .setSupportDeepLink(true)
                .build();
        TTAdNative ttAdNative = TTAdSdk.getAdManager().createAdNative(context);

        ttAdNative.loadFullScreenVideoAd(csjAdSlot, new TTAdNative.FullScreenVideoAdListener() {
            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"onError code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ttFullScreenVideoAd) {
                try {
                    LogUtil.d(TAG,"onFullScreenVideoAdLoad");
                    if (ttFullScreenVideoAd!=null){
                        intertitialAd = ttFullScreenVideoAd;
                        ttFullScreenVideoAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {
                            @Override
                            public void onAdShow() {
                                callAdExposure();
                            }

                            @Override
                            public void onAdVideoBarClick() {
                                callAdClicked();
                            }

                            @Override
                            public void onAdClose() {
                                callAdClosed();
                            }

                            @Override
                            public void onVideoComplete() {

                            }

                            @Override
                            public void onSkippedVideo() {

                            }
                        });
                        callLoadedSuccess();
                        callRenderSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFullScreenVideoCached() {

            }

            @Override
            public void onFullScreenVideoCached(TTFullScreenVideoAd ttFullScreenVideoAd) {

            }
        });
    }

    @Override
    public void destroy() {

    }
}
