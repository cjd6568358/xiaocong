package org.eclipse.paho.client.mqttv3;

import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import org.eclipse.paho.client.mqttv3.util.Strings;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttTopic {
    private String name;

    public String getName() {
        return this.name;
    }

    public String toString() {
        return getName();
    }

    public static void validate(String topicString, boolean wildcardAllowed) throws IllegalArgumentException {
        try {
            int topicLen = topicString.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET).length;
            if (topicLen < 1 || topicLen > 65535) {
                throw new IllegalArgumentException("Invalid topic length, should be in range[1, 65535]!");
            }
            if (!wildcardAllowed) {
                if (Strings.containsAny(topicString, "#+")) {
                    throw new IllegalArgumentException("The topic name MUST NOT contain any wildcard characters (#+)");
                }
            } else if (!Strings.equalsAny(topicString, new String[]{"#", "+"})) {
                if (Strings.countMatches(topicString, "#") > 1 || (topicString.indexOf("#") != -1 && !topicString.endsWith("/#"))) {
                    throw new IllegalArgumentException(new StringBuffer("Invalid usage of multi-level wildcard in topic string: ").append(topicString).toString());
                }
                validateSingleLevelWildcard(topicString);
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private static void validateSingleLevelWildcard(String topicString) {
        char singleLevelWildcardChar = "+".charAt(0);
        char topicLevelSeparatorChar = "/".charAt(0);
        char[] chars = topicString.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length; i++) {
            char prev = i + (-1) >= 0 ? chars[i - 1] : (char) 0;
            char next = i + 1 < length ? chars[i + 1] : (char) 0;
            if (chars[i] == singleLevelWildcardChar && ((prev != topicLevelSeparatorChar && prev != 0) || (next != topicLevelSeparatorChar && next != 0))) {
                String errorMessage = new StringBuffer("Invalid usage of single-level wildcard in topic string '").append(topicString).append("'!").toString();
                throw new IllegalArgumentException(errorMessage);
            }
        }
    }

    public static boolean isMatched(String topicFilter, String topicName) throws IllegalArgumentException {
        int curn = 0;
        int curf = 0;
        int curn_end = topicName.length();
        int curf_end = topicFilter.length();
        validate(topicFilter, true);
        validate(topicName, false);
        if (topicFilter.equals(topicName)) {
            return true;
        }
        while (curf < curf_end && curn < curn_end && ((topicName.charAt(curn) != '/' || topicFilter.charAt(curf) == '/') && (topicFilter.charAt(curf) == '+' || topicFilter.charAt(curf) == '#' || topicFilter.charAt(curf) == topicName.charAt(curn)))) {
            if (topicFilter.charAt(curf) == '+') {
                int nextpos = curn + 1;
                while (nextpos < curn_end && topicName.charAt(nextpos) != '/') {
                    curn++;
                    nextpos = curn + 1;
                }
            } else if (topicFilter.charAt(curf) == '#') {
                curn = curn_end - 1;
            }
            curf++;
            curn++;
        }
        return curn == curn_end && curf == curf_end;
    }
}
