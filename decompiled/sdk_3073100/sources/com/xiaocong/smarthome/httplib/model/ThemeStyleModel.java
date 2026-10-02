package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ThemeStyleModel {
    private boolean isSelected;
    private int themeImage;
    private String themeName;

    public ThemeStyleModel(String themeName, int themeImage, boolean isSelected) {
        this.themeName = themeName;
        this.themeImage = themeImage;
        this.isSelected = isSelected;
    }

    public String getThemeName() {
        return this.themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public int getThemeImage() {
        return this.themeImage;
    }

    public void setThemeImage(int themeImage) {
        this.themeImage = themeImage;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}
