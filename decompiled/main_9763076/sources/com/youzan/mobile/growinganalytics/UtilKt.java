package com.youzan.mobile.growinganalytics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.tencent.android.tpush.common.Constants;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Util.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class UtilKt {
    private static final Integer[] NETWROK_2G_TYPES = {1, 4, 2, 7, 11};
    private static final Integer[] NETWORK_3G_TYPES = {6, 3, 5, 8, 9, 10, 12, 14, 15};
    private static final Integer[] NETWORK_4G_TYPES = {13};

    public static final boolean isEmpty(String $receiver) {
        return $receiver == null || StringsKt.isBlank($receiver);
    }

    public static final boolean hasInternetPermission(Context $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0 && $receiver.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0;
    }

    @SuppressLint({"MissingPermission"})
    public static final NetworkType getAPNType(Context $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        NetworkType networkType = (NetworkType) null;
        try {
            if ($receiver.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
                return NetworkType.NO_PERMISSION;
            }
            Object systemService = $receiver.getSystemService("connectivity");
            if (!(systemService instanceof ConnectivityManager)) {
                systemService = null;
            }
            ConnectivityManager connManager = (ConnectivityManager) systemService;
            if (connManager != null) {
                NetworkInfo info = connManager.getActiveNetworkInfo();
                if (info == null) {
                    networkType = NetworkType.UNKNOWN;
                } else {
                    info.getType();
                    switch (info.getType()) {
                        case 0:
                            int subType = info.getSubtype();
                            Object systemService2 = $receiver.getSystemService("phone");
                            if (!(systemService2 instanceof TelephonyManager)) {
                                systemService2 = null;
                            }
                            TelephonyManager telManager = (TelephonyManager) systemService2;
                            boolean check = telManager != null ? telManager.isNetworkRoaming() : true ? false : true;
                            if (check && ArraysKt.contains(NETWROK_2G_TYPES, Integer.valueOf(subType))) {
                                networkType = NetworkType.MOBILE_2G;
                                Logger.Companion.d("Network", "mobile network 2G");
                            } else if (check && ArraysKt.contains(NETWORK_3G_TYPES, Integer.valueOf(subType))) {
                                networkType = NetworkType.MOBILE_3G;
                                Logger.Companion.d("Network", "mobile network 3G");
                            } else if (check && ArraysKt.contains(NETWORK_4G_TYPES, Integer.valueOf(subType))) {
                                networkType = NetworkType.MOBILE_4G;
                                Logger.Companion.d("Network", "mobile network 4G");
                            } else {
                                networkType = NetworkType.MOBILE;
                            }
                            break;
                        case 1:
                            networkType = NetworkType.WIFI;
                            break;
                    }
                }
            }
            return networkType != null ? networkType : NetworkType.UNKNOWN;
        } catch (Exception e) {
            networkType = (NetworkType) null;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.TypeCastException */
    public static final String getIpAddress(boolean useIPV4) throws TypeCastException {
        String upperCase;
        String result = Constants.MAIN_VERSION_TAG;
        try {
            Iterable list = Collections.list(NetworkInterface.getNetworkInterfaces());
            Intrinsics.checkExpressionValueIsNotNull(list, "Collections.list(Network…e.getNetworkInterfaces())");
            Iterable netInterfaces = (List) list;
            Iterable $receiver$iv = netInterfaces;
            for (Object element$iv : $receiver$iv) {
                NetworkInterface netInterface = (NetworkInterface) element$iv;
                Iterable addresses = Collections.list(netInterface.getInetAddresses());
                Iterable $receiver$iv2 = addresses;
                for (Object element$iv2 : $receiver$iv2) {
                    InetAddress address = (InetAddress) element$iv2;
                    if (!address.isLoopbackAddress()) {
                        String addressStr = address.getHostAddress();
                        boolean isIPV4 = StringsKt.indexOf$default(addressStr, ":", 0, false, 6, null) < 0;
                        if (useIPV4 && isIPV4) {
                            Intrinsics.checkExpressionValueIsNotNull(addressStr, "addressStr");
                            result = addressStr;
                        } else if (!useIPV4 && !isIPV4) {
                            int delim = StringsKt.indexOf$default(addressStr, "%", 0, false, 6, null);
                            if (delim < 0) {
                                if (addressStr == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                }
                                upperCase = addressStr.toUpperCase();
                                Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
                            } else {
                                if (addressStr == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                }
                                String strSubstring = addressStr.substring(0, delim);
                                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                                if (strSubstring == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                }
                                upperCase = strSubstring.toUpperCase();
                                Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
                            }
                            result = upperCase;
                        } else {
                            result = Constants.MAIN_VERSION_TAG;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
