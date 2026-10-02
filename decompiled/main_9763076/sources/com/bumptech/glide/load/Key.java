package com.bumptech.glide.load;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface Key {
    void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException;
}
