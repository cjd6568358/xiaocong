package com.youzan.androidsdk.model.trade;

import android.text.TextUtils;
import com.youzan.androidsdk.model.goods.GoodsSkuItemModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeCartGoodsModel {

    /* JADX INFO: renamed from: ʹ, reason: contains not printable characters */
    private long f305;

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private String f306;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private String f307;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private String f308;

    /* JADX INFO: renamed from: ʾ, reason: contains not printable characters */
    private int f309;

    /* JADX INFO: renamed from: ʿ, reason: contains not printable characters */
    private String f310;

    /* JADX INFO: renamed from: ˈ, reason: contains not printable characters */
    private String f311;

    /* JADX INFO: renamed from: ˉ, reason: contains not printable characters */
    private String f312;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private boolean f313 = true;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f314;

    /* JADX INFO: renamed from: ˌ, reason: contains not printable characters */
    private String f315;

    /* JADX INFO: renamed from: ˍ, reason: contains not printable characters */
    private int f316;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f317;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private int f318;

    /* JADX INFO: renamed from: ˑ, reason: contains not printable characters */
    private String f319;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private String f320;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private long f321;

    /* JADX INFO: renamed from: ՙ, reason: contains not printable characters */
    private String f322;

    /* JADX INFO: renamed from: י, reason: contains not printable characters */
    private int f323;

    /* JADX INFO: renamed from: ـ, reason: contains not printable characters */
    private String f324;

    /* JADX INFO: renamed from: ٴ, reason: contains not printable characters */
    private String f325;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private int f326;

    /* JADX INFO: renamed from: ᐧ, reason: contains not printable characters */
    private long f327;

    /* JADX INFO: renamed from: ᐨ, reason: contains not printable characters */
    private String f328;

    /* JADX INFO: renamed from: ᴵ, reason: contains not printable characters */
    private String f329;

    /* JADX INFO: renamed from: ᵎ, reason: contains not printable characters */
    private int f330;

    /* JADX INFO: renamed from: ᵔ, reason: contains not printable characters */
    private long f331;

    /* JADX INFO: renamed from: ᵢ, reason: contains not printable characters */
    private long f332;

    /* JADX INFO: renamed from: ⁱ, reason: contains not printable characters */
    private int f333;

    /* JADX INFO: renamed from: ﹳ, reason: contains not printable characters */
    private long f334;

    /* JADX INFO: renamed from: ﾞ, reason: contains not printable characters */
    private long f335;

    public TradeCartGoodsModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f314 = o.optString("thumb_url");
            this.f317 = o.optString("discount_price");
            this.f318 = o.optInt("num");
            this.f326 = o.optInt("stock_num");
            this.f306 = o.optString("discount");
            this.f307 = o.optString("title");
            this.f308 = o.optString("activity_alias");
            this.f320 = o.optString("platform");
            this.f321 = o.optLong("kdt_id");
            this.f309 = o.optInt("sub_type");
            this.f310 = o.optString("alias");
            this.f311 = o.optString("nobody");
            this.f312 = o.optString("sku");
            this.f316 = o.optInt("direct_seller");
            this.f319 = o.optString("ext");
            this.f324 = o.optString("store_id");
            this.f327 = o.optLong("pay_price");
            this.f328 = o.optString("error_msg");
            this.f334 = o.optLong("create_time");
            this.f335 = o.optLong("goods_id");
            this.f305 = o.optLong("sku_id");
            this.f322 = o.optString("attachment_url");
            this.f323 = o.optInt("service_type");
            this.f325 = o.optString("messages");
            this.f329 = o.optString("support_express_type");
            this.f330 = o.optInt("goods_type");
            this.f331 = o.optLong("updated_time");
            this.f332 = o.optLong("channel_id");
            this.f333 = o.optInt("limit_num");
            if (!TextUtils.isEmpty(this.f312) && !this.f312.toLowerCase().equals("null")) {
                JSONArray array = new JSONArray(this.f312);
                int length = array.length();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    if (i > 0) {
                        builder.append(", ");
                    }
                    GoodsSkuItemModel item = new GoodsSkuItemModel(array.getJSONObject(i));
                    builder.append(item.getvDesc());
                }
                this.f315 = builder.toString();
            }
        }
    }

    public String getActivityAlias() {
        return this.f308;
    }

    public void setActivityAlias(String activityAlias) {
        this.f308 = activityAlias;
    }

    public String getAlias() {
        return this.f310;
    }

    public void setAlias(String alias) {
        this.f310 = alias;
    }

    public String getAttachmentUrl() {
        return this.f322;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.f322 = attachmentUrl;
    }

    public long getChannelId() {
        return this.f332;
    }

    public void setChannelId(long channelId) {
        this.f332 = channelId;
    }

    public long getCreateTime() {
        return this.f334;
    }

    public void setCreateTime(long createTime) {
        this.f334 = createTime;
    }

    public int getDirectSeller() {
        return this.f316;
    }

    public void setDirectSeller(int directSeller) {
        this.f316 = directSeller;
    }

    public String getDiscount() {
        return this.f306;
    }

    public void setDiscount(String discount) {
        this.f306 = discount;
    }

    public String getDiscountPrice() {
        return this.f317;
    }

    public void setDiscountPrice(String discountPrice) {
        this.f317 = discountPrice;
    }

    public String getErrorMsg() {
        return this.f328;
    }

    public void setErrorMsg(String errorMsg) {
        this.f328 = errorMsg;
    }

    public String getExt() {
        return this.f319;
    }

    public void setExt(String ext) {
        this.f319 = ext;
    }

    public long getGoodsId() {
        return this.f335;
    }

    public void setGoodsId(long goodsId) {
        this.f335 = goodsId;
    }

    public int getGoodsType() {
        return this.f330;
    }

    public void setGoodsType(int goodsType) {
        this.f330 = goodsType;
    }

    public long getKdtId() {
        return this.f321;
    }

    public void setKdtId(long kdtId) {
        this.f321 = kdtId;
    }

    public int getLimitNum() {
        return this.f333;
    }

    public void setLimitNum(int limitNum) {
        this.f333 = limitNum;
    }

    public String getMessages() {
        return this.f325;
    }

    public void setMessages(String messages) {
        this.f325 = messages;
    }

    public String getNobody() {
        return this.f311;
    }

    public void setNobody(String nobody) {
        this.f311 = nobody;
    }

    public int getNum() {
        return this.f318;
    }

    public void setNum(int num) {
        this.f318 = num;
    }

    public long getPayPrice() {
        return this.f327;
    }

    public void setPayPrice(long payPrice) {
        this.f327 = payPrice;
    }

    public String getPlatform() {
        return this.f320;
    }

    public void setPlatform(String platform) {
        this.f320 = platform;
    }

    public int getServiceType() {
        return this.f323;
    }

    public void setServiceType(int serviceType) {
        this.f323 = serviceType;
    }

    public String getSku() {
        return this.f312;
    }

    public void setSku(String sku) {
        this.f312 = sku;
    }

    public String getSkuDesc() {
        return this.f315;
    }

    public void setSkuDesc(String skuDesc) {
        this.f315 = skuDesc;
    }

    public long getSkuId() {
        return this.f305;
    }

    public void setSkuId(long skuId) {
        this.f305 = skuId;
    }

    public int getStockNum() {
        return this.f326;
    }

    public void setStockNum(int stockNum) {
        this.f326 = stockNum;
    }

    public String getStoreId() {
        return this.f324;
    }

    public void setStoreId(String storeId) {
        this.f324 = storeId;
    }

    public int getSubType() {
        return this.f309;
    }

    public void setSubType(int subType) {
        this.f309 = subType;
    }

    public String getSupportExpressType() {
        return this.f329;
    }

    public void setSupportExpressType(String supportExpressType) {
        this.f329 = supportExpressType;
    }

    public String getThumbUrl() {
        return this.f314;
    }

    public void setThumbUrl(String thumbUrl) {
        this.f314 = thumbUrl;
    }

    public String getTitle() {
        return this.f307;
    }

    public void setTitle(String title) {
        this.f307 = title;
    }

    public long getUpdatedTime() {
        return this.f331;
    }

    public void setUpdatedTime(long updatedTime) {
        this.f331 = updatedTime;
    }

    public boolean isSelected() {
        return this.f313;
    }

    public void setSelected(boolean selected) {
        this.f313 = selected;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TradeCartGoodsModel model = (TradeCartGoodsModel) o;
        if (this.f321 == model.f321 && this.f335 == model.f335) {
            return this.f305 == model.f305;
        }
        return false;
    }

    public int hashCode() {
        int result = (int) (this.f321 ^ (this.f321 >>> 32));
        return (((result * 31) + ((int) (this.f335 ^ (this.f335 >>> 32)))) * 31) + ((int) (this.f305 ^ (this.f305 >>> 32)));
    }
}
