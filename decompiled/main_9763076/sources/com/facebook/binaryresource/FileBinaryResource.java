package com.facebook.binaryresource;

import com.facebook.common.internal.Preconditions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FileBinaryResource implements BinaryResource {
    private final File mFile;

    private FileBinaryResource(File file) {
        this.mFile = (File) Preconditions.checkNotNull(file);
    }

    public File getFile() {
        return this.mFile;
    }

    @Override // com.facebook.binaryresource.BinaryResource
    public InputStream openStream() throws IOException {
        return new FileInputStream(this.mFile);
    }

    @Override // com.facebook.binaryresource.BinaryResource
    public long size() {
        return this.mFile.length();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof FileBinaryResource)) {
            return false;
        }
        FileBinaryResource that = (FileBinaryResource) obj;
        return this.mFile.equals(that.mFile);
    }

    public int hashCode() {
        return this.mFile.hashCode();
    }

    public static FileBinaryResource createOrNull(File file) {
        if (file != null) {
            return new FileBinaryResource(file);
        }
        return null;
    }
}
