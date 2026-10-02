package com.youzan.androidsdk.model.goods;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class GoodsDetailModel {

    /* JADX INFO: renamed from: ʳ, reason: contains not printable characters */
    private boolean f127;

    /* JADX INFO: renamed from: ʴ, reason: contains not printable characters */
    private List<GoodsSkuModel> f128;

    /* JADX INFO: renamed from: ʹ, reason: contains not printable characters */
    private String f129;

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private int f130;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private String f131;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private String f132;

    /* JADX INFO: renamed from: ʾ, reason: contains not printable characters */
    private String f133;

    /* JADX INFO: renamed from: ʿ, reason: contains not printable characters */
    private int f134;

    /* JADX INFO: renamed from: ˆ, reason: contains not printable characters */
    private List<GoodsImageModel> f135;

    /* JADX INFO: renamed from: ˇ, reason: contains not printable characters */
    private List<GoodsQrcodeModel> f136;

    /* JADX INFO: renamed from: ˈ, reason: contains not printable characters */
    private String f137;

    /* JADX INFO: renamed from: ˉ, reason: contains not printable characters */
    private boolean f138;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public String f139;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f140;

    /* JADX INFO: renamed from: ˌ, reason: contains not printable characters */
    private boolean f141;

    /* JADX INFO: renamed from: ˍ, reason: contains not printable characters */
    private boolean f142;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f143;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f144;

    /* JADX INFO: renamed from: ˑ, reason: contains not printable characters */
    private boolean f145;

    /* JADX INFO: renamed from: ˡ, reason: contains not printable characters */
    private List<GoodsTagModel> f146;

    /* JADX INFO: renamed from: ˮ, reason: contains not printable characters */
    private List<GoodsMessageModel> f147;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private String f148;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private String f149;

    /* JADX INFO: renamed from: ՙ, reason: contains not printable characters */
    private int f150;

    /* JADX INFO: renamed from: י, reason: contains not printable characters */
    private int f151;

    /* JADX INFO: renamed from: ـ, reason: contains not printable characters */
    private String f152;

    /* JADX INFO: renamed from: ٴ, reason: contains not printable characters */
    private double f153;

    /* JADX INFO: renamed from: ۥ, reason: contains not printable characters */
    private String f154;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private int f155;

    /* JADX INFO: renamed from: ᐠ, reason: contains not printable characters */
    private long f156;

    /* JADX INFO: renamed from: ᐣ, reason: contains not printable characters */
    private long f157;

    /* JADX INFO: renamed from: ᐧ, reason: contains not printable characters */
    private long f158;

    /* JADX INFO: renamed from: ᐨ, reason: contains not printable characters */
    private String f159;

    /* JADX INFO: renamed from: ᴵ, reason: contains not printable characters */
    private int f160;

    /* JADX INFO: renamed from: ᵎ, reason: contains not printable characters */
    private String f161;

    /* JADX INFO: renamed from: ᵔ, reason: contains not printable characters */
    private String f162;

    /* JADX INFO: renamed from: ᵢ, reason: contains not printable characters */
    private int f163;

    /* JADX INFO: renamed from: ⁱ, reason: contains not printable characters */
    private boolean f164;

    /* JADX INFO: renamed from: ﹳ, reason: contains not printable characters */
    private String f165;

    /* JADX INFO: renamed from: ﹶ, reason: contains not printable characters */
    private int f166;

    /* JADX INFO: renamed from: ﹺ, reason: contains not printable characters */
    private int f167;

    /* JADX INFO: renamed from: ｰ, reason: contains not printable characters */
    private String f168;

    /* JADX INFO: renamed from: ﾞ, reason: contains not printable characters */
    private String f169;

    public GoodsDetailModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f139 = o.optString("kdt_id");
            this.f140 = o.optString("num_iid");
            this.f143 = o.optString("alias");
            this.f144 = o.optString("title");
            this.f155 = o.optInt("cid");
            this.f130 = o.optInt("promotion_cid");
            this.f131 = o.optString("tag_ids");
            this.f132 = o.optString("desc");
            this.f148 = o.optString("origin_price");
            this.f149 = o.optString("outer_id");
            this.f133 = o.optString("outer_buy_url");
            this.f134 = o.optInt("buy_quota");
            this.f137 = o.optString("created");
            this.f138 = o.optBoolean("is_virtual");
            this.f141 = o.optBoolean("is_listing");
            this.f142 = o.optBoolean("is_lock");
            this.f145 = o.optBoolean("is_used");
            this.f152 = o.optString("product_type");
            this.f158 = o.optLong("auto_listing_time");
            this.f159 = o.optString("detail_url");
            this.f165 = o.optString("share_url");
            this.f169 = o.optString("pic_url");
            this.f129 = o.optString("pic_thumb_url");
            this.f150 = o.optInt("num");
            this.f151 = o.optInt("sold_num");
            this.f153 = o.optDouble("price");
            this.f160 = o.optInt("post_type");
            this.f161 = o.optString("post_fee");
            this.f162 = o.optString("delivery_template_fee");
            this.f163 = o.optInt("item_type");
            this.f164 = o.optBoolean("is_supplier_item");
            this.f166 = o.optInt("like_count");
            this.f167 = o.optInt("template_id");
            this.f168 = o.optString("template_title");
            this.f127 = o.optBoolean("join_level_discount");
            this.f154 = o.optString("sku_tree");
            this.f156 = o.optLong("item_validity_start");
            this.f157 = o.optLong("item_validity_end");
            JSONArray skusArray = o.optJSONArray("skus");
            if (skusArray != null && skusArray.length() > 0) {
                this.f128 = new ArrayList(skusArray.length());
                for (int i = 0; i < skusArray.length(); i++) {
                    this.f128.add(new GoodsSkuModel(skusArray.optJSONObject(i)));
                }
            }
            JSONArray itemImgsArray = o.optJSONArray("item_imgs");
            if (itemImgsArray != null && itemImgsArray.length() > 0) {
                this.f135 = new ArrayList(itemImgsArray.length());
                for (int i2 = 0; i2 < itemImgsArray.length(); i2++) {
                    this.f135.add(new GoodsImageModel(itemImgsArray.optJSONObject(i2)));
                }
            }
            JSONArray itemQrcodesArray = o.optJSONArray("item_qrcodes");
            if (itemQrcodesArray != null && itemQrcodesArray.length() > 0) {
                this.f136 = new ArrayList(itemQrcodesArray.length());
                for (int i3 = 0; i3 < itemQrcodesArray.length(); i3++) {
                    this.f136.add(new GoodsQrcodeModel(itemQrcodesArray.optJSONObject(i3)));
                }
            }
            JSONArray itemTagsArray = o.optJSONArray("item_tags");
            if (itemTagsArray != null && itemTagsArray.length() > 0) {
                this.f146 = new ArrayList(itemTagsArray.length());
                for (int i4 = 0; i4 < itemTagsArray.length(); i4++) {
                    this.f146.add(new GoodsTagModel(itemTagsArray.optJSONObject(i4)));
                }
            }
            JSONArray itemMessagesArray = o.optJSONArray("messages");
            if (itemMessagesArray != null && itemMessagesArray.length() > 0) {
                this.f147 = new ArrayList(itemMessagesArray.length());
                for (int i5 = 0; i5 < itemMessagesArray.length(); i5++) {
                    this.f147.add(new GoodsMessageModel(itemMessagesArray.optJSONObject(i5)));
                }
            }
        }
    }

    public String getAlias() {
        return this.f143;
    }

    public long getAutoListingTime() {
        return this.f158;
    }

    public int getBuyQuota() {
        return this.f134;
    }

    public int getCid() {
        return this.f155;
    }

    public String getCreated() {
        return this.f137;
    }

    public String getDeliveryTemplateFee() {
        return this.f162;
    }

    public String getDesc() {
        return this.f132;
    }

    public String getDetailUrl() {
        return this.f159;
    }

    public boolean isListing() {
        return this.f141;
    }

    public boolean isLock() {
        return this.f142;
    }

    public boolean isSupplierItem() {
        return this.f164;
    }

    public boolean isUsed() {
        return this.f145;
    }

    public boolean isVirtual() {
        return this.f138;
    }

    public List<GoodsImageModel> getItemImgs() {
        return this.f135;
    }

    public List<GoodsQrcodeModel> getItemQrcodes() {
        return this.f136;
    }

    public List<GoodsTagModel> getItemTags() {
        return this.f146;
    }

    public int getItemType() {
        return this.f163;
    }

    public boolean isJoinLevelDiscount() {
        return this.f127;
    }

    public int getLikeCount() {
        return this.f166;
    }

    public List<GoodsMessageModel> getMessages() {
        return this.f147;
    }

    public int getNum() {
        return this.f150;
    }

    public String getNumIid() {
        return this.f140;
    }

    public String getOriginPrice() {
        return this.f148;
    }

    public String getOuterBuyUrl() {
        return this.f133;
    }

    public String getOuterId() {
        return this.f149;
    }

    public String getPicThumbUrl() {
        return this.f129;
    }

    public String getPicUrl() {
        return this.f169;
    }

    public String getPostFee() {
        return this.f161;
    }

    public int getPostType() {
        return this.f160;
    }

    public double getPrice() {
        return this.f153;
    }

    public String getProductType() {
        return this.f152;
    }

    public int getPromotionCid() {
        return this.f130;
    }

    public String getShareUrl() {
        return this.f165;
    }

    public List<GoodsSkuModel> getSkus() {
        return this.f128;
    }

    public int getSoldNum() {
        return this.f151;
    }

    public String getTagIds() {
        return this.f131;
    }

    public int getTemplateId() {
        return this.f167;
    }

    public String getTemplateTitle() {
        return this.f168;
    }

    public String getTitle() {
        return this.f144;
    }

    public String getSkuTree() {
        return this.f154;
    }

    public String getKdtId() {
        return this.f139;
    }

    public long getItemValidityEnd() {
        return this.f157;
    }

    public long getItemValidityStart() {
        return this.f156;
    }
}
