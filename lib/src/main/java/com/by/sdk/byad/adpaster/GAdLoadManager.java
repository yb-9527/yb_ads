package com.by.sdk.byad.adpaster;

import android.content.Context;
import android.text.TextUtils;

import com.by.sdk.byad.bean.GAdInfo;
import com.by.sdk.byad.bean.GroupBean;
import com.by.sdk.byad.error.ErrorCodeUtil;
import com.by.sdk.byad.utils.MyThreadPools;
import com.by.sdk.byad.utils.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GAdLoadManager {
    private static final String TAG = "GAdLoadManager";
    private Context context;
    private final BaseAdLoader adLoader;
    private static final int MAX_CONCURRENT_LOADS = 3;


    private final AtomicReference<GroupBean> successfulAd = new AtomicReference<>();
    private final AtomicReference<Boolean> isAdLoadComplete = new AtomicReference<>(false);
    private final List<GroupBean> successfulAds = Collections.synchronizedList(new ArrayList<>());
    private final List<Boolean> priorityProcessed = Collections.synchronizedList(new ArrayList<>());
    private final List<GroupBean> groupBeanList = Collections.synchronizedList(new ArrayList<>());
    private final LinkedList<GroupBean> linkedList = new LinkedList<>();
    // 记录加载失败的数量
    final AtomicReference<Integer> failedCount = new AtomicReference<>(0);



    public GAdLoadManager(Context context, BaseAdLoader adLoader) {
        this.context = context;
        this.adLoader = adLoader;
    }

    public synchronized void filterAd(List<GAdInfo> adList){
        List<GAdInfo> groupList = getGroupList(adList);

        if (!groupList.isEmpty()){
            startLoadAd(groupList);
        }
    }



    private synchronized void startLoadAd(List<GAdInfo> sortedList) {
        // 先将adInfoList转换为GroupBean列表

        for (GAdInfo adInfo : sortedList) {
            GroupBean groupBean = new GroupBean();
            groupBean.setAdInfo(adInfo);
            groupBeanList.add(groupBean);
        }

        // 按优先级排序（从高到低）
        Collections.sort(groupBeanList, new Comparator<GroupBean>() {
            @Override
            public int compare(GroupBean o1, GroupBean o2) {
                return o2.getAdInfo().getPriority() - o1.getAdInfo().getPriority();
            }
        });


        // 记录每个优先级是否已经处理完成
        for (int i = 0; i < groupBeanList.size(); i++) {
            priorityProcessed.add(false);
        }
        linkedList.clear();
        linkedList.addAll(groupBeanList);

        loadNext();
    }

    private synchronized boolean isAllPreviousFailed(int currentIndex, List<Boolean> priorityProcessed) {
        for (int i = 0; i < currentIndex; i++) {
            if (!priorityProcessed.get(i)) {
                return false;
            }
        }
        return true;
    }

    private synchronized void currentLoadAd(GroupBean groupBean, int currentIndex) {
        IPlatformLoader platformLoader = adLoader.createPlatformLoader(context, groupBean.getAdInfo());
        platformLoader.setConCurrentLoadListener(new IConCurrentLoadListener() {
            @Override
            public void onLoadedSuccess(Object t) {
                try {
                    LogUtil.d(TAG,"LoadedSucc");
                    groupBean.setLoadedAd(t);

                    // 如果当前广告成功加载且是目前最高优先级的广告，直接使用它并结束流程
                    if (currentIndex == 0 || isAllPreviousFailed(currentIndex, priorityProcessed) || isBestPriority(groupBean.getAdInfo().getPriority())) {
                        successfulAd.compareAndSet(null, groupBean);
                        //1.最优返回
                        handleLoadFinish();
                        return;
                    }

                    successfulAds.add(groupBean);
                    priorityProcessed.set(currentIndex, true);
                    loadNext();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadedError(int code, String msg) {
                try {
                    priorityProcessed.set(currentIndex, true);
                    failedCount.set(failedCount.get() + 1);

                    // 如果所有广告都加载失败了
                    if (failedCount.get() == groupBeanList.size()) {
                        //3.所有失败
                        handleLoadFinish();
                        return;
                    }
                    loadNext();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRenderSuccess(Object t) {
                // 如果是最终选中的广告,处理渲染成功回调
                try {
                    LogUtil.d(TAG,"RenderSucc");
                    GroupBean currentSuccessfulAd = successfulAd.get();
                    groupBean.setRenderedAd(t);
//                    handleLoadFinish();
                    if(currentSuccessfulAd != null && groupBean.equals(currentSuccessfulAd)) {
                        if (adLoader!=null){
                            adLoader.getLoaderListener().onRenderSuccess(t);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRenderError(int code, String msg) {
                // 如果是最终选中的广告,处理渲染失败回调
                try {
                    GroupBean currentSuccessfulAd = successfulAd.get();
                    groupBean.setRenderFail(true);
                    groupBean.setErrorCode(code);
                    groupBean.setErrorMsg(msg);
                    if(currentSuccessfulAd != null && groupBean.equals(currentSuccessfulAd)) {
                        if (adLoader!=null){
                            adLoader.getLoaderListener().onRenderError(code,msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        platformLoader.loadAd(adLoader.getLocalMap());
    }

    private boolean isBestPriority(int priority) {
        try {
            for (int i = 0; i < groupBeanList.size(); i++) {
                GroupBean groupBean = groupBeanList.get(i);
                if (groupBean.getAdInfo().getPriority()>priority){
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 如果还没有加载完毕，那么继续加载
     */
    private synchronized void loadNext() {
        try {
            if (isAdLoadComplete.get()){
                return;
            }
            boolean isLoadComplete=true;
            for (int i = 0; i < priorityProcessed.size(); i++) {
                if (!priorityProcessed.get(i)){
                    isLoadComplete=false;
                }
            }
            /**
             * 4.所有广告处理完毕，成功/失败
             */
            if (isLoadComplete){
                handleLoadFinish();
            }else {
                for (int i = 0; i < MAX_CONCURRENT_LOADS; i++) {
                    if (!linkedList.isEmpty()){
                        GroupBean groupBean = linkedList.removeFirst();
                        final int currentIndex = groupBeanList.indexOf(groupBean);

                        MyThreadPools.getInstance().execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    currentLoadAd(groupBean,currentIndex);
                                } catch (Exception e) {
                                    LogUtil.d(TAG,"load error,msg="+e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //所有的都加载失败
    private void onAllAdLoadFailed() {
        try {
            isAdLoadComplete.compareAndSet(false,true);
            if (adLoader!=null){
                adLoader.getLoaderListener().onLoadedError(ErrorCodeUtil.AD_LOAD_ERR,"广告加载失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized List<GAdInfo> getGroupList(List<GAdInfo> adList){
        if (adList==null || adList.isEmpty()){
            return new ArrayList<>();
        }
        try {

            List<GAdInfo> gAdInfos = new ArrayList<>();
            //1.按优先级分组，1-100，100最高
            // 过滤掉appId或pid为空的数据
            for (int i = 0; i < adList.size(); i++) {
                GAdInfo ad = adList.get(i);
                if (ad==null){
                    continue;
                }
                if (!TextUtils.isEmpty(ad.getAppId()) && !TextUtils.isEmpty(ad.getPid())){
                    gAdInfos.add(ad);
                }
            }

            return gAdInfos;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }



    /**
     * 处理超时逻辑
     */
    public void handleTimeout() {
        //2.超时
        handleLoadFinish();
    }

    /**
     * 结束条件
     * 1.最优返回
     * 2.超时
     * 3.所有失败
     * 4.所有广告处理完毕，成功/失败
     */
    private void handleLoadFinish() {
        try {
            if (isAdLoadComplete.get()){
                return;
            }
            // 如果没有找到合适的广告，从成功列表中选择优先级最高的
            if (successfulAd.get() == null && !successfulAds.isEmpty()) {
                Collections.sort(successfulAds, new Comparator<GroupBean>() {
                    @Override
                    public int compare(GroupBean o1, GroupBean o2) {
                        return o2.getAdInfo().getPriority() - o1.getAdInfo().getPriority();
                    }
                });
                successfulAd.compareAndSet(null, successfulAds.get(0));
            }

            // 处理所有广告加载失败的情况
            if (successfulAd.get() == null) {
                onAllAdLoadFailed();
            }else {
                if (adLoader!=null){
                    GroupBean groupBean = successfulAd.get();
                    adLoader.getLoaderListener().onLoadedSuccess(groupBean.getLoadedAd());

                    try {
                        if (groupBean.getRenderedAd()!=null){
                            adLoader.getLoaderListener().onRenderSuccess(groupBean.getRenderedAd());
                        }

                        if (groupBean.isRenderFail()){
                            adLoader.getLoaderListener().onRenderError(groupBean.getErrorCode(),groupBean.getErrorMsg());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    isAdLoadComplete.compareAndSet(false,true);
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
            if (!isAdLoadComplete.get()){
                onAllAdLoadFailed();
            }

        }

    }
}
