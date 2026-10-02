package com.baidu.uaq.agent.android.crashes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ThreadInfo.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h extends com.baidu.uaq.agent.android.harvest.type.d {
    private boolean an;
    private long ao;
    private int ap;
    private StackTraceElement[] aq;
    private String ar;
    private String threadName;

    private h() {
    }

    public h(Throwable throwable) {
        this.an = true;
        this.ao = Thread.currentThread().getId();
        this.threadName = Thread.currentThread().getName();
        this.ap = Thread.currentThread().getPriority();
        this.aq = throwable.getStackTrace();
        this.ar = Thread.currentThread().getState().toString();
    }

    public h(Thread thread, StackTraceElement[] stackTrace) {
        this.an = false;
        this.ao = thread.getId();
        this.threadName = thread.getName();
        this.ap = thread.getPriority();
        this.aq = stackTrace;
        this.ar = thread.getState().toString();
    }

    public long N() {
        return this.ao;
    }

    public static List<h> b(Throwable throwable) {
        List<h> threads = new ArrayList<>();
        h crashedThread = new h(throwable);
        long crashedThreadId = crashedThread.N();
        threads.add(crashedThread);
        for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] threadStackTrace = entry.getValue();
            if (thread.getId() != crashedThreadId) {
                threads.add(new h(thread, threadStackTrace));
            }
        }
        return threads;
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONObject z() {
        JSONObject data = new JSONObject();
        try {
            data.put("crashed", Boolean.valueOf(this.an));
            data.put("state", this.ar);
            data.put("threadNumber", Long.valueOf(this.ao));
            data.put("threadId", this.threadName);
            data.put(LogFactory.PRIORITY_KEY, Integer.valueOf(this.ap));
            data.put("stack", O());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static h d(JSONObject jsonObject) {
        h info = new h();
        try {
            info.an = jsonObject.getBoolean("crashed");
            info.ar = jsonObject.getString("state");
            info.ao = jsonObject.getLong("threadNumber");
            info.threadName = jsonObject.getString("threadId");
            info.ap = jsonObject.getInt(LogFactory.PRIORITY_KEY);
            info.aq = b(jsonObject.getJSONArray("stack"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static StackTraceElement[] b(JSONArray jsonArray) {
        StackTraceElement[] stack = new StackTraceElement[jsonArray.length()];
        int i = 0;
        while (true) {
            try {
                int i2 = i;
                if (i2 >= jsonArray.length()) {
                    break;
                }
                String fileName = "unknown";
                if (jsonArray.getJSONObject(i2).optString("fileName") != null) {
                    fileName = jsonArray.getJSONObject(i2).optString("fileName");
                }
                String className = jsonArray.getJSONObject(i2).getString("className");
                String methodName = jsonArray.getJSONObject(i2).getString("methodName");
                int lineNumber = jsonArray.getJSONObject(i2).getInt("lineNumber");
                StackTraceElement stackTraceElement = new StackTraceElement(className, methodName, fileName, lineNumber);
                int i3 = i2 + 1;
                try {
                    stack[i2] = stackTraceElement;
                    i = i3 + 1;
                } catch (JSONException e) {
                    e = e;
                    e.printStackTrace();
                    return stack;
                }
            } catch (JSONException e2) {
                e = e2;
            }
        }
        return stack;
    }

    public static List<h> c(JSONArray jsonArray) {
        List<h> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(d(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private JSONArray O() {
        JSONArray data = new JSONArray();
        StackTraceElement[] arr = this.aq;
        for (StackTraceElement element : arr) {
            try {
                if (element != null) {
                    JSONObject elementJson = new JSONObject();
                    if (element.getFileName() != null) {
                        elementJson.put("fileName", element.getFileName());
                    }
                    elementJson.put("className", element.getClassName());
                    elementJson.put("methodName", element.getMethodName());
                    elementJson.put("lineNumber", Integer.valueOf(element.getLineNumber()));
                    data.put(elementJson);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
