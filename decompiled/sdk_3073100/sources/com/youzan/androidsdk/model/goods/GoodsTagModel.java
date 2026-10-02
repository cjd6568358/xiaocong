package com.youzan.androidsdk.model.goods;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class GoodsTagModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private String f219;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private String f220;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private String f221;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f222;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f223;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f224;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f225;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private int f226;

    public GoodsTagModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f222 = o.optInt("id");
            this.f223 = o.optString("name");
            this.f224 = o.optString("type");
            this.f225 = o.optString("created");
            this.f226 = o.optInt("item_num");
            this.f219 = o.optString("tag_url");
            this.f220 = o.optString("share_url");
            this.f221 = o.optString("desc");
        }
    }

    public int getId() {
        return this.f222;
    }

    public void setId(int id) {
        this.f222 = id;
    }

    public String getName() {
        return this.f223;
    }

    public void setName(String name) {
        this.f223 = name;
    }

    public String getType() {
        return this.f224;
    }

    public void setType(String type) {
        this.f224 = type;
    }

    public String getCreated() {
        return this.f225;
    }

    public void setCreated(String created) {
        this.f225 = created;
    }

    public int getItemNum() {
        return this.f226;
    }

    public void setItemNum(int itemNum) {
        this.f226 = itemNum;
    }

    public String getTagUrl() {
        return this.f219;
    }

    public void setTagUrl(String tagUrl) {
        this.f219 = tagUrl;
    }

    public String getShareUrl() {
        return this.f220;
    }

    public void setShareUrl(String shareUrl) {
        this.f220 = shareUrl;
    }

    public String getDesc() {
        return this.f221;
    }

    public void setDesc(String desc) {
        this.f221 = desc;
    }
}
