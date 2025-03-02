package com.by.sdk.ands.csj.splash;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.CSJAdError;
import com.bytedance.sdk.openadsdk.CSJSplashAd;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.utils.DimensionUtils;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.splash.GCustomSplashAdapter;

public class CSJSplashAdapter extends GCustomSplashAdapter {
    private static final String TAG = "CsjSplashAdapter";
    private CSJSplashAd splashAd;
    @Override
    public void showAd(ViewGroup container) {
        if (splashAd!=null){
            splashAd.showSplashView(container);
        }
    }

    @Override
    public void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info) {
        TTAdNative ttAdNative = TTAdSdk.getAdManager().createAdNative(context);

        int adContentWidth = 1080;
        int adContentHeight = 1920;
        if (0 < adSlot.getWidth() && 0 < adSlot.getHeight()) {
            adContentWidth = adSlot.getWidth();
            adContentHeight = adSlot.getHeight();
        }else {
            try {
                DisplayMetrics displayMetrics = this.adLoader.getContext().getResources().getDisplayMetrics();
                if (0 < displayMetrics.widthPixels && 0 < displayMetrics.heightPixels) {
                    adContentWidth = displayMetrics.widthPixels;
                    adContentHeight = displayMetrics.heightPixels;
                }
            } catch (Exception e) {
            }
        }

        //step4:创建广告请求参数AdSlot, 具体参数含义参考文档
        AdSlot csjAdSlot = new AdSlot.Builder()
                .setCodeId(info.getPid()) //广告位id
                .setSupportDeepLink(true)
                .setImageAcceptedSize(adContentWidth, adContentHeight)
                .setExpressViewAcceptedSize(DimensionUtils.px2dip(context, adContentWidth), DimensionUtils.px2dip(context, adContentHeight))
                .build();


        ttAdNative.loadSplashAd(csjAdSlot, new TTAdNative.CSJSplashAdListener() {


            @Override
            public void onSplashLoadSuccess(CSJSplashAd csjSplashAd) {
                try {
                    LogUtil.d(TAG,"CSJ load success");
                    callLoadedSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSplashLoadFail(CSJAdError csjAdError) {
                try {
                    LogUtil.d(TAG,"csj onSplashLoadFail,code="+csjAdError.getCode()+",msg="+csjAdError.getMsg());
                    callLoadedError(csjAdError.getCode(),csjAdError.getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSplashRenderSuccess(CSJSplashAd csjSplashAd) {

                try {
                    callRenderSuccess();

                    if (csjSplashAd!=null){
                        splashAd = csjSplashAd;
                        csjSplashAd.setSplashAdListener(new CSJSplashAd.SplashAdListener() {
                            @Override
                            public void onSplashAdShow(CSJSplashAd csjSplashAd) {
                                callAdExposure();
                            }

                            @Override
                            public void onSplashAdClick(CSJSplashAd csjSplashAd) {
                                callAdClicked();
                            }

                            @Override
                            public void onSplashAdClose(CSJSplashAd csjSplashAd, int i) {
                                callAdClosed();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSplashRenderFail(CSJSplashAd csjSplashAd, CSJAdError csjAdError) {
                try {
                    LogUtil.d(TAG,"csj onSplashRenderFail,code="+csjAdError.getCode()+",msg="+csjAdError.getMsg());
                    callRenderError(csjAdError.getCode(),csjAdError.getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },3500);
    }


    @Override
    public void destroy() {
        splashAd =null;
    }
}
