package com.youzan.spiderman.c.e;

import android.content.Context;
import com.youzan.spiderman.c.b.g;
import com.youzan.spiderman.utils.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: compiled from: SyncResourceManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class f {
    private Context a;
    private g b;
    private List<c> c = Collections.synchronizedList(new ArrayList());
    private Set<String> d = new HashSet();

    public void a(Context context, g syncConfig) {
        this.a = context.getApplicationContext();
        this.b = syncConfig;
    }

    public void a(List<String> resourceList) {
        if (resourceList != null && !resourceList.isEmpty()) {
            Set<String> resources = new HashSet<>(resourceList);
            a(resources);
        }
    }

    public void a(Set<String> resourceSet) {
        if (resourceSet != null && !resourceSet.isEmpty()) {
            c syncDownloadJob = new c(resourceSet, this.a, this.b, new a() { // from class: com.youzan.spiderman.c.e.f.1
                @Override // com.youzan.spiderman.c.e.a
                public void a(c job, Set<String> notDownloadedResource) {
                    f.this.a(job, notDownloadedResource);
                }
            });
            this.c.add(syncDownloadJob);
            com.youzan.spiderman.a.c.a().a(syncDownloadJob);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(c syncDownloadJob, Set<String> notDownloadedResource) {
        this.c.remove(syncDownloadJob);
        int newNotSize = notDownloadedResource.size();
        synchronized (this.d) {
            int size = this.d.size();
            if (newNotSize + size > 300) {
                int removeCount = (newNotSize + size) - 300;
                Logger.e("SyncResourceManager", "not download resource list is larger than 300, remove some count:" + removeCount, new Object[0]);
                try {
                    Iterator<String> iter = this.d.iterator();
                    while (true) {
                        try {
                            int removeCount2 = removeCount;
                            if (!iter.hasNext()) {
                                break;
                            }
                            removeCount = removeCount2 - 1;
                            if (removeCount2 <= 0) {
                                break;
                            }
                            iter.next();
                            iter.remove();
                            Logger.e("SyncResourceManager", "remove not downloads exception", e);
                        } catch (Exception e) {
                            e = e;
                            Logger.e("SyncResourceManager", "remove not downloads exception", e);
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }
            this.d.addAll(notDownloadedResource);
            Logger.i("SyncResourceManager", "下载队列剩余: " + size, new Object[0]);
            if (this.c.isEmpty()) {
                b();
            }
        }
    }

    private void b() {
        b resourceListPref = new b();
        resourceListPref.a(this.d);
        try {
            com.youzan.spiderman.cache.d.a(resourceListPref, "resource_list_pref");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<String> a() {
        b resourceListPref = (b) com.youzan.spiderman.cache.d.a(b.class, "resource_list_pref");
        Set<String> resources = resourceListPref.a();
        if (resources == null) {
            return new HashSet<>();
        }
        return resources;
    }
}
