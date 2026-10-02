package com.ixiaocong.smarthome.phone.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.android.XcApplication;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.login.RelevancePhoneActivity;
import com.ixiaocong.smarthome.phone.android.event.eventbus.LoginEvent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.UserLoginModel;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.XCManager;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.util.ACache;
import java.util.HashMap;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XcApplication.getInstance().registerWX().handleIntent(getIntent(), this);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        XcApplication.getInstance().registerWX().handleIntent(intent, this);
    }

    public void onReq(BaseReq baseReq) {
        XcLogger.i("WXEntryActivity--", "baseReq");
    }

    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED /* -4 */:
                finish();
                XcLogger.i("WXEntryActivity--", "BaseResp.ErrCode.ERR_AUTH_DENIED");
                break;
            case -3:
            case -1:
            default:
                String result = "result：" + baseResp.errCode + "-" + baseResp.errStr + "-" + baseResp.openId + "-" + baseResp.transaction + "-" + baseResp.getType() + "-" + baseResp.checkArgs();
                finish();
                XcLogger.i("WXEntryActivity--", result);
                break;
            case -2:
                XcLogger.i("WXEntryActivity--", "BaseResp.ErrCode.ERR_USER_CANCEL");
                finish();
                break;
            case 0:
                XcLogger.i("WXEntryActivity--", "BaseResp.ErrCode.ERR_OK");
                if (baseResp instanceof SendAuth.Resp) {
                    SendAuth.Resp newResp = (SendAuth.Resp) baseResp;
                    String code = newResp.code;
                    XcLogger.i("WXEntryActivity--", "code=" + code);
                    wxLogin(code);
                }
                break;
        }
    }

    private void wxLogin(String code) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("code", code);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("user/wx/login");
        XCRequest.getInstance().request(this, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.wxapi.WXEntryActivity.1
            public void onComplete(XCResponseBean var1) {
                UserLoginModel loginModel = (UserLoginModel) JSON.parseObject(var1.getData(), UserLoginModel.class);
                if (!TextUtils.isEmpty(loginModel.getToken())) {
                    SpUtils.saveToLocal(WXEntryActivity.this, "xiao_cong_jfnda", "xiao_cong_jfnda", loginModel.getPhone());
                    SpUtils.saveToLocal(WXEntryActivity.this, "NLC_ahe_9l", "NLC_ahe_9l", loginModel.getToken());
                    SpUtils.saveToLocal(WXEntryActivity.this, "xiao_cong_uid", "xiao_cong_uid", loginModel.getUid());
                    XCManager.getInstance().loginWithToken(loginModel.getToken());
                    EventBus.getDefault().post(new LoginEvent(true));
                    WXEntryActivity.this.startActivity(new Intent(WXEntryActivity.this, (Class<?>) MainFragmentActivity.class));
                    ActivityManagerUtil.getScreenManager().popAllActivity();
                } else {
                    Intent intent = new Intent(WXEntryActivity.this, (Class<?>) RelevancePhoneActivity.class);
                    intent.putExtra("ofjdkldkdl", loginModel.getOpenId());
                    WXEntryActivity.this.startActivity(intent);
                }
                ACache.get(WXEntryActivity.this).clear();
                WXEntryActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(WXEntryActivity.this, "启动微信登录失败,请稍后重试");
                WXEntryActivity.this.finish();
            }
        });
    }
}
