package com.by.sdk.ands.ks.feed;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.by.sdk.ands.custom.feed.GCustomFeedExpressAd;
import com.kwad.sdk.api.KsFeedAd;

public class KSFeedExpressAd extends GCustomFeedExpressAd {
    private final Context context;
    private final KsFeedAd ksFeedAd;

    public KSFeedExpressAd(Context context,KsFeedAd ksFeedAd) {
        this.context = context;
        this.ksFeedAd = ksFeedAd;
    }

    @Override
    public void render(Activity activity) {
        try {
            if (ksFeedAd!=null){
                ksFeedAd.setAdInteractionListener(new KsFeedAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked() {
                        callAdClicked();
                    }

                    @Override
                    public void onAdShow() {
                        callAdExposure();
                    }

                    @Override
                    public void onDislikeClicked() {
                        callAdClosed();
                    }

                    @Override
                    public void onDownloadTipsDialogShow() {

                    }

                    @Override
                    public void onDownloadTipsDialogDismiss() {

                    }
                });
                ksFeedAd.render(new KsFeedAd.AdRenderListener() {
                    @Override
                    public void onAdRenderSuccess(View view) {
                        callRenderSuccess();
                    }

                    @Override
                    public void onAdRenderFailed(int i, String s) {
                        callRenderError(i,s);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getExpressView() {
        try {
            if (ksFeedAd!=null){
                return ksFeedAd.getFeedView(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
