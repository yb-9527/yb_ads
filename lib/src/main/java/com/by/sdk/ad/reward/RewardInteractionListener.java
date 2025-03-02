package com.by.sdk.ad.reward;

import com.by.sdk.ad.InteractionListener;

import java.util.Map;

public interface RewardInteractionListener extends InteractionListener {
    void onReward(Map<String,Object> map);
}
