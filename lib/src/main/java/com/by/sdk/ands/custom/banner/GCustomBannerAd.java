package com.by.sdk.ands.custom.banner;

import android.view.ViewGroup;

import com.by.sdk.ad.banner.BannerAd;

public class GCustomBannerAd extends BannerAd {
    private GCustomBannerAdapter bannerLoader;

    public GCustomBannerAd(GCustomBannerAdapter platformLoader, String platform) {
        super(platformLoader, platform);
        bannerLoader = platformLoader;
    }


    @Override
    public void showAd(ViewGroup adContainer) {
        if (bannerLoader!=null){
            bannerLoader.showAd(adContainer);
        }
    }
}
