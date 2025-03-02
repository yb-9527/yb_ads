package com.by.sdk.byad;

import androidx.annotation.NonNull;

public class GAdSlot {
    private String pid;
    /**
     * 广告加载所需的Json数据
     */
    private String adJsonData;

    /**
     * 设置请求广告的宽高,单位px
     * @param width
     * @param height
     */
    private int width;

    private int height;

    /**
     * 针对广点通是否全屏
     */
    private boolean isSplashFullScreen;

    /**
     * 激励视频是否静音
     */
    public boolean isMute = true;

    /**
     * 信息流加载个数，默认1
     */
    private int fetchCount = 1;



    public String getPid() {
        return pid;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean getIsSplashFullScreen() {
        return isSplashFullScreen;
    }

    public boolean getIsMute() {
        return isMute;
    }

    public int getFetchCount() {
        return fetchCount;
    }

    public String getAdJsonData() {
        return adJsonData;
    }

    public GAdSlot(Builder builder) {
        this.pid = builder.pid;
        this.width = builder.width;
        this.height = builder.height;
        this.isSplashFullScreen = builder.isSplashFullScreen;
        this.isMute = builder.isMute;
        this.fetchCount = builder.fetchCount;
        this.adJsonData = builder.adJsonData;
    }


    public static class Builder {

        /**
         * 广告加载所需的Json数据
         */
        private String adJsonData;
        private String pid;

        private int width;

        private int height;

        /**
         * 针对广点通是否全屏
         */
        private boolean isSplashFullScreen;

        /**
         * 激励视频是否静音
         */
        public boolean isMute = true;


        /**
         * 信息流加载个数，默认1
         */
        private int fetchCount = 1;

        public Builder() {
        }

        /**
         * pid
         */
        public Builder setPid(@NonNull String arg1) {
            this.pid = arg1;
            return this;
        }

        /**
         * width
         */
        public Builder setWidth(@NonNull int arg1) {
            this.width = arg1;
            return this;
        }

        /**
         * height
         */
        public Builder setHeight(@NonNull int arg1) {
            this.height = arg1;
            return this;
        }

        /**
         * height
         */
        public Builder setIsSplashFullScreen(@NonNull boolean arg1) {
            this.isSplashFullScreen = arg1;
            return this;
        }

        /**
         * isMute
         */
        public Builder setIsMute(@NonNull boolean arg1) {
            this.isMute = arg1;
            return this;
        }

        /**
         * fetchCount
         */
        public Builder setFetchCount(@NonNull int arg1) {
            this.fetchCount = arg1;
            return this;
        }

        /**
         * adJsonStr
         */
        public Builder setAdJsonData(@NonNull String arg1) {
            this.adJsonData = arg1;
            return this;
        }


        /**
         * 构建MsAdSlot
         */
        public GAdSlot build() {
            return new GAdSlot(this);
        }
    }
}
