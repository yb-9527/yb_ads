package com.by.sdk.ad.reward;

import com.by.sdk.ad.BaseAd;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public abstract class RewardAd extends BaseAd implements IRewardAd {
    public RewardAd(IPlatformLoader<RewardAdLoader> platformLoader, String platform) {
        super(platformLoader, platform);
    }
}
