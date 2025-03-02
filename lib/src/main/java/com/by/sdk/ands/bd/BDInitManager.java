package com.by.sdk.ands.bd;

import com.baidu.mobads.sdk.api.BDAdConfig;
import com.baidu.mobads.sdk.api.BDDialogParams;
import com.baidu.mobads.sdk.api.MobadsPermissionSettings;
import com.by.sdk.byad.GAdConfig;
import com.by.sdk.byad.GAdSdk;
import com.by.sdk.byad.utils.GContanst;
import com.by.sdk.ands.custom.GCustomInitLoader;

import java.util.Map;

public class BDInitManager extends GCustomInitLoader {
    private static final String TAG = "BDInitManager";
    @Override
    protected void initADN(String appId, String appKey, Map<String, Object> map) {
        try {
            BDAdConfig bdAdConfig = new BDAdConfig.Builder()
                    .setAppsid(appId)
                    .setDialogParams(new BDDialogParams.Builder()
                            .setDlDialogType(BDDialogParams.TYPE_BOTTOM_POPUP)
                            .setDlDialogAnimStyle(BDDialogParams.ANIM_STYLE_NONE)
                            .build())
                    .build(GAdSdk.getContext());

            bdAdConfig.init();
            GAdConfig.CustomController customController = GAdSdk.getConfig().customController();
            if (customController!=null){
                if (customController.isCanUsePersonalRecommend()){
                    MobadsPermissionSettings.setLimitPersonalAds(false);
                }else {
                    MobadsPermissionSettings.setLimitPersonalAds(true);
                }
                MobadsPermissionSettings.setPermissionReadDeviceID(customController.isCanUsePhoneState());
                MobadsPermissionSettings.setPermissionLocation(customController.isCanUseLocation());
                MobadsPermissionSettings.setPermissionStorage(customController.canUseStoragePermission());
                MobadsPermissionSettings.setPermissionAppList(customController.canReadInstalledPackages());
            }else {
                MobadsPermissionSettings.setPermissionReadDeviceID(true);
                MobadsPermissionSettings.setPermissionLocation(true);
                MobadsPermissionSettings.setPermissionStorage(true);
                MobadsPermissionSettings.setPermissionAppList(true);
            }

            callInitSucc();
        } catch(Throwable e) {
            callInitFail();
            e.printStackTrace();
        }
    }

    @Override
    public String getPlatform() {
        return GContanst.PLATFROM_BD;
    }
}
