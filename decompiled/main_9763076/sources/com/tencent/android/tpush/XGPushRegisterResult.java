package com.tencent.android.tpush;

import android.content.Intent;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPushRegisterResult implements XGIResult {
    long a = 0;
    String b = Constants.MAIN_VERSION_TAG;
    String c = Constants.MAIN_VERSION_TAG;
    String d = Constants.MAIN_VERSION_TAG;
    short e = 0;
    String f = Constants.MAIN_VERSION_TAG;
    String g = Constants.MAIN_VERSION_TAG;
    int h = 0;

    public long getAccessId() {
        return this.a;
    }

    public String getDeviceId() {
        return this.b;
    }

    public String getAccount() {
        return this.c;
    }

    public String getTicket() {
        return this.d;
    }

    public short getTicketType() {
        return this.e;
    }

    public String getToken() {
        return this.f;
    }

    public String getOtherPushToken() {
        return this.g;
    }

    public int getPushChannel() {
        return this.h;
    }

    XGPushRegisterResult() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TPushRegisterMessage [accessId=").append(this.a).append(", deviceId=").append(this.b).append(", account=").append(this.c).append(", ticket=").append(this.d).append(", ticketType=").append((int) this.e).append(", token=").append(this.f).append("]");
        return sb.toString();
    }

    @Override // com.tencent.android.tpush.XGIResult
    public void parseIntent(Intent intent) {
        try {
            this.a = intent.getLongExtra("accId", -1L);
            this.b = intent.getStringExtra(Constants.FLAG_DEVICE_ID);
            this.c = intent.getStringExtra(Constants.FLAG_ACCOUNT);
            this.d = intent.getStringExtra(Constants.FLAG_TICKET);
            this.e = intent.getShortExtra(Constants.FLAG_TICKET_TYPE, (short) 0);
            this.f = intent.getStringExtra(Constants.FLAG_TOKEN);
        } catch (Throwable th) {
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Constants.FLAG_ACCOUNT, this.c);
            jSONObject.put(Constants.FLAG_TICKET, this.d);
            jSONObject.put(Constants.FLAG_DEVICE_ID, this.b);
            jSONObject.put(Constants.FLAG_TICKET_TYPE, (int) this.e);
            jSONObject.put(Constants.FLAG_TOKEN, this.f);
        } catch (Throwable th) {
        }
        return jSONObject;
    }
}
