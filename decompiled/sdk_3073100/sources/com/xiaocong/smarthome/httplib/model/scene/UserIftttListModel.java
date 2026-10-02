package com.xiaocong.smarthome.httplib.model.scene;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UserIftttListModel {
    private List<IftttModel> sceneList;

    public void setSceneList(List<IftttModel> sceneList) {
        this.sceneList = sceneList;
    }

    public List<IftttModel> getSceneList() {
        return this.sceneList;
    }

    public class IftttModel {
        private String backgroundImage;
        private String extendIntro;
        private String sceneIcon;
        private int source;
        private int status;
        private String triggerIcon;
        private String triggerId;
        private String triggerIntro;
        private String triggerName;
        private String type;

        public IftttModel() {
        }

        public String getSceneIcon() {
            return this.sceneIcon;
        }

        public void setSceneIcon(String sceneIcon) {
            this.sceneIcon = sceneIcon;
        }

        public void setTriggerName(String triggerName) {
            this.triggerName = triggerName;
        }

        public void setBackgroundImage(String backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        public void setTriggerId(String triggerId) {
            this.triggerId = triggerId;
        }

        public void setTriggerIcon(String triggerIcon) {
            this.triggerIcon = triggerIcon;
        }

        public void setTriggerIntro(String triggerIntro) {
            this.triggerIntro = triggerIntro;
        }

        public void setExtendIntro(String extendIntro) {
            this.extendIntro = extendIntro;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getTriggerName() {
            return this.triggerName;
        }

        public String getBackgroundImage() {
            return this.backgroundImage;
        }

        public String getTriggerId() {
            return this.triggerId;
        }

        public String getTriggerIntro() {
            return this.triggerIntro;
        }

        public String getExtendIntro() {
            return this.extendIntro;
        }

        public String getTriggerIcon() {
            return this.triggerIcon;
        }

        public String getType() {
            return this.type;
        }

        public int getSource() {
            return this.source;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
