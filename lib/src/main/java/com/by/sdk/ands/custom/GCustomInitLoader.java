package com.by.sdk.ands.custom;

import java.util.Map;

public abstract class GCustomInitLoader {

    private volatile  boolean isInitSucc;
    private InitCallback initCallback;

    public void init(String appId, String appKey, Map<String, Object> map){
        try {
            initADN(appId,appKey,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void initADN(String appId, String appKey, Map<String, Object> map);


    public abstract String getPlatform();

    protected synchronized void callInitSucc(){
        isInitSucc = true;
        if (initCallback!=null){
            initCallback.succ();
        }
    }

    protected synchronized void callInitFail(){
        isInitSucc = false;
        if (initCallback!=null){
            initCallback.fail();
        }
    }

    public boolean getInitStatus(){
        return isInitSucc;
    }

    public interface InitCallback{
        void succ();
        void fail();
    }

    public synchronized void setInitCallback(InitCallback initCallback){
        this.initCallback = initCallback;
    }
}
