package com.by.sdk.ad.feed;

import java.util.List;

public interface FeedAdListener  {
    void onLoadedSuccess(List<IFeedAd> adList);

    void onLoadedError(int code,String msg);
}
