package com.xiaocong.smarthome.sdk.openapi.bean;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCSDKProductSubTypeModel {
    private List<SubProductModel> products;

    public List<SubProductModel> getProducts() {
        return this.products;
    }

    public void setProducts(List<SubProductModel> products) {
        this.products = products;
    }

    public static class SubProductModel {
        private String categoryId;
        private String deviceType;
        private String isGw;
        private String moduleId;
        private String network;
        private Object partner;
        private String productBrand;
        private String productId;
        private String productImage;
        private String productName;

        public String getDeviceType() {
            return this.deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getProductImage() {
            return this.productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getProductId() {
            return this.productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Object getPartner() {
            return this.partner;
        }

        public void setPartner(Object partner) {
            this.partner = partner;
        }

        public String getProductBrand() {
            return this.productBrand;
        }

        public void setProductBrand(String productBrand) {
            this.productBrand = productBrand;
        }

        public String getIsGw() {
            return this.isGw;
        }

        public void setIsGw(String isGw) {
            this.isGw = isGw;
        }

        public String getModuleId() {
            return this.moduleId;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }

        public String getCategoryId() {
            return this.categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getProductName() {
            return this.productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getNetwork() {
            return this.network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }
    }
}
