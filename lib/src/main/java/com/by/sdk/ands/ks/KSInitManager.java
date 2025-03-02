package com.by.sdk.ands.ks;

import android.location.Location;

import com.by.sdk.byad.GAdSdk;
import com.by.sdk.byad.utils.GContanst;
import com.by.sdk.ands.custom.GCustomInitLoader;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsCustomController;
import com.kwad.sdk.api.SdkConfig;

import java.util.Map;

public class KSInitManager extends GCustomInitLoader {
    @Override
    protected void initADN(String appId, String appKey, Map<String, Object> map) {
        if (GAdSdk.getConfig().customController()!=null && GAdSdk.getConfig().customController().isCanUsePersonalRecommend()){
            KsAdSDK.setPersonalRecommend(true);
        }else {
            KsAdSDK.setPersonalRecommend(false);
        }
        SdkConfig.Builder builder = new SdkConfig.Builder();
        builder.appId(appId)
                .customController(new KsCustomController() {
                    @Override
                    public boolean canReadLocation() {
                        if (GAdSdk.getConfig().customController()!=null){
                            return GAdSdk.getConfig().customController().isCanUseLocation();
                        }
                        return super.canReadLocation();
                    }



                    @Override
                    public boolean canUsePhoneState() {
                        if (GAdSdk.getConfig().customController()!=null){
                            return GAdSdk.getConfig().customController().isCanUsePhoneState();
                        }
                        return super.canUsePhoneState();
                    }


                    @Override
                    public String getAndroidId() {
                        if (GAdSdk.getConfig().customController()!=null){
                            return GAdSdk.getConfig().customController().getAndroidId();
                        }
                        return super.getAndroidId();
                    }

                    @Override
                    public String getOaid() {
                        return super.getOaid();
                    }

                    @Override
                    public boolean canUseMacAddress() {
                        if (GAdSdk.getConfig().customController()!=null){
                            return GAdSdk.getConfig().customController().canUseMacAddress();
                        }
                        return super.canUseMacAddress();
                    }

                    

                    @Override
                    public boolean canUseNetworkState() {
                        if (GAdSdk.getConfig().customController()!=null){
                            return GAdSdk.getConfig().customController().canUseNetworkState();
                        }
                        return super.canUseNetworkState();
                    }

                    @Override
                    public boolean canUseStoragePermission() {
                        if (GAdSdk.getConfig().customController()!=null){
                            return GAdSdk.getConfig().customController().canUseStoragePermission();
                        }
                        return super.canUseStoragePermission();
                    }

                    @Override
                    public boolean canReadInstalledPackages() {
                        if (GAdSdk.getConfig().customController()!=null){
                            return GAdSdk.getConfig().customController().canReadInstalledPackages();
                        }
                        return super.canReadInstalledPackages();
                    }
                })
                .debug(GAdSdk.getConfig().enableDebug());

        try {
            KsAdSDK.init(GAdSdk.getContext(), builder.build());
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
        return GContanst.PLATFROM_KS;
    }
}
