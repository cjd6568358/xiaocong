package com.youzan.androidsdk.model.action;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ServerActionModel {
    public static final String ACTION_ALERT = "alert";
    public static final String ACTION_GOTO_NATIVE = "goto_native";
    public static final String ACTION_GOTO_WEBVIEW = "goto_webview";

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private int f108;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private String f109;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private ServerParameterModel f110;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f111;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f112;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f113;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f114;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private String f115;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private String f116;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private String f117;
    public static int NEW_SIGN_VISIBLE = -1;
    public static int NEW_SIGN_NONE = 0;

    public ServerActionModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f111 = o.optString("tool_icon");
            this.f112 = o.optString("tool_parameter");
            this.f113 = o.optInt("new_sign");
            this.f114 = o.optString("tool_value");
            this.f117 = o.optString("tool_title");
            this.f108 = o.optInt("created_time");
            this.f109 = o.optString("tool_type");
            this.f110 = TextUtils.isEmpty(this.f112) ? null : new ServerParameterModel(this.f112);
            m79(this.f109);
        }
    }

    public String getToolIcon() {
        return this.f111;
    }

    public String getToolParameter() {
        return this.f112;
    }

    public int getNewSign() {
        return this.f113;
    }

    public String getToolValue() {
        return this.f114;
    }

    public String getToolTitle() {
        return this.f117;
    }

    public int getCreatedTime() {
        return this.f108;
    }

    public String getToolType() {
        return this.f109;
    }

    public boolean hasNew() {
        return this.f113 != NEW_SIGN_NONE;
    }

    public boolean hasAction() {
        return TextUtils.isEmpty(this.f115);
    }

    public String getActionType() {
        return this.f115;
    }

    public String getActionName() {
        return this.f116;
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private void m79(String toolType) {
        if (!TextUtils.isEmpty(toolType)) {
            String[] parts = TextUtils.split(toolType, "\\:");
            if (parts.length > 0) {
                this.f115 = parts[0];
            }
            if (parts.length > 1) {
                this.f116 = parts[1];
            }
        }
    }

    public ServerParameterModel getParameter() {
        return this.f110;
    }
}
