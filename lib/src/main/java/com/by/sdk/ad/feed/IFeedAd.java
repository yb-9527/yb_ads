package com.by.sdk.ad.feed;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.by.sdk.ad.IAd;
import com.by.sdk.ands.custom.feed.GCustomFeedNativeAd;

import java.util.List;

public interface IFeedAd extends IAd {
    /**
     * 获取标题
     * @return
     */
    String getTitle();

    /**
     * 描述
     * @return
     */
    String getDescription();

    /**
     * 获取引导行动语
     * @return
     */
    String getActionText();

    /**
     * 获取来源
     * @return
     */
    String getAdSource();

    /**
     * 获取图片列表
     * @return
     */
    List<String> getImageList();

    /**
     * 获取广告形式 图片、视频
     * @return
     */
    int getAdPatternType();

    /**
     * 获取交互类型 ,0:NORMAL, 1:
     * @return
     */
    int getInteractionType();

    /**
     * 获取广告来源Logo
     * @return
     */
    String getFromLogo();


    /**
     * 广告图标
     * @return
     */
    String getIconUrl();
    /**
     * 获取下载六要素
     * @return
     */
    GCustomFeedNativeAd.DownloadInfo getDownloadInfo();

    /**
     * 获取模板View
     * @return
     */
    View getExpressView();

    /**
     * 获取自渲染视频View
     * @return
     */
    View getNativeVideoView();
    /**
     * 设置自渲染视频监听
     * @param mediaListener 视频监听
     */
    void setVideoAdListener(FeedAdMediaListener mediaListener);

    /**
     *
     * @param activity  广告将要展示所在的activity
     * @param container 渲染广告最外层的ViewGroup
     * @param clickViews    可点击的View的列表
     * @param creativeViews 用于下载或者拨打电话的View
     */
    void bindViewForInteraction(Activity activity, ViewGroup container, List<View> clickViews, List<View> creativeViews);

    /**
     * 模板类型渲染
     */
    void render(Activity activity);

    /**
     * 视频时长
     * @return
     */
    long getVideoDuration();


    void setExpressAdInteractionListener(FeedExpressAdInteractionListener listener);

    FeedExpressAdInteractionListener getExpressAdInteractionListener();

    void setNativeAdInteractionListener(FeedInteractionListener nativeListener);

    FeedInteractionListener getNativeAdInteractionListener();

    boolean isFeedExpress();
}
