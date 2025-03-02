package com.by.sdk.ad.splash;

import com.by.sdk.ad.BaseAd;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public abstract class SplashAd extends BaseAd implements ISplashAd{
    public SplashAd(IPlatformLoader<SplashAdLoader> platformLoader, String platform) {
        super(platformLoader, platform);
    }


}
