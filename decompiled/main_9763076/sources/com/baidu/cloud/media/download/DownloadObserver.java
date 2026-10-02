package com.baidu.cloud.media.download;

import java.util.Observable;
import java.util.Observer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DownloadObserver implements Observer {
    public abstract void update(DownloadableVideoItem downloadableVideoItem);

    @Override // java.util.Observer
    public void update(Observable observable, Object obj) {
        if (observable instanceof DownloadableVideoItem) {
            update((DownloadableVideoItem) observable);
        }
    }
}
