package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HomeGroupModel {
    private String id;
    private String name;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HomeGroupModel that = (HomeGroupModel) o;
        if (this.id == null ? that.id != null : !this.id.equals(that.id)) {
            return false;
        }
        if (this.name != null) {
            return this.name.equals(that.name);
        }
        return that.name == null;
    }

    public int hashCode() {
        int result = this.id != null ? this.id.hashCode() : 0;
        return (result * 31) + (this.name != null ? this.name.hashCode() : 0);
    }
}
