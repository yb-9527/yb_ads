package com.by.sdk.ad.feed;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.by.sdk.ad.BaseAd;
import com.by.sdk.byad.adpaster.IPlatformLoader;
import com.by.sdk.ands.custom.feed.GCustomFeedNativeAd;
import com.by.sdk.ands.custom.proxy.GCustomProxyAd;

import java.util.List;

public abstract class FeedAd extends BaseAd implements IFeedAd{
    protected String title;
    protected String description;
    protected String actionText;
    protected String adSource;
    protected List<String> imageList;
    protected int adPatternType;
    protected int interactionType;
    protected String fromLogo;
    protected String iconUrl;
    protected GCustomFeedNativeAd.DownloadInfo downloadInfo;
    protected View expressView;
    protected View nativeVideoView;
    protected FeedAdMediaListener mediaListener;
    protected long videoDuration;
    protected GCustomProxyAd proxyAd;

    protected FeedExpressAdInteractionListener expressListener;
    protected FeedInteractionListener nativeListener;


//    public abstract boolean isFeedExpress();


    public void setPlatform(String platform){
        this.platform = platform;
    }

    public void setPlatformLoader(IPlatformLoader<FeedAdLoader> platformLoader) {
        this.platformLoader = platformLoader;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getActionText() {
        return actionText;
    }

    @Override
    public String getAdSource() {
        return adSource;
    }

    @Override
    public List<String> getImageList() {
        return imageList;
    }

    @Override
    public int getAdPatternType() {
        return adPatternType;
    }

    @Override
    public int getInteractionType() {
        return interactionType;
    }

    @Override
    public String getFromLogo() {
        return fromLogo;
    }

    @Override
    public String getIconUrl() {
        return iconUrl;
    }

    @Override
    public GCustomFeedNativeAd.DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    @Override
    public View getExpressView() {
        return expressView;
    }

    @Override
    public View getNativeVideoView() {
        return nativeVideoView;
    }

    @Override
    public void setVideoAdListener(FeedAdMediaListener mediaListener) {
        this.mediaListener = mediaListener;
    }

    @Override
    public void bindViewForInteraction(Activity activity, ViewGroup container, List<View> clickViews, List<View> creativeViews) {

    }

    @Override
    public void render(Activity activity) {

    }

    @Override
    public long getVideoDuration() {
        return videoDuration;
    }

    public void setProxyAd(GCustomProxyAd proxyAd) {
        this.proxyAd = proxyAd;
    }

    @Override
    public void setExpressAdInteractionListener(FeedExpressAdInteractionListener listener) {
        expressListener = listener;
    }

    @Override
    public FeedExpressAdInteractionListener getExpressAdInteractionListener() {
        return expressListener;
    }

    @Override
    public void setNativeAdInteractionListener(FeedInteractionListener nativeListener){
        this.nativeListener = nativeListener;
    }

    @Override
    public FeedInteractionListener getNativeAdInteractionListener() {
        return nativeListener;
    }
}
