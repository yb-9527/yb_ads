package com.by.sdk.ad;

import android.view.View;

public interface IAd {
    void setInteractionListener(InteractionListener listener);
    InteractionListener getInteractionListener();
    View getAdView();

    void setAdView(View adView);

    String getPlatform();
}
