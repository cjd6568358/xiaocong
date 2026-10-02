package com.ixiaocong.smarthome.phone.android.detail.activity.shared;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.SharedDevicesModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SharedHomeDeviceActivity extends XcBaseActivity implements View.OnClickListener {
    private Button mBtnAppend;
    private ImageView mIvBack;
    private List<SharedDevicesModel.SharedDevices> mListData;
    private RecyclerView mRvSharedDev;

    protected int getLayoutId() {
        return R.layout.activity_shared_home_device;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mRvSharedDev = (RecyclerView) $(R.id.rv_shared_device_home);
        this.mBtnAppend = (Button) $(R.id.btn_append_shared_device_home);
        this.mRvSharedDev.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void initData() {
    }

    protected void onResume() {
        super.onResume();
        loadSupportDevices();
    }

    private void loadSupportDevices() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("share/devices");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedHomeDeviceActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedHomeDeviceActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void initAdapter() {
        super.initAdapter();
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mBtnAppend.setOnClickListener(this);
        this.mRvSharedDev.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedHomeDeviceActivity.2
            @Override // android.support.v7.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    SharedHomeDeviceActivity.this.mBtnAppend.setVisibility(8);
                } else {
                    SharedHomeDeviceActivity.this.mBtnAppend.setVisibility(0);
                }
            }
        });
        this.mRvSharedDev.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedHomeDeviceActivity.3
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                Intent intent = new Intent(SharedHomeDeviceActivity.this.mActivity, (Class<?>) SharedMemberActivity.class);
                intent.putExtra(Constants.FLAG_DEVICE_ID, ((SharedDevicesModel.SharedDevices) SharedHomeDeviceActivity.this.mListData.get(position)).getDeviceId());
                intent.putExtra("deviceName", ((SharedDevicesModel.SharedDevices) SharedHomeDeviceActivity.this.mListData.get(position)).getDeviceName());
                SharedHomeDeviceActivity.this.startActivity(intent);
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_append_shared_device_home /* 2131296320 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) SharedAddDeviceActivity.class);
                startActivity(intent);
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.getScreenManager().popActivity(this);
    }
}
