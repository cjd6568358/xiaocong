package com.youzan.androidsdk.event;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class EventCenter {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private Map<String, Event> f95;

    public EventCenter() {
        this.f95 = null;
        this.f95 = new HashMap();
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private Event m66(String method) {
        return this.f95.get(method);
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private void m67(Event event) {
        this.f95.put(event.subscribe(), event);
    }

    public void subscribe(Event event) {
        if (!TextUtils.isEmpty(event.subscribe())) {
            m67(event);
        }
    }

    public boolean dispatch(Context context, String method, String param) {
        Event event = m66(method);
        if (event == null) {
            return false;
        }
        event.call(context, param);
        return true;
    }

    public List<Event> getEvents() {
        List<Event> eventList = new ArrayList<>();
        eventList.addAll(this.f95.values());
        return eventList;
    }
}
