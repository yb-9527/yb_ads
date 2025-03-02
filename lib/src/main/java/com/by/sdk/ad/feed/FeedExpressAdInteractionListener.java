package com.by.sdk.ad.feed;

public interface FeedExpressAdInteractionListener extends FeedInteractionListener{
    void onAdClicked();
    void onAdExposure();
    void onAdClosed();

    void onRenderError(int errorCode,String errMsg);

    void onRenderSuccess();
}
