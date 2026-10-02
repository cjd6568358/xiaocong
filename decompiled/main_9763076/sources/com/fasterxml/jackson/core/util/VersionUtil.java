package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Version;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Pattern;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class VersionUtil {
    public static final String VERSION_FILE = "VERSION.txt";
    private static final Pattern VERSION_SEPARATOR = Pattern.compile("[-_./;:]");
    private final Version _version;

    protected VersionUtil() {
        Version v = null;
        try {
            v = versionFor(getClass());
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load Version information for bundle (via " + getClass().getName() + ").");
        }
        this._version = v == null ? Version.unknownVersion() : v;
    }

    public Version version() {
        return this._version;
    }

    public static Version versionFor(Class<?> cls) {
        Version version = null;
        try {
            InputStream in = cls.getResourceAsStream(VERSION_FILE);
            if (in != null) {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, HTTP.UTF_8));
                    String groupStr = null;
                    String artifactStr = null;
                    String versionStr = br.readLine();
                    if (versionStr != null && (groupStr = br.readLine()) != null) {
                        groupStr = groupStr.trim();
                        artifactStr = br.readLine();
                        if (artifactStr != null) {
                            artifactStr = artifactStr.trim();
                        }
                    }
                    version = parseVersion(versionStr, groupStr, artifactStr);
                    try {
                        in.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } catch (Throwable th) {
                    try {
                        in.close();
                        throw th;
                    } catch (IOException e2) {
                        throw new RuntimeException(e2);
                    }
                }
            }
        } catch (IOException e3) {
        }
        return version == null ? Version.unknownVersion() : version;
    }

    public static Version mavenVersionFor(ClassLoader classLoader, String groupId, String artifactId) {
        InputStream pomPoperties = classLoader.getResourceAsStream("META-INF/maven/" + groupId.replaceAll("\\.", "/") + "/" + artifactId + "/pom.properties");
        if (pomPoperties != null) {
            try {
                Properties props = new Properties();
                props.load(pomPoperties);
                String versionStr = props.getProperty(ClientCookie.VERSION_ATTR);
                String pomPropertiesArtifactId = props.getProperty("artifactId");
                String pomPropertiesGroupId = props.getProperty("groupId");
                Version version = parseVersion(versionStr, pomPropertiesGroupId, pomPropertiesArtifactId);
                try {
                    return version;
                } catch (IOException e) {
                    return version;
                }
            } catch (IOException e2) {
            } finally {
                try {
                    pomPoperties.close();
                } catch (IOException e3) {
                }
            }
        }
        return Version.unknownVersion();
    }

    @Deprecated
    public static Version parseVersion(String versionStr) {
        return parseVersion(versionStr, null, null);
    }

    public static Version parseVersion(String versionStr, String groupId, String artifactId) {
        if (versionStr == null) {
            return null;
        }
        String versionStr2 = versionStr.trim();
        if (versionStr2.length() == 0) {
            return null;
        }
        String[] parts = VERSION_SEPARATOR.split(versionStr2);
        int major = parseVersionPart(parts[0]);
        int minor = parts.length > 1 ? parseVersionPart(parts[1]) : 0;
        int patch = parts.length > 2 ? parseVersionPart(parts[2]) : 0;
        String snapshot = parts.length > 3 ? parts[3] : null;
        return new Version(major, minor, patch, snapshot, groupId, artifactId);
    }

    protected static int parseVersionPart(String partStr) {
        String partStr2 = partStr.toString();
        int len = partStr2.length();
        int number = 0;
        for (int i = 0; i < len; i++) {
            char c = partStr2.charAt(i);
            if (c > '9' || c < '0') {
                break;
            }
            number = (number * 10) + (c - '0');
        }
        return number;
    }
}
