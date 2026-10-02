package com.facebook.imagepipeline.producers;

import android.net.Uri;
import android.util.Base64;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DataFetchProducer extends LocalFetchProducer {
    public DataFetchProducer(PooledByteBufferFactory pooledByteBufferFactory, boolean fileDescriptorEnabled) {
        super(CallerThreadExecutor.getInstance(), pooledByteBufferFactory, fileDescriptorEnabled);
    }

    @Override // com.facebook.imagepipeline.producers.LocalFetchProducer
    protected EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException {
        byte[] data = getData(imageRequest.getSourceUri().toString());
        return getByteBufferBackedEncodedImage(new ByteArrayInputStream(data), data.length);
    }

    @Override // com.facebook.imagepipeline.producers.LocalFetchProducer
    protected String getProducerName() {
        return "DataFetchProducer";
    }

    static byte[] getData(String uri) {
        Preconditions.checkArgument(uri.substring(0, 5).equals("data:"));
        int commaPos = uri.indexOf(44);
        String dataStr = uri.substring(commaPos + 1, uri.length());
        if (isBase64(uri.substring(0, commaPos))) {
            return Base64.decode(dataStr, 0);
        }
        String str = Uri.decode(dataStr);
        return str.getBytes();
    }

    static boolean isBase64(String prefix) {
        if (!prefix.contains(";")) {
            return false;
        }
        String[] parameters = prefix.split(";");
        return parameters[parameters.length - 1].equals("base64");
    }
}
