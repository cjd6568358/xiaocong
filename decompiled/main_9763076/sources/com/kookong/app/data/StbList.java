package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StbList implements SerializableEx {
    private static final long serialVersionUID = 4459906454483857119L;
    public List<Stb> stbList = new ArrayList();

    public static class Remote implements SerializableEx {
        private static final long serialVersionUID = 7767960517395975003L;
        public int rid;
        public String rname;
    }

    public static class Stb implements SerializableEx {
        private static final long serialVersionUID = 8281594505773979269L;
        public int bid;
        public String bname;
        public List<Remote> remotes = new ArrayList();
    }
}
