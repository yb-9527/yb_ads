package com.by.sdk.ad.feed;

public interface FeedAdMediaListener {
    void onVideoLoaded();

    void onVideoStart();

    void onVideoPause();

    void onVideoCompleted();

    void onVideoError();

    void onVideoResume();

    void onProgressUpdate(long current,long duration);
}
