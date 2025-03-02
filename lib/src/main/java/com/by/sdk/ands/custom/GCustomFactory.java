package com.by.sdk.ands.custom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GCustomFactory {
    private static Map<String,GCustomInitLoader> initLoaderMap;
    static {
        initLoaderMap = new ConcurrentHashMap<>();
    }
    public static GCustomInitLoader getInitLoader(String platform){
        if (!initLoaderMap.containsKey(platform)){
            return null;
        }
        return initLoaderMap.get(platform);
    }

    public static void putInitLoader(String platform,GCustomInitLoader loader){
        if (!initLoaderMap.containsKey(platform)){
            initLoaderMap.put(platform,loader);
        }
    }
}
