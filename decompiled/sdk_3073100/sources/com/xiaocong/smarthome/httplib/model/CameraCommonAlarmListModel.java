package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CameraCommonAlarmListModel {
    private int codeStream;
    private List<CameraAlarmModel> list;
    private String liveUrl;
    private int noService;
    private String noServicePrompt;
    private String queryId;
    private int setStream;

    public List<CameraAlarmModel> getList() {
        return this.list;
    }

    public void setList(List<CameraAlarmModel> list) {
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

    public String getQueryId() {
        return this.queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getLiveUrl() {
        return this.liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public int getCodeStream() {
        return this.codeStream;
    }

    public void setCodeStream(int codeStream) {
        this.codeStream = codeStream;
    }

    public int getSetStream() {
        return this.setStream;
    }

    public void setSetStream(int setStream) {
        this.setStream = setStream;
    }

    public class CameraAlarmModel {
        private int alarm;
        private String beginTime;
        private String endTime;
        private String streamId;
        private String streamTime;

        public CameraAlarmModel() {
        }

        public int getAlarm() {
            return this.alarm;
        }

        public void setAlarm(int alarm) {
            this.alarm = alarm;
        }

        public String getStreamId() {
            return this.streamId;
        }

        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }

        public String getStreamTime() {
            return this.streamTime;
        }

        public void setStreamTime(String streamTime) {
            this.streamTime = streamTime;
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
    }
}
