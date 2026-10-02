package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ProductListModel {
    private List<SubProductModel> products;

    public List<SubProductModel> getProducts() {
        return this.products;
    }

    public void setProducts(List<SubProductModel> products) {
        this.products = products;
    }

    public class SubProductModel {
        private String categoryId;
        private String deviceType;
        private String isGw;
        private String moduleId;
        private String network;
        private String partner;
        private String productBrand;
        private String productId;
        private String productImage;
        private String productName;

        public SubProductModel() {
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setProductBrand(String productBrand) {
            this.productBrand = productBrand;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public void setIsGw(String isGw) {
            this.isGw = isGw;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        public String getProductId() {
            return this.productId;
        }

        public String getProductName() {
            return this.productName;
        }

        public String getProductBrand() {
            return this.productBrand;
        }

        public String getProductImage() {
            return this.productImage;
        }

        public String getCategoryId() {
            return this.categoryId;
        }

        public String getDeviceType() {
            return this.deviceType;
        }

        public String getPartner() {
            return this.partner;
        }

        public String getIsGw() {
            return this.isGw;
        }

        public String getModuleId() {
            return this.moduleId;
        }

        public String getNetwork() {
            return this.network;
        }
    }
}
