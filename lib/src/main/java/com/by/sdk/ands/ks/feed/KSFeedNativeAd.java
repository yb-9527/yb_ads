package com.by.sdk.ands.ks.feed;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.by.sdk.byad.utils.BYAdContanst;
import com.by.sdk.ands.custom.feed.GCustomFeedNativeAd;
import com.kwad.sdk.api.KsAdVideoPlayConfig;
import com.kwad.sdk.api.KsImage;
import com.kwad.sdk.api.KsNativeAd;
import com.kwad.sdk.api.model.AdSourceLogoType;
import com.kwad.sdk.api.model.InteractionType;
import com.kwad.sdk.api.model.MaterialType;

import java.util.ArrayList;
import java.util.List;

public class KSFeedNativeAd extends GCustomFeedNativeAd {
    private final Context context;
    private final KsNativeAd ksNativeAd;

    public KSFeedNativeAd(Context context, KsNativeAd ksNativeAd) {
        this.context = context;
        this.ksNativeAd = ksNativeAd;

        try {
            if (ksNativeAd!=null){
                setTitle(ksNativeAd.getAppName());
                setDescription(ksNativeAd.getAdDescription());
                setActionText(ksNativeAd.getActionDescription());
                setAdSource(ksNativeAd.getAdSource());
                List<String> imgList = getImgList(ksNativeAd);
                if (!imgList.isEmpty()){
                    setImageList(imgList);
                }

                int adPatternType = getAdPatternType(ksNativeAd);
                setAdPatternType(adPatternType);

                int interactionType = getInteractionType(ksNativeAd);
                setInteractionType(interactionType);

                setFromLogo(ksNativeAd.getAdSourceLogoUrl(AdSourceLogoType.NORMAL));
                if (ksNativeAd.getAppIconUrl()!=null){
                    setIconUrl(ksNativeAd.getAppIconUrl());
                }
                DownloadInfo download = getDownloadInfo(ksNativeAd);
                setDownloadInfo(download);

                setNativeVideoView(ksNativeAd.getVideoView(context,new KsAdVideoPlayConfig.Builder()
                        .videoSoundEnable(false)
                        .build()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindViewForInteraction(Activity activity, ViewGroup container, List<View> clickViews, List<View> creativeViews) {
        try {
            if (ksNativeAd!=null){
                ksNativeAd.registerViewForInteraction(activity, container, clickViews, new KsNativeAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, KsNativeAd ksNativeAd) {
                        callAdClicked();
                    }

                    @Override
                    public void onAdShow(KsNativeAd ksNativeAd) {
                        callAdExposure();
                    }

                    @Override
                    public boolean handleDownloadDialog(DialogInterface.OnClickListener onClickListener) {
                        return false;
                    }

                    @Override
                    public void onDownloadTipsDialogShow() {

                    }

                    @Override
                    public void onDownloadTipsDialogDismiss() {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DownloadInfo getDownloadInfo(KsNativeAd ksNativeAd) {
        DownloadInfo download = new DownloadInfo();
        download.setAppName(ksNativeAd.getAppName());
        download.setAppVersion(ksNativeAd.getAppVersion());
        download.setDeveloperName(ksNativeAd.getCorporationName());
        download.setPrivacyUrl(ksNativeAd.getAppPrivacyUrl());
        download.setPermissionUrl(ksNativeAd.getPermissionInfoUrl());
        download.setFunctionDescUrl(ksNativeAd.getIntroductionInfo());

        return download;
    }

    private int getInteractionType(KsNativeAd ksNativeAd) {
        try {
            if (ksNativeAd.getInteractionType() == InteractionType.DOWNLOAD) {
                return BYAdContanst.INTERACTION_TYPE_DOWNLOAD_APP;
            }
        } catch(Throwable e) {
            e.printStackTrace();
        }
        // H5 和 UNKNOWN 都是 NORMAL
        return BYAdContanst.INTERACTION_TYPE_NORMAL;
    }

    private int getAdPatternType(KsNativeAd ksNativeAd) {
        try {
            switch (ksNativeAd.getMaterialType()) {
                case MaterialType.SINGLE_IMG:
                    return BYAdContanst.NATIVE_LARGE_IMG;
                case MaterialType.GROUP_IMG:
                    return BYAdContanst.NATIVE_GROUP_IMG;
                case MaterialType.VIDEO:
                    return BYAdContanst.NATIVE_VIDEO;
                default:
                    return BYAdContanst.NATIVE_LARGE_IMG;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BYAdContanst.NATIVE_LARGE_IMG;
    }

    private List<String> getImgList(KsNativeAd ksNativeAd) {
        List<String> imgList = new ArrayList<>();
        try {
            List<KsImage> images = ksNativeAd.getImageList();
            KsImage videoCoverImage = ksNativeAd.getVideoCoverImage();
            if (videoCoverImage!=null&& !TextUtils.isEmpty(videoCoverImage.getImageUrl())){
                imgList.add(videoCoverImage.getImageUrl());
            }
            if (images!=null && !images.isEmpty()){
                for (int i = 0; i < images.size(); i++) {
                    KsImage ksImage = images.get(i);
                    if (ksImage!=null){
                        imgList.add(ksImage.getImageUrl());
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imgList;
    }
}
