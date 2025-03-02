package com.by.sdk.ands.bd.feed;

import android.content.Context;
import android.util.DisplayMetrics;

import com.baidu.mobads.sdk.api.BaiduNativeManager;
import com.baidu.mobads.sdk.api.ExpressResponse;
import com.baidu.mobads.sdk.api.NativeResponse;
import com.baidu.mobads.sdk.api.RequestParameters;
import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.feed.GCustomFeedAdapter;

import java.util.ArrayList;
import java.util.List;

public class BDFeedAdapter extends GCustomFeedAdapter {
    private static final String TAG = "BDFeedAdapter";
    @Override
    public void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info) {
        if (info.isExpress()){
            loadExpress(context,adSlot,info);
        }else {
            loadNative(context,adSlot,info);
        }
    }

    private void loadNative(Context context, GAdSlot adSlot, GCustomInfo info) {
        RequestParameters requestParameters = new RequestParameters.Builder()
//                .downloadAppConfirmPolicy(RequestParameters.DOWNLOAD_APP_CONFIRM_NEVER)
                .build();
        BaiduNativeManager nativeManager = new BaiduNativeManager(context.getApplicationContext(), info.getPid());
        nativeManager.loadFeedAd(requestParameters, new BaiduNativeManager.FeedAdListener() {
            @Override
            public void onNativeLoad(List<NativeResponse> list) {
                try {
                    LogUtil.d(TAG,"BD onNativeLoad");
                    if (list!=null && !list.isEmpty()){
                        ArrayList<BDFeedNativeAd> nativeAds = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            NativeResponse nativeResponse = list.get(i);
                            BDFeedNativeAd gdtFeedNativeAd = new BDFeedNativeAd(context,nativeResponse);
                            nativeAds.add(gdtFeedNativeAd);
                        }
                        callLoadedSuccess(nativeAds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNativeFail(int i, String s, NativeResponse nativeResponse) {
                LogUtil.d(TAG,"onNativeFail: code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onNoAd(int i, String s, NativeResponse nativeResponse) {
                LogUtil.d(TAG,"onNoAd: code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onVideoDownloadSuccess() {

            }

            @Override
            public void onVideoDownloadFailed() {

            }

            @Override
            public void onLpClosed() {

            }
        });
    }

    private void loadExpress(Context context, GAdSlot adSlot, GCustomInfo info) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels / dm.density);
        int height = 0;

        if (adSlot.getWidth()>0){
            width = (int) (adSlot.getWidth()/dm.density);
        }
        if (adSlot.getHeight()>0){
            height = (int) (adSlot.getHeight()/dm.density);
        }

        BaiduNativeManager nativeManager = new BaiduNativeManager(context.getApplicationContext(), info.getPid());
        nativeManager.setAppSid(info.getApp_id());
//        nativeManager.setAppSid("a3d1186d");
        nativeManager.setCacheVideoOnlyWifi(true);
        final RequestParameters requestParameters = new RequestParameters.Builder()
                .setWidth(width)
                .setHeight(height)
                .downloadAppConfirmPolicy(RequestParameters.DOWNLOAD_APP_CONFIRM_ALWAYS)
                .build();

        nativeManager.loadExpressAd(requestParameters, new BaiduNativeManager.ExpressAdListener() {
            @Override
            public void onNativeLoad(List<ExpressResponse> list) {
                try {
                    if (list!=null && !list.isEmpty()){
                        ArrayList<BDFeedExpressAd> expressAds = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            ExpressResponse expressResponse = list.get(i);
                            BDFeedExpressAd bdFeedExpressAd = new BDFeedExpressAd(expressResponse);
                            expressAds.add(bdFeedExpressAd);
                        }
                        callLoadedSuccess(expressAds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNativeFail(int i, String s, ExpressResponse expressResponse) {
                LogUtil.d(TAG,"onNativeFail: code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onNoAd(int i, String s, ExpressResponse expressResponse) {
                LogUtil.d(TAG,"onNoAd: code="+i+",msg="+s);
                callLoadedError(i,s);
            }

            @Override
            public void onVideoDownloadSuccess() {

            }

            @Override
            public void onVideoDownloadFailed() {

            }

            @Override
            public void onLpClosed() {

            }
        });
    }

    @Override
    public void destroy() {

    }
}
