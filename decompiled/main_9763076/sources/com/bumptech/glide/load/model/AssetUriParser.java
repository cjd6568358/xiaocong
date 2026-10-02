package com.bumptech.glide.load.model;

import android.net.Uri;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class AssetUriParser {
    private static final int ASSET_PREFIX_LENGTH = "file:///android_asset/".length();

    public static boolean isAssetUri(Uri uri) {
        return "file".equals(uri.getScheme()) && !uri.getPathSegments().isEmpty() && "android_asset".equals(uri.getPathSegments().get(0));
    }

    public static String toAssetPath(Uri uri) {
        return uri.toString().substring(ASSET_PREFIX_LENGTH);
    }
}
