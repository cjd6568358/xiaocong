package com.bumptech.glide.load.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FileDescriptorLocalUriFetcher extends LocalUriFetcher<ParcelFileDescriptor> {
    public FileDescriptorLocalUriFetcher(Context context, Uri uri) {
        super(context, uri);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.bumptech.glide.load.data.LocalUriFetcher
    public ParcelFileDescriptor loadResource(Uri uri, ContentResolver contentResolver) throws FileNotFoundException {
        return contentResolver.openAssetFileDescriptor(uri, "r").getParcelFileDescriptor();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bumptech.glide.load.data.LocalUriFetcher
    public void close(ParcelFileDescriptor data) throws IOException {
        data.close();
    }
}
