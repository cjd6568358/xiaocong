package com.tencent.android.tpush;

import android.content.Intent;
import com.tencent.android.tpush.common.Constants;
import java.io.Serializable;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPushTextMessage implements Serializable {
    private static final long serialVersionUID = -1854661081378847806L;
    String title = Constants.MAIN_VERSION_TAG;
    String content = Constants.MAIN_VERSION_TAG;
    String customContent = Constants.MAIN_VERSION_TAG;
    int pushChannel = 0;
    private Intent simpleIntent = null;

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getCustomContent() {
        return this.customContent;
    }

    public int getPushChannel() {
        return this.pushChannel;
    }

    void a(Intent intent) {
        this.simpleIntent = intent;
        if (intent != null) {
            this.simpleIntent.removeExtra("content");
        }
    }

    Intent a() {
        return this.simpleIntent;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("XGPushShowedResult [title=").append(this.title).append(", content=").append(this.content).append(", customContent=").append(this.customContent).append("]");
        return sb.toString();
    }
}
