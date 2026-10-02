package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ModelList implements SerializableEx {
    private static final long serialVersionUID = 4162782270534101145L;
    public List<Model> modelList = new ArrayList();

    public static class Model implements SerializableEx {
        private static final long serialVersionUID = 5327479114610180272L;
        public int bid;
        public String bname;
        public String mname;
        public int remoteId;
    }
}
