package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WebSocketHandshake {
    String host;
    InputStream input;
    OutputStream output;
    int port;
    String uri;

    public WebSocketHandshake(InputStream input, OutputStream output, String uri, String host, int port) {
        this.input = input;
        this.output = output;
        this.uri = uri;
        this.host = host;
        this.port = port;
    }

    public void execute() throws IOException {
        String key = new StringBuffer("mqtt3-").append(System.currentTimeMillis() / 1000).toString();
        String b64Key = Base64.encode(key);
        sendHandshakeRequest(b64Key);
        receiveHandshakeResponse(b64Key);
    }

    private void sendHandshakeRequest(String key) throws IOException {
        try {
            String path = "/mqtt";
            URI srvUri = new URI(this.uri);
            if (srvUri.getRawPath() != null && srvUri.getRawPath().length() != 0) {
                path = srvUri.getRawPath();
                if (srvUri.getRawQuery() != null && srvUri.getRawQuery().length() != 0) {
                    path = new StringBuffer(String.valueOf(path)).append("?").append(srvUri.getRawQuery()).toString();
                }
            }
            PrintWriter pw = new PrintWriter(this.output);
            pw.print(new StringBuffer("GET ").append(path).append(" HTTP/1.1").append("\r\n").toString());
            if (this.port != 80 && this.port != 443) {
                pw.print(new StringBuffer("Host: ").append(this.host).append(":").append(this.port).append("\r\n").toString());
            } else {
                pw.print(new StringBuffer("Host: ").append(this.host).append("\r\n").toString());
            }
            pw.print("Upgrade: websocket\r\n");
            pw.print("Connection: Upgrade\r\n");
            pw.print(new StringBuffer("Sec-WebSocket-Key: ").append(key).append("\r\n").toString());
            pw.print("Sec-WebSocket-Protocol: mqttv3.1\r\n");
            pw.print("Sec-WebSocket-Version: 13\r\n");
            String userInfo = srvUri.getUserInfo();
            if (userInfo != null) {
                pw.print(new StringBuffer("Authorization: Basic ").append(Base64.encode(userInfo)).append("\r\n").toString());
            }
            pw.print("\r\n");
            pw.flush();
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private void receiveHandshakeResponse(String key) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(this.input));
        ArrayList responseLines = new ArrayList();
        String line = in.readLine();
        if (line == null) {
            throw new IOException("WebSocket Response header: Invalid response from Server, It may not support WebSockets.");
        }
        while (!line.equals("")) {
            responseLines.add(line);
            line = in.readLine();
        }
        Map headerMap = getHeaders(responseLines);
        String connectionHeader = (String) headerMap.get("connection");
        if (connectionHeader == null || connectionHeader.equalsIgnoreCase("upgrade")) {
            throw new IOException("WebSocket Response header: Incorrect connection header");
        }
        String upgradeHeader = (String) headerMap.get("upgrade");
        if (upgradeHeader.toLowerCase().indexOf("websocket") == -1) {
            throw new IOException("WebSocket Response header: Incorrect upgrade.");
        }
        String secWebsocketProtocolHeader = (String) headerMap.get("sec-websocket-protocol");
        if (secWebsocketProtocolHeader == null) {
            throw new IOException("WebSocket Response header: empty sec-websocket-protocol");
        }
        if (!headerMap.containsKey("sec-websocket-accept")) {
            throw new IOException("WebSocket Response header: Missing Sec-WebSocket-Accept");
        }
        try {
            verifyWebSocketKey(key, (String) headerMap.get("sec-websocket-accept"));
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        } catch (HandshakeFailedException e2) {
            throw new IOException("WebSocket Response header: Incorrect Sec-WebSocket-Key");
        }
    }

    private Map getHeaders(ArrayList headers) {
        Map headerMap = new HashMap();
        for (int i = 1; i < headers.size(); i++) {
            String headerPre = (String) headers.get(i);
            String[] header = headerPre.split(":");
            headerMap.put(header[0].toLowerCase(), header[1]);
        }
        return headerMap;
    }

    private void verifyWebSocketKey(String key, String accept) throws HandshakeFailedException, NoSuchAlgorithmException {
        byte[] sha1Bytes = sha1(new StringBuffer(String.valueOf(key)).append(WebSocketProtocol.ACCEPT_MAGIC).toString());
        String encodedSha1Bytes = Base64.encodeBytes(sha1Bytes).trim();
        if (!encodedSha1Bytes.equals(accept.trim())) {
            throw new HandshakeFailedException();
        }
    }

    private byte[] sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        return result;
    }
}
