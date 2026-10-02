package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CategoryList implements SerializableEx {
    public List<Category> list = new ArrayList();

    public static class Category implements SerializableEx {
        public short id;
        public String name = Constants.MAIN_VERSION_TAG;
    }
}
