package com.by.sdk.ands.ks.feed;

import android.content.Context;

import androidx.annotation.Nullable;

import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.feed.GCustomFeedAdapter;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsFeedAd;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsNativeAd;
import com.kwad.sdk.api.KsScene;

import java.util.ArrayList;
import java.util.List;

public class KSFeedAdapter extends GCustomFeedAdapter {
    private static final String TAG = "KSFeedAdapter";
    @Override
    public void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info) {
        if (info.isExpress()){
            loadExpress(context,adSlot,info);
        }else {
            loadNative(context,adSlot,info);
        }
    }

    private void loadNative(Context context, GAdSlot adSlot, GCustomInfo info) {
        KsScene scene = new KsScene.Builder(Long.parseLong(info.getPid()))
                .adNum(adSlot.getFetchCount())
                .build();

        KsAdSDK.getLoadManager().loadNativeAd(scene, new KsLoadManager.NativeAdListener() {

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"KS onError: code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onNativeAdLoad(@Nullable List<KsNativeAd> list) {
                try {
                    if (list!=null && !list.isEmpty()){
                        ArrayList<KSFeedNativeAd> nativeAds = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            KsNativeAd ksNativeAd = list.get(i);
                            KSFeedNativeAd ksFeedNativeAd = new KSFeedNativeAd(context,ksNativeAd);

                            nativeAds.add(ksFeedNativeAd);
                        }
                        callLoadedSuccess(nativeAds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadExpress(Context context, GAdSlot adSlot, GCustomInfo info) {
        KsScene.Builder builder = new KsScene.Builder(Long.parseLong(info.getPid()))
                .adNum(adSlot.getFetchCount());

        KsAdSDK.getLoadManager().loadConfigFeedAd(builder.build(), new KsLoadManager.FeedAdListener() {
            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"KS onError: code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onFeedAdLoad(@Nullable List<KsFeedAd> list) {
                try {
                    if (list!=null && !list.isEmpty()){
                        ArrayList<KSFeedExpressAd> expressAds = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            KsFeedAd ksFeedAd = list.get(i);
                            KSFeedExpressAd ksFeedExpressAd = new KSFeedExpressAd(context,ksFeedAd);
                            expressAds.add(ksFeedExpressAd);
                        }
                        callLoadedSuccess(expressAds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void destroy() {

    }
}
