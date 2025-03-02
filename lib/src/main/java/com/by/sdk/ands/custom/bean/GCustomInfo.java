package com.by.sdk.ands.custom.bean;

public class GCustomInfo {
    private String app_id;
    private String app_key;
    private String pid;
    private boolean isExpress =true;
    private int fetchCount =1;
    private String custom_ext;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isExpress() {
        return isExpress;
    }

    public void setExpress(boolean express) {
        isExpress = express;
    }

    public int getFetchCount() {
        return fetchCount;
    }

    public void setFetchCount(int fetchCount) {
        this.fetchCount = fetchCount;
    }

    public String getCustom_ext() {
        return custom_ext;
    }

    public void setCustom_ext(String custom_ext) {
        this.custom_ext = custom_ext;
    }
}
