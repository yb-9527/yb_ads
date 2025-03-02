package com.by.sdk.ands.gdt.feed;

import android.app.Activity;
import android.view.View;

import com.by.sdk.ands.custom.feed.GCustomFeedExpressAd;
import com.qq.e.ads.nativ.NativeExpressADView;

public class GDTFeedExpressAd extends GCustomFeedExpressAd {
    private final NativeExpressADView nativeExpressADView;

    public GDTFeedExpressAd(NativeExpressADView nativeExpressADView) {
        this.nativeExpressADView = nativeExpressADView;
    }

    @Override
    public void render(Activity activity) {
        if (nativeExpressADView!=null){
            nativeExpressADView.render();
        }
    }

    @Override
    public View getExpressView() {
        return nativeExpressADView;
    }
}
