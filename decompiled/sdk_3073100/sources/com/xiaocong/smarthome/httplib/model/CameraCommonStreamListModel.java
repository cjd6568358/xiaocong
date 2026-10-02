package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CameraCommonStreamListModel {
    private List<CommonStreamModel> list;
    private int noService;
    private String noServicePrompt;

    public List<CommonStreamModel> getList() {
        return this.list;
    }

    public void setList(List<CommonStreamModel> list) {
        this.list = list;
    }

    public int getNoService() {
        return this.noService;
    }

    public void setNoService(int noService) {
        this.noService = noService;
    }

    public String getNoServicePrompt() {
        return this.noServicePrompt;
    }

    public void setNoServicePrompt(String noServicePrompt) {
        this.noServicePrompt = noServicePrompt;
    }

    public class CommonStreamModel {
        private String beginTime;
        private String endTime;
        private String image;

        public CommonStreamModel() {
        }

        public String getBeginTime() {
            return this.beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return this.endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getImage() {
            return this.image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
