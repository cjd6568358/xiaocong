package com.scwang.smartrefresh.layout.impl;

import android.view.View;
import android.view.ViewGroup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RefreshFooterWrapper extends RefreshInternalWrapper implements RefreshFooter, InvocationHandler {
    private RefreshKernel mRefreshKernel;
    private Method mRequestDrawBackgroundForHeaderMethod;
    private Method mRequestNeedTouchEventWhenRefreshingMethod;
    private Method mRequestRemeasureHeightForHeaderMethod;

    public RefreshFooterWrapper(View wrapper) {
        super(wrapper);
    }

    @Override // com.scwang.smartrefresh.layout.impl.RefreshInternalWrapper, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        if (this.mWrapperView instanceof RefreshInternal) {
            RefreshKernel proxy = (RefreshKernel) Proxy.newProxyInstance(RefreshKernel.class.getClassLoader(), new Class[]{RefreshKernel.class}, this);
            proxy.requestDrawBackgroundForHeader(0);
            proxy.requestRemeasureHeightForHeader();
            proxy.requestNeedTouchEventWhenRefreshing(false);
            this.mRefreshKernel = kernel;
            ((RefreshInternal) this.mWrapperView).onInitialized(proxy, height, extendHeight);
            return;
        }
        ViewGroup.LayoutParams params = this.mWrapperView.getLayoutParams();
        if (params instanceof SmartRefreshLayout.LayoutParams) {
            kernel.requestDrawBackgroundForFooter(((SmartRefreshLayout.LayoutParams) params).backgroundColor);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshFooter
    public boolean setNoMoreData(boolean noMoreData) {
        if (this.mWrapperView instanceof RefreshFooter) {
            ((RefreshFooter) this.mWrapperView).setNoMoreData(noMoreData);
            return false;
        }
        return false;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue = null;
        if (this.mRefreshKernel != null) {
            if (method.equals(this.mRequestDrawBackgroundForHeaderMethod)) {
                this.mRefreshKernel.requestDrawBackgroundForFooter(((Integer) args[0]).intValue());
            } else if (method.equals(this.mRequestRemeasureHeightForHeaderMethod)) {
                this.mRefreshKernel.requestRemeasureHeightForFooter();
            } else if (method.equals(this.mRequestNeedTouchEventWhenRefreshingMethod)) {
                this.mRefreshKernel.requestNeedTouchEventWhenLoading(((Boolean) args[0]).booleanValue());
            } else {
                returnValue = method.invoke(this.mRefreshKernel, args);
            }
        }
        if (!method.getReturnType().equals(RefreshKernel.class)) {
            return returnValue;
        }
        if (this.mRefreshKernel == null && RefreshKernel.class.equals(method.getDeclaringClass())) {
            if (this.mRequestDrawBackgroundForHeaderMethod == null) {
                this.mRequestDrawBackgroundForHeaderMethod = method;
                return proxy;
            }
            if (this.mRequestRemeasureHeightForHeaderMethod == null) {
                this.mRequestRemeasureHeightForHeaderMethod = method;
                return proxy;
            }
            if (this.mRequestNeedTouchEventWhenRefreshingMethod == null) {
                this.mRequestNeedTouchEventWhenRefreshingMethod = method;
                return proxy;
            }
            return proxy;
        }
        return proxy;
    }
}
