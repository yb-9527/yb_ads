package com.by.sdk.byad.adpaster;

import com.by.sdk.byad.bean.BYAdInfo;

import java.util.HashMap;

public interface IPlatformLoader<T extends BaseAdLoader> {
    void loadAd(HashMap<String, Object> localMap);
    void destroy();
    void init(T adLoader, BYAdInfo gAdInfo);
    BYAdInfo getAdInfo();
    T getAdLoader();
    void setConCurrentLoadListener(IConCurrentLoadListener listener);
    IConCurrentLoadListener getConCurrentLoadListener();
    IAdLoadListener getLoaderListener();
}
