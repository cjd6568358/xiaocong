package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ProductCategoryModel {
    private List<ProductModel> list;

    public List<ProductModel> getList() {
        return this.list;
    }

    public void setList(List<ProductModel> list) {
        this.list = list;
    }

    public class ProductModel {
        private String categoryCode;
        private String categoryId;
        private String categoryName;

        public ProductModel() {
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return this.categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public void setCategoryCode(String categoryCode) {
            this.categoryCode = categoryCode;
        }

        public String getCategoryId() {
            return this.categoryId;
        }

        public String getCategoryCode() {
            return this.categoryCode;
        }
    }
}
