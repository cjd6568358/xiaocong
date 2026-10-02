package com.youzan.androidsdk.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.event.Event;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Keep
public interface YouzanClient {
    public static final int PAGE_TYPE_HTML5 = 1;
    public static final int PAGE_TYPE_NATIVE_CART = 18;
    public static final int PAGE_TYPE_NATIVE_GOODS = 17;
    public static final int PAGE_TYPE_NATIVE_PAY_RESULT = 19;
    public static final int PAGE_TYPE_NATIVE_TRADE_LIST = 20;
    public static final int PAGE_TYPE_UNKNOWN = 0;

    @Keep
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageType {
    }

    Context getContext();

    int getPageType();

    String getTitle();

    String getUrl();

    void loadUrl(String str);

    boolean pageCanGoBack();

    boolean pageGoBack();

    boolean receiveFile(int i, Intent intent);

    void reload();

    void sharePage();

    void subscribe(Event event);

    void sync(YouzanToken youzanToken);

    boolean syncNot();
}
