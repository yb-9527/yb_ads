package com.by.sdk.ad.banner;

import com.by.sdk.ad.BaseAd;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public abstract class BannerAd extends BaseAd implements IBannerAd {
    public BannerAd(IPlatformLoader<BannerAdLoader> platformLoader, String platform) {
        super(platformLoader, platform);
    }
}
