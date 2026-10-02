package com.ixiaocong.smarthome.phone.android.detail.activity.accredit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleImageView;
import com.ixiaocong.smarthome.phone.android.detail.adater.AccreditPermissionListAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AccreditDetailActivity extends XcBaseActivity implements View.OnClickListener, HintDialogCallback {
    private CircleImageView cvDeviceImg;
    private AccreditPermissionListAdapter mAccreditPermissionAdapter;
    private String mDeviceId;
    private String mDeviceImg;
    private String mDeviceName;
    private List<String> mDevicePermission;
    private RecyclerView rvPermission;
    private TextView tvDeviceName;

    protected int getLayoutId() {
        return R.layout.activity_accredit_detail;
    }

    protected void initView() {
        this.tvDeviceName = (TextView) $(R.id.tv_accredit_device_name);
        this.rvPermission = (RecyclerView) $(R.id.lv_accredit_device_list);
        this.cvDeviceImg = (CircleImageView) $(R.id.cv_accredit_device_img);
        this.rvPermission.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    public void addListener() {
        super.addListener();
        int[] resIds = {R.id.left_titlebar_image, R.id.tv_cancel_accredit};
        for (int id : resIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    protected void initData() {
        Intent intent = getIntent();
        this.mDeviceId = intent.getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mDeviceName = intent.getStringExtra("deviceName");
        this.mDeviceImg = intent.getStringExtra("deviceImg");
        this.mDevicePermission = intent.getStringArrayListExtra("devicePermission");
        initAdapter();
        Glide.with(this.mActivity).load(this.mDeviceImg).asBitmap().placeholder(R.drawable.default_img_icon).into(new SimpleTarget<Bitmap>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.accredit.AccreditDetailActivity.1
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, GlideAnimation glideAnimation) {
                onResourceReady((Bitmap) obj, (GlideAnimation<? super Bitmap>) glideAnimation);
            }

            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                AccreditDetailActivity.this.cvDeviceImg.setImageBitmap(resource);
            }
        });
        this.tvDeviceName.setText(this.mDeviceName + "获得以下权限");
    }

    private void loadAccreditCancel() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("cancelAppId", this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("authorize/cancel");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.accredit.AccreditDetailActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AccreditDetailActivity.this.mActivity, "取消授权成功");
                AccreditDetailActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AccreditDetailActivity.this.mActivity, var1.getErrorMessage());
            }
        });
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
        this.mAccreditPermissionAdapter = new AccreditPermissionListAdapter();
        this.rvPermission.setAdapter(this.mAccreditPermissionAdapter);
        this.mAccreditPermissionAdapter.setNewData(this.mDevicePermission);
        this.mAccreditPermissionAdapter.notifyDataSetChanged();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                onBackPressed();
                break;
            case R.id.tv_cancel_accredit /* 2131296958 */:
                OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, null, "取消对其授权后,将失去对小葱智能设备控制权限,确定取消?");
                break;
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        loadAccreditCancel();
    }
}
