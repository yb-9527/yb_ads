package com.by.sdk.byad.adpaster;

public interface IAdLoadListener<T> {

    void onLoadedSuccess(T t);

    void onLoadedError(int code,String msg);

    void onRenderSuccess(T t);

    void onRenderError(int code,String msg);
}
