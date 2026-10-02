package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class RoomWeatherModel {
    private String buyingLink;
    private String ch2o;
    private String co2;
    private String humidity;
    private String pm2dot5;
    private String temperature;

    public String getBuyingLink() {
        return this.buyingLink;
    }

    public void setBuyingLink(String buyingLink) {
        this.buyingLink = buyingLink;
    }

    public void setCh2o(String ch2o) {
        this.ch2o = ch2o;
    }

    public void setPm2dot5(String pm2dot5) {
        this.pm2dot5 = pm2dot5;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String getCh2o() {
        return this.ch2o;
    }

    public String getPm2dot5() {
        return this.pm2dot5;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public String getCo2() {
        return this.co2;
    }
}
