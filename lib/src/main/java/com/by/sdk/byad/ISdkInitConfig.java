package com.by.sdk.byad;

import android.content.Context;

import java.util.HashMap;

public interface ISdkInitConfig {
    void init(Context context, String appId, String appkey, HashMap<String, Object> customMap);
}
