package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SwitchRelationParametersModel {
    private List<ListBean> list;

    public List<ListBean> getList() {
        return this.list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String parameterId;
        private String parameterName;

        public String getParameterId() {
            return this.parameterId;
        }

        public void setParameterId(String parameterId) {
            this.parameterId = parameterId;
        }

        public String getParameterName() {
            return this.parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }
    }
}
