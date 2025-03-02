package com.by.sdk.byad.bean;

public class GroupBean {
    private GAdInfo adInfo;

    private Object loadedAd;
    private Object renderedAd;
    private boolean isRenderFail;
    private int errorCode;
    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isRenderFail() {
        return isRenderFail;
    }

    public void setRenderFail(boolean renderFail) {
        isRenderFail = renderFail;
    }

    public GAdInfo getAdInfo() {
        return adInfo;
    }

    public void setAdInfo(GAdInfo adInfo) {
        this.adInfo = adInfo;
    }

    public Object getLoadedAd() {
        return loadedAd;
    }

    public void setLoadedAd(Object loadedAd) {
        this.loadedAd = loadedAd;
    }


    public Object getRenderedAd() {
        return renderedAd;
    }

    public void setRenderedAd(Object renderedAd) {
        this.renderedAd = renderedAd;
    }
}
