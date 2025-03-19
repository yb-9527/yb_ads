# 持续更新!!!!

简单易接入的聚合广告sdk,接入参考demohttps://github.com/yb-9527/yb_demo

# 一、添加依赖

参考demo引入穿山甲、百度、广点通、快手sdk，然后添加下面的依赖

```Java
implementation(name: 'by_sdk_1.0.0.0_release', ext: 'aar') // by


implementation(name: 'Baidu_MobAds_SDK-release_v9.371', ext: 'aar') // 百度
implementation(name: 'open_ad_sdk_6.6.0.7', ext: 'aar') // 穿山甲
implementation(name: 'GDTSDK.unionNormal.4.611.1481', ext: 'aar') // 广点通
implementation(name: 'ks_adsdk-ad-3.3.72-publishRelease', ext: 'aar') // 快手
```

# 二、添加权限

```Java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />

<uses-permission android:name="android.permission.QUERY_ALL_PACKAGES" />
```

# 三、网络相关

添加以下配置以支持http

在Application Module的AndroidManifest的application标签中增加：

```Java
<application...
    android:usesCleartextTraffic="true"...>...</application>
```

# 四、FileProvider

csj_file_path.xml

```java
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-files-path name="external_files_path" path="Download" />
    <external-path name="tt_external_download" path="Download" />
    <files-path name="tt_internal_file_download" path="Download" />
    <cache-path name="tt_internal_cache_download" path="Download" />
    <external-path name="tt_external_root" path="." />

</paths>

```

gdt_file_path.xml

```java
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
     <!-- 这个下载路径也不可以修改，必须为com_qq_e_download -->
     <external-cache-path
         name="gdt_sdk_download_path"
         path="com_qq_e_download" />
     <external-cache-path
         name="gdt_sdk_download_path1"
         path="com_qq_e_download" />
     <cache-path
         name="gdt_sdk_download_path2"
         path="com_qq_e_download" />
     <root-path
         name="root"
         path="." />
     <cache-path
         name="cache"
         path="." />
     <external-path
         name="external"
         path="." />
     <external-cache-path
         name="external_cache_path"
         path="." />
     <external-path
         name="external_files"
         path="."/>
     <external-path path="mimoDownload" name="files_root" />
     <external-path path="." name="external_storage_root" />
</paths>

```



```Java
<!-- 穿山甲 begin -->
<provider
    android:name="com.bytedance.sdk.openadsdk.TTFileProvider"
    android:authorities="${applicationId}.TTFileProvider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/csj_file_path" />
</provider>
<provider
    android:name="com.bytedance.sdk.openadsdk.multipro.TTMultiProvider"
    android:authorities="${applicationId}.TTMultiProvider"
    android:exported="false" /> 
    <!-- 穿山甲 end -->
    
    
<service
    android:name="com.qq.e.comm.DownloadService"
    android:exported="false" /> <!-- 请开发者注意字母的大小写，ADActivity，而不是AdActivity -->
<activity
    android:name="com.qq.e.ads.ADActivity"
    android:configChanges="keyboard|keyboardHidden|orientation|screenSize" />
<activity
    android:name="com.qq.e.ads.PortraitADActivity"
    android:configChanges="keyboard|keyboardHidden|orientation|screenSize"
    android:screenOrientation="portrait" />
<activity
    android:name="com.qq.e.ads.LandscapeADActivity"
    android:configChanges="keyboard|keyboardHidden|orientation|screenSize"
    android:screenOrientation="sensorLandscape" />

<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/gdt_file_path" />
</provider>
```

# 五、jsonData格式

数据说明

| 参数名       | 类型   | 说明                                 |
| ------------ | ------ | ------------------------------------ |
| platformName | String | *目前只支持**GDT**、CSJ、**BD**、KS* |
| appId        | String | 三方广告的appId                      |
| pid          | String | 三方广告的pid                        |
| priority     | int    | 优先级1-10，数字越大优先级越高       |
| feedType     | int    | 信息流格式，1：自渲染，2：模板       |

```Java
[
    {
        "appId": "5038235",
        "platformName":"CSJ",
        "pid": "838235262",
        "priority": 1
    },
    {
        "appId": "1101152570",
        "platformName":"GDT",
        "pid": "9093517612222759",
        "priority": 1
    },
    {
        "appId": "e866cfb0",
        "platformName":"BD",
        "pid": "2058622",
        "priority": 1
    },
    {
        "appId": "90009",
        "platformName":"KS",
        "pid": "4000000042",
        "priority": 2
    }
]
```

# 六、广告类型
* [开屏](https://github.com/yb-9527/yb_ads/wiki/%E5%BC%80%E5%B1%8F)
* [插屏](https://github.com/yb-9527/yb_ads/wiki/%E6%8F%92%E5%B1%8F)
* [激励视频](https://github.com/yb-9527/yb_ads/wiki/%E6%BF%80%E5%8A%B1%E8%A7%86%E9%A2%91)
* [信息流](https://github.com/yb-9527/yb_ads/wiki/%E4%BF%A1%E6%81%AF%E6%B5%81)
* [Banner](https://github.com/yb-9527/yb_ads/wiki/Banner)

# 七、技术支持
![img.png](https://github.com/yb-9527/yb_ads/blob/master/img/5a76b3c30e45b78ff5a39901ffd369f.jpg?raw=true =300x200)