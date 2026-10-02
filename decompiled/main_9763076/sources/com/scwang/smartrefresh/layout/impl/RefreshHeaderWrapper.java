package com.scwang.smartrefresh.layout.impl;

import android.view.View;
import android.view.ViewGroup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RefreshHeaderWrapper extends RefreshInternalWrapper implements RefreshHeader, InvocationHandler {
    private RefreshKernel mRefreshKernel;
    private Method mRequestDrawBackgroundForFooterMethod;
    private Method mRequestNeedTouchEventWhenLoadingMethod;
    private Method mRequestRemeasureHeightForFooterMethod;

    public RefreshHeaderWrapper(View wrapper) {
        super(wrapper);
    }

    @Override // com.scwang.smartrefresh.layout.impl.RefreshInternalWrapper, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        if (this.mWrapperView instanceof RefreshInternal) {
            RefreshKernel proxy = (RefreshKernel) Proxy.newProxyInstance(RefreshKernel.class.getClassLoader(), new Class[]{RefreshKernel.class}, this);
            proxy.requestDrawBackgroundForFooter(0);
            proxy.requestRemeasureHeightForFooter();
            proxy.requestNeedTouchEventWhenLoading(false);
            this.mRefreshKernel = kernel;
            ((RefreshInternal) this.mWrapperView).onInitialized(proxy, height, extendHeight);
            return;
        }
        ViewGroup.LayoutParams params = this.mWrapperView.getLayoutParams();
        if (params instanceof SmartRefreshLayout.LayoutParams) {
            kernel.requestDrawBackgroundForHeader(((SmartRefreshLayout.LayoutParams) params).backgroundColor);
        }
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue = null;
        if (this.mRefreshKernel != null) {
            if (method.equals(this.mRequestDrawBackgroundForFooterMethod)) {
                this.mRefreshKernel.requestDrawBackgroundForHeader(((Integer) args[0]).intValue());
            } else if (method.equals(this.mRequestRemeasureHeightForFooterMethod)) {
                this.mRefreshKernel.requestRemeasureHeightForHeader();
            } else if (method.equals(this.mRequestNeedTouchEventWhenLoadingMethod)) {
                this.mRefreshKernel.requestNeedTouchEventWhenRefreshing(((Boolean) args[0]).booleanValue());
            } else {
                returnValue = method.invoke(this.mRefreshKernel, args);
            }
        }
        if (!method.getReturnType().equals(RefreshKernel.class)) {
            return returnValue;
        }
        if (this.mRefreshKernel == null && RefreshKernel.class.equals(method.getDeclaringClass())) {
            if (this.mRequestDrawBackgroundForFooterMethod == null) {
                this.mRequestDrawBackgroundForFooterMethod = method;
                return proxy;
            }
            if (this.mRequestRemeasureHeightForFooterMethod == null) {
                this.mRequestRemeasureHeightForFooterMethod = method;
                return proxy;
            }
            if (this.mRequestNeedTouchEventWhenLoadingMethod == null) {
                this.mRequestNeedTouchEventWhenLoadingMethod = method;
                return proxy;
            }
            return proxy;
        }
        return proxy;
    }
}
