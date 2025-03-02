package com.by.sdk.ad.feed;

import java.util.List;

public class FeedAdListenerAdapter implements FeedAdEventListener {
    private FeedAdListener feedAdListener;

    public FeedAdListenerAdapter(FeedAdListener feedAdListener) {
        this.feedAdListener = feedAdListener;
    }

    @Override
    public void onLoadedSuccess(List<IFeedAd> iFeedAds) {
        if (feedAdListener!=null){
            feedAdListener.onLoadedSuccess(iFeedAds);
        }
    }

    @Override
    public void onLoadedError(int code, String msg) {
        if (feedAdListener!=null){
            feedAdListener.onLoadedError(code,msg);
        }
    }

    @Override
    public void onRenderSuccess(List<IFeedAd> iFeedAds) {

    }

    @Override
    public void onRenderError(int code, String msg) {

    }
}
