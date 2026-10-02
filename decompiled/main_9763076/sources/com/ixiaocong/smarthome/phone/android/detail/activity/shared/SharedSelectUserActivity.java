package com.ixiaocong.smarthome.phone.android.detail.activity.shared;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.AppDetailSettingManager;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.SharedSelectUserAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.SharedMemberModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SharedSelectUserActivity extends XcBaseActivity implements View.OnClickListener {
    private SharedSelectUserAdapter mAdapter;
    private Button mBtnShared;
    private String mDeviceId;
    private EditText mEtInputUser;
    private ImageView mIvBack;
    private ImageView mIvSelectUser;
    private List<SharedMemberModel.MemberModel> mMemberList;
    private RecyclerView mRvUsers;

    protected int getLayoutId() {
        return R.layout.activity_shared_select_user;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mIvSelectUser = (ImageView) $(R.id.iv_select_shared_user_icon);
        this.mEtInputUser = (EditText) $(R.id.et_input_shared_user_no);
        this.mBtnShared = (Button) $(R.id.btn_shared_device_next);
        this.mRvUsers = (RecyclerView) $(R.id.rv_shared_select_user);
        this.mRvUsers.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ActivityManagerUtil.getScreenManager().pushActivity(this);
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
    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mAdapter = new SharedSelectUserAdapter(this.mActivity);
        this.mRvUsers.setAdapter(this.mAdapter);
    }

    protected void onResume() {
        super.onResume();
        loadOftenSharedUser();
    }

    private void loadOftenSharedUser() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        new HashMap();
        httpSetting.setPath("share/users");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedSelectUserActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SharedMemberModel sharedMemberModel = (SharedMemberModel) JSON.parseObject(var1.getData(), SharedMemberModel.class);
                XcLogger.e("SharedSelectUserActivity", "-------" + sharedMemberModel.getList().size());
                SharedSelectUserActivity.this.mMemberList = sharedMemberModel.getList();
                SharedSelectUserActivity.this.mAdapter.setNewData(SharedSelectUserActivity.this.mMemberList);
                SharedSelectUserActivity.this.mAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedSelectUserActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mIvSelectUser.setOnClickListener(this);
        this.mBtnShared.setOnClickListener(this);
        this.mRvUsers.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedSelectUserActivity.2
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                Intent intent = new Intent(SharedSelectUserActivity.this.mActivity, (Class<?>) SharedUserDetailActivity.class);
                intent.putExtra("userName", ((SharedMemberModel.MemberModel) SharedSelectUserActivity.this.mMemberList.get(position)).getNickname());
                intent.putExtra("userImg", ((SharedMemberModel.MemberModel) SharedSelectUserActivity.this.mMemberList.get(position)).getPortrait());
                intent.putExtra("userId", ((SharedMemberModel.MemberModel) SharedSelectUserActivity.this.mMemberList.get(position)).getUid());
                intent.putExtra(Constants.FLAG_DEVICE_ID, SharedSelectUserActivity.this.mDeviceId);
                SharedSelectUserActivity.this.startActivity(intent);
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_shared_device_next /* 2131296334 */:
                String userPhone = this.mEtInputUser.getText().toString().replaceAll(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, Constants.MAIN_VERSION_TAG).replaceAll("-", Constants.MAIN_VERSION_TAG);
                if (TextUtils.isEmpty(userPhone)) {
                    ToastUtils.showShort(this.mActivity, "请选择分享人");
                } else if (userPhone.length() != 11) {
                    ToastUtils.showShort(this.mActivity, "请输入分享人的正确手机号");
                } else {
                    verifyUserPhone(userPhone);
                }
                break;
            case R.id.iv_select_shared_user_icon /* 2131296516 */:
                if (ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.READ_CONTACTS") != 0) {
                    ActivityCompat.requestPermissions(this.mActivity, new String[]{"android.permission.READ_CONTACTS"}, 100);
                } else {
                    Uri uri = ContactsContract.Contacts.CONTENT_URI;
                    Intent intent = new Intent("android.intent.action.PICK", uri);
                    startActivityForResult(intent, 100);
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
        }
    }

    private void verifyUserPhone(String userPhone) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("phone", userPhone);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("share/checkByPhone");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedSelectUserActivity.3
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SharedMemberModel.MemberModel memberModel = (SharedMemberModel.MemberModel) JSON.parseObject(var1.getData(), SharedMemberModel.MemberModel.class);
                Intent intent = new Intent(SharedSelectUserActivity.this.mActivity, (Class<?>) SharedUserDetailActivity.class);
                intent.putExtra("userName", memberModel.getNickname());
                intent.putExtra("userImg", memberModel.getPortrait());
                intent.putExtra("userId", memberModel.getUid());
                intent.putExtra(Constants.FLAG_DEVICE_ID, SharedSelectUserActivity.this.mDeviceId);
                SharedSelectUserActivity.this.startActivity(intent);
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedSelectUserActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            Activity activity = this.mActivity;
            if (resultCode == -1) {
                if (data == null) {
                    ToastUtils.showShort(this.mActivity, "您未选择要分享联系人");
                    return;
                }
                Uri uri = data.getData();
                String[] contacts = getPhoneContacts(uri);
                if (contacts == null) {
                    ToastUtils.showShort(this.mActivity, "选择分享联系人失败");
                    return;
                }
                this.mEtInputUser.setText(contacts[1].trim());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] != 0) {
                ToastUtils.showShort(this.mActivity, "您当前未授权App访问通讯录功能");
                AppDetailSettingManager.getAppDetailSettingIntent(this.mActivity);
            } else {
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                Intent intent = new Intent("android.intent.action.PICK", uri);
                startActivityForResult(intent, 100);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        int nameFieldColumnIndex = cursor.getColumnIndex("display_name");
        contact[0] = cursor.getString(nameFieldColumnIndex);
        String ContactId = cursor.getString(cursor.getColumnIndex("_id"));
        Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id=" + ContactId, null, null);
        if (phone != null) {
            phone.moveToFirst();
            contact[1] = phone.getString(phone.getColumnIndex("data1"));
        }
        phone.close();
        cursor.close();
        return contact;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.getScreenManager().popActivity(this);
    }
}
