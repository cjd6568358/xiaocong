package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FilenameFilter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PersistanceFileNameFilter implements FilenameFilter {
    private final String fileExtension;

    public PersistanceFileNameFilter(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override // java.io.FilenameFilter
    public boolean accept(File dir, String name) {
        return name.endsWith(this.fileExtension);
    }
}
