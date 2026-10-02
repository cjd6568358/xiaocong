package com.ixiaocong.smarthome.phone.android.detail.activity.area;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.gson.Gson;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.LocationManager;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.fragment.area.AreaFragment;
import com.ixiaocong.smarthome.phone.android.event.eventbus.LocationEvent;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.base.XcBaseFragment;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.AreaInfoModel;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.zxing.utils.DPIUtil;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AreaSelectActivity extends XcBaseActivity implements AreaFragment.OnFragmentInteractionListener {
    private String mAddress;
    private ImageView mLeftBack;
    private ImageView mLocation;
    private Map<String, String> mParams;
    private RelativeLayout mTitleBarRight;
    private int mType;
    private Map map = new HashMap();
    private Fragment oneFragment;
    private TextView tvRightTitle;
    private Fragment twoFragment;

    protected int getLayoutId() {
        return R.layout.activity_area_select;
    }

    protected void initView() {
        this.oneFragment = AreaFragment.newInstance(Constants.MAIN_VERSION_TAG);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, this.oneFragment).commit();
        this.mLeftBack = (ImageView) $(R.id.left_titlebar_image);
        this.tvRightTitle = (TextView) $(R.id.right_titlebar_text);
        this.mLocation = (ImageView) $(R.id.right_titlebar_image);
        this.mTitleBarRight = (RelativeLayout) $(R.id.rl_right_titlebar_image);
        this.mTitleBarRight.setVisibility(8);
        this.tvRightTitle.setVisibility(0);
        this.tvRightTitle.setText("定位");
        this.tvRightTitle.setTextSize(14.0f);
        this.mLocation.setPadding(0, DPIUtil.dip2px(this.mActivity, 10.0f), DPIUtil.dip2px(this.mActivity, 15.0f), DPIUtil.dip2px(this.mActivity, 10.0f));
    }

    public void addListener() {
        super.addListener();
        this.mLeftBack.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.area.AreaSelectActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AreaSelectActivity.this.onBackPressed();
            }
        });
        this.tvRightTitle.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.area.AreaSelectActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SpUtils.clearSp("spLocation", AreaSelectActivity.this.mActivity);
                LocationManager.getInstance().getCurrentLocation(AreaSelectActivity.this.getApplicationContext());
                AreaSelectActivity.this.finish();
            }
        });
        this.mLocation.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.area.AreaSelectActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
            }
        });
    }

    protected void initData() {
        this.mType = getIntent().getIntExtra("type", 0);
        if (this.mType == 1) {
            this.tvRightTitle.setVisibility(8);
        }
        this.mParams = new HashMap();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }

    @Override // com.ixiaocong.smarthome.phone.android.detail.fragment.area.AreaFragment.OnFragmentInteractionListener
    public void onFragmentInteraction(AreaInfoModel.DistrictListBean areaInfo) {
        if (areaInfo != null) {
            FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
            String level = areaInfo.getLevel();
            switch (level) {
                case "1":
                    if (this.mType == 1) {
                        this.mAddress = areaInfo.getName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                    }
                    fragmentTransactionBeginTransaction.hide(this.oneFragment);
                    XcBaseFragment xcBaseFragmentNewInstance = AreaFragment.newInstance(areaInfo.getAdcode() + Constants.MAIN_VERSION_TAG);
                    this.twoFragment = xcBaseFragmentNewInstance;
                    fragmentTransactionBeginTransaction.add(R.id.content, (Fragment) xcBaseFragmentNewInstance).addToBackStack(null).commit();
                    break;
                case "2":
                    loadAreaInfo(areaInfo, fragmentTransactionBeginTransaction);
                    break;
                case "3":
                    if (this.mType == 1) {
                        this.mAddress += areaInfo.getName();
                        this.mParams.put("address", this.mAddress);
                        this.mParams.put("adcode", areaInfo.getAdcode());
                        EventBus.getDefault().post(this.mParams);
                    } else {
                        LocationEvent event = new LocationEvent();
                        event.setCityName(areaInfo.getName());
                        event.setAdCode(areaInfo.getAdcode());
                        event.setLat(areaInfo.getLatitude());
                        event.setLot(areaInfo.getLongitude());
                        SpUtils.saveToLocal(this.mActivity, "spLocation", "spLocation", new Gson().toJson(event));
                        EventBus.getDefault().post(event);
                    }
                    XcLogger.e("homeAddress=", this.mAddress);
                    finish();
                    break;
            }
        }
    }

    private void loadAreaInfo(final AreaInfoModel.DistrictListBean areaInfo, final FragmentTransaction transaction) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("padcode", areaInfo.getAdcode());
        httpSetting.setParamsMap(params);
        httpSetting.setPath("district/list");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.area.AreaSelectActivity.4
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
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AreaInfoModel areaInfoModel = (AreaInfoModel) JSON.parseObject(var1.getData(), AreaInfoModel.class);
                if (areaInfoModel.getDistrictList().size() > 0) {
                    transaction.hide(AreaSelectActivity.this.twoFragment);
                    transaction.add(R.id.content, (Fragment) AreaFragment.newInstance(areaInfo.getAdcode() + Constants.MAIN_VERSION_TAG)).addToBackStack(null).commit();
                    if (AreaSelectActivity.this.mType == 1) {
                        AreaSelectActivity.this.mAddress += areaInfo.getName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                        return;
                    }
                    return;
                }
                if (areaInfoModel.getDistrictList().size() == 0) {
                    if (AreaSelectActivity.this.mType == 1) {
                        AreaSelectActivity.this.mAddress += areaInfo.getName();
                        AreaSelectActivity.this.mParams.put("address", AreaSelectActivity.this.mAddress);
                        AreaSelectActivity.this.mParams.put("adcode", areaInfo.getAdcode());
                        EventBus.getDefault().post(AreaSelectActivity.this.mParams);
                    } else {
                        LocationEvent event = new LocationEvent();
                        event.setCityName(areaInfo.getName());
                        event.setAdCode(areaInfo.getAdcode());
                        event.setLat(areaInfo.getLatitude());
                        event.setLot(areaInfo.getLongitude());
                        SpUtils.saveToLocal(AreaSelectActivity.this.mActivity, "spLocation", "spLocation", new Gson().toJson(event));
                        EventBus.getDefault().post(event);
                    }
                    AreaSelectActivity.this.finish();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AreaSelectActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }
}
