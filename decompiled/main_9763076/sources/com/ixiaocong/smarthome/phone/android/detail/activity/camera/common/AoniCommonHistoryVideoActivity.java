package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.TimeZoneUtil;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.CalendarSelcetDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonReplay;
import com.ixiaocong.smarthome.phone.android.detail.adater.AoniCommonHistoryPagerAdapter;
import com.ixiaocong.smarthome.phone.android.detail.adater.AoniCommonHistoryVideoAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.CameraCommonPlayStreamModel;
import com.xiaocong.smarthome.httplib.model.CameraCommonStreamListModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.timerRuler.TimeRulerView;
import com.xiaocong.smarthome.timerRuler.bean.TimeSlot;
import com.xiaocong.smarthome.timerRuler.listener.OnBarMoveListener;
import com.xiaocong.smarthome.timerRuler.utils.DateUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniCommonHistoryVideoActivity extends XcBaseActivity implements View.OnClickListener, CommonTypeCallback, EditDialogCallback {
    private boolean isContains = false;
    private CalendarSelcetDialog mCalendarDialog;
    private LinearLayout mDateLayout;
    private String mDeviceId;
    private CameraCommonReplay mHistoryReplay;
    private ImageView mIvBack;
    private String mProductId;
    private TimeRulerView mRulerView;
    private RecyclerView mRvVideo;
    private CameraCommonStreamListModel mStreamListModel;
    private CameraCommonPlayStreamModel mStreamModel;
    private TabLayout mTabSelect;
    private List<TimeSlot> mTimeSolts;
    private List<String> mTitles;
    private TextView mTvCloudStorage;
    private TextView mTvHistoryHint;
    private TextView mTvSelectDate;
    private AoniCommonHistoryVideoAdapter mVideoAdapter;
    private ViewPager mVpSelect;

    protected int getLayoutId() {
        return R.layout.activity_aoni_common_history_video;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvCloudStorage = (TextView) $(R.id.tv_aoni_common_history_cloud_storage);
        this.mTvSelectDate = (TextView) $(R.id.tv_aoni_common_history_select_date);
        this.mTvHistoryHint = (TextView) $(R.id.tv_common_camera_history_video_hint);
        this.mTabSelect = (TabLayout) $(R.id.tl_aoni_common_history_tab);
        this.mVpSelect = (ViewPager) $(R.id.vp_aoni_common_hitroty_pager);
        this.mDateLayout = (LinearLayout) $(R.id.ll_aoni_common_history_select_date);
        this.mRulerView = $(R.id.tr_common_camera_history_ruler);
        this.mHistoryReplay = (CameraCommonReplay) $(R.id.ccr_common_history_replay);
        this.mRvVideo = (RecyclerView) $(R.id.rv_common_camera_history_video);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.mActivity);
        layoutManager.setOrientation(0);
        this.mRvVideo.setLayoutManager(layoutManager);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mProductId = getIntent().getStringExtra("productId");
        this.mHistoryReplay.initReplay(this.mActivity, "历史录像");
        this.mHistoryReplay.setTypeCallback(AoniCommonHistoryVideoActivity$$Lambda$1.lambdaFactory$(this));
        this.mTvSelectDate.setText(TimeZoneUtil.timeToString(Long.valueOf(System.currentTimeMillis()), 3));
        this.mTimeSolts = new ArrayList();
        long endTime = System.currentTimeMillis();
        long beginTime = TimeZoneUtil.stringToTime(TimeZoneUtil.timeToString(Long.valueOf(System.currentTimeMillis()), 3), 8).longValue();
        loadStream(beginTime, endTime, true);
    }

    protected void onResume() {
        super.onResume();
        if (!this.mHistoryReplay.isPlaying()) {
            this.mHistoryReplay.start();
        }
    }

    private void loadStream(long beginTime, long endTime, final boolean isRecycler) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("beginTime", beginTime + Constants.MAIN_VERSION_TAG);
        params.put("endTime", endTime + Constants.MAIN_VERSION_TAG);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/getVideoList");
        httpSetting.setHttpTimeout(20000);
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonHistoryVideoActivity.1
            public void onComplete(XCResponseBean var1) {
                AoniCommonHistoryVideoActivity.this.mStreamListModel = (CameraCommonStreamListModel) JSON.parseObject(var1.getData(), CameraCommonStreamListModel.class);
                if (AoniCommonHistoryVideoActivity.this.mStreamListModel != null) {
                    if (AoniCommonHistoryVideoActivity.this.mStreamListModel.getNoService() == 0) {
                        if (AoniCommonHistoryVideoActivity.this.mStreamListModel.getList() != null && AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().size() != 0) {
                            AoniCommonHistoryVideoActivity.this.mTvHistoryHint.setVisibility(4);
                            AoniCommonHistoryVideoActivity.this.mHistoryReplay.setVisibleActionIcon(true);
                            AoniCommonHistoryVideoActivity.this.mVideoAdapter.setNewData(AoniCommonHistoryVideoActivity.this.mStreamListModel.getList());
                            AoniCommonHistoryVideoActivity.this.mVideoAdapter.notifyDataSetChanged();
                            AoniCommonHistoryVideoActivity.this.mTimeSolts.clear();
                            for (int i = 0; i < AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().size(); i++) {
                                AoniCommonHistoryVideoActivity.this.mTimeSolts.add(new TimeSlot(DateUtils.getTodayStart(Long.parseLong(((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().get(i)).getBeginTime())), Long.parseLong(((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().get(i)).getBeginTime()), Long.parseLong(((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().get(i)).getEndTime())));
                                XcLogger.e("timeSolts", DateUtils.getTodayStart(Long.parseLong(((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().get(i)).getBeginTime())) + " , " + Long.parseLong(((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().get(i)).getBeginTime()) + " , " + Long.parseLong(((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().get(i)).getEndTime()));
                            }
                            AoniCommonHistoryVideoActivity.this.mRulerView.setVedioTimeSlot(AoniCommonHistoryVideoActivity.this.mTimeSolts);
                            AoniCommonHistoryVideoActivity.this.mRulerView.setCurrentTimeMillis(Long.parseLong(((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().get(0)).getBeginTime()));
                            if (isRecycler) {
                                if (AoniCommonHistoryVideoActivity.this.mStreamListModel.getNoService() == 1) {
                                    ToastUtils.showShort(AoniCommonHistoryVideoActivity.this.mActivity, AoniCommonHistoryVideoActivity.this.mStreamListModel.getNoServicePrompt());
                                    return;
                                }
                                AoniCommonHistoryVideoActivity.this.mRvVideo.setVisibility(0);
                                AoniCommonHistoryVideoActivity.this.mRulerView.setVisibility(4);
                                AoniCommonHistoryVideoActivity.this.getStreamUrl(((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().get(0)).getBeginTime(), ((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().get(0)).getEndTime(), 0L, true);
                                return;
                            }
                            AoniCommonHistoryVideoActivity.this.mRvVideo.setVisibility(8);
                            AoniCommonHistoryVideoActivity.this.mRulerView.setVisibility(0);
                            AoniCommonHistoryVideoActivity.this.selectWheelVideo(Long.parseLong(((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mStreamListModel.getList().get(0)).getBeginTime()));
                            return;
                        }
                        HttpLoadingHelper.getInstance().dismissProcessLoading();
                        AoniCommonHistoryVideoActivity.this.mTvHistoryHint.setVisibility(0);
                        AoniCommonHistoryVideoActivity.this.mHistoryReplay.setVisibleActionIcon(false);
                        AoniCommonHistoryVideoActivity.this.mTvHistoryHint.setText("暂未查询到当天历史录像");
                        return;
                    }
                    HttpLoadingHelper.getInstance().dismissProcessLoading();
                    AoniCommonHistoryVideoActivity.this.mTvHistoryHint.setVisibility(0);
                    AoniCommonHistoryVideoActivity.this.mHistoryReplay.setVisibleActionIcon(false);
                    AoniCommonHistoryVideoActivity.this.mTvHistoryHint.setText(AoniCommonHistoryVideoActivity.this.mStreamListModel.getNoServicePrompt());
                    return;
                }
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniCommonHistoryVideoActivity.this.mRvVideo.setVisibility(8);
                AoniCommonHistoryVideoActivity.this.mRulerView.setVisibility(4);
                AoniCommonHistoryVideoActivity.this.mTvHistoryHint.setVisibility(0);
                AoniCommonHistoryVideoActivity.this.mHistoryReplay.setVisibleActionIcon(false);
                AoniCommonHistoryVideoActivity.this.mTvHistoryHint.setText("暂未查询到当天历史录像");
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniCommonHistoryVideoActivity.this.mHistoryReplay.setVisibleActionIcon(false);
                ToastUtils.showShort(AoniCommonHistoryVideoActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getStreamUrl(String beginTime, String endTime, final long selectTime, final boolean isRecycler) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/getPlayStreamUrl");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonHistoryVideoActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniCommonHistoryVideoActivity.this.mStreamModel = (CameraCommonPlayStreamModel) JSON.parseObject(var1.getData(), CameraCommonPlayStreamModel.class);
                if (AoniCommonHistoryVideoActivity.this.mStreamModel == null || AoniCommonHistoryVideoActivity.this.mStreamModel.getList() == null || AoniCommonHistoryVideoActivity.this.mStreamModel.getList().size() == 0) {
                    ToastUtils.showShort(AoniCommonHistoryVideoActivity.this.mActivity, "暂未发现该时间段内的历史录像");
                    AoniCommonHistoryVideoActivity.this.mHistoryReplay.setVisibleActionIcon(false);
                } else {
                    if (isRecycler) {
                        AoniCommonHistoryVideoActivity.this.mHistoryReplay.setVisibleActionIcon(true);
                        AoniCommonHistoryVideoActivity.this.mHistoryReplay.replayVideo(((CameraCommonPlayStreamModel.PlayStreamModel) AoniCommonHistoryVideoActivity.this.mStreamModel.getList().get(0)).getPlayUrl(), 0);
                        AoniCommonHistoryVideoActivity.this.mRulerView.setCurrentTimeMillis(((CameraCommonPlayStreamModel.PlayStreamModel) AoniCommonHistoryVideoActivity.this.mStreamModel.getList().get(0)).getBeginTime());
                        return;
                    }
                    AoniCommonHistoryVideoActivity.this.selectWheelPlayVideo(AoniCommonHistoryVideoActivity.this.mStreamModel.getList(), selectTime);
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniCommonHistoryVideoActivity.this.mHistoryReplay.setVisibleActionIcon(false);
                ToastUtils.showShort(AoniCommonHistoryVideoActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectWheelPlayVideo(List<CameraCommonPlayStreamModel.PlayStreamModel> list, long selectTime) {
        for (int i = 0; i < list.size(); i++) {
            long endTime = Long.valueOf(list.get(i).getEndTime()).longValue();
            long beginTime = Long.valueOf(list.get(i).getBeginTime()).longValue();
            if (selectTime < endTime && selectTime >= beginTime) {
                XcLogger.e("rulerWheelView", "selectWheelPlayVideo,endTime=" + endTime + ",beginTime=" + beginTime + ",selectTime= " + selectTime);
                this.mHistoryReplay.replayVideo(list.get(i).getPlayUrl(), -1);
                this.mRulerView.setCurrentTimeMillis(list.get(i).getBeginTime());
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectWheelVideo(long selectTime) {
        if (this.mVideoAdapter.getData().size() > 0) {
            for (int i = 0; i < this.mVideoAdapter.getData().size(); i++) {
                long endTime = Long.valueOf(((CameraCommonStreamListModel.CommonStreamModel) this.mVideoAdapter.getData().get(i)).getEndTime()).longValue();
                long beginTime = Long.valueOf(((CameraCommonStreamListModel.CommonStreamModel) this.mVideoAdapter.getData().get(i)).getBeginTime()).longValue();
                if (selectTime < endTime && selectTime >= beginTime) {
                    this.isContains = true;
                    XcLogger.e("rulerWheelView", "selectWheelVideo,endTime=" + endTime + ",beginTime=" + beginTime + ",selectTime=" + selectTime);
                    getStreamUrl(String.valueOf(beginTime), String.valueOf(endTime), selectTime, false);
                    break;
                } else {
                    this.isContains = false;
                    if (this.mHistoryReplay.isPlaying()) {
                        this.mHistoryReplay.pause();
                        XcLogger.e("rulerWheelView", "selectWheelVideo,pause");
                    }
                }
            }
            if (!this.isContains) {
                ToastUtils.showShort(this.mActivity, "没有当前时间的视频录像");
            }
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void initAdapter() {
        super.initAdapter();
        this.mTitles = new ArrayList();
        this.mTitles.add("缩略图");
        this.mTitles.add("时间轴");
        AoniCommonHistoryPagerAdapter historyAdapter = new AoniCommonHistoryPagerAdapter(this.mTitles);
        this.mVpSelect.setAdapter(historyAdapter);
        this.mTabSelect.setupWithViewPager(this.mVpSelect);
        this.mVpSelect.setCurrentItem(0);
        this.mVideoAdapter = new AoniCommonHistoryVideoAdapter(this.mActivity);
        this.mRvVideo.setAdapter(this.mVideoAdapter);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mDateLayout.setOnClickListener(this);
        this.mTvCloudStorage.setOnClickListener(this);
        this.mTabSelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonHistoryVideoActivity.3
            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                XcLogger.i("rulerWheelView", "onTabSelected---" + AoniCommonHistoryVideoActivity.this.mRulerView.getCurrentTimeMillis() + ",selectTime=" + TimeZoneUtil.timeToString(Long.valueOf(AoniCommonHistoryVideoActivity.this.mRulerView.getCurrentTimeMillis()), 4));
                if (AoniCommonHistoryVideoActivity.this.mVideoAdapter.getData().size() > 0) {
                    AoniCommonHistoryVideoActivity.this.mTvHistoryHint.setVisibility(4);
                    if (tab.getPosition() == 1) {
                        AoniCommonHistoryVideoActivity.this.mRulerView.setVisibility(0);
                        AoniCommonHistoryVideoActivity.this.mRvVideo.setVisibility(8);
                        return;
                    } else {
                        if (tab.getPosition() == 0) {
                            AoniCommonHistoryVideoActivity.this.mRulerView.setVisibility(4);
                            AoniCommonHistoryVideoActivity.this.mRvVideo.setVisibility(0);
                            return;
                        }
                        return;
                    }
                }
                AoniCommonHistoryVideoActivity.this.mRulerView.setVisibility(4);
                AoniCommonHistoryVideoActivity.this.mTvHistoryHint.setVisibility(0);
                AoniCommonHistoryVideoActivity.this.mRvVideo.setVisibility(8);
            }

            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        this.mRvVideo.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonHistoryVideoActivity.4
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                if (AoniCommonHistoryVideoActivity.this.mStreamListModel != null) {
                    if (AoniCommonHistoryVideoActivity.this.mStreamListModel.getNoService() == 1) {
                        ToastUtils.showShort(AoniCommonHistoryVideoActivity.this.mActivity, AoniCommonHistoryVideoActivity.this.mStreamListModel.getNoServicePrompt());
                    } else {
                        AoniCommonHistoryVideoActivity.this.getStreamUrl(((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mVideoAdapter.getData().get(position)).getBeginTime(), ((CameraCommonStreamListModel.CommonStreamModel) AoniCommonHistoryVideoActivity.this.mVideoAdapter.getData().get(position)).getEndTime(), 0L, true);
                    }
                }
            }
        });
        this.mRulerView.setOnBarMoveListener(new OnBarMoveListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonHistoryVideoActivity.5
            public void onDragBar(boolean isLeftDrag, long currentTime) {
                XcLogger.e("OnBarMoveListener", "onDragBar,isLeftDrag=" + isLeftDrag + ",currentTime=" + TimeZoneUtil.timeToString(Long.valueOf(currentTime), 2));
            }

            public void onBarMoving(long currentTime) {
                XcLogger.e("OnBarMoveListener", "onBarMoving,currentTime=" + TimeZoneUtil.timeToString(Long.valueOf(currentTime), 2));
            }

            public void onBarMoveFinish(long currentTime) {
                AoniCommonHistoryVideoActivity.this.selectWheelVideo(currentTime);
                XcLogger.e("OnBarMoveListener", "onBarMoveFinish,currentTime=" + TimeZoneUtil.timeToString(Long.valueOf(currentTime), 2));
            }

            public void onMoveExceedStartTime() {
                XcLogger.e("OnBarMoveListener", "超过开始时间了");
            }

            public void onMoveExceedEndTime() {
                XcLogger.e("OnBarMoveListener", "超过结束时间了");
            }

            public void onMaxScale() {
                XcLogger.e("OnBarMoveListener", "超过最大缩放值了");
            }

            public void onMinScale() {
                XcLogger.e("OnBarMoveListener", "超过最小缩放值了");
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.ll_aoni_common_history_select_date /* 2131296575 */:
                if (this.mCalendarDialog == null) {
                    this.mCalendarDialog = new CalendarSelcetDialog(this.mActivity, AoniCommonHistoryVideoActivity$$Lambda$2.lambdaFactory$(this), 2018);
                }
                this.mHistoryReplay.pause();
                this.mCalendarDialog.show();
                break;
            case R.id.tv_aoni_common_history_cloud_storage /* 2131296933 */:
                this.mHistoryReplay.pause();
                Intent intent = new Intent(this.mActivity, (Class<?>) AoniCommonCloudStorageActivity.class);
                intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent.putExtra("productId", this.mProductId);
                startActivity(intent);
                break;
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback
    public void editMsgCallback(String editMsg) {
        if (!TextUtils.isEmpty(editMsg)) {
            String currentTime = this.mTvSelectDate.getText().toString().trim();
            if (TextUtils.isEmpty(currentTime) || !editMsg.equals(currentTime)) {
                this.mTvSelectDate.setText(editMsg);
                long beginTime = TimeZoneUtil.stringToTime(editMsg, 8).longValue();
                long endTime = TimeZoneUtil.stringToTime(TimeZoneUtil.getAfterOneDayF(TimeZoneUtil.timeToString(Long.valueOf(beginTime), 3)), 8).longValue();
                XcLogger.e("callbackDate", "endTime=" + endTime + "-beginTime=" + beginTime + "-currentItem=" + this.mVpSelect.getCurrentItem());
                if (this.mVpSelect.getCurrentItem() == 0) {
                    loadStream(beginTime, endTime, true);
                } else {
                    loadStream(beginTime, endTime, false);
                }
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mHistoryReplay.onDestory();
        this.mRulerView.closeMove();
    }

    public void onBackPressed() {
        if (this.mHistoryReplay.isFullScreen()) {
            this.mHistoryReplay.onBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    public void resultTypeCalllback(int type) {
        if (type == -1) {
            this.mRulerView.openMove();
            return;
        }
        if (type == -2) {
            this.mRulerView.closeMove();
            return;
        }
        if (this.mStreamModel != null && this.mStreamModel.getList() != null && this.mStreamModel.getList().size() > 0) {
            if (type >= 0 && type < this.mStreamModel.getList().size() - 1) {
                this.mHistoryReplay.replayVideo(((CameraCommonPlayStreamModel.PlayStreamModel) this.mStreamModel.getList().get(type + 1)).getPlayUrl(), type + 1);
                this.mRulerView.setCurrentTimeMillis(((CameraCommonPlayStreamModel.PlayStreamModel) this.mStreamModel.getList().get(type + 1)).getBeginTime());
                this.mRulerView.openMove();
            } else {
                this.mRulerView.closeMove();
                this.mHistoryReplay.pause();
                ToastUtils.showShort(this.mActivity, "播发完毕");
            }
        }
    }
}
