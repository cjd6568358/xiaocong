package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ProductNetConfModel {
    private NetConfModel help;

    public NetConfModel getHelp() {
        return this.help;
    }

    public void setHelp(NetConfModel help) {
        this.help = help;
    }

    public class NetConfModel {
        private String faq;
        private String help;
        private String image;
        private String intro;
        private String netconfigCode;
        private String productId;
        private String xConfigKey;

        public NetConfModel() {
        }

        public String getNetconfigCode() {
            return this.netconfigCode;
        }

        public void setNetconfigCode(String netconfigCode) {
            this.netconfigCode = netconfigCode;
        }

        public String getProductId() {
            return this.productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getIntro() {
            return this.intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getHelp() {
            return this.help;
        }

        public void setHelp(String help) {
            this.help = help;
        }

        public String getImage() {
            return this.image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getxConfigKey() {
            return this.xConfigKey;
        }

        public void setxConfigKey(String xConfigKey) {
            this.xConfigKey = xConfigKey;
        }

        public String getFaq() {
            return this.faq;
        }

        public void setFaq(String faq) {
            this.faq = faq;
        }
    }
}
