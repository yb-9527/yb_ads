package com.by.sdk.ands.gdt.banner;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.view.ViewGroup;

import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.banner.GCustomBannerAdapter;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.comm.util.AdError;

public class GDTBannerAdapter extends GCustomBannerAdapter {
    private static final String TAG = "GDTBannerAdapter";
    private UnifiedBannerView gdtBannerView;

    @Override
    public void showAd(ViewGroup container) {
        try {
            if (gdtBannerView!=null){
                container.removeAllViews();
                if (gdtBannerView.getParent()!=null && gdtBannerView.getParent() instanceof ViewGroup){
                    ((ViewGroup) gdtBannerView.getParent()).removeView(gdtBannerView);
                }
                container.addView(gdtBannerView);
            }
        } catch (Exception e) {
            callRenderError(ErrorCodeUtil.AD_RENDER_ERR,"gdt banner render err");
            e.printStackTrace();
        }
    }

    @Override
    public void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info) {
        LogUtil.d(TAG,"load gdt custom ad,current thread="+(Looper.myLooper()==Looper.getMainLooper()));
        gdtBannerView = new UnifiedBannerView((Activity) context, info.getPid(), new UnifiedBannerADListener() {
            @Override
            public void onNoAD(AdError adError) {
                try {
                    LogUtil.d(TAG,"onNoAD code="+adError.getErrorCode()+",msg="+adError.getErrorMsg());
                    callLoadedError(adError.getErrorCode(),adError.getErrorMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onADReceive() {
                LogUtil.d(TAG,"onADReceive");
                callLoadedSuccess();
                callRenderSuccess();
            }

            @Override
            public void onADExposure() {
                callAdExposure();
            }

            @Override
            public void onADClosed() {
                callAdClosed();
            }

            @Override
            public void onADClicked() {
                callAdClicked();
            }

            @Override
            public void onADLeftApplication() {

            }
        });

        gdtBannerView.setRefresh(0);
        gdtBannerView.loadAD();
    }
}
