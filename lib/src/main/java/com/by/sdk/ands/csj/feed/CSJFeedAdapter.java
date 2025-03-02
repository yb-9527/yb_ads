package com.by.sdk.ands.csj.feed;

import android.content.Context;
import android.util.DisplayMetrics;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.feed.GCustomFeedAdapter;

import java.util.ArrayList;
import java.util.List;

public class CSJFeedAdapter extends GCustomFeedAdapter {
    private static final String TAG = "CSJFeedAdapter";
    @Override
    public void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info) {
        int adWidth = adSlot.getWidth();
        int adHeight = adSlot.getHeight();

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        if (adWidth<=0){
            adWidth = (int) (dm.widthPixels / dm.density);
        }
        if (adHeight<0){
            adHeight=0;
        }
        final AdSlot csjAdSlot = new AdSlot.Builder()
                .setCodeId(info.getPid())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(720, 1080)
                .setExpressViewAcceptedSize(adWidth, adHeight)
                .setAdCount(adSlot.getFetchCount()) // 有时候获取到的个数会小于 adCount 数
                .build();
        if (info.isExpress()){
            loadExpress(context,csjAdSlot);
        }else {
            loadNative(context,csjAdSlot);
        }
    }

    private void loadNative(Context context, AdSlot adSlot) {
        TTAdNative adNative = TTAdSdk.getAdManager().createAdNative(context.getApplicationContext());
        adNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"CSJ native onError,code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> list) {
                if (list!=null && !list.isEmpty()){
                    LogUtil.d(TAG,"onFeedAdLoad");
                    List<CSJFeedNativeAd> nativeAds = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        TTFeedAd ttFeedAd = list.get(i);
                        CSJFeedNativeAd csjFeedNativeAd = new CSJFeedNativeAd(ttFeedAd);
                        nativeAds.add(csjFeedNativeAd);
                    }
                    callLoadedSuccess(nativeAds);
                }
            }
        });
    }

    private void loadExpress(Context context, AdSlot adSlot) {
        TTAdNative adNative = TTAdSdk.getAdManager().createAdNative(context.getApplicationContext());
        adNative.loadNativeExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"CSJ onError: code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
                try {
                    if (list!=null && !list.isEmpty()){
                        List<CSJFeedExpressAd> expressAds = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            TTNativeExpressAd ttNativeExpressAd = list.get(i);
                            CSJFeedExpressAd csjFeedExpressAd = new CSJFeedExpressAd(ttNativeExpressAd);
                            expressAds.add(csjFeedExpressAd);
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
