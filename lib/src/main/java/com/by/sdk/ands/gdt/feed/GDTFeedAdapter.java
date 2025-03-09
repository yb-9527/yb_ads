package com.by.sdk.ands.gdt.feed;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.feed.GCustomFeedAdapter;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeADUnifiedListener;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeUnifiedAD;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.comm.util.AdError;

import java.util.ArrayList;
import java.util.List;

public class GDTFeedAdapter extends GCustomFeedAdapter {
    private static final String TAG = "GDTFeedAdapter";
    private List<GDTFeedExpressAd> expressAds;

    @Override
    public void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info) {
        if (info.isExpress()){
            loadExpress(context,adSlot,info);
        }else {
            loadNative(context,adSlot,info);
        }
    }

    private void loadNative(Context context, BYAdSlot adSlot, GCustomInfo info) {
        NativeUnifiedAD nativeUnifiedAD = new NativeUnifiedAD(context, info.getPid(), new NativeADUnifiedListener() {
            @Override
            public void onADLoaded(List<NativeUnifiedADData> list) {
                try {
                    if (list!=null && !list.isEmpty()){
                        ArrayList<GDTFeedNativeAd> nativeAds = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            NativeUnifiedADData nativeUnifiedADData = list.get(i);
                            GDTFeedNativeAd gdtFeedNativeAd = new GDTFeedNativeAd(context,nativeUnifiedADData);
                            nativeAds.add(gdtFeedNativeAd);
                        }
                        callLoadedSuccess(nativeAds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNoAD(AdError adError) {
                try {
                    LogUtil.d(TAG,"onNoAD code="+adError.getErrorCode()+",msg="+adError.getErrorMsg());
                    callLoadedError(adError.getErrorCode(),adError.getErrorMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        nativeUnifiedAD.loadData(adSlot.getFetchCount());
    }

    private void loadExpress(Context context, BYAdSlot adSlot, GCustomInfo info) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = ADSize.FULL_WIDTH;
        int height = ADSize.AUTO_HEIGHT;
        if (adSlot.getWidth()>0){
            width = (int) (adSlot.getWidth()/dm.density);
        }
        if (adSlot.getHeight()>0){
            height = (int) (adSlot.getHeight()/dm.density);
        }

        ADSize adSize = new ADSize(width,height);

        NativeExpressAD nativeExpressAD = new NativeExpressAD(context, adSize, info.getPid(), new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                try {
                    if (list!=null && !list.isEmpty()){
                        expressAds = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            NativeExpressADView nativeExpressADView = list.get(i);
                            GDTFeedExpressAd gdtFeedExpressAd = new GDTFeedExpressAd(nativeExpressADView);
                            expressAds.add(gdtFeedExpressAd);
                        }
                        callLoadedSuccess(expressAds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {
                try {
                    if (expressAds!=null && !expressAds.isEmpty()){
                        for (int i = 0; i < expressAds.size(); i++) {
                            GDTFeedExpressAd gdtFeedExpressAd = expressAds.get(i);
                            if (gdtFeedExpressAd.getExpressView().equals(nativeExpressADView)){
                                gdtFeedExpressAd.callRenderError(ErrorCodeUtil.AD_RENDER_ERR,"gdt render error");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                try {
                    if (expressAds!=null && !expressAds.isEmpty()){
                        for (int i = 0; i < expressAds.size(); i++) {
                            GDTFeedExpressAd gdtFeedExpressAd = expressAds.get(i);
                            if (gdtFeedExpressAd.getExpressView().equals(nativeExpressADView)){
                                gdtFeedExpressAd.callRenderSuccess();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {
                try {
                    if (expressAds!=null && !expressAds.isEmpty()){
                        for (int i = 0; i < expressAds.size(); i++) {
                            GDTFeedExpressAd gdtFeedExpressAd = expressAds.get(i);
                            if (gdtFeedExpressAd.getExpressView().equals(nativeExpressADView)){
                                gdtFeedExpressAd.callAdExposure();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
                try {
                    if (expressAds!=null && !expressAds.isEmpty()){
                        for (int i = 0; i < expressAds.size(); i++) {
                            GDTFeedExpressAd gdtFeedExpressAd = expressAds.get(i);
                            if (gdtFeedExpressAd.getExpressView().equals(nativeExpressADView)){
                                gdtFeedExpressAd.callAdClicked();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                try {
                    if (expressAds!=null && !expressAds.isEmpty()){
                        for (int i = 0; i < expressAds.size(); i++) {
                            GDTFeedExpressAd gdtFeedExpressAd = expressAds.get(i);
                            if (gdtFeedExpressAd.getExpressView().equals(nativeExpressADView)){
                                gdtFeedExpressAd.callAdClosed();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onNoAD(AdError adError) {
                try {
                    Log.d(TAG, "gdt onNoAD" );
                    LogUtil.d(TAG,"onNoAD code="+adError.getErrorCode()+",msg="+adError.getErrorMsg());
                    callLoadedError(adError.getErrorCode(),adError.getErrorMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        VideoOption videoOption = new VideoOption.Builder()
//                .setAutoPlayPolicy(getAdLoader().getIsVideoAutoPlay()?VideoOption.AutoPlayPolicy.ALWAYS:VideoOption.AutoPlayPolicy.WIFI)
                .build();
        nativeExpressAD.setVideoOption(videoOption);
        nativeExpressAD.loadAD(adSlot.getFetchCount());
    }

    @Override
    public void destroy() {

    }
}
