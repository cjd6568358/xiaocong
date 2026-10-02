package com.baidu.cloud.media.download;

import com.tencent.android.tpush.common.Constants;
import java.util.Observable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DownloadableVideoItem extends Observable {
    public static final int ERROR_CODE_INVALID_URL = 1;
    public static final int ERROR_CODE_M3U8_DRM_INVALID = 6;
    public static final int ERROR_CODE_M3U8_INVALID_FORMAT = 4;
    public static final int ERROR_CODE_M3U8_SAVE_FAILED = 5;
    public static final int ERROR_CODE_NETWORK_FAILED = 2;
    public static final int ERROR_CODE_NO_ERROR = 0;
    public static final int ERROR_CODE_SDCARD_UNMOUNTED = 3;
    public static final int ERROR_CODE_TS_SAVE_FAILED = 7;
    public static final String[] a = {"ERROR_CODE_NO_ERROR", "ERROR_CODE_INVALID_URL", "ERROR_CODE_NETWORK_FAILED", "ERROR_CODE_SDCARD_UNMOUNTED", "ERROR_CODE_M3U8_INVALID_FORMAT", "ERROR_CODE_M3U8_SAVE_FAILED", "ERROR_CODE_M3U8_DRM_INVALID", "ERROR_CODE_TS_SAVE_FAILED"};
    protected String b;
    protected String c;
    protected String d;
    protected volatile int e;
    protected volatile DownloadStatus f = DownloadStatus.NONE;
    protected String g;
    protected volatile int h;

    public enum DownloadStatus {
        NONE(0, "first add"),
        DOWNLOADING(1, "downloading videos"),
        PAUSED(2, "paused"),
        COMPLETED(3, "completed"),
        ERROR(4, "failed to download"),
        DELETED(5, "delete manually"),
        PENDING(6, "pending, will start automatically(blocked by Parallel Strategy)");

        private int code;
        private String msg;

        DownloadStatus(int i, String str) {
            this.code = i;
            this.msg = str;
        }

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.msg;
        }
    }

    public int getErrorCode() {
        return this.h;
    }

    public String getFailReason() {
        return this.g;
    }

    public String getLocalAbsolutePath() {
        if (this.d == null || this.d.equals(Constants.MAIN_VERSION_TAG)) {
            return this.c;
        }
        if (this.c == null || this.c.equals(Constants.MAIN_VERSION_TAG)) {
            return null;
        }
        return this.c + "/" + this.d;
    }

    public float getProgress() {
        return this.e / 100.0f;
    }

    public DownloadStatus getStatus() {
        return this.f;
    }

    public String getUrl() {
        return this.b;
    }
}
