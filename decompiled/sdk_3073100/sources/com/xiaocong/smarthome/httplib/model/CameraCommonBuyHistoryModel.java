package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CameraCommonBuyHistoryModel {
    private List<CommonBuyHistoryModel> list;
    private int page;
    private int totalItem;
    private int totalPage;

    public List<CommonBuyHistoryModel> getList() {
        return this.list;
    }

    public void setList(List<CommonBuyHistoryModel> list) {
        this.list = list;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItem() {
        return this.totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public class CommonBuyHistoryModel {
        private String orderId;
        private String orderName;
        private String orderPrice;
        private int orderStatus;
        private String orderTime;

        public CommonBuyHistoryModel() {
        }

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderName() {
            return this.orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getOrderPrice() {
            return this.orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getOrderTime() {
            return this.orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public int getOrderStatus() {
            return this.orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }
    }
}
