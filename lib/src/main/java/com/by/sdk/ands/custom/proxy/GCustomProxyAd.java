package com.by.sdk.ands.custom.proxy;

import com.by.sdk.ad.BaseAd;
import com.by.sdk.ad.feed.FeedAd;

public class GCustomProxyAd implements IProxyListener{
    private BaseAd customAd;

    public GCustomProxyAd(BaseAd ad) {
        this.customAd = ad;
    }


    @Override
    public void onAdClicked() {
        try {
            if (customAd!=null ){
                if (customAd instanceof FeedAd){
                    FeedAd feedAd = (FeedAd) customAd;
                    if (feedAd.isFeedExpress() && feedAd.getExpressAdInteractionListener()!=null){
                        feedAd.getExpressAdInteractionListener().onAdClicked();
                    }else if (feedAd.getNativeAdInteractionListener()!=null){
                        feedAd.getNativeAdInteractionListener().onAdClicked();
                    }
                }else if (customAd.getInteractionListener()!=null){
                    customAd.getInteractionListener().onAdClicked();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAdExposure() {
        try {
            if (customAd!=null ){
                if (customAd instanceof FeedAd){
                    FeedAd feedAd = (FeedAd) customAd;
                    if (feedAd.isFeedExpress() && feedAd.getExpressAdInteractionListener()!=null){
                        feedAd.getExpressAdInteractionListener().onAdExposure();
                    }else if (feedAd.getNativeAdInteractionListener()!=null){
                        feedAd.getNativeAdInteractionListener().onAdExposure();
                    }
                }else if (customAd.getInteractionListener()!=null){
                    customAd.getInteractionListener().onAdExposure();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAdClosed() {
        try {
            if (customAd!=null ){
                if (customAd instanceof FeedAd){
                    FeedAd feedAd = (FeedAd) customAd;
                    if (feedAd.isFeedExpress() && feedAd.getExpressAdInteractionListener()!=null){
                        feedAd.getExpressAdInteractionListener().onAdClosed();
                    }else if (feedAd.getNativeAdInteractionListener()!=null){
                        feedAd.getNativeAdInteractionListener().onAdClosed();
                    }
                }else if (customAd.getInteractionListener()!=null){
                    customAd.getInteractionListener().onAdClosed();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
