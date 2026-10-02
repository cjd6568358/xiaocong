package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SystemMsgModel {
    private List<MessagesBean> messages;
    private int page;
    private int pageSize;
    private int totalItem;
    private int totalPage;

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

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MessagesBean> getMessages() {
        return this.messages;
    }

    public void setMessages(List<MessagesBean> messages) {
        this.messages = messages;
    }

    public static class MessagesBean {
        private String content;
        private String datetime;
        private String icon;
        private String title;

        public String getDatetime() {
            return this.datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
