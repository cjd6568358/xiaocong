package com.baidu.uaq.agent.android.util;

import com.baidu.cloud.media.player.IMediaPlayer;
import com.baidu.uaq.agent.android.UAQ;
import com.baidu.uaq.agent.android.logging.a;
import com.baidu.uaq.agent.android.logging.b;
import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;

/* JADX INFO: compiled from: NetworkErrorUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static final a LOG = b.bg();
    private static final UAQ AGENT = UAQ.getInstance();

    public static int c(Exception e) {
        if (e instanceof ClientProtocolException) {
            return -1011;
        }
        if (e instanceof UnknownHostException) {
            return -1006;
        }
        if ((e instanceof SocketTimeoutException) || (e instanceof ConnectTimeoutException)) {
            return -1001;
        }
        if (e instanceof ConnectException) {
            return IMediaPlayer.MEDIA_ERROR_IO;
        }
        if (e instanceof MalformedURLException) {
            return -1000;
        }
        if (e instanceof SocketException) {
            return -2001;
        }
        if (e instanceof ProtocolException) {
            return -3001;
        }
        if (e instanceof FileNotFoundException) {
            return -4001;
        }
        return !(e instanceof SSLException) ? -1 : -1200;
    }
}
