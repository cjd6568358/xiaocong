package com.facebook.common.util;

import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import org.apache.http.HttpHost;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UriUtil {
    private static final String LOCAL_CONTACT_IMAGE_PREFIX = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "display_photo").getPath();

    public static boolean isNetworkUri(Uri uri) {
        String scheme = getSchemeOrNull(uri);
        return "https".equals(scheme) || HttpHost.DEFAULT_SCHEME_NAME.equals(scheme);
    }

    public static boolean isLocalFileUri(Uri uri) {
        String scheme = getSchemeOrNull(uri);
        return "file".equals(scheme);
    }

    public static boolean isLocalContentUri(Uri uri) {
        String scheme = getSchemeOrNull(uri);
        return "content".equals(scheme);
    }

    public static boolean isLocalContactUri(Uri uri) {
        return isLocalContentUri(uri) && "com.android.contacts".equals(uri.getAuthority()) && !uri.getPath().startsWith(LOCAL_CONTACT_IMAGE_PREFIX);
    }

    public static boolean isLocalCameraUri(Uri uri) {
        String uriString = uri.toString();
        return uriString.startsWith(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()) || uriString.startsWith(MediaStore.Images.Media.INTERNAL_CONTENT_URI.toString());
    }

    public static boolean isLocalAssetUri(Uri uri) {
        String scheme = getSchemeOrNull(uri);
        return "asset".equals(scheme);
    }

    public static boolean isLocalResourceUri(Uri uri) {
        String scheme = getSchemeOrNull(uri);
        return "res".equals(scheme);
    }

    public static boolean isDataUri(Uri uri) {
        return "data".equals(getSchemeOrNull(uri));
    }

    public static String getSchemeOrNull(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.getScheme();
    }
}
