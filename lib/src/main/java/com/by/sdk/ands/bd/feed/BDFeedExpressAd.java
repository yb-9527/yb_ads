package com.by.sdk.ands.bd.feed;

import android.app.Activity;
import android.view.View;

import com.baidu.mobads.sdk.api.ExpressResponse;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.feed.GCustomFeedExpressAd;

public class BDFeedExpressAd extends GCustomFeedExpressAd {
    private static final String TAG = "BDFeedExpressAd";
    private final ExpressResponse expressResponse;

    public BDFeedExpressAd(ExpressResponse expressResponse) {
        this.expressResponse = expressResponse;
    }

    @Override
    public void render(Activity activity) {
        try {
            if (expressResponse!=null){
                expressResponse.setInteractionListener(new ExpressResponse.ExpressInteractionListener() {
                    @Override
                    public void onAdClick() {
                        callAdClicked();
                    }

                    @Override
                    public void onAdExposed() {
                        callAdExposure();
                    }

                    @Override
                    public void onAdRenderFail(View view, String s, int i) {
                        callRenderError(i,s);
                    }

                    @Override
                    public void onAdRenderSuccess(View view, float v, float v1) {
                        callRenderSuccess();
                    }

                    @Override
                    public void onAdUnionClick() {

                    }
                });

                expressResponse.setAdCloseListener(new ExpressResponse.ExpressCloseListener() {
                    @Override
                    public void onAdClose(ExpressResponse expressResponse) {
                        LogUtil.d(TAG,"onAdClose");
                        callAdClosed();
                    }
                });

                expressResponse.setAdDislikeListener(new ExpressResponse.ExpressDislikeListener() {
                    @Override
                    public void onDislikeWindowShow() {

                    }

                    @Override
                    public void onDislikeItemClick(String s) {
                        LogUtil.d(TAG,"onDislikeItemClick");
                        callAdClosed();
                    }

                    @Override
                    public void onDislikeWindowClose() {

                    }
                });
                expressResponse.bindInteractionActivity(activity);
                expressResponse.render();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getExpressView() {
        if(expressResponse!=null){
            return expressResponse.getExpressAdView();
        }
        return null;
    }
}
