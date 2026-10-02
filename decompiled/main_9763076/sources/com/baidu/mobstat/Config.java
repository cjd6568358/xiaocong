package com.baidu.mobstat;

import android.os.Build;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Config {
    public static final String LOG_SEND_URL;

    static {
        LOG_SEND_URL = Build.VERSION.SDK_INT < 9 ? "http://hmma.baidu.com/app.gif" : "https://hmma.baidu.com/app.gif";
    }

    public enum EventViewType {
        BUTTON(1);

        private int a;

        EventViewType(int i) {
            this.a = i;
        }

        @Override // java.lang.Enum
        public String toString() {
            return String.valueOf(this.a);
        }

        public int getValue() {
            return this.a;
        }
    }
}
