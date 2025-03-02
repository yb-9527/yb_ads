package com.by.sdk.ands.ks.reward;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;

import com.by.sdk.byad.GAdSlot;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.ands.custom.bean.GCustomInfo;
import com.by.sdk.ands.custom.reward.GCustomRewardAdapter;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsRewardVideoAd;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsVideoPlayConfig;

import java.util.List;
import java.util.Map;

public class KSRewardAdapter extends GCustomRewardAdapter {

    private KsRewardVideoAd ksRewardVideoAd;
    private boolean isMute;

    @Override
    public void showAd(Activity activity) {
        try {
            if (ksRewardVideoAd!=null){
                KsVideoPlayConfig config = new KsVideoPlayConfig.Builder().videoSoundEnable(!isMute).build();
                ksRewardVideoAd.showRewardVideoAd(activity,config);
            }
        } catch (Exception e) {
            callRenderError(ErrorCodeUtil.AD_RENDER_ERR,"ks render err");
            e.printStackTrace();
        }
    }

    @Override
    public void loadCustomAd(Context context, GAdSlot adSlot, GCustomInfo info) {
        isMute = adSlot.getIsMute();
        KsScene scene = new KsScene.Builder(Long.parseLong(info.getPid())).build();
//        KsScene scene = new KsScene.Builder(90009001).build();

        KsAdSDK.getLoadManager().loadRewardVideoAd(scene, new KsLoadManager.RewardVideoAdListener() {
            @Override
            public void onError(int i, String s) {
                callLoadedError(i,s);
            }

            public void onRewardVideoResult(@Nullable List<KsRewardVideoAd> list) {

            }

            public void onRequestResult(int i) {

            }

            @Override
            public void onRewardVideoAdLoad(@Nullable List<KsRewardVideoAd> adList) {
                if (adList!=null && !adList.isEmpty()){
                    ksRewardVideoAd = adList.get(0);
                    ksRewardVideoAd.setRewardAdInteractionListener(new KsRewardVideoAd.RewardAdInteractionListener() {
                        @Override
                        public void onAdClicked() {
                            callAdClicked();
                        }

                        @Override
                        public void onPageDismiss() {
                            callAdClosed();
                        }

                        @Override
                        public void onVideoPlayError(int i, int i1) {

                        }

                        @Override
                        public void onVideoPlayEnd() {

                        }

                        @Override
                        public void onVideoSkipToEnd(long l) {

                        }

                        @Override
                        public void onVideoPlayStart() {
                            callAdExposure();
                        }

                        @Override
                        public void onRewardVerify() {

                        }

                        @Override
                        public void onRewardVerify(Map<String, Object> map) {
                            callReward(map);
                        }

                        @Override
                        public void onRewardStepVerify(int i, int i1) {

                        }

                        @Override
                        public void onExtraRewardVerify(int i) {

                        }
                    });

                    callLoadedSuccess();
                    callRenderSuccess();
                }
            }
        });
    }

    @Override
    public void destroy() {

    }
}
