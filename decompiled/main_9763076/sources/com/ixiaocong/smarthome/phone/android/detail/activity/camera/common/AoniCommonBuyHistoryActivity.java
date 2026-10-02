package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.XcApplication;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.AoniCommonBuyHistoryAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.CameraCommonBuyHistoryModel;
import com.xiaocong.smarthome.httplib.model.CommonWXPayModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniCommonBuyHistoryActivity extends XcBaseActivity {
    private String mDeviceId;
    private AoniCommonBuyHistoryAdapter mHistoryAdapter;
    private CameraCommonBuyHistoryModel mHistoryModel;
    private ImageView mIvBack;
    private int mPageNum = 1;
    private int mPageSize = 20;
    private String mProductId;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvHistory;
    private TextView mTvBuyHint;

    static /* synthetic */ int access$608(AoniCommonBuyHistoryActivity x0) {
        int i = x0.mPageNum;
        x0.mPageNum = i + 1;
        return i;
    }

    protected int getLayoutId() {
        return R.layout.activity_aoni_common_buy_hisotry;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvBuyHint = (TextView) $(R.id.tv_common_camera_buy_history_hint);
        this.mRefreshLayout = (SmartRefreshLayout) $(R.id.srl_common_camera_buy_history);
        this.mRvHistory = (RecyclerView) $(R.id.rv_common_camera_buy_history);
        this.mRvHistory.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mProductId = getIntent().getStringExtra("productId");
        loadOrderList(true, 1);
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
        this.mHistoryAdapter = new AoniCommonBuyHistoryAdapter(this.mActivity);
        this.mRvHistory.setAdapter(this.mHistoryAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadOrderList(final boolean isLoad, int page) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> noSignParams = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        noSignParams.put("page", page + Constants.MAIN_VERSION_TAG);
        noSignParams.put("pageSize", this.mPageSize + Constants.MAIN_VERSION_TAG);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(noSignParams);
        httpSetting.setPath("camera/order/list");
        if (isLoad) {
            HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        }
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonBuyHistoryActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniCommonBuyHistoryActivity.this.mHistoryModel = (CameraCommonBuyHistoryModel) JSON.parseObject(var1.getData(), CameraCommonBuyHistoryModel.class);
                if (AoniCommonBuyHistoryActivity.this.mHistoryModel != null && AoniCommonBuyHistoryActivity.this.mHistoryModel.getList() != null && AoniCommonBuyHistoryActivity.this.mHistoryModel.getList().size() != 0) {
                    AoniCommonBuyHistoryActivity.this.mTvBuyHint.setVisibility(8);
                    AoniCommonBuyHistoryActivity.this.mRefreshLayout.setVisibility(0);
                    if (isLoad) {
                        AoniCommonBuyHistoryActivity.this.mHistoryAdapter.setNewData(AoniCommonBuyHistoryActivity.this.mHistoryModel.getList());
                    } else {
                        AoniCommonBuyHistoryActivity.this.mHistoryAdapter.addData(AoniCommonBuyHistoryActivity.this.mHistoryModel.getList());
                    }
                    AoniCommonBuyHistoryActivity.this.mHistoryAdapter.notifyDataSetChanged();
                    return;
                }
                if (isLoad) {
                    AoniCommonBuyHistoryActivity.this.mTvBuyHint.setVisibility(0);
                    AoniCommonBuyHistoryActivity.this.mRefreshLayout.setVisibility(8);
                } else {
                    ToastUtils.showShort(AoniCommonBuyHistoryActivity.this.mActivity, "我也是有底线的...");
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AoniCommonBuyHistoryActivity.this.mActivity, var1.getErrorMessage());
            }
        });
        if (this.mRefreshLayout != null) {
            if (!isLoad) {
                this.mRefreshLayout.finishLoadMore();
            } else {
                this.mRefreshLayout.finishRefresh();
            }
        }
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(AoniCommonBuyHistoryActivity$$Lambda$1.lambdaFactory$(this));
        this.mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonBuyHistoryActivity.2
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout) {
                AoniCommonBuyHistoryActivity.access$608(AoniCommonBuyHistoryActivity.this);
                if (AoniCommonBuyHistoryActivity.this.mHistoryModel != null) {
                    if (AoniCommonBuyHistoryActivity.this.mHistoryModel.getPage() < AoniCommonBuyHistoryActivity.this.mPageSize) {
                        AoniCommonBuyHistoryActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else if (AoniCommonBuyHistoryActivity.this.mHistoryModel.getTotalPage() < AoniCommonBuyHistoryActivity.this.mPageNum) {
                        AoniCommonBuyHistoryActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        AoniCommonBuyHistoryActivity.this.loadOrderList(false, AoniCommonBuyHistoryActivity.this.mPageNum);
                    }
                }
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                AoniCommonBuyHistoryActivity.this.loadOrderList(true, 1);
            }
        });
        this.mRvHistory.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonBuyHistoryActivity.3
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                if (((CameraCommonBuyHistoryModel.CommonBuyHistoryModel) AoniCommonBuyHistoryActivity.this.mHistoryAdapter.getData().get(position)).getOrderStatus() == 0) {
                    AoniCommonBuyHistoryActivity.this.requestPayId(((CameraCommonBuyHistoryModel.CommonBuyHistoryModel) AoniCommonBuyHistoryActivity.this.mHistoryAdapter.getData().get(position)).getOrderId());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPayId(String orderId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/pay/getPayIdByOrder");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonBuyHistoryActivity.4
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                CommonWXPayModel payModel = (CommonWXPayModel) JSON.parseObject(var1.getData(), CommonWXPayModel.class);
                if (payModel != null) {
                    AoniCommonBuyHistoryActivity.this.wxPay(payModel);
                } else {
                    ToastUtils.showShort(AoniCommonBuyHistoryActivity.this.mActivity, "获取微信支付订单失败");
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AoniCommonBuyHistoryActivity.this.mActivity, "获取微信支付订单失败");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wxPay(CommonWXPayModel payModel) {
        PayReq payReq = new PayReq();
        payReq.appId = payModel.getAppId();
        payReq.partnerId = payModel.getPartnerId();
        payReq.prepayId = payModel.getPayId();
        payReq.packageValue = payModel.getPackageValue();
        payReq.nonceStr = payModel.getNonceStr();
        payReq.timeStamp = payModel.getTimestamp();
        payReq.sign = payModel.getPaySign();
        payReq.signType = payModel.getSignType();
        XcApplication.getInstance().registerWX().sendReq(payReq);
    }
}
