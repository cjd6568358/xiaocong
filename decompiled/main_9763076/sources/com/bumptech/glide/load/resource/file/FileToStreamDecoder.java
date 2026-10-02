package com.bumptech.glide.load.resource.file;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FileToStreamDecoder<T> implements ResourceDecoder<File, T> {
    private static final FileOpener DEFAULT_FILE_OPENER = new FileOpener();
    private final FileOpener fileOpener;
    private ResourceDecoder<InputStream, T> streamDecoder;

    public FileToStreamDecoder(ResourceDecoder<InputStream, T> streamDecoder) {
        this(streamDecoder, DEFAULT_FILE_OPENER);
    }

    FileToStreamDecoder(ResourceDecoder<InputStream, T> streamDecoder, FileOpener fileOpener) {
        this.streamDecoder = streamDecoder;
        this.fileOpener = fileOpener;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource<T> decode(File source, int width, int height) throws IOException {
        InputStream is = null;
        try {
            is = this.fileOpener.open(source);
            Resource<T> result = this.streamDecoder.decode(is, width, height);
            return result;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public String getId() {
        return Constants.MAIN_VERSION_TAG;
    }

    static class FileOpener {
        FileOpener() {
        }

        public InputStream open(File file) throws FileNotFoundException {
            return new FileInputStream(file);
        }
    }
}
