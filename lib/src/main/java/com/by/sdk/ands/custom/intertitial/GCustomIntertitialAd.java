package com.by.sdk.ands.custom.intertitial;

import android.app.Activity;

import com.by.sdk.ad.intertitial.IntertitialAd;

public class GCustomIntertitialAd extends IntertitialAd {
    private final GCustomIntertitialAdapter platformLoader;

    public GCustomIntertitialAd(GCustomIntertitialAdapter platformLoader, String platform) {
        super(platformLoader, platform);
        this.platformLoader = platformLoader;
    }

    @Override
    public void showAd(Activity activity) {
        if (platformLoader!=null){
            platformLoader.showAd(activity);
        }
    }
}
