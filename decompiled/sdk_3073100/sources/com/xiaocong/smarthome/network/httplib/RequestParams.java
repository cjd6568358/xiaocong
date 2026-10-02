package com.xiaocong.smarthome.network.httplib;

import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class RequestParams {
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    protected static final String LOG_TAG = "RequestParams";
    protected boolean autoCloseInputStreams;
    protected String contentEncoding;
    protected final ConcurrentHashMap<String, FileWrapper> fileParams;
    protected boolean isRepeatable;
    protected final ConcurrentHashMap<String, StreamWrapper> streamParams;
    protected final ConcurrentHashMap<String, String> urlParams;
    protected final ConcurrentHashMap<String, Object> urlParamsWithObjects;
    protected boolean useJsonStreamer;

    public void setContentEncoding(String encoding) {
        if (encoding != null) {
            this.contentEncoding = encoding;
        } else {
            Log.d(LOG_TAG, "setContentEncoding called with null attribute");
        }
    }

    public RequestParams() {
        this((Map<String, String>) null);
    }

    public RequestParams(Map<String, String> source) {
        this.urlParams = new ConcurrentHashMap<>();
        this.streamParams = new ConcurrentHashMap<>();
        this.fileParams = new ConcurrentHashMap<>();
        this.urlParamsWithObjects = new ConcurrentHashMap<>();
        this.contentEncoding = AsyncHttpResponseHandler.DEFAULT_CHARSET;
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public RequestParams(final String key, final String value) {
        this(new HashMap<String, String>() { // from class: com.xiaocong.smarthome.network.httplib.RequestParams.1
            {
                put(key, value);
            }
        });
    }

    public RequestParams(Object... keysAndValues) {
        this.urlParams = new ConcurrentHashMap<>();
        this.streamParams = new ConcurrentHashMap<>();
        this.fileParams = new ConcurrentHashMap<>();
        this.urlParamsWithObjects = new ConcurrentHashMap<>();
        this.contentEncoding = AsyncHttpResponseHandler.DEFAULT_CHARSET;
        int len = keysAndValues.length;
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        }
        for (int i = 0; i < len; i += 2) {
            String key = String.valueOf(keysAndValues[i]);
            String val = String.valueOf(keysAndValues[i + 1]);
            put(key, val);
        }
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            this.urlParams.put(key, value);
        }
    }

    public void put(String key, File file) throws FileNotFoundException {
        put(key, file, (String) null);
    }

    public void put(String key, File file, String contentType) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        if (key != null) {
            this.fileParams.put(key, new FileWrapper(file, contentType));
        }
    }

    public void put(String key, InputStream stream) {
        put(key, stream, (String) null);
    }

    public void put(String key, InputStream stream, String name) {
        put(key, stream, name, null);
    }

    public void put(String key, InputStream stream, String name, String contentType) {
        put(key, stream, name, contentType, this.autoCloseInputStreams);
    }

    public void put(String key, InputStream stream, String name, String contentType, boolean autoClose) {
        if (key != null && stream != null) {
            this.streamParams.put(key, StreamWrapper.newInstance(stream, name, contentType, autoClose));
        }
    }

    public void put(String key, Object value) {
        if (key != null && value != null) {
            this.urlParamsWithObjects.put(key, value);
        }
    }

    public void put(String key, int value) {
        if (key != null) {
            this.urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, long value) {
        if (key != null) {
            this.urlParams.put(key, String.valueOf(value));
        }
    }

    public void add(String key, String value) {
        if (key != null && value != null) {
            Object params = this.urlParamsWithObjects.get(key);
            if (params == null) {
                params = new HashSet();
                put(key, params);
            }
            if (params instanceof List) {
                ((List) params).add(value);
            } else if (params instanceof Set) {
                ((Set) params).add(value);
            }
        }
    }

    public void remove(String key) {
        this.urlParams.remove(key);
        this.streamParams.remove(key);
        this.fileParams.remove(key);
        this.urlParamsWithObjects.remove(key);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }
        for (Map.Entry<String, StreamWrapper> entry2 : this.streamParams.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(entry2.getKey());
            result.append("=");
            result.append("STREAM");
        }
        for (Map.Entry<String, FileWrapper> entry3 : this.fileParams.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(entry3.getKey());
            result.append("=");
            result.append("FILE");
        }
        List<BasicNameValuePair> params = getParamsList(null, this.urlParamsWithObjects);
        for (BasicNameValuePair kv : params) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(kv.getName());
            result.append("=");
            result.append(kv.getValue());
        }
        return result.toString();
    }

    public void setHttpEntityIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
    }

    public void setUseJsonStreamer(boolean useJsonStreamer) {
        this.useJsonStreamer = useJsonStreamer;
    }

    public void setAutoCloseInputStreams(boolean flag) {
        this.autoCloseInputStreams = flag;
    }

    public HttpEntity getEntity(ResponseHandlerInterface progressHandler) throws IOException {
        if (this.useJsonStreamer) {
            return createJsonStreamerEntity(progressHandler);
        }
        if (this.streamParams.isEmpty() && this.fileParams.isEmpty()) {
            return createFormEntity();
        }
        return createMultipartEntity(progressHandler);
    }

    private HttpEntity createJsonStreamerEntity(ResponseHandlerInterface progressHandler) throws IOException {
        JsonStreamerEntity entity = new JsonStreamerEntity(progressHandler, (this.fileParams.isEmpty() && this.streamParams.isEmpty()) ? false : true);
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            entity.addPart(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry2 : this.urlParamsWithObjects.entrySet()) {
            entity.addPart(entry2.getKey(), entry2.getValue());
        }
        for (Map.Entry<String, FileWrapper> entry3 : this.fileParams.entrySet()) {
            entity.addPart(entry3.getKey(), entry3.getValue());
        }
        for (Map.Entry<String, StreamWrapper> entry4 : this.streamParams.entrySet()) {
            StreamWrapper stream = entry4.getValue();
            if (stream.inputStream != null) {
                entity.addPart(entry4.getKey(), StreamWrapper.newInstance(stream.inputStream, stream.name, stream.contentType, stream.autoClose));
            }
        }
        return entity;
    }

    private HttpEntity createFormEntity() {
        try {
            return new UrlEncodedFormEntity(getParamsList(), this.contentEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "createFormEntity failed", e);
            return null;
        }
    }

    private HttpEntity createMultipartEntity(ResponseHandlerInterface progressHandler) throws IOException {
        SimpleMultipartEntity entity = new SimpleMultipartEntity(progressHandler);
        entity.setIsRepeatable(this.isRepeatable);
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            entity.addPart(entry.getKey(), entry.getValue());
        }
        List<BasicNameValuePair> params = getParamsList(null, this.urlParamsWithObjects);
        for (BasicNameValuePair kv : params) {
            entity.addPart(kv.getName(), kv.getValue());
        }
        for (Map.Entry<String, StreamWrapper> entry2 : this.streamParams.entrySet()) {
            StreamWrapper stream = entry2.getValue();
            if (stream.inputStream != null) {
                entity.addPart(entry2.getKey(), stream.name, stream.inputStream, stream.contentType);
            }
        }
        for (Map.Entry<String, FileWrapper> entry3 : this.fileParams.entrySet()) {
            FileWrapper fileWrapper = entry3.getValue();
            entity.addPart(entry3.getKey(), fileWrapper.file, fileWrapper.contentType);
        }
        return entity;
    }

    protected List<BasicNameValuePair> getParamsList() {
        List<BasicNameValuePair> lparams = new LinkedList<>();
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            lparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        lparams.addAll(getParamsList(null, this.urlParamsWithObjects));
        return lparams;
    }

    private List<BasicNameValuePair> getParamsList(String key, Object value) {
        Object nestedValue;
        List<BasicNameValuePair> params = new LinkedList<>();
        if (value instanceof Map) {
            Map map = (Map) value;
            List list = new ArrayList(map.keySet());
            Collections.sort(list);
            for (Object nestedKey : list) {
                if ((nestedKey instanceof String) && (nestedValue = map.get(nestedKey)) != null) {
                    params.addAll(getParamsList(key == null ? (String) nestedKey : String.format("%s[%s]", key, nestedKey), nestedValue));
                }
            }
        } else if (value instanceof List) {
            for (Object nestedValue2 : (List) value) {
                params.addAll(getParamsList(String.format("%s[]", key), nestedValue2));
            }
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            for (Object nestedValue3 : array) {
                params.addAll(getParamsList(String.format("%s[]", key), nestedValue3));
            }
        } else if (value instanceof Set) {
            Set set = (Set) value;
            for (Object nestedValue4 : set) {
                params.addAll(getParamsList(key, nestedValue4));
            }
        } else if (value instanceof String) {
            params.add(new BasicNameValuePair(key, (String) value));
        }
        return params;
    }

    protected String getParamString() {
        return URLEncodedUtils.format(getParamsList(), this.contentEncoding);
    }

    public static class FileWrapper {
        public final String contentType;
        public final File file;

        public FileWrapper(File file, String contentType) {
            this.file = file;
            this.contentType = contentType;
        }
    }

    public static class StreamWrapper {
        public final boolean autoClose;
        public final String contentType;
        public final InputStream inputStream;
        public final String name;

        public StreamWrapper(InputStream inputStream, String name, String contentType, boolean autoClose) {
            this.inputStream = inputStream;
            this.name = name;
            this.contentType = contentType;
            this.autoClose = autoClose;
        }

        static StreamWrapper newInstance(InputStream inputStream, String name, String contentType, boolean autoClose) {
            if (contentType == null) {
                contentType = RequestParams.APPLICATION_OCTET_STREAM;
            }
            return new StreamWrapper(inputStream, name, contentType, autoClose);
        }
    }
}
