package com.ut.mini;

import android.text.TextUtils;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.p;
import com.alibaba.mtl.log.model.LogField;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UTHitBuilders {

    public static class UTHitBuilder {
        public static final String FIELD_ARG1 = "_field_arg1";
        public static final String FIELD_ARG2 = "_field_arg2";
        public static final String FIELD_ARG3 = "_field_arg3";
        public static final String FIELD_ARGS = "_field_args";
        public static final String FIELD_EVENT_ID = "_field_event_id";
        public static final String FIELD_PAGE = "_field_page";
        private Map<String, String> z = new HashMap();

        public UTHitBuilder() {
            if (!this.z.containsKey(FIELD_PAGE)) {
                this.z.put(FIELD_PAGE, "UT");
            }
        }

        public UTHitBuilder setProperty(String aKey, String aValue) {
            if (!TextUtils.isEmpty(aKey) && aValue != null) {
                if (this.z.containsKey(aKey)) {
                    this.z.remove(aKey);
                }
                this.z.put(aKey, aValue);
            } else {
                i.a("setProperty", "key is null or key is empty or value is null,please check it!");
            }
            return this;
        }

        public UTHitBuilder setProperties(Map<String, String> aProperties) {
            if (aProperties != null) {
                this.z.putAll(aProperties);
            }
            return this;
        }

        public String getProperty(String aKey) {
            if (aKey == null || !this.z.containsKey(aKey)) {
                return null;
            }
            return this.z.get(aKey);
        }

        public Map<String, String> build() {
            HashMap map = new HashMap();
            map.putAll(this.z);
            if (!a(map)) {
                return null;
            }
            e(map);
            d(map);
            if (map.containsKey(LogField.EVENTID.toString())) {
                return map;
            }
            return null;
        }

        private static boolean a(Map<String, String> map) {
            if (map != null) {
                if (map.containsKey(null)) {
                    map.remove(null);
                }
                if (map.containsKey("")) {
                    map.remove("");
                }
                if (map.containsKey(LogField.PAGE.toString())) {
                    i.a("checkIlleagleProperty", "IlleaglePropertyKey(PAGE) is setted when you call the method setProperty or setProperties ,please use another key to replace it!");
                    return false;
                }
                if (map.containsKey(LogField.EVENTID.toString())) {
                    i.a("checkIlleagleProperty", "IlleaglePropertyKey(EVENTID) is setted when you call the method setProperty or setProperties ,please use another key to replace it!");
                    return false;
                }
                if (map.containsKey(LogField.ARG1.toString())) {
                    i.a("checkIlleagleProperty", "IlleaglePropertyKey(ARG1) is setted when you call the method setProperty or setProperties ,please use another key to replace it!");
                    return false;
                }
                if (map.containsKey(LogField.ARG2.toString())) {
                    i.a("checkIlleagleProperty", "IlleaglePropertyKey(ARG2) is setted when you call the method setProperty or setProperties ,please use another key to replace it!");
                    return false;
                }
                if (map.containsKey(LogField.ARG3.toString())) {
                    i.a("checkIlleagleProperty", "IlleaglePropertyKey(ARG3) is setted when you call the method setProperty or setProperties ,please use another key to replace it!");
                    return false;
                }
            }
            return true;
        }

        private static void d(Map<String, String> map) {
            if (map != null) {
                if (map.containsKey(FIELD_PAGE)) {
                    String str = map.get(FIELD_PAGE);
                    map.remove(FIELD_PAGE);
                    map.put(LogField.PAGE.toString(), str);
                }
                if (map.containsKey(FIELD_ARG1)) {
                    String str2 = map.get(FIELD_ARG1);
                    map.remove(FIELD_ARG1);
                    map.put(LogField.ARG1.toString(), str2);
                }
                if (map.containsKey(FIELD_ARG2)) {
                    String str3 = map.get(FIELD_ARG2);
                    map.remove(FIELD_ARG2);
                    map.put(LogField.ARG2.toString(), str3);
                }
                if (map.containsKey(FIELD_ARG3)) {
                    String str4 = map.get(FIELD_ARG3);
                    map.remove(FIELD_ARG3);
                    map.put(LogField.ARG3.toString(), str4);
                }
                if (map.containsKey(FIELD_ARGS)) {
                    String str5 = map.get(FIELD_ARGS);
                    map.remove(FIELD_ARGS);
                    map.put(LogField.ARGS.toString(), str5);
                }
                if (map.containsKey(FIELD_EVENT_ID)) {
                    String str6 = map.get(FIELD_EVENT_ID);
                    map.remove(FIELD_EVENT_ID);
                    map.put(LogField.EVENTID.toString(), str6);
                }
            }
        }

        private static void e(Map<String, String> map) {
            if (map != null) {
                if (map.containsKey(LogField.PAGE.toString())) {
                    map.remove(LogField.PAGE.toString());
                }
                if (map.containsKey(LogField.EVENTID.toString())) {
                    map.remove(LogField.EVENTID.toString());
                }
                if (map.containsKey(LogField.ARG1.toString())) {
                    map.remove(LogField.ARG1.toString());
                }
                if (map.containsKey(LogField.ARG2.toString())) {
                    map.remove(LogField.ARG2.toString());
                }
                if (map.containsKey(LogField.ARG3.toString())) {
                    map.remove(LogField.ARG3.toString());
                }
                if (map.containsKey(LogField.ARGS.toString())) {
                    map.remove(LogField.ARGS.toString());
                }
            }
        }
    }

    public static class UTCustomHitBuilder extends UTHitBuilder {
        public UTCustomHitBuilder(String aEventLabel) {
            if (!TextUtils.isEmpty(aEventLabel)) {
                super.setProperty(UTHitBuilder.FIELD_ARG1, aEventLabel);
            }
            super.setProperty(UTHitBuilder.FIELD_EVENT_ID, "19999");
            super.setProperty(UTHitBuilder.FIELD_ARG3, "0");
        }

        public UTCustomHitBuilder setDurationOnEvent(long aDuration) {
            if (aDuration < 0) {
                aDuration = 0;
            }
            super.setProperty(UTHitBuilder.FIELD_ARG3, "" + aDuration);
            return this;
        }

        public UTCustomHitBuilder setEventPage(String aPage) {
            if (!TextUtils.isEmpty(aPage)) {
                super.setProperty(UTHitBuilder.FIELD_PAGE, aPage);
            }
            return this;
        }

        @Override // com.ut.mini.UTHitBuilders.UTHitBuilder
        public Map<String, String> build() {
            Map<String, String> mapBuild = super.build();
            if (mapBuild != null) {
                String str = mapBuild.get(LogField.PAGE.toString());
                String str2 = mapBuild.get(LogField.ARG1.toString());
                if (str2 != null) {
                    mapBuild.remove(LogField.ARG1.toString());
                    mapBuild.remove(LogField.PAGE.toString());
                    Map<String, String> mapB = p.b(mapBuild);
                    mapB.put(LogField.ARG1.toString(), str2);
                    mapB.put(LogField.PAGE.toString(), str);
                    return mapB;
                }
            }
            return mapBuild;
        }
    }

    public static class UTPageHitBuilder extends UTHitBuilder {
        public UTPageHitBuilder(String aPageName) {
            if (!TextUtils.isEmpty(aPageName)) {
                super.setProperty(UTHitBuilder.FIELD_PAGE, aPageName);
            }
            super.setProperty(UTHitBuilder.FIELD_EVENT_ID, "2001");
            super.setProperty(UTHitBuilder.FIELD_ARG3, "0");
        }

        public UTPageHitBuilder setReferPage(String aReferPage) {
            if (!TextUtils.isEmpty(aReferPage)) {
                super.setProperty(UTHitBuilder.FIELD_ARG1, aReferPage);
            }
            return this;
        }

        public UTPageHitBuilder setDurationOnPage(long aDuration) {
            if (aDuration < 0) {
                aDuration = 0;
            }
            super.setProperty(UTHitBuilder.FIELD_ARG3, "" + aDuration);
            return this;
        }
    }

    public static class UTControlHitBuilder extends UTHitBuilder {
        public UTControlHitBuilder(String aControlName) {
            if (TextUtils.isEmpty(aControlName)) {
                throw new IllegalArgumentException("Control name can not be empty.");
            }
            String currentPageName = UTPageHitHelper.getInstance().getCurrentPageName();
            if (TextUtils.isEmpty(currentPageName)) {
                throw new IllegalArgumentException("Please call in at PageAppear and PageDisAppear.");
            }
            super.setProperty(UTHitBuilder.FIELD_PAGE, currentPageName);
            super.setProperty(UTHitBuilder.FIELD_EVENT_ID, "2101");
            super.setProperty(UTHitBuilder.FIELD_ARG1, currentPageName + "_" + aControlName);
        }

        public UTControlHitBuilder(String aPageName, String aControlName) {
            if (TextUtils.isEmpty(aControlName)) {
                throw new IllegalArgumentException("Control name can not be empty.");
            }
            if (TextUtils.isEmpty(aPageName)) {
                throw new IllegalArgumentException("Page name can not be empty.");
            }
            super.setProperty(UTHitBuilder.FIELD_PAGE, aPageName);
            super.setProperty(UTHitBuilder.FIELD_EVENT_ID, "2101");
            super.setProperty(UTHitBuilder.FIELD_ARG1, aPageName + "_" + aControlName);
        }
    }
}
