package com.by.sdk.ands;

import com.by.sdk.ad.banner.BannerAdLoader;
import com.by.sdk.ad.feed.FeedAdLoader;
import com.by.sdk.ad.intertitial.IntertitialAdLoader;
import com.by.sdk.ad.reward.RewardAdLoader;
import com.by.sdk.ad.splash.SplashAdLoader;
import com.by.sdk.byad.ISdkInitConfig;
import com.by.sdk.byad.bean.BYAdInfo;
import com.by.sdk.byad.adpaster.IPlatformLoader;

public interface IPlatform {
    ISdkInitConfig getConfig();

    IPlatformLoader splashLoader(SplashAdLoader adLoader, BYAdInfo info);
    IPlatformLoader rewardLoader(RewardAdLoader adLoader, BYAdInfo info);
    IPlatformLoader feedLoader(FeedAdLoader adLoader, BYAdInfo info);
    IPlatformLoader intertitialLoader(IntertitialAdLoader adLoader, BYAdInfo info);
    IPlatformLoader bannerLoader(BannerAdLoader adLoader, BYAdInfo info);
}
