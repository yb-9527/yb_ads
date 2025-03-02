package com.by.sdk.ands.custom.splash;

import android.view.ViewGroup;

import com.by.sdk.ad.splash.SplashAd;

public class GCustomSplashAd extends SplashAd {
    private final GCustomSplashAdapter splashLoader;

    public GCustomSplashAd(GCustomSplashAdapter splashLoader, String platform) {
        super(splashLoader, platform);
        this.splashLoader = splashLoader;
    }

    @Override
    public void showAd(ViewGroup adContainer) {
        if (splashLoader!=null){
            splashLoader.showAd(adContainer);
        }
    }
}
