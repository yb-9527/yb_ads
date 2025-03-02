package com.by.sdk.byad.adpaster;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.by.sdk.ad.AdType;
import com.by.sdk.ad.banner.BannerAdLoader;
import com.by.sdk.ad.feed.FeedAdLoader;
import com.by.sdk.ad.intertitial.IntertitialAdLoader;
import com.by.sdk.ad.reward.RewardAdLoader;
import com.by.sdk.ad.splash.SplashAdLoader;
import com.by.sdk.byad.GAdSdk;
import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.bean.GAdInfo;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.GContanst;
import com.by.sdk.byad.utils.LogUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public abstract class BaseAdLoader<T extends IAdLoadListener> {
    private static final String TAG = "BaseAdLoader";
    private Context context;
    private final GAdSlot adSlot;
    private T loaderListener;
    private volatile GAdLoadManager loadManager;
    private HashMap<String,Object> localMap;
    private static final long DEFAULT_TIMEOUT = 10000; // 默认超时时间10秒
    private Handler mHandler = new Handler();

    public BaseAdLoader(Context context, GAdSlot adSlot, T loaderListener) {
        this.context = context;
        this.adSlot = adSlot;
        this.loaderListener = loaderListener;
    }

    protected synchronized void loadAd(){
        try {
            if (GAdSdk.getConfig() == null){
                Log.e(TAG, "sdk init error ");
                loadError(ErrorCodeUtil.INIT_ERR,"sdk init error");
                return;
            }
            AdType adType = getAdType();
            if (null == adType) {
                LogUtil.e(TAG, "AdType is null！");
                loadError(ErrorCodeUtil.ADTYPE_ERR,"AdType is null");
                return;
            }

            if (adSlot==null || TextUtils.isEmpty(adSlot.getAdJsonData())){
                LogUtil.e(TAG, "json data is null！");
                loadError(ErrorCodeUtil.AD_DATA_ERR,"json data is null");
                return;
            }
            LogUtil.d(TAG,"start load ad,adType="+adType.value()+",data="+adSlot.getAdJsonData());
            startTimeout();
            Type type = new TypeToken<List<GAdInfo>>(){}.getType();
            Gson gson = new Gson();
            List<GAdInfo> list = gson.fromJson(adSlot.getAdJsonData(), type);

            if (loadManager==null){
                loadManager = new GAdLoadManager(context,BaseAdLoader.this);
            }
            if (list!=null && !list.isEmpty()){
                handleCustomPlatform(adType,list);
                loadManager.filterAd(list);
            }else {
                loadError(ErrorCodeUtil.AD_DATA_ERR,"ad data is null");
            }
        } catch (Exception e) {
            loadError(ErrorCodeUtil.AD_DATA_ERR,"ad data is null,"+e.getMessage());
            e.printStackTrace();
        }

    }



    private void startTimeout() {
        long all_timeout = DEFAULT_TIMEOUT;

        try {
            if (getFetchTimeout()>0){
                all_timeout = getFetchTimeout();
            }


//            LogUtil.e(TAG,"allTimeout="+all_timeout);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (loadManager!=null){
                        loadManager.handleTimeout();
                    }
                }
            }, all_timeout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected abstract IPlatformLoader createPlatformLoader(Context context, GAdInfo jAdInfo);

    public Context getContext() {
        return context;
    }

    public T getLoaderListener() {
        return loaderListener;
    }

    public AdType getAdType() {
        if (this instanceof FeedAdLoader) {
            return AdType.FEED;
        }

        if (this instanceof BannerAdLoader) {
            return AdType.BANNER;
        }
        if (this instanceof SplashAdLoader) {
            return AdType.SPLASH;
        }
        if (this instanceof IntertitialAdLoader) {
            return AdType.INTERSTITIAL;
        }
        if (this instanceof RewardAdLoader) {
            return AdType.REWARD;
        }
        return null;
    }


    private void loadError(int code, String msg) {
        if (loaderListener!=null){
            loaderListener.onLoadedError(code,msg);
        }
    }

    public GAdSlot getAdSlot(){
        return adSlot;
    }

    public int getFetchTimeout() {
        return 0;
    }


    private void handleCustomPlatform(AdType adType, List<GAdInfo> adList) {
        try {
            for (int i = 0; i < adList.size(); i++) {
                GAdInfo ad = adList.get(i);
                if (GContanst.PLATFROM_CSJ.equals(ad.getPlatformName())){
                    setCsjClass(adType,ad);
                }else if (GContanst.PLATFROM_GDT.equals(ad.getPlatformName())){
                    setGdtClass(adType,ad);
                }else if (GContanst.PLATFROM_BD.equals(ad.getPlatformName())){
                    setBdClass(adType,ad);
                }else if (GContanst.PLATFROM_KS.equals(ad.getPlatformName())){
                    setKsClass(adType,ad);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setKsClass(AdType adType, GAdInfo ad) {
        try {
            ad.setInitCls("com.by.sdk.ands.ks.KSInitManager");
            switch (adType){
                case BANNER:
                    break;
                case FEED:
                    ad.setCls("com.by.sdk.ands.ks.feed.KSFeedAdapter");
                    break;
                case REWARD:
                    ad.setCls("com.by.sdk.ands.ks.reward.KSRewardAdapter");
                    break;
                case SPLASH:
                    ad.setCls("com.by.sdk.ands.ks.splash.KSSplashAdapter");
                    break;
                case INTERSTITIAL:
                    ad.setCls("com.by.sdk.ands.ks.intertitial.KSIntertitialAdapter");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBdClass(AdType adType, GAdInfo ad) {
        try {
            ad.setInitCls("com.by.sdk.ands.bd.BDInitManager");
            switch (adType){
                case BANNER:
    //                ad.setCls("com.by.sdk.ands.gdt.banner.GDTBannerAdapter");
                    break;
                case FEED:
                    ad.setCls("com.by.sdk.ands.bd.feed.BDFeedAdapter");
                    break;
                case REWARD:
                    ad.setCls("com.by.sdk.ands.bd.reward.BDRewardAdapter");
                    break;
                case SPLASH:
                    ad.setCls("com.by.sdk.ands.bd.splash.BDSplashAdapter");
                    break;
                case INTERSTITIAL:
                    ad.setCls("com.by.sdk.ands.bd.intertitial.BDIntertitialAdapter");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGdtClass(AdType adType, GAdInfo ad) {
        try {
            ad.setInitCls("com.by.sdk.ands.gdt.GDTInitManager");
            switch (adType){
                case BANNER:
                    ad.setCls("com.by.sdk.ands.gdt.banner.GDTBannerAdapter");
                    break;
                case FEED:
                    ad.setCls("com.by.sdk.ands.gdt.feed.GDTFeedAdapter");
                    break;
                case REWARD:
                    ad.setCls("com.by.sdk.ands.gdt.reward.GDTRewardAdapter");
                    break;
                case SPLASH:
                    ad.setCls("com.by.sdk.ands.gdt.splash.GDTSplashAdapter");
                    break;
                case INTERSTITIAL:
                    ad.setCls("com.by.sdk.ands.gdt.intertitial.GDTIntertitialAdapter");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCsjClass(AdType adType, GAdInfo ad) {
        try {
            ad.setInitCls("com.by.sdk.ands.csj.CSJInitManager");
            switch (adType){
                case BANNER:
                    ad.setCls("com.by.sdk.ands.csj.banner.CSJBannerAdapter");
                    break;
                case FEED:
                    ad.setCls("com.by.sdk.ands.csj.feed.CSJFeedAdapter");
                    break;
                case REWARD:
                    ad.setCls("com.by.sdk.ands.csj.reward.CSJRewardAdapter");
                    break;
                case SPLASH:
                    ad.setCls("com.by.sdk.ands.csj.splash.CSJSplashAdapter");
                    break;
                case INTERSTITIAL:
                    ad.setCls("com.by.sdk.ands.csj.intertitial.CSJIntertitialAdapter");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLocalMap(HashMap<String, Object> localMap) {
        this.localMap = localMap;
    }

    public HashMap<String, Object> getLocalMap() {
        return localMap;
    }
}
