package com.xiaocong.smarthome.httplib.model;

import com.xiaocong.smarthome.greendao.model.insert.SceneDB;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MainDevListModel {
    public List<DeviceListModel> devices;
    private String greetings;
    public List<HomeGroupModel> groups;
    private HomeBean home;
    public List<HomeListModel.HomeListBean> homes;
    public List<SceneDB> scenes;
    public WeatherModel weather;
    public RoomWeatherModel weatherHome;

    public String getGreetings() {
        return this.greetings;
    }

    public void setGreetings(String greetings) {
        this.greetings = greetings;
    }

    public HomeBean getHome() {
        return this.home;
    }

    public void setHome(HomeBean home) {
        this.home = home;
    }

    public WeatherModel getWeather() {
        return this.weather;
    }

    public void setWeather(WeatherModel weather) {
        this.weather = weather;
    }

    public RoomWeatherModel getWeatherHome() {
        return this.weatherHome;
    }

    public void setWeatherHome(RoomWeatherModel weatherHome) {
        this.weatherHome = weatherHome;
    }

    public List<DeviceListModel> getDevices() {
        return this.devices;
    }

    public void setDevices(List<DeviceListModel> devices) {
        this.devices = devices;
    }

    public List<HomeGroupModel> getGroups() {
        return this.groups;
    }

    public void setGroups(List<HomeGroupModel> groups) {
        this.groups = groups;
    }

    public List<SceneDB> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<SceneDB> scenes) {
        this.scenes = scenes;
    }

    public List<HomeListModel.HomeListBean> getHomes() {
        return this.homes;
    }

    public void setHomes(List<HomeListModel.HomeListBean> homes) {
        this.homes = homes;
    }

    public static class HomeBean {
        private String homeId;
        private String homeName;

        public String getHomeId() {
            return this.homeId;
        }

        public void setHomeId(String homeId) {
            this.homeId = homeId;
        }

        public String getHomeName() {
            return this.homeName;
        }

        public void setHomeName(String homeName) {
            this.homeName = homeName;
        }
    }
}
