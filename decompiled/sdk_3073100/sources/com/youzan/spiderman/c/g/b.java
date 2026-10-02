package com.youzan.spiderman.c.g;

import com.youzan.spiderman.c.b.h;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.RegexUtil;
import com.youzan.spiderman.utils.StringUtils;
import java.util.List;

/* JADX INFO: compiled from: UploadPattern.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    public static boolean a(h uploadConfig, c uploadUrl) {
        List<String> urlPattern;
        if (uploadConfig == null || uploadUrl == null) {
            return false;
        }
        String url = uploadUrl.a();
        if (StringUtils.isEmpty(url) || (urlPattern = uploadConfig.b()) == null) {
            return false;
        }
        for (String pattern : urlPattern) {
            try {
                if (RegexUtil.isMatch(pattern, url)) {
                    return true;
                }
            } catch (Exception e) {
                Logger.e("UploadPattern", "match exception, patter:" + pattern + ", url:" + url, e);
            }
        }
        return false;
    }
}
