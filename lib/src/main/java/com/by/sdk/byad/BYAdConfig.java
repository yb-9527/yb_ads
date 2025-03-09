package com.by.sdk.byad;

import androidx.annotation.NonNull;

public class BYAdConfig {
    public CustomController customController;
    private String appId;
    private boolean enableDebug;

    public String getAppId() {
        return appId;
    }

    public boolean enableDebug() {
        return enableDebug;
    }

    public CustomController customController() {
        return customController;
    }

    public BYAdConfig(Builder builder) {
        this.appId = builder.appId;
        this.enableDebug = builder.enableDebug;
        this.customController = builder.customController;
    }

    public static abstract class CustomController {

        public boolean isCanUsePersonalRecommend(){
            return true;
        }

        public boolean isCanUseLocation() {
            return true;
        }

        public boolean isCanUsePhoneState() {
            return true;
        }


        public boolean isCanUseImsi(){
            return true;
        }

        public boolean isCanUseWifiState() {
            return true;
        }

        public boolean isCanUseAndroidId() {
            return true;
        }

        public String getAndroidId() {
            return null;
        }

        public boolean canUseMacAddress() {
            return true;
        }


        public boolean canUseNetworkState() {
            return true;
        }

        public boolean canUseStoragePermission() {
            return true;
        }

        public boolean canReadInstalledPackages() {
            return true;
        }



    }


    public static class Builder {
        private String appId;
        private boolean enableDebug;
        private CustomController customController;

        public Builder setAppId(String appId){
            this.appId = appId;
            return this;
        }

        public Builder setEnableDebug(boolean enableDebug){
            this.enableDebug = enableDebug;
            return this;
        }

        public Builder customController(@NonNull CustomController arg1) {
            this.customController = arg1;
            return this;
        }
        public BYAdConfig build(){
            return new BYAdConfig(this);
        }
    }
}
