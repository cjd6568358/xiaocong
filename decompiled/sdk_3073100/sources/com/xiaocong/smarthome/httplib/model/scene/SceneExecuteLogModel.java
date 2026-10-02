package com.xiaocong.smarthome.httplib.model.scene;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SceneExecuteLogModel {
    private List<LogListModel> logList;

    public List<LogListModel> getLogList() {
        return this.logList;
    }

    public void setLogList(List<LogListModel> logList) {
        this.logList = logList;
    }

    public static class LogListModel {
        private List<ActionLogModel> actionLog;
        private long executeTime;
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getExecuteTime() {
            return this.executeTime;
        }

        public void setExecuteTime(long executeTime) {
            this.executeTime = executeTime;
        }

        public List<ActionLogModel> getActionLog() {
            return this.actionLog;
        }

        public void setActionLog(List<ActionLogModel> actionLog) {
            this.actionLog = actionLog;
        }

        public class ActionLogModel {
            private int code;
            private String desc;

            public ActionLogModel() {
            }

            public int getCode() {
                return this.code;
            }

            public String getDesc() {
                return this.desc;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
