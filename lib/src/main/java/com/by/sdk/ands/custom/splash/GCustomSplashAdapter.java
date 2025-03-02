package com.by.sdk.ands.custom.splash;

import android.content.Context;
import android.view.ViewGroup;

import com.by.sdk.ad.splash.SplashAdListener;
import com.by.sdk.ad.splash.SplashAdLoader;
import com.by.sdk.ad.splash.SplashInteractionListener;
import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.adpaster.BasePlatformLoader;
import com.by.sdk.ands.custom.GCustomInitLoader;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.proxy.GCustomProxyAd;

import java.util.HashMap;

public abstract class GCustomSplashAdapter extends BasePlatformLoader<SplashAdLoader, SplashAdListener> {

    private GCustomSplashAd customSplashAd;
    private GCustomProxyAd proxyAd;

    @Override
    public void loadAd(HashMap<String, Object> localMap) {
        initADN(localMap, new GCustomInitLoader.InitCallback() {
            @Override
            public void succ() {
                try {
                    GCustomInfo info = new GCustomInfo();
                    info.setApp_id(getAdInfo().getAppId());
                    info.setApp_key(getAdInfo().getAppKey());
                    info.setPid(getAdInfo().getPid());
                    loadCustomAd(getAdLoader().getContext(),getAdLoader().getAdSlot(),info);
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
    public abstract void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info);


    protected void callLoadedSuccess(){
        try {
            if (customSplashAd==null){
                customSplashAd = new GCustomSplashAd(this,getPlatform());
            }
            if (proxyAd ==null){
                proxyAd = new GCustomProxyAd(customSplashAd);
            }
            if (getConCurrentLoadListener()!=null){
                getConCurrentLoadListener().onLoadedSuccess(customSplashAd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void callRenderSuccess(){
        try {
            if (getConCurrentLoadListener()!=null){
                getConCurrentLoadListener().onRenderSuccess(customSplashAd);
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

    protected void callAdSkip(){
        if (customSplashAd!=null && customSplashAd.getInteractionListener() instanceof SplashInteractionListener){
            ((SplashInteractionListener) customSplashAd.getInteractionListener()).onSkip(customSplashAd);
        }
    }

    protected void callTimeOver(){
        if (customSplashAd!=null && customSplashAd.getInteractionListener() instanceof SplashInteractionListener){
            ((SplashInteractionListener) customSplashAd.getInteractionListener()).onTimeOver(customSplashAd);
        }
    }

    protected void callTick(long leftMillisecond){
        if (customSplashAd!=null && customSplashAd.getInteractionListener() instanceof SplashInteractionListener){
            ((SplashInteractionListener) customSplashAd.getInteractionListener()).onTick(leftMillisecond);
        }
    }

}
