package com.xiaocong.smarthome.sdk.http.callback;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.xiaocong.smarthome.network.httplib.TextHttpResponseHandler;
import com.xiaocong.smarthome.network.util.XCHttpLog;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.uilib.widget.XCToastUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class XCHttpCallBack extends TextHttpResponseHandler {
    public abstract void onFailure(int i, Map<String, String> map, XCResponseBean xCResponseBean, Throwable th);

    public abstract void onSuccess(int i, Map<String, String> map, XCResponseBean xCResponseBean);

    @Override // com.xiaocong.smarthome.network.httplib.TextHttpResponseHandler
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        BuglyLog.e("XCHttpLog_bugly", "statusCode:" + statusCode + ",responseString:" + responseString, throwable);
        CrashReport.postCatchedException(throwable);
        if (TextUtils.isEmpty(responseString)) {
        }
        XCResponseBean bean = new XCResponseBean();
        bean.setCode(-100);
        bean.setMsg("加载失败,请稍后重试!");
        onFailure(statusCode, headersToMap(headers), bean, throwable);
    }

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    public void onFinish() {
        super.onFinish();
    }

    @Override // com.xiaocong.smarthome.network.httplib.TextHttpResponseHandler
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Context mContext;
        Context mContext2;
        XCResponseBean bean = null;
        try {
            JSONObject jo = JSONObject.parseObject(responseString);
            if (jo != null) {
                XCResponseBean bean2 = new XCResponseBean();
                try {
                    bean2.setCode(jo.getInteger("code"));
                    bean2.setMsg(jo.getString("msg"));
                    Object data = jo.get("data");
                    if (data != null) {
                        bean2.setData(data.toString());
                    }
                    bean2.setSuccess(jo.getBoolean("success"));
                    bean = bean2;
                } catch (Throwable th) {
                    e = th;
                    XCHttpLog.e(e.getMessage());
                    onFailure(statusCode, headersToMap(headers), makeIllegalBean(), e);
                    return;
                }
            }
            if (bean != null) {
                if (bean.getCode().intValue() == 0) {
                    onSuccess(statusCode, headersToMap(headers), bean);
                    return;
                }
                if (100 == bean.getCode().intValue()) {
                    if (this.mWeakContext != null && (mContext2 = this.mWeakContext.get()) != null) {
                        Intent intent = new Intent("XCSDK.HttpReceiver");
                        intent.putExtra("httpReceiverCode", bean.getCode());
                        intent.putExtra("httpReceiverMsg", bean.getMsg());
                        LocalBroadcastManager.getInstance(mContext2).sendBroadcast(intent);
                    }
                    onFailure(statusCode, headersToMap(headers), bean, (Throwable) null);
                    return;
                }
                if (this.mWeakContext != null && (mContext = this.mWeakContext.get()) != null) {
                    XCToastUtil.showToast(mContext, bean.getMsg(), 0);
                }
                onFailure(statusCode, headersToMap(headers), bean, (Throwable) null);
                return;
            }
            onFailure(statusCode, headersToMap(headers), makeIllegalBean(), (Throwable) null);
        } catch (Throwable th2) {
            e = th2;
        }
    }

    private XCResponseBean makeIllegalBean() {
        XCResponseBean bean = new XCResponseBean();
        bean.setCode(-100);
        bean.setMsg("加载失败,请稍后重试!");
        return bean;
    }

    private static Map<String, String> headersToMap(Header[] headers) {
        HashMap<String, String> map = new HashMap<>();
        if (headers != null) {
            for (Header item : headers) {
                map.put(item.getName(), item.getValue());
            }
        }
        return map;
    }
}
