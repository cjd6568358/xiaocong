package com.ixiaocong.smarthome.phone.android.widget.sharedialog;

import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Item {
    private Drawable icon;
    private int id;
    private String title;

    public Item() {
    }

    public Item(int id, String title, Drawable icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Drawable getIcon() {
        return this.icon;
    }
}
