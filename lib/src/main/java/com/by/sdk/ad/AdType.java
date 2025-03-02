package com.by.sdk.ad;

public enum AdType {
    SPLASH(1), // 开屏
    BANNER(2), // 横幅 banner
    INTERSTITIAL(3), // 插屏
    REWARD(4), // 激励视频
    FEED(5) ;// 模板渲染信息流

    private int value;

    AdType(int value) {
        this.value = value;
    }

    public int value() { 
        return value;
    }
}
