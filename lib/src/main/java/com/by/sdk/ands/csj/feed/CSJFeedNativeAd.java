package com.by.sdk.ands.csj.feed;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bytedance.sdk.openadsdk.ComplianceInfo;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.by.sdk.byad.utils.BYAdContanst;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.feed.GCustomFeedNativeAd;

import java.util.ArrayList;
import java.util.List;

public class CSJFeedNativeAd extends GCustomFeedNativeAd {
    private static final String TAG = "CSJFeedNativeAd";
    private final TTFeedAd ttFeedAd;

    public CSJFeedNativeAd(TTFeedAd ttFeedAd) {
        this.ttFeedAd = ttFeedAd;
        try {
            if (ttFeedAd!=null){
                setTitle(ttFeedAd.getTitle());
                setDescription(ttFeedAd.getDescription());
                setActionText(ttFeedAd.getButtonText());
                setAdSource(ttFeedAd.getSource());
                List<TTImage> images = ttFeedAd.getImageList();
                if (images!=null && !images.isEmpty()){
                    List<String> imgList = getImgList(images);
                    setImageList(imgList);
                }

                int adPatternType = getAdPatternType(ttFeedAd);
                setAdPatternType(adPatternType);

                int interactionType = getInteractionType(ttFeedAd);
                setInteractionType(interactionType);

//                setFromLogo(ttFeedAd.getAdLogo());
                if (ttFeedAd.getIcon()!=null){
                    setIconUrl(ttFeedAd.getIcon().getImageUrl());
                }
                if (ttFeedAd.getComplianceInfo()!=null){
                    DownloadInfo download = getDownloadInfo(ttFeedAd);
                    setDownloadInfo(download);
                }

                setNativeVideoView(ttFeedAd.getAdView());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindViewForInteraction(Activity activity, ViewGroup container, List<View> clickViews, List<View> creativeViews) {
        if (ttFeedAd!=null){
            ttFeedAd.registerViewForInteraction(container, clickViews, creativeViews, new TTNativeAd.AdInteractionListener() {
                @Override
                public void onAdClicked(View view, TTNativeAd ttNativeAd) {
                    LogUtil.d(TAG,"CSJ onAdClicked");
                    callAdClicked();
                }

                @Override
                public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {
//                    LogUtil.d(TAG,"onAdCreativeClick");
                    this.onAdClicked(view,ttNativeAd);
                }

                @Override
                public void onAdShow(TTNativeAd ttNativeAd) {
                    LogUtil.d(TAG,"CSJ onAdShow");
                    callAdExposure();
                }
            });
        }
    }



    private static @NonNull List<String> getImgList(List<TTImage> images) {
        List<String> imgList = new ArrayList<>();
        try {
            for (int i = 0; i < images.size(); i++) {
                TTImage ttImage = images.get(i);
                if (ttImage!=null){
                    imgList.add(ttImage.getImageUrl());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgList;
    }

    private static int getInteractionType(TTFeedAd ttFeedAd) {
        int interactionType= BYAdContanst.INTERACTION_TYPE_NORMAL;
        switch (ttFeedAd.getInteractionType()){
            case TTAdConstant.INTERACTION_TYPE_BROWSER:
            case TTAdConstant.INTERACTION_TYPE_LANDING_PAGE:
            case TTAdConstant.INTERACTION_TYPE_DIAL:
                interactionType = BYAdContanst.INTERACTION_TYPE_NORMAL;
                break;
            case TTAdConstant.INTERACTION_TYPE_DOWNLOAD:
                interactionType = BYAdContanst.INTERACTION_TYPE_DOWNLOAD_APP;
                break;
        }
        return interactionType;
    }

    private static int getAdPatternType(TTFeedAd ttFeedAd) {
        int adPatternType = BYAdContanst.NATIVE_LARGE_IMG;
        switch (ttFeedAd.getImageMode()) {
            case TTAdConstant.IMAGE_MODE_VIDEO:
            case TTAdConstant.IMAGE_MODE_VIDEO_VERTICAL:
                adPatternType = BYAdContanst.NATIVE_VIDEO;
                break;
            case TTAdConstant.IMAGE_MODE_LARGE_IMG:
                adPatternType = BYAdContanst.NATIVE_LARGE_IMG;
                break;
            case TTAdConstant.IMAGE_MODE_SMALL_IMG:
                adPatternType = BYAdContanst.NATIVE_SMALL_IMG;
                break;
            case TTAdConstant.IMAGE_MODE_GROUP_IMG:
                adPatternType = BYAdContanst.NATIVE_GROUP_IMG;
                break;
        }
        return adPatternType;
    }

    private static @NonNull DownloadInfo getDownloadInfo(TTFeedAd ttFeedAd) {
        ComplianceInfo complianceInfo = ttFeedAd.getComplianceInfo();
        DownloadInfo download = new DownloadInfo();
        download.setAppName(complianceInfo.getAppName());
        download.setAppVersion(complianceInfo.getAppVersion());
        download.setDeveloperName(complianceInfo.getDeveloperName());
        download.setPrivacyUrl(complianceInfo.getPrivacyUrl());
        download.setPermissionUrl(complianceInfo.getPermissionUrl());
        download.setFunctionDescUrl(complianceInfo.getFunctionDescUrl());
        return download;
    }
}
