package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class AreaInfoModel {
    private List<DistrictListBean> districtList;

    public List<DistrictListBean> getDistrictList() {
        return this.districtList;
    }

    public void setDistrictList(List<DistrictListBean> districtList) {
        this.districtList = districtList;
    }

    public static class DistrictListBean {
        private String adcode;
        private String latitude;
        private String level;
        private String longitude;
        private String name;

        public String getAdcode() {
            return this.adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getLevel() {
            return this.level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLatitude() {
            return this.latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLongitude() {
            return this.longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
