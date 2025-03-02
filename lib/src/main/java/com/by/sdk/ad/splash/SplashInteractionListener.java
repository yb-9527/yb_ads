package com.by.sdk.ad.splash;

import com.by.sdk.ad.InteractionListener;

public interface SplashInteractionListener extends InteractionListener {
    void onSkip(ISplashAd splashAd);

    void onTimeOver(ISplashAd splashAd);

    void onTick(long leftMillisecond);
}
