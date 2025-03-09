package com.by.sdk.ands.ks;

import com.by.sdk.byad.BYAdSdk;
import com.by.sdk.byad.utils.BYAdContanst;
import com.by.sdk.ands.custom.GCustomInitLoader;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsCustomController;
import com.kwad.sdk.api.SdkConfig;

import java.util.Map;

public class KSInitManager extends GCustomInitLoader {
    @Override
    protected void initADN(String appId, String appKey, Map<String, Object> map) {
        if (BYAdSdk.getConfig().customController()!=null && BYAdSdk.getConfig().customController().isCanUsePersonalRecommend()){
            KsAdSDK.setPersonalRecommend(true);
        }else {
            KsAdSDK.setPersonalRecommend(false);
        }
        SdkConfig.Builder builder = new SdkConfig.Builder();
        builder.appId(appId)
                .customController(new KsCustomController() {
                    @Override
                    public boolean canReadLocation() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().isCanUseLocation();
                        }
                        return super.canReadLocation();
                    }



                    @Override
                    public boolean canUsePhoneState() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().isCanUsePhoneState();
                        }
                        return super.canUsePhoneState();
                    }


                    @Override
                    public String getAndroidId() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().getAndroidId();
                        }
                        return super.getAndroidId();
                    }

                    @Override
                    public String getOaid() {
                        return super.getOaid();
                    }

                    @Override
                    public boolean canUseMacAddress() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().canUseMacAddress();
                        }
                        return super.canUseMacAddress();
                    }

                    

                    @Override
                    public boolean canUseNetworkState() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().canUseNetworkState();
                        }
                        return super.canUseNetworkState();
                    }

                    @Override
                    public boolean canUseStoragePermission() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().canUseStoragePermission();
                        }
                        return super.canUseStoragePermission();
                    }

                    @Override
                    public boolean canReadInstalledPackages() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().canReadInstalledPackages();
                        }
                        return super.canReadInstalledPackages();
                    }
                })
                .debug(BYAdSdk.getConfig().enableDebug());

        try {
            KsAdSDK.init(BYAdSdk.getContext(), builder.build());
        } catch (Exception e) {
            e.printStackTrace();
            callInitFail();
            return;
        }

        try {
            KsAdSDK.start();
            callInitSucc();
        } catch(Throwable e) {
            callInitFail();
            e.printStackTrace();
        }
    }

    @Override
    public String getPlatform() {
        return BYAdContanst.PLATFROM_KS;
    }
}
