package com.xiaocong.smarthome.sdk.openapi.bean;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCSystemMsgModel {
    private List<SystemMsgItemsModel> items;
    private int pageNum;
    private int pageSize;
    private int totalItem;

    public int getTotalItem() {
        return this.totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<SystemMsgItemsModel> getItems() {
        return this.items;
    }

    public void setItems(List<SystemMsgItemsModel> items) {
        this.items = items;
    }

    public static class SystemMsgItemsModel {
        private String content;
        private String datetime;
        private int directType;
        private String directUrl;
        private String image;
        private String subject;

        public String getDatetime() {
            return this.datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public int getDirectType() {
            return this.directType;
        }

        public void setDirectType(int directType) {
            this.directType = directType;
        }

        public String getSubject() {
            return this.subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getDirectUrl() {
            return this.directUrl;
        }

        public void setDirectUrl(String directUrl) {
            this.directUrl = directUrl;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return this.image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
