package com.by.sdk.byad.bean;

public class BYAdInfo {
    /**
     * 目前只支持GDT、CSJ、BD、KS
     */
    private String platformName;
    private String appId;
    private String appKey;
    private String pid;


    private int priority;//优先级
    private String initCls;//初始化全类名
    private String cls;//自定义平台全类名

    private String custom_ext;//自定义额外数据，数据格式为json

    private int feedType=2;//信息流类型，1：自渲染，2：模板

    public int getFeedType() {
        return feedType;
    }

    public void setFeedType(int feedType) {
        this.feedType = feedType;
    }

    public String getInitCls() {
        return initCls;
    }

    public void setInitCls(String initCls) {
        this.initCls = initCls;
    }

    public String getCustom_ext() {
        return custom_ext;
    }

    public void setCustom_ext(String custom_ext) {
        this.custom_ext = custom_ext;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }



    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
