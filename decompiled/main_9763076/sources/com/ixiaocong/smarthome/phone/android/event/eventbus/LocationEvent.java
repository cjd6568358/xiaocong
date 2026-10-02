package com.ixiaocong.smarthome.phone.android.event.eventbus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LocationEvent {
    private String adCode;
    private String cityName;
    private String district;
    private String lat;
    private String lot;
    private String province;
    private String street;

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLot() {
        return this.lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getAdCode() {
        return this.adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }
}
