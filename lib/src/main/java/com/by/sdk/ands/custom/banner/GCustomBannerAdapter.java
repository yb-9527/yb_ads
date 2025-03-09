package com.by.sdk.ands.custom.banner;

import android.content.Context;
import android.view.ViewGroup;

import com.by.sdk.ad.banner.BannerAdListener;
import com.by.sdk.ad.banner.BannerAdLoader;
import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.SDKHandler;
import com.by.sdk.byad.adpaster.BasePlatformLoader;
import com.by.sdk.ands.custom.GCustomInitLoader;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.proxy.GCustomProxyAd;

import java.util.HashMap;

public abstract class GCustomBannerAdapter extends BasePlatformLoader<BannerAdLoader, BannerAdListener> {

    private GCustomBannerAd customBannerAd;
    private GCustomProxyAd proxyAd;

    @Override
    public void loadAd(HashMap<String, Object> localMap) {
        initADN(localMap, new GCustomInitLoader.InitCallback() {
            @Override
            public void succ() {
                try {
                    SDKHandler.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            GCustomInfo info = new GCustomInfo();
                            info.setApp_id(getAdInfo().getAppId());
                            info.setApp_key(getAdInfo().getAppKey());
                            info.setPid(getAdInfo().getPid());
                            loadCustomAd(getAdLoader().getContext(),getAdLoader().getAdSlot(),info);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {
                try {
                    if (getConCurrentLoadListener()!=null){
                        getConCurrentLoadListener().onLoadedError(ErrorCodeUtil.ADN_INIT_ERR,"ADN init error");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public abstract void showAd(ViewGroup container);
    public abstract void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info);


    protected void callLoadedSuccess(){
        try {
            if (customBannerAd==null){
                customBannerAd = new GCustomBannerAd(this,getPlatform());
            }
            if (proxyAd ==null){
                proxyAd = new GCustomProxyAd(customBannerAd);
            }
            if (getConCurrentLoadListener()!=null){
                getConCurrentLoadListener().onLoadedSuccess(customBannerAd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void callRenderSuccess(){
        try {
            if (getConCurrentLoadListener()!=null){
                getConCurrentLoadListener().onRenderSuccess(customBannerAd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void callLoadedError(int code,String msg){
        try {
            if (getConCurrentLoadListener()!=null){
                getConCurrentLoadListener().onLoadedError(code,msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void callRenderError(int code,String msg){
        try {
            if (getConCurrentLoadListener()!=null){
                getConCurrentLoadListener().onRenderError(code,msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    protected void callAdExposure(){
        if (proxyAd!=null){
            proxyAd.onAdExposure();
        }
    }

    protected void callAdClicked(){
        if (proxyAd!=null){
            proxyAd.onAdClicked();
        }
    }

    protected void callAdClosed(){
        if (proxyAd!=null){
            proxyAd.onAdClosed();
        }
    }
    @Override
    public void destroy() {

    }
}
