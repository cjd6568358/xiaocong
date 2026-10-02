package com.facebook.react;

import android.app.Service;
import com.facebook.react.jstasks.HeadlessJsTaskEventListener;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class HeadlessJsTaskService extends Service implements HeadlessJsTaskEventListener {
    private final Set<Integer> mActiveTasks = new CopyOnWriteArraySet();
}
