package com.by.sdk.ands.gdt;

import com.by.sdk.byad.BYAdSdk;
import com.by.sdk.byad.utils.BYAdContanst;
import com.by.sdk.ands.custom.GCustomInitLoader;
import com.qq.e.comm.managers.GDTAdSdk;
import com.qq.e.comm.managers.setting.GlobalSetting;

import java.util.Map;

public class GDTInitManager extends GCustomInitLoader {
    @Override
    protected void initADN(String appId, String appKey, Map<String, Object> map) {


        try {
            if (BYAdSdk.getConfig().customController()!=null){

                /**
                 * 1	屏蔽个性化推荐广告
                 * 0、所有非1的值	不屏蔽个性化推荐广告
                 */
                if (BYAdSdk.getConfig().customController().isCanUsePersonalRecommend()){
                    GlobalSetting.setPersonalizedState(0);
                }else {
                    GlobalSetting.setPersonalizedState(1);
                }
                boolean canUsePhoneState = BYAdSdk.getConfig().customController().isCanUsePhoneState();
                GlobalSetting.setAgreePrivacyStrategy(canUsePhoneState);

                GlobalSetting.setEnableCollectAppInstallStatus(BYAdSdk.getConfig().customController().canReadInstalledPackages());
            }


            GDTAdSdk.initWithoutStart(BYAdSdk.getContext(),appId);
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
        return BYAdContanst.PLATFROM_GDT;
    }
}
