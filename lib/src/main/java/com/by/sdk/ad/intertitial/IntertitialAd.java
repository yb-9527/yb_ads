package com.by.sdk.ad.intertitial;

import com.by.sdk.ad.BaseAd;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public abstract class IntertitialAd extends BaseAd implements IIntertitialAd {

    public IntertitialAd(IPlatformLoader<IntertitialAdLoader> platformLoader, String platform) {
        super(platformLoader, platform);
    }
}
