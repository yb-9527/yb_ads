package com.by.sdk.ands.custom.feed;

import android.app.Activity;
import android.view.View;

import com.by.sdk.ad.feed.FeedAd;

public abstract class GCustomFeedExpressAd extends FeedAd {

    public GCustomFeedExpressAd() {

    }

    @Override
    public boolean isFeedExpress() {
        return true;
    }

    public void setExpressView(View expressView) {
        this.expressView = expressView;
    }

    public abstract void render(Activity activity);

    public abstract View getExpressView();

    public void callRenderSuccess(){
//        if (view!=null){
//            setExpressView(view);
//        }
        if (getExpressAdInteractionListener()!=null){
            getExpressAdInteractionListener().onRenderSuccess();
        }
    }

    public void callRenderError(int code,String msg){
        if (getExpressAdInteractionListener()!=null){
            getExpressAdInteractionListener().onRenderError(code,msg);
        }
    }

    public void callAdExposure(){
        if (proxyAd!=null){
            proxyAd.onAdExposure();
        }
    }

    public void callAdClicked(){
        if (proxyAd!=null){
            proxyAd.onAdClicked();
        }
    }

    public void callAdClosed(){
        if (proxyAd!=null){
            proxyAd.onAdClosed();
        }
    }
}
