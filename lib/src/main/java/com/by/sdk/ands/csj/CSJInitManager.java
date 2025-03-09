package com.by.sdk.ands.csj;


import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTCustomController;
import com.by.sdk.byad.BYAdSdk;
import com.by.sdk.byad.utils.BYAdContanst;
import com.by.sdk.ands.custom.GCustomInitLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class CSJInitManager extends GCustomInitLoader {

    private String personalTypeValue="1";
    @Override
    protected void initADN(String appId, String appKey, Map<String, Object> map) {
        try {
            TTAdSdk.init(BYAdSdk.getContext(),buildConfig(appId));
            TTAdSdk.start(new TTAdSdk.Callback() {
                @Override
                public void success() {
                    callInitSucc();
                }

                @Override
                public void fail(int i, String s) {
                    callInitFail();
                }
            });
        } catch (Exception e) {
            callInitFail();
            e.printStackTrace();
        }
    }

    private TTAdConfig buildConfig(String appId) {
        /**
         * 不传或传空或传非01值没任何影响,默认不屏蔽
         *
         * 0，屏蔽个性化推荐广告；
         *
         * 1，不屏蔽个性化推荐广告
         */
        if (BYAdSdk.getConfig().customController()!=null){
            if (BYAdSdk.getConfig().customController().isCanUsePersonalRecommend()){
                personalTypeValue="1";
            }else {
                personalTypeValue="0";
            }
        }

//        LogUtil.e(TAG,"personalTypeValue="+personalTypeValue);
        TTAdConfig.Builder builder = new TTAdConfig.Builder();

        builder.appId(appId)
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .data(getData(personalTypeValue))
                .supportMultiProcess(false)//是否支持多进程
                .customController(new TTCustomController() {
                    @Override
                    public String getDevOaid() {
                        return super.getDevOaid();
                    }

                    @Override
                    public boolean isCanUseLocation() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().isCanUseLocation();
                        }
                        return true;
                    }


                    @Override
                    public boolean isCanUsePhoneState() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().isCanUsePhoneState();
                        }
                        return super.isCanUsePhoneState();
                    }



                    @Override
                    public boolean alist() {

                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().canReadInstalledPackages();
                        }
                        return super.alist();
                    }

                    @Override
                    public boolean isCanUseWifiState() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().isCanUseWifiState();
                        }
                        return  super.isCanUseWifiState();

                    }

                    @Override
                    public boolean isCanUseWriteExternal() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().canUseStoragePermission();
                        }

                        return super.isCanUseWriteExternal();

                    }

                    @Override
                    public boolean isCanUseAndroidId() {
                        if (BYAdSdk.getConfig().customController()!=null){
                            return BYAdSdk.getConfig().customController().isCanUseAndroidId();
                        }
                        return super.isCanUseAndroidId();
                    }


                });
        builder.debug(BYAdSdk.getConfig().enableDebug()); //测试阶段打开，可以通过日志排查问题，上线时去除该调用
        return builder.build();
    }

    private String getData(String personalTypeValue) {
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject personalObject = new JSONObject();
            personalObject.put( "name" ,  "personal_ads_type" );
            personalObject.put( "value" , personalTypeValue);
            jsonArray.put(personalObject);
            return jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "" ;
    }

    @Override
    public String getPlatform() {
        return BYAdContanst.PLATFROM_CSJ;
    }
}
