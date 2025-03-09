package com.by.sdk.byad.adpaster;

import android.content.Context;
import android.text.TextUtils;

import com.by.sdk.byad.bean.BYAdInfo;
import com.by.sdk.ands.custom.GCustomFactory;
import com.by.sdk.ands.custom.GCustomInitLoader;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public abstract class BasePlatformLoader<T extends BaseAdLoader,K extends IAdLoadListener> implements IPlatformLoader<T> {
    private Context context;
    protected T adLoader;
    protected IAdLoadListener loadListener;
    private BYAdInfo gAdInfo;
    private IConCurrentLoadListener currentLoadListener;


    @Override
    public void init(T adLoader, BYAdInfo gAdInfo) {
        this.context = adLoader.getContext();
        this.adLoader = adLoader;
        this.loadListener = adLoader.getLoaderListener();
        this.gAdInfo = gAdInfo;
    }

    @Override
    public T getAdLoader() {
        return adLoader;
    }

    @Override
    public void setConCurrentLoadListener(IConCurrentLoadListener listener) {
        currentLoadListener = listener;
    }

    @Override
    public IConCurrentLoadListener getConCurrentLoadListener() {
        return currentLoadListener;
    }

    @Override
    public IAdLoadListener getLoaderListener() {
        return loadListener;
    }

    @Override
    public BYAdInfo getAdInfo() {
        return gAdInfo;
    }

    protected String getPlatform(){
        try {
            if (gAdInfo!=null && !TextUtils.isEmpty(gAdInfo.getInitCls())){
                GCustomInitLoader initLoader = GCustomFactory.getInitLoader(gAdInfo.getInitCls());
                if (initLoader!=null){
                    return initLoader.getPlatform();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void initADN(Map<String, Object> localMap, GCustomInitLoader.InitCallback callback){
        if (gAdInfo == null || TextUtils.isEmpty(gAdInfo.getInitCls())){
            if (callback != null) {
                callback.fail();  // 添加参数无效时的错误回调
            }
            return;
        }
        if (gAdInfo!=null && !TextUtils.isEmpty(gAdInfo.getInitCls())){
            try {
                String platform =null;
                if (TextUtils.isEmpty(platform)){
                    platform = gAdInfo.getInitCls();
                }
                if (!TextUtils.isEmpty(platform)){
                    GCustomInitLoader initLoader = GCustomFactory.getInitLoader(platform);
                    if (initLoader==null){
                        Class<?> aClass = Class.forName(gAdInfo.getInitCls());
                        Constructor<?> constructor = aClass.getConstructor();
                        constructor.setAccessible(true);
                        initLoader = (GCustomInitLoader) constructor.newInstance();
                        GCustomFactory.putInitLoader(platform,initLoader);
                    }
                    initLoader.setInitCallback(callback);
                    if (initLoader.getInitStatus()){
                        if (callback!=null){
                            callback.succ();
                            return;
                        }
                    }

                    if (localMap==null){
                        localMap = new HashMap<>();
                    }
                    initLoader.init(gAdInfo.getAppId(),gAdInfo.getAppKey(),localMap);

                }else {
                    // 平台标识获取失败时的错误回调
                    if (callback != null) {
                        callback.fail();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                if (callback != null) {
                    callback.fail();  // 添加异常情况下的错误回调
                }
            }
        }
    }


}
