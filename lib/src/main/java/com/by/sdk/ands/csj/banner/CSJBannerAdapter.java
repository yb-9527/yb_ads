package com.by.sdk.ands.csj.banner;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.by.sdk.byad.error.ErrorCodeUtil;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.banner.GCustomBannerAdapter;
import com.by.sdk.ands.custom.bean.GCustomInfo;

import java.util.List;

public class CSJBannerAdapter extends GCustomBannerAdapter {
    private static final String TAG = "CSJBannerAdapter";
    private TTNativeExpressAd ttNativeExpressAd;

    @Override
    public void showAd(ViewGroup container) {
        try {
            if (ttNativeExpressAd!=null){
                View adView = ttNativeExpressAd.getExpressAdView();
                ttNativeExpressAd.setDislikeCallback((Activity) container.getContext(), new TTAdDislike.DislikeInteractionCallback() {
                    @Override
                    public void onShow() {

                    }

                    @Override
                    public void onSelected(int i, String s, boolean b) {
                        callAdClosed();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                if (null != adView.getParent() && adView.getParent()instanceof ViewGroup) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }
                if (null != container ) {
                    container.removeAllViews();
                    container.addView(adView);
                }
            }
        } catch (Exception e) {
            callRenderError(ErrorCodeUtil.AD_RENDER_ERR,"render error");
            e.printStackTrace();
        }
    }

    @Override
    public void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info) {

        int adWidth = adSlot.getWidth();
        int adHeight = adSlot.getHeight();

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        if (adWidth<=0){
            adWidth = (int) (dm.widthPixels / dm.density);
        }
        if (adHeight<0){
            adHeight=0;
        }


        TTAdNative ttAdNative = TTAdSdk.getAdManager().createAdNative(context);
        AdSlot csjAdSlot = new AdSlot.Builder()
                .setCodeId(info.getPid()) //广告位id
                .setSupportDeepLink(true)
                .setExpressViewAcceptedSize(adWidth, adHeight)
                .build();
        ttAdNative.loadBannerExpressAd(csjAdSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"onError: code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
                try {
                    if (list!=null && !list.isEmpty()){
                        ttNativeExpressAd = list.get(0);
                        ttNativeExpressAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
                            @Override
                            public void onAdClicked(View view, int i) {
                                callAdClicked();
                            }

                            @Override
                            public void onAdShow(View view, int i) {
                                callAdExposure();
                            }

                            @Override
                            public void onRenderFail(View view, String s, int i) {
                                callRenderError(i,s);
                            }

                            @Override
                            public void onRenderSuccess(View view, float v, float v1) {
                                callRenderSuccess();
                            }
                        });

                        callLoadedSuccess();
                        ttNativeExpressAd.render();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
