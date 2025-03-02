package com.by.sdk.byad.utils;

import com.by.sdk.ands.custom.CustomPlatform;

public class PlatfromFactory {

    private static CustomPlatform customPlatform;
    static {
        customPlatform = new CustomPlatform();
    }


    public static CustomPlatform getCustomPlatfrom(){
        return customPlatform;
    }
}
