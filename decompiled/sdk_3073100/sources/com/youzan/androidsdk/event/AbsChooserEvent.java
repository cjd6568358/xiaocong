package com.youzan.androidsdk.event;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class AbsChooserEvent implements Event {
    public abstract void call(Context context, Intent intent, int i) throws ActivityNotFoundException;

    public String subscribe() {
        return EventAPI.EVENT_FILE_CHOOSER;
    }

    public final void call(Context context, String data) {
        try {
            Meta meta = new Meta(data);
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType(meta.acceptType);
            call(context, intent, meta.requestId);
        } catch (Exception e) {
        }
    }

    public static final class Meta {
        private static final String KEY_ACCEPT_TYPE = "accept_type";
        private static final String KEY_REQUEST_ID = "request_id";
        public String acceptType;
        public int requestId;

        public Meta() {
        }

        Meta(String json) throws JSONException {
            if (!TextUtils.isEmpty(json)) {
                JSONObject obj = new JSONObject(json);
                this.requestId = obj.optInt(KEY_REQUEST_ID);
                this.acceptType = obj.optString(KEY_ACCEPT_TYPE);
            }
        }

        public String toJSON() {
            JSONObject obj = new JSONObject();
            try {
                obj.put(KEY_REQUEST_ID, String.valueOf(this.requestId));
                obj.put(KEY_ACCEPT_TYPE, this.acceptType);
            } catch (JSONException e) {
            }
            return obj.toString();
        }
    }
}
