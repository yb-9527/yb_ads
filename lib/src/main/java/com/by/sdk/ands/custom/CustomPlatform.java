package com.by.sdk.ands.custom;

import android.text.TextUtils;

import com.by.sdk.ad.banner.BannerAdLoader;
import com.by.sdk.ad.feed.FeedAdLoader;
import com.by.sdk.ad.intertitial.IntertitialAdLoader;
import com.by.sdk.ad.reward.RewardAdLoader;
import com.by.sdk.ad.splash.SplashAdLoader;
import com.by.sdk.byad.ISdkInitConfig;
import com.by.sdk.byad.bean.GAdInfo;
import com.by.sdk.byad.adpaster.IPlatformLoader;
import com.by.sdk.ands.BasePlatform;
import com.by.sdk.ands.custom.banner.GCustomBannerAdapter;
import com.by.sdk.ands.custom.feed.GCustomFeedAdapter;
import com.by.sdk.ands.custom.intertitial.GCustomIntertitialAdapter;
import com.by.sdk.ands.custom.reward.GCustomRewardAdapter;
import com.by.sdk.ands.custom.splash.GCustomSplashAdapter;

import java.lang.reflect.Constructor;

public class CustomPlatform extends BasePlatform {
    @Override
    public ISdkInitConfig getConfig() {
        return super.getConfig();
    }

    @Override
    public IPlatformLoader<SplashAdLoader> splashLoader(SplashAdLoader adLoader, GAdInfo info) {
        if (!TextUtils.isEmpty(info.getCls())){
            try {
                Class<?> aClass = Class.forName(info.getCls());
                Constructor<?> constructor = aClass.getConstructor();
                constructor.setAccessible(true);
                return (GCustomSplashAdapter) constructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public IPlatformLoader<RewardAdLoader> rewardLoader(RewardAdLoader adLoader, GAdInfo info) {
        if (!TextUtils.isEmpty(info.getCls())){
            try {
                Class<?> aClass = Class.forName(info.getCls());
                Constructor<?> constructor = aClass.getConstructor();
                constructor.setAccessible(true);
                return (GCustomRewardAdapter) constructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public IPlatformLoader<FeedAdLoader> feedLoader(FeedAdLoader adLoader, GAdInfo info) {
        if (!TextUtils.isEmpty(info.getCls())){
            try {
                Class<?> aClass = Class.forName(info.getCls());
                Constructor<?> constructor = aClass.getConstructor();
                constructor.setAccessible(true);
                return (GCustomFeedAdapter) constructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public IPlatformLoader<IntertitialAdLoader> intertitialLoader(IntertitialAdLoader adLoader, GAdInfo info) {
        if (!TextUtils.isEmpty(info.getCls())){
            try {
                Class<?> aClass = Class.forName(info.getCls());
                Constructor<?> constructor = aClass.getConstructor();
                constructor.setAccessible(true);
                return (GCustomIntertitialAdapter) constructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public IPlatformLoader<BannerAdLoader> bannerLoader(BannerAdLoader adLoader, GAdInfo info) {
        if (!TextUtils.isEmpty(info.getCls())){
            try {
                Class<?> aClass = Class.forName(info.getCls());
                Constructor<?> constructor = aClass.getConstructor();
                constructor.setAccessible(true);
                return (GCustomBannerAdapter) constructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
