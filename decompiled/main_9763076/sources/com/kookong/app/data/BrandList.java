package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BrandList implements SerializableEx {
    private static final long serialVersionUID = -6358065578704438419L;
    public List<Brand> brandList = new ArrayList();
    public int hotCount = 0;

    public static class Brand implements SerializableEx, Comparable {
        private static final long serialVersionUID = 6579767242005830344L;
        public int brandId;
        public String cname;
        public String ename;
        public String initial;
        public String pinyin;

        @Override // java.lang.Comparable
        public int compareTo(Object o) {
            Brand brand = (Brand) o;
            return this.initial.compareTo(brand.initial);
        }
    }
}
