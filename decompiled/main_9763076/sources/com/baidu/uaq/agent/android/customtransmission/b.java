package com.baidu.uaq.agent.android.customtransmission;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: UploadConfigureStorage.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static final ConcurrentHashMap<String, APMUploadConfigure> au = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, ArrayList<a>> av = new ConcurrentHashMap<>();

    public static synchronized void a(APMUploadConfigure uploadConfigure) {
        if (com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().aD() < 1) {
            LOG.error("Agent has shutdown when add Upload Configure");
        } else {
            au.put(uploadConfigure.getUploadName(), uploadConfigure);
        }
    }

    @Deprecated
    static synchronized void e(String uploadName) {
        au.remove(uploadName);
    }

    static synchronized void b(String uploadName, String block) {
        if (com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().aD() < 1) {
            LOG.error("Agent has shutdown when add Upload Block");
        } else {
            synchronized (av) {
                ArrayList<a> blockArray = av.get(uploadName);
                if (blockArray == null) {
                    blockArray = new ArrayList<>();
                }
                blockArray.add(new a(block));
                av.put(uploadName, blockArray);
            }
        }
    }

    public static synchronized ArrayList<String> a(String uploadName, Boolean isRetransmission) {
        ArrayList<String> blockArrayStr;
        blockArrayStr = new ArrayList<>();
        ArrayList<a> blockArrayNew = new ArrayList<>();
        synchronized (av) {
            ArrayList<a> blockArray = av.get(uploadName);
            if (blockArray == null || blockArray.size() == 0) {
                blockArrayStr = null;
            } else if (isRetransmission.booleanValue()) {
                for (int i = 0; i < blockArray.size(); i++) {
                    int retryCount = blockArray.get(i).getRetryCount();
                    if (retryCount < 3) {
                        blockArrayNew.add(blockArray.get(i));
                        blockArray.get(i).Q();
                        blockArrayStr.add(blockArray.get(i).P());
                    }
                }
                a(uploadName, blockArrayNew);
            } else {
                for (a blockData : blockArray) {
                    blockArrayStr.add(blockData.P());
                }
                av.remove(uploadName);
            }
        }
        return blockArrayStr;
    }

    public static synchronized ConcurrentHashMap<String, APMUploadConfigure> R() {
        return au;
    }

    public static synchronized ConcurrentHashMap<String, ArrayList<a>> S() {
        return av;
    }

    static synchronized void a(String uploadName, ArrayList<a> blockDatas) {
        synchronized (av) {
            try {
                if (blockDatas != null) {
                    av.put(uploadName, blockDatas);
                } else {
                    av.remove(uploadName);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static synchronized void b(String uploadName, Boolean isRetransmission) {
        ArrayList<a> blockArray = av.get(uploadName);
        if (isRetransmission.booleanValue()) {
            ArrayList<a> blockArrayNew = new ArrayList<>();
            for (a blockData : blockArray) {
                int retryCount = blockData.getRetryCount();
                if (retryCount == 0) {
                    blockArrayNew.add(blockData);
                }
            }
            a(uploadName, blockArrayNew);
        }
    }
}
