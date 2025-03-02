package com.by.sdk.ands.csj.feed;

import android.app.Activity;
import android.view.View;

import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.feed.GCustomFeedExpressAd;

public class CSJFeedExpressAd extends GCustomFeedExpressAd {
    private static final String TAG = "CSJFeedExpressAd";
    private final TTNativeExpressAd ttNativeExpressAd;

    public CSJFeedExpressAd(TTNativeExpressAd ttNativeExpressAd) {
        this.ttNativeExpressAd = ttNativeExpressAd;
    }

    @Override
    public void render(Activity activity) {
        if (ttNativeExpressAd!=null){
            ttNativeExpressAd.setDislikeCallback(activity, new TTAdDislike.DislikeInteractionCallback() {
                @Override
                public void onShow() {

                }

                @Override
                public void onSelected(int i, String s, boolean b) {
                    callAdClosed();
                }

                @Override
                public void onCancel() {

                }
            });
            ttNativeExpressAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
                @Override
                public void onAdClicked(View view, int i) {
                    callAdClicked();
                }

                @Override
                public void onAdShow(View view, int i) {
                    callAdExposure();
                }

                @Override
                public void onRenderFail(View view, String s, int i) {
                    callRenderError(i,s);
                }

                @Override
                public void onRenderSuccess(View view, float v, float v1) {
                    LogUtil.d(TAG,"CSJ onRenderSuccess,view="+view);
                    callRenderSuccess();
                }
            });

            ttNativeExpressAd.render();
        }



    }

    @Override
    public View getExpressView() {
        if (ttNativeExpressAd!=null){
            return ttNativeExpressAd.getExpressAdView();
        }
        return null;
    }
}
