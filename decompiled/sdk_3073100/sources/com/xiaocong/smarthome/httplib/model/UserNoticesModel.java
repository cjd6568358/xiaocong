package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UserNoticesModel {
    private List<NotifyModel> list;

    public void setList(List<NotifyModel> list) {
        this.list = list;
    }

    public List<NotifyModel> getList() {
        return this.list;
    }

    public class NotifyModel {
        private String content;
        private String datetime;
        private int directType;
        private String subject;
        private String url;

        public NotifyModel() {
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getDirectType() {
            return this.directType;
        }

        public void setDirectType(int directType) {
            this.directType = directType;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDatetime() {
            return this.datetime;
        }

        public String getSubject() {
            return this.subject;
        }

        public String getContent() {
            return this.content;
        }

        public String getUrl() {
            return this.url;
        }

        public String toString() {
            return "NotifyModel{datetime='" + this.datetime + "', subject='" + this.subject + "', type='" + this.directType + "', content='" + this.content + "', url='" + this.url + "'}";
        }
    }
}
