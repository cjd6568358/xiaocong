package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WeatherModel {
    private String airText;
    private String airValue;
    private String humidity;
    private String icon;
    private String pm25;
    private String temp;
    private String text;
    private String windDir;
    private String windLevel;

    public void setAirText(String airText) {
        this.airText = airText;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public void setAirValue(String airValue) {
        this.airValue = airValue;
    }

    public void setWindLevel(String windLevel) {
        this.windLevel = windLevel;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getAirText() {
        return this.airText;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getTemp() {
        return this.temp;
    }

    public String getPm25() {
        return this.pm25;
    }

    public String getAirValue() {
        return this.airValue;
    }

    public String getWindLevel() {
        return this.windLevel;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public String getText() {
        return this.text;
    }

    public String getWindDir() {
        return this.windDir;
    }
}
