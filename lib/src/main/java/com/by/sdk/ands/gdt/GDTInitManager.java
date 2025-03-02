package com.by.sdk.ands.gdt;

import com.by.sdk.byad.GAdSdk;
import com.by.sdk.byad.utils.GContanst;
import com.by.sdk.ands.custom.GCustomInitLoader;
import com.qq.e.comm.managers.GDTAdSdk;
import com.qq.e.comm.managers.setting.GlobalSetting;

import java.util.Map;

public class GDTInitManager extends GCustomInitLoader {
    @Override
    protected void initADN(String appId, String appKey, Map<String, Object> map) {


        try {
            if (GAdSdk.getConfig().customController()!=null){

                /**
                 * 1	屏蔽个性化推荐广告
                 * 0、所有非1的值	不屏蔽个性化推荐广告
                 */
                if (GAdSdk.getConfig().customController().isCanUsePersonalRecommend()){
                    GlobalSetting.setPersonalizedState(0);
                }else {
                    GlobalSetting.setPersonalizedState(1);
                }
                boolean canUsePhoneState = GAdSdk.getConfig().customController().isCanUsePhoneState();
                GlobalSetting.setAgreePrivacyStrategy(canUsePhoneState);

                GlobalSetting.setEnableCollectAppInstallStatus(GAdSdk.getConfig().customController().canReadInstalledPackages());
            }


            GDTAdSdk.initWithoutStart(GAdSdk.getContext(),appId);
            GDTAdSdk.start(new GDTAdSdk.OnStartListener() {
                @Override
                public void onStartSuccess() {
                    callInitSucc();
                }

                @Override
                public void onStartFailed(Exception e) {
                    callInitFail();
                }
            });
        } catch (Exception e) {
            callInitFail();
            e.printStackTrace();
        }


    }

    @Override
    public String getPlatform() {
        return GContanst.PLATFROM_GDT;
    }
}
