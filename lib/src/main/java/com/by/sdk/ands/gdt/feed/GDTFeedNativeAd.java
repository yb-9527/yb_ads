package com.by.sdk.ands.gdt.feed;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.by.sdk.byad.utils.GContanst;
import com.by.sdk.byad.utils.LogUtil;
import com.by.sdk.ands.custom.feed.GCustomFeedNativeAd;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.MediaView;
import com.qq.e.ads.nativ.NativeADEventListener;
import com.qq.e.ads.nativ.NativeADMediaListener;
import com.qq.e.ads.nativ.NativeUnifiedADAppMiitInfo;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.ads.nativ.widget.NativeAdContainer;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;

import java.util.ArrayList;
import java.util.List;

public class GDTFeedNativeAd extends GCustomFeedNativeAd {
    private static final String TAG = "GDTFeedNativeAd";
    private final NativeUnifiedADData nativeUnifiedADData;

    public GDTFeedNativeAd(Context context, NativeUnifiedADData nativeUnifiedADData) {
        this.nativeUnifiedADData = nativeUnifiedADData;

        try {
            if (nativeUnifiedADData!=null){
                setTitle(nativeUnifiedADData.getTitle());
                setDescription(nativeUnifiedADData.getDesc());
                setActionText(nativeUnifiedADData.getButtonText());
//                setAdSource(nativeUnifiedADData.get());
                List<String> images = getImageList(nativeUnifiedADData);
                if (!images.isEmpty()){
                    setImageList(images);
                }

                int adPatternType = getAdPatternType(nativeUnifiedADData);
                setAdPatternType(adPatternType);

                int interactionType = getInteractionType(nativeUnifiedADData);
                setInteractionType(interactionType);

                //            setFromLogo();
                if (nativeUnifiedADData.getIconUrl()!=null){
                    setIconUrl(nativeUnifiedADData.getIconUrl());
                }
                if (nativeUnifiedADData.getAppMiitInfo()!=null){
                    DownloadInfo download = getDownloadInfo(nativeUnifiedADData);
                    setDownloadInfo(download);
                }

                MediaView gdtMediaView = new MediaView(context);
                VideoOption.Builder builder = new VideoOption.Builder();

                builder.setAutoPlayMuted(true);
                builder.setNeedCoverImage(true);
                builder.setNeedProgressBar(true);
                builder.setEnableDetailPage(true);//必须设置为false，否则广点通广告无法正常播放（跳转到详情页再返回到信息流页后，视频无法正常播放，同时点击其它广告日志提示点击过快）
                builder.setEnableUserControl(false);//必须设置为true，否则用户点击视频播放器无反应（不会播放视频）

                this.nativeUnifiedADData.bindMediaView(gdtMediaView, builder.build(), new NativeADMediaListener() {
                    @Override
                    public void onVideoInit() {

                    }

                    @Override
                    public void onVideoLoading() {

                    }

                    @Override
                    public void onVideoReady() {

                    }

                    @Override
                    public void onVideoLoaded(int i) {

                    }

                    @Override
                    public void onVideoStart() {

                    }

                    @Override
                    public void onVideoPause() {

                    }

                    @Override
                    public void onVideoResume() {

                    }

                    @Override
                    public void onVideoCompleted() {

                    }

                    @Override
                    public void onVideoError(AdError adError) {

                    }

                    @Override
                    public void onVideoStop() {

                    }

                    @Override
                    public void onVideoClicked() {

                    }
                });
                setNativeVideoView(gdtMediaView);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getImageList(NativeUnifiedADData nativeUnifiedADData) {
        List<String> list = new ArrayList<>();
        try {
            int adPatternType = nativeUnifiedADData.getAdPatternType();
            //广点通逻辑：两图（logo图片和展示图片）两文字（标题和内容）类型
            // 视频类型
            //一图两文字：一个logo图片、标题、内容
            //三图两文字：三个展示图片、标题、内容
            //两图两文字和视频类型，要从广点通的imgUrl中取图片
            if (adPatternType == AdPatternType.NATIVE_2IMAGE_2TEXT || adPatternType == AdPatternType.NATIVE_1IMAGE_2TEXT
                    || adPatternType == AdPatternType.NATIVE_VIDEO) {
                list.add(nativeUnifiedADData.getImgUrl());
            } else if (adPatternType == AdPatternType.NATIVE_3IMAGE) {
                list.addAll(nativeUnifiedADData.getImgList());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return list;
    }

    private DownloadInfo getDownloadInfo(NativeUnifiedADData nativeUnifiedADData) {
        NativeUnifiedADAppMiitInfo appMiitInfo = nativeUnifiedADData.getAppMiitInfo();
        DownloadInfo download = new DownloadInfo();
        download.setAppName(appMiitInfo.getAppName());
        download.setAppVersion(appMiitInfo.getVersionName());
        download.setDeveloperName(appMiitInfo.getAuthorName());
        download.setPrivacyUrl(appMiitInfo.getPrivacyAgreement());
        download.setPermissionUrl(appMiitInfo.getPermissionsUrl());
        download.setFunctionDescUrl(appMiitInfo.getDescriptionUrl());
        return download;
    }

    private int getInteractionType(NativeUnifiedADData nativeUnifiedADData) {
        try {
            if (nativeUnifiedADData!=null){
                return nativeUnifiedADData.isAppAd() ? GContanst.INTERACTION_TYPE_DOWNLOAD_APP : GContanst.INTERACTION_TYPE_NORMAL;
            }
        } catch(Throwable e) {
            e.printStackTrace();
        }
        return GContanst.INTERACTION_TYPE_NORMAL;
    }

    private int getAdPatternType(NativeUnifiedADData nativeUnifiedADData) {
        try {
            switch (nativeUnifiedADData.getAdPatternType()) {
                case AdPatternType.NATIVE_VIDEO:
                    return GContanst.NATIVE_VIDEO;
                case AdPatternType.NATIVE_1IMAGE_2TEXT:
                case AdPatternType.NATIVE_2IMAGE_2TEXT:
                    return GContanst.NATIVE_LARGE_IMG;
                case AdPatternType.NATIVE_3IMAGE:
                    return GContanst.NATIVE_GROUP_IMG;
                default:
                    return GContanst.NATIVE_LARGE_IMG;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return GContanst.NATIVE_LARGE_IMG;
    }

    @Override
    public void bindViewForInteraction(Activity activity, ViewGroup container, List<View> clickViews, List<View> creativeViews) {
        try {
            NativeAdContainer gdtNativeAdContainer = new NativeAdContainer(container.getContext());//NativeAdContainer必须为container父容器，否则点击无效
            ViewGroup parent = (ViewGroup) container.getParent();
            if (null != parent) {
                int index = parent.indexOfChild(container);
                ViewGroup.LayoutParams originalLayoutParams = container.getLayoutParams();
                ViewGroup.LayoutParams emptyLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                parent.removeView(container);
                gdtNativeAdContainer.setId(container.getId());
                gdtNativeAdContainer.addView(container, emptyLayoutParams);
                parent.addView(gdtNativeAdContainer, index, originalLayoutParams);
            }


            nativeUnifiedADData.setNativeAdEventListener(new NativeADEventListener() {
                @Override
                public void onADExposed() {
                    callAdExposure();
                }

                @Override
                public void onADClicked() {
                    callAdClicked();
                }

                @Override
                public void onADError(AdError adError) {
                    LogUtil.d(TAG,"GDT onADError: code="+adError.getErrorCode()+",msg="+adError.getErrorMsg());
                }

                @Override
                public void onADStatusChanged() {

                }
            });

            nativeUnifiedADData.bindAdToView(activity, gdtNativeAdContainer, null, clickViews);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
