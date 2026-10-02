package android.support.v4.net;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ConnectivityManagerCompat {
    private static final ConnectivityManagerCompatImpl IMPL;

    interface ConnectivityManagerCompatImpl {
        boolean isActiveNetworkMetered(ConnectivityManager connectivityManager);
    }

    static class ConnectivityManagerCompatBaseImpl implements ConnectivityManagerCompatImpl {
        ConnectivityManagerCompatBaseImpl() {
        }

        @Override // android.support.v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatImpl
        public boolean isActiveNetworkMetered(ConnectivityManager cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return true;
            }
            int type = info.getType();
            switch (type) {
                case 0:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 8:
                default:
                    return true;
                case 1:
                case 7:
                case 9:
                    return false;
            }
        }
    }

    static class ConnectivityManagerCompatApi16Impl extends ConnectivityManagerCompatBaseImpl {
        ConnectivityManagerCompatApi16Impl() {
        }

        @Override // android.support.v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatBaseImpl, android.support.v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatImpl
        public boolean isActiveNetworkMetered(ConnectivityManager cm) {
            return cm.isActiveNetworkMetered();
        }
    }

    static class ConnectivityManagerCompatApi24Impl extends ConnectivityManagerCompatApi16Impl {
        ConnectivityManagerCompatApi24Impl() {
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            IMPL = new ConnectivityManagerCompatApi24Impl();
        } else if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new ConnectivityManagerCompatApi16Impl();
        } else {
            IMPL = new ConnectivityManagerCompatBaseImpl();
        }
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
        return IMPL.isActiveNetworkMetered(cm);
    }
}
