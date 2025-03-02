package com.by.sdk.ands.custom.feed;

import android.view.View;

import com.by.sdk.ad.feed.FeedAd;
import com.by.sdk.ad.feed.FeedAdMediaListener;

import java.util.List;

public abstract class GCustomFeedNativeAd extends FeedAd {

    public GCustomFeedNativeAd() {
    }

    @Override
    public boolean isFeedExpress() {
        return false;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
    }

    public void setAdSource(String adSource) {
        this.adSource = adSource;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public void setAdPatternType(int adPatternType) {
        this.adPatternType = adPatternType;
    }

    public void setInteractionType(int interactionType) {
        this.interactionType = interactionType;
    }

    public void setFromLogo(String fromLogo) {
        this.fromLogo = fromLogo;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setDownloadInfo(GCustomFeedNativeAd.DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    public void setNativeVideoView(View nativeVideoView) {
        this.nativeVideoView = nativeVideoView;
    }

    public void setMediaListener(FeedAdMediaListener mediaListener) {
        this.mediaListener = mediaListener;
    }

    public void setVideoDuration(long videoDuration) {
        this.videoDuration = videoDuration;
    }

    public static class DownloadInfo{
        private String appName;//获取AppName
        private String appVersion;//获取应用版本
        private String developerName;//获取开发者名称
        private String privacyUrl;//获取隐私协议
        private String permissionUrl;//获取权限列表url
        private String functionDescUrl;//获取应用功能url

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getDeveloperName() {
            return developerName;
        }

        public void setDeveloperName(String developerName) {
            this.developerName = developerName;
        }

        public String getPrivacyUrl() {
            return privacyUrl;
        }

        public void setPrivacyUrl(String privacyUrl) {
            this.privacyUrl = privacyUrl;
        }

        public String getPermissionUrl() {
            return permissionUrl;
        }

        public void setPermissionUrl(String permissionUrl) {
            this.permissionUrl = permissionUrl;
        }

        public String getFunctionDescUrl() {
            return functionDescUrl;
        }

        public void setFunctionDescUrl(String functionDescUrl) {
            this.functionDescUrl = functionDescUrl;
        }
    }


    public void callAdExposure(){
        if (proxyAd!=null){
            proxyAd.onAdExposure();
        }
    }

    public void callAdClicked(){
        if (proxyAd!=null){
            proxyAd.onAdClicked();
        }
    }

    public void callAdClosed(){
        if (proxyAd!=null){
            proxyAd.onAdClosed();
        }
    }

}
