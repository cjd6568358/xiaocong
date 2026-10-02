package com.baidu.uaq.agent.android.harvest.multiharvest;

import com.baidu.uaq.agent.android.UAQ;
import com.baidu.uaq.agent.android.customtransmission.APMUploadConfigure;
import com.baidu.uaq.agent.android.util.g;
import com.baidu.uaq.agent.android.util.k;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: MultiHarvestConnection.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private HttpClient bm;
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static final UAQ AGENT = UAQ.getInstance();

    private void aE() {
        int connectionTimeout = (int) TimeUnit.MILLISECONDS.convert(20L, TimeUnit.SECONDS);
        BasicHttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
        HttpConnectionParams.setSoTimeout(params, connectionTimeout);
        HttpConnectionParams.setTcpNoDelay(params, true);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager connMgr = new ThreadSafeClientConnManager(params, schReg);
        this.bm = new DefaultHttpClient(connMgr, params);
    }

    public com.baidu.uaq.agent.android.harvest.b a(String data, APMUploadConfigure apmUploadConfigure) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        k.Y("MultiHarvest sendData = " + data);
        String dataOpt = data.replace("\\/", "/");
        HttpPost dataPost = b(dataOpt, apmUploadConfigure);
        if (dataPost != null) {
            long harvestSize = dataPost.getEntity().getContentLength();
            LOG.E("HarvestSize = " + harvestSize + "bytes");
            com.baidu.uaq.agent.android.stats.a.br().d("Supportability/AgentHealth/Collector/HarvestSize", harvestSize);
        }
        return a(dataPost);
    }

    public com.baidu.uaq.agent.android.harvest.b v(String data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        k.Y("MultiHarvest sendData = " + data);
        String dataOpt = data.replace("\\/", "/");
        HttpPost dataPost = w(dataOpt);
        if (dataPost != null) {
            long harvestSize = dataPost.getEntity().getContentLength();
            LOG.E("HarvestSize = " + harvestSize + "bytes");
            com.baidu.uaq.agent.android.stats.a.br().d("Supportability/AgentHealth/Collector/HarvestSize", harvestSize);
        }
        return a(dataPost);
    }

    private HttpPost b(String message, APMUploadConfigure apmUploadConfigure) {
        return a(message, "deflate", apmUploadConfigure);
    }

    private HttpPost w(String message) {
        return a(aH(), message, "deflate");
    }

    private HttpPost a(String message, String contentEncoding, APMUploadConfigure apmUploadConfigure) throws Throwable {
        String uri = apmUploadConfigure.getUrl();
        LOG.E("MultiHarvest POST <uri> = " + uri);
        HttpPost post = new HttpPost(uri);
        Map<String, String> dic = apmUploadConfigure.getHeaderMap();
        if (dic.size() <= 0) {
            LOG.error("Http header is null");
        }
        for (Map.Entry<String, String> entry : dic.entrySet()) {
            post.addHeader(entry.getKey(), entry.getValue());
        }
        if (AGENT.getConfig().getAPIKey() == null) {
            LOG.error("Cannot create POST without an Application Token.");
            return null;
        }
        byte[] deflated = x(message);
        post.setEntity(new ByteArrayEntity(deflated));
        return post;
    }

    private HttpPost a(String uri, String message, String contentEncoding) throws Throwable {
        String uuidHeader;
        LOG.E("MultiHarvest POST <uri> = " + uri);
        HttpPost post = new HttpPost(uri);
        post.addHeader(HTTP.CONTENT_TYPE, "application/json");
        post.addHeader(HTTP.CONTENT_ENCODING, contentEncoding);
        String property = System.getProperty("http.agent");
        if (property != null) {
            post.addHeader(HTTP.USER_AGENT, System.getProperty("http.agent").trim());
        }
        post.addHeader("X-UAQ-WanType", com.baidu.uaq.agent.android.a.c());
        try {
            String uuidHeader2 = AGENT.getConfig().getCuid();
            if (uuidHeader2.equals("null")) {
                com.baidu.uaq.agent.android.harvest.bean.c deviceInformation = com.baidu.uaq.agent.android.a.a().e();
                if (deviceInformation.aq()) {
                    uuidHeader = deviceInformation.getDeviceId();
                } else {
                    uuidHeader = com.baidu.uaq.agent.android.util.d.P(deviceInformation.getDeviceId());
                }
                post.addHeader("X-UAQ-UUID", uuidHeader);
            } else {
                post.addHeader("X-UAQ-UUID", com.baidu.uaq.agent.android.util.d.P(uuidHeader2));
            }
            post.addHeader("X-UAQ-Channel", com.baidu.uaq.agent.android.util.d.P(AGENT.getConfig().getChannel()));
        } catch (Exception e) {
            LOG.a("Caught error while createPost AES: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        if (AGENT.getConfig().getAPIKey() == null) {
            LOG.error("Cannot create POST without an Application Token.");
            return null;
        }
        post.addHeader("X-App-License-Key", AGENT.getConfig().getAPIKey());
        if ("deflate".equals(contentEncoding)) {
            byte[] deflated = x(message);
            post.setEntity(new ByteArrayEntity(deflated));
            return post;
        }
        try {
            post.setEntity(new StringEntity(message, "utf-8"));
            return post;
        } catch (UnsupportedEncodingException e2) {
            LOG.error("UTF-8 is unsupported");
            throw new IllegalArgumentException(e2);
        }
    }

    private com.baidu.uaq.agent.android.harvest.b a(HttpPost post) {
        if (post == null) {
            LOG.error("Failed to send POST to collector");
            return null;
        }
        com.baidu.uaq.agent.android.harvest.b harvestResponse = new com.baidu.uaq.agent.android.harvest.b();
        try {
            com.baidu.uaq.agent.android.stats.b timer = new com.baidu.uaq.agent.android.stats.b();
            timer.bu();
            if (this.bm == null) {
                aE();
            }
            HttpResponse response = this.bm.execute(post);
            harvestResponse.f(timer.bv());
            harvestResponse.setStatusCode(response.getStatusLine().getStatusCode());
            try {
                harvestResponse.f(a(response));
                return harvestResponse;
            } catch (IOException e) {
                LOG.a("Failed to retrieve collector response: ", e);
                return harvestResponse;
            }
        } catch (Exception e2) {
            LOG.a("Failed to send POST to collector: ", e2);
            b(e2);
            return null;
        }
    }

    private byte[] x(String message) throws Throwable {
        ByteArrayOutputStream baos = null;
        try {
            Deflater deflater = new Deflater();
            deflater.setInput(message.getBytes());
            deflater.finish();
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            try {
                byte[] buf = new byte[8192];
                while (!deflater.finished()) {
                    int byteCount = deflater.deflate(buf);
                    if (byteCount <= 0) {
                        LOG.error("HTTP request contains an incomplete payload");
                    }
                    baos2.write(buf, 0, byteCount);
                }
                deflater.end();
                byte[] byteArray = baos2.toByteArray();
                if (baos2 != null) {
                    try {
                        baos2.close();
                    } catch (IOException e) {
                        LOG.error("Failed to close io.");
                    }
                }
                return byteArray;
            } catch (Throwable th) {
                th = th;
                baos = baos2;
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException e2) {
                        LOG.error("Failed to close io.");
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static String a(HttpResponse response) throws IOException {
        char[] buf = new char[8192];
        StringBuilder sb = new StringBuilder();
        if (response == null || response.getEntity() == null) {
            return null;
        }
        InputStream in = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while (true) {
            try {
                int n = reader.read(buf);
                if (n >= 0) {
                    sb.append(buf, 0, n);
                } else {
                    in.close();
                    return sb.toString();
                }
            } catch (Exception e) {
                in.close();
                return sb.toString();
            }
        }
    }

    private void b(Exception e) {
        com.baidu.uaq.agent.android.stats.a.br().L("Supportability/AgentHealth/Collector/ResponseErrorCodes/" + g.c(e));
    }

    private String y(String resource) {
        String protocol = AGENT.getConfig().isUseSsl() ? "https://" : "http://";
        String port = ":" + AGENT.getConfig().getCollectorPort();
        return protocol + AGENT.getConfig().getCollectorHost() + port + resource;
    }

    private String aH() {
        return y("/mobile/v4/data/index.php");
    }
}
