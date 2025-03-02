package com.by.sdk.ands.custom.reward;

import android.app.Activity;

import com.by.sdk.ad.reward.RewardAd;

public class GCustomRewardAd extends RewardAd {
    private final GCustomRewardAdapter platformLoader;

    public GCustomRewardAd(GCustomRewardAdapter platformLoader, String platform) {
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
