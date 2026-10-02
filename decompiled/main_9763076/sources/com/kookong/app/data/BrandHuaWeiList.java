package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BrandHuaWeiList implements SerializableEx {
    private static final long serialVersionUID = -9203932753583051221L;
    public List<Brand> brandList = new ArrayList();
    public int hotCount = 0;

    public static class Brand implements SerializableEx, Comparable {
        private static final long serialVersionUID = -8143386744144886047L;
        public int brandId;
        public String engName;
        public String initial;
        public String name;
        public String pinyin;
        public String simName;
        public String traName;

        @Override // java.lang.Comparable
        public int compareTo(Object o) {
            Brand brand = (Brand) o;
            return this.initial.compareTo(brand.initial);
        }
    }
}
