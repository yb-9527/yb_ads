package com.by.sdk.byad.adpaster;

public interface IConCurrentLoadListener {
    void onLoadedSuccess(Object t);

    void onLoadedError(int code,String msg);

    void onRenderSuccess(Object t);

    void onRenderError(int code,String msg);
}
