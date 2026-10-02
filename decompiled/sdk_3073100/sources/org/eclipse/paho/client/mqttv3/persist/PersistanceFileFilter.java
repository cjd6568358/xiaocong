package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FileFilter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PersistanceFileFilter implements FileFilter {
    private final String fileExtension;

    public PersistanceFileFilter(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override // java.io.FileFilter
    public boolean accept(File pathname) {
        return pathname.getName().endsWith(this.fileExtension);
    }
}
