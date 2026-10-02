package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CameraCommonPlayStreamModel {
    private long beginTime;
    private long endTime;
    private List<PlayStreamModel> list;
    private int noService;
    private String noServicePrompt;

    public long getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
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

    public List<PlayStreamModel> getList() {
        return this.list;
    }

    public void setList(List<PlayStreamModel> list) {
        this.list = list;
    }

    public class PlayStreamModel {
        private long beginTime;
        private long endTime;
        private String playUrl;

        public PlayStreamModel() {
        }

        public long getBeginTime() {
            return this.beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getEndTime() {
            return this.endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getPlayUrl() {
            return this.playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }
    }
}
