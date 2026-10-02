package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CountryList implements SerializableEx {
    private static final long serialVersionUID = 6260801607149807621L;
    public List<Country> countryList = new ArrayList();

    public static class Country implements SerializableEx {
        private static final long serialVersionUID = 4571153074561468234L;
        public String countryCode = Constants.MAIN_VERSION_TAG;
        public String countryName = Constants.MAIN_VERSION_TAG;
    }
}
