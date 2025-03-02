package com.by.sdk.ad;

import android.view.View;

import com.by.sdk.byad.adpaster.BaseAdLoader;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public abstract class BaseAd<K extends BaseAdLoader> implements IAd {
    protected InteractionListener interactionListener;
    protected IPlatformLoader<K> platformLoader;
    protected String platform;
    protected View adView;

    public BaseAd(){
    }
    public BaseAd(IPlatformLoader<K> platformLoader, String platform) {
        this.platformLoader = platformLoader;
        this.platform = platform;
    }

    @Override
    public View getAdView() {
        return adView;
    }

    @Override
    public void setAdView(View adView) {
        this.adView = adView;
    }

    @Override
    public void setInteractionListener(InteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }

    @Override
    public InteractionListener getInteractionListener() {
        return interactionListener;
    }

    @Override
    public String getPlatform() {
        return platform;
    }

    public IPlatformLoader<K> getPlatformLoader() {
        return platformLoader;
    }
}
