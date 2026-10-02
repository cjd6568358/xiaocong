package com.bumptech.glide.signature;

import com.bumptech.glide.load.Key;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class EmptySignature implements Key {
    private static final EmptySignature EMPTY_KEY = new EmptySignature();

    public static EmptySignature obtain() {
        return EMPTY_KEY;
    }

    private EmptySignature() {
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
    }
}
