package com.xiaocong.smarthome.sdk.openapi.business;

import com.xiaocong.smarthome.sdk.openapi.XCManager;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCServiceInvocationHandler implements InvocationHandler {
    private Object target;

    XCServiceInvocationHandler(Object target) {
        this.target = target;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        XCDataCallback callback;
        if (XCManager.getInstance().isInitialSuccess()) {
            return method.invoke(this.target, args);
        }
        if (args != null && args.length > 0 && (callback = (XCDataCallback) args[args.length - 1]) != null) {
            callback.onError(new XCErrorMessage(-1001, "初始化失败,请重试!"));
        }
        return new Object();
    }
}
