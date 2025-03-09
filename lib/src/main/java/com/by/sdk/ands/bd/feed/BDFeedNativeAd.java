package com.by.sdk.ands.bd.feed;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mobads.sdk.api.NativeResponse;
import com.baidu.mobads.sdk.api.XNativeView;
import com.by.sdk.byad.utils.BYAdContanst;
import com.by.sdk.ands.custom.feed.GCustomFeedNativeAd;

import java.util.ArrayList;
import java.util.List;

public class BDFeedNativeAd extends GCustomFeedNativeAd {

    private final NativeResponse nativeResponse;

    public BDFeedNativeAd(Context context,NativeResponse nativeResponse) {
        this.nativeResponse = nativeResponse;

        try {
            if (nativeResponse!=null){
                setTitle(nativeResponse.getTitle());
                setDescription(nativeResponse.getDesc());
                setActionText(nativeResponse.getActButtonString());
//                setAdSource(nativeResponse.get());
                List<String> imgList = getImgList(nativeResponse);
                setImageList(imgList);

                int adPatternType = getAdPatternType(nativeResponse);
                setAdPatternType(adPatternType);

                int interactionType = getInteractionType(nativeResponse);
                setInteractionType(interactionType);
                setFromLogo(nativeResponse.getBaiduLogoUrl());
                if (nativeResponse.getIconUrl()!=null){
                    setIconUrl(nativeResponse.getIconUrl());
                }
                DownloadInfo download = getDownloadInfo(nativeResponse);
                setDownloadInfo(download);

                setNativeVideoView(getVideoView(context,nativeResponse));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View getVideoView(Context context, NativeResponse nativeResponse) {
        XNativeView xNativeView = new XNativeView(context);
        xNativeView.setNativeItem(nativeResponse);
        xNativeView.setVideoMute(true);

        xNativeView.post(new Runnable() {
            @Override
            public void run() {
                xNativeView.render();
            }
        });
        return xNativeView;
    }

    private DownloadInfo getDownloadInfo(NativeResponse nativeResponse) {
        DownloadInfo download = new DownloadInfo();
//        download.setAppName(nativeResponse.getap());
        download.setAppVersion(nativeResponse.getAppVersion());
        download.setDeveloperName(nativeResponse.getPublisher());
        download.setPrivacyUrl(nativeResponse.getAppPrivacyLink());
        download.setPermissionUrl(nativeResponse.getAppPermissionLink());
        download.setFunctionDescUrl(nativeResponse.getAppFunctionLink());
        return download;
    }

    private int getInteractionType(NativeResponse nativeResponse) {
        int type = BYAdContanst.INTERACTION_TYPE_NORMAL;
        switch (nativeResponse.getAdActionType()){
            case NativeResponse.ACTION_TYPE_APP_DOWNLOAD:
                type =  BYAdContanst.INTERACTION_TYPE_DOWNLOAD_APP;
                break;
            case NativeResponse.ACTION_TYPE_DEEP_LINK:
            case NativeResponse.ACTION_TYPE_LANDING_PAGE:
                type = BYAdContanst.INTERACTION_TYPE_NORMAL;
                break;
        }
        return type;
    }

    private int getAdPatternType(NativeResponse nativeResponse) {
        switch (nativeResponse.getMaterialType()) {
            case VIDEO:
                return BYAdContanst.NATIVE_VIDEO;
            case NORMAL:
                if (nativeResponse.getMultiPicUrls() != null) {
                    return BYAdContanst.NATIVE_GROUP_IMG;
                } else if ((nativeResponse.getIconUrl() != null ? nativeResponse.getIconUrl() : "").equals(nativeResponse.getImageUrl())) {
                    return BYAdContanst.NATIVE_SMALL_IMG;
                } else {
                    return BYAdContanst.NATIVE_LARGE_IMG;
                }
            default:
                return  BYAdContanst.NATIVE_LARGE_IMG;
        }
    }

    private List<String> getImgList(NativeResponse nativeResponse) {

        List<String> imgList = new ArrayList<>();
        try {
            if (nativeResponse !=null){
                if (!TextUtils.isEmpty(nativeResponse.getImageUrl())) {
                    imgList.add(nativeResponse.getImageUrl());
                }
                List<String> list = nativeResponse.getMultiPicUrls();
                if (list != null && !list.isEmpty()) {
                    imgList.addAll(list);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return imgList;
    }

    @Override
    public void bindViewForInteraction(Activity activity, ViewGroup container, List<View> clickViews, List<View> creativeViews) {
        if (nativeResponse!=null){
            nativeResponse.registerViewForInteraction(container, clickViews, creativeViews, new NativeResponse.AdInteractionListener() {
                @Override
                public void onAdClick() {
                    callAdClicked();
                }

                @Override
                public void onADExposed() {
                    callAdExposure();
                }

                @Override
                public void onADExposureFailed(int i) {

                }

                @Override
                public void onADStatusChanged() {

                }

                @Override
                public void onAdUnionClick() {

                }
            });
        }
    }
}
