package com.xiaocong.smarthome.sdk.network;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.uilib.widget.XCToastUtil;
import com.xiaocong.smarthome.xcnetwork.RxObserver;
import com.xiaocong.smarthome.xcnetwork.utils.SPUtil;
import com.xiaocong.smarthome.xcnetwork.utils.XcLogger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class WGRxObserver extends RxObserver<XCResponseBean> {
    public WGRxObserver(Context context) {
        super(context);
    }

    @Override // com.xiaocong.smarthome.xcnetwork.RxObserver, rx.Observer
    public void onNext(XCResponseBean bean) {
        try {
            if (bean != null) {
                if (bean.getCode().intValue() == 0) {
                    onSuccess(bean);
                    return;
                }
                onFail(bean);
                XCToastUtil.showToast(this.context, bean != null ? bean.getMsg() + "" : "返回参数错误", 0);
                if (100 == bean.getCode().intValue()) {
                    Intent intent = new Intent("XCSDK.HttpReceiver");
                    intent.putExtra("httpReceiverCode", bean.getCode());
                    intent.putExtra("httpReceiverMsg", bean.getMsg());
                    LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
                    return;
                }
                return;
            }
            onFail(makeIllegalBean("数据返回异常,请稍后重试!"));
            XcLogger.e("XCHttpLog", "response is null");
        } catch (Exception e) {
            XcLogger.e("XCHttpLog", e.toString());
            onFail(makeIllegalBean("数据解析异常,请稍后重试!"));
        }
    }

    @Override // com.xiaocong.smarthome.xcnetwork.RxObserver, rx.Observer
    public void onError(Throwable e) {
        super.onError(e);
        SPUtil.getInstance(this.context).removeSP("dns_ip");
        RequestNetwork.getInstance().removeRequst();
        onFail(makeIllegalBean("加载失败,请稍后重试..."));
    }

    public void onFail(XCResponseBean wgResponseBean) {
        XcLogger.e("onFail", wgResponseBean.toString());
        HttpLoadingHelper.getInstance().dismissProcessLoading();
    }

    private XCResponseBean makeIllegalBean(String str) {
        XCResponseBean bean = new XCResponseBean();
        bean.setCode(-100);
        if (TextUtils.isEmpty(str)) {
            str = "加载失败,请稍后重试!";
        }
        bean.setMsg(str);
        return bean;
    }
}
