package com.baidu.uaq.agent.android.harvest.health;

import com.tencent.android.tpush.common.Constants;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AgentHealthException.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends com.baidu.uaq.agent.android.harvest.type.c {
    private String aX;
    private final AtomicLong aY;
    private Map<String, String> aZ;
    private StackTraceElement[] aq;
    private String message;
    private String threadName;

    public b(Exception e) {
        this(e, Thread.currentThread().getName());
    }

    public b(Exception e, String threadName) {
        this(e.getClass().getName(), e.getMessage(), threadName, e.getStackTrace());
    }

    public b(String exceptionClass, String message, String threadName, StackTraceElement[] stackTrace) {
        this(exceptionClass, message, threadName, stackTrace, null);
    }

    public b(String exceptionClass, String message, String threadName, StackTraceElement[] stackTrace, Map<String, String> extras) {
        this.aY = new AtomicLong(1L);
        this.aX = exceptionClass;
        this.message = message;
        this.threadName = threadName;
        this.aq = stackTrace;
        this.aZ = extras;
    }

    public void increment() {
        this.aY.getAndIncrement();
    }

    public String ay() {
        return this.aX;
    }

    public StackTraceElement[] getStackTrace() {
        return this.aq;
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONArray U() {
        JSONArray data = new JSONArray();
        try {
            data.put(0, this.aX);
            data.put(1, this.message != null ? this.message : Constants.MAIN_VERSION_TAG);
            data.put(2, this.threadName);
            data.put(3, az());
            data.put(4, this.aY.get());
            data.put(5, aA());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    private JSONArray az() {
        JSONArray stack = new JSONArray();
        for (StackTraceElement element : this.aq) {
            stack.put(element.toString());
        }
        return stack;
    }

    private JSONObject aA() {
        JSONObject data = new JSONObject();
        try {
            if (this.aZ != null) {
                for (Map.Entry<String, String> entry : this.aZ.entrySet()) {
                    data.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
