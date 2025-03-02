package com.by.sdk.ands.custom.feed;

import android.content.Context;
import android.text.TextUtils;

import com.by.sdk.ad.feed.FeedAd;
import com.by.sdk.ad.feed.FeedAdEventListener;
import com.by.sdk.ad.feed.FeedAdLoader;
import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.adpaster.BasePlatformLoader;
import com.by.sdk.ands.custom.GCustomInitLoader;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.proxy.GCustomProxyAd;

import java.util.HashMap;
import java.util.List;

public abstract class GCustomFeedAdapter extends BasePlatformLoader<FeedAdLoader, FeedAdEventListener> {
    private GCustomProxyAd proxyAd;
    private FeedAd feedAd;;
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

    public abstract void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info);

    /**
     * 是否是模板，true为模板，false为自渲染
     */
    protected void callLoadedSuccess(List<? extends FeedAd> feedList){
        try {
            if (feedList!=null){
                for (FeedAd feed:feedList) {
                    if (TextUtils.isEmpty(feed.getPlatform())){
                        feed.setPlatform(getPlatform());
                    }
                    if (feed.getPlatformLoader()==null){
                        feed.setPlatformLoader(this);
                    }
                    feed.setProxyAd(new GCustomProxyAd(feed));
                }
                if (getConCurrentLoadListener()!=null){
                    getConCurrentLoadListener().onLoadedSuccess(feedList);
                    getConCurrentLoadListener().onRenderSuccess(feedList);
                }
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


}
