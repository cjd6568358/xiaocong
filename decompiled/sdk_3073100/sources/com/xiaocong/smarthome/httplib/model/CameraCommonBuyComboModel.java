package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CameraCommonBuyComboModel {
    private List<BuyComboModel> list;

    public List<BuyComboModel> getList() {
        return this.list;
    }

    public void setList(List<BuyComboModel> list) {
        this.list = list;
    }

    public class BuyComboModel {
        private String price;
        private String templateItemId;
        private String title;

        public BuyComboModel() {
        }

        public String getPrice() {
            return this.price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTemplateItemId() {
            return this.templateItemId;
        }

        public void setTemplateItemId(String templateItemId) {
            this.templateItemId = templateItemId;
        }
    }
}
