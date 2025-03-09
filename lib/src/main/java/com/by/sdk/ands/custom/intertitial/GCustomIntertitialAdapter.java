package com.by.sdk.ands.custom.intertitial;

import android.app.Activity;
import android.content.Context;

import com.by.sdk.ad.intertitial.IntertitialAdListener;
import com.by.sdk.ad.intertitial.IntertitialAdLoader;
import com.by.sdk.byad.BYAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.adpaster.BasePlatformLoader;
import com.by.sdk.ands.custom.GCustomInitLoader;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.proxy.GCustomProxyAd;

import java.util.HashMap;

public abstract class GCustomIntertitialAdapter extends BasePlatformLoader<IntertitialAdLoader, IntertitialAdListener> {

    private GCustomIntertitialAd customIntertitialAd;
    private GCustomProxyAd proxyAd;

    @Override
    public void loadAd(HashMap<String, Object> localMap) {
        initADN(localMap, new GCustomInitLoader.InitCallback() {
            @Override
            public void succ() {
                try {
                    if (customIntertitialAd==null){
                        customIntertitialAd = new GCustomIntertitialAd(GCustomIntertitialAdapter.this,getPlatform());
                    }
                    if (proxyAd ==null){
                        proxyAd = new GCustomProxyAd(customIntertitialAd);
                    }
                    GCustomInfo info = new GCustomInfo();
                    info.setApp_id(getAdInfo().getAppId());
                    info.setApp_key(getAdInfo().getAppKey());
                    info.setPid(getAdInfo().getPid());
                    info.setExpress(getAdInfo().getFeedType()!=1);
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

    public abstract void showAd(Activity activity);
    public abstract void loadCustomAd(Context context, BYAdSlot adSlot, GCustomInfo info);

    protected void callLoadedSuccess(){

        if (getConCurrentLoadListener()!=null){
            getConCurrentLoadListener().onLoadedSuccess(customIntertitialAd);
        }
    }

    protected void callRenderSuccess(){
        if (getConCurrentLoadListener()!=null){
            getConCurrentLoadListener().onRenderSuccess(customIntertitialAd);
        }
    }

    protected void callLoadedError(int code,String msg){
        if (getConCurrentLoadListener()!=null){
            getConCurrentLoadListener().onLoadedError(code,msg);
        }
    }

    protected void callRenderError(int code,String msg){
        if (getConCurrentLoadListener()!=null){
            getConCurrentLoadListener().onRenderError(code,msg);
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
}
