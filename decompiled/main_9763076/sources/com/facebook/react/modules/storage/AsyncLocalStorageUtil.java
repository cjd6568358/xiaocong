package com.facebook.react.modules.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.facebook.react.bridge.ReadableArray;
import com.tencent.android.tpush.SettingsContentProvider;
import java.util.Arrays;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AsyncLocalStorageUtil {
    static String buildKeySelection(int selectionCount) {
        String[] list = new String[selectionCount];
        Arrays.fill(list, "?");
        return "key IN (" + TextUtils.join(", ", list) + ")";
    }

    static String[] buildKeySelectionArgs(ReadableArray keys, int start, int count) {
        String[] selectionArgs = new String[count];
        for (int keyIndex = 0; keyIndex < count; keyIndex++) {
            selectionArgs[keyIndex] = keys.getString(start + keyIndex);
        }
        return selectionArgs;
    }

    public static String getItemImpl(SQLiteDatabase db, String key) {
        String string = null;
        String[] columns = {"value"};
        String[] selectionArgs = {key};
        Cursor cursor = db.query("catalystLocalStorage", columns, "key=?", selectionArgs, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                string = cursor.getString(0);
            }
            return string;
        } finally {
            cursor.close();
        }
    }

    static boolean setItemImpl(SQLiteDatabase db, String key, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SettingsContentProvider.KEY, key);
        contentValues.put("value", value);
        long inserted = db.insertWithOnConflict("catalystLocalStorage", null, contentValues, 5);
        return -1 != inserted;
    }

    static boolean mergeImpl(SQLiteDatabase db, String key, String value) throws JSONException {
        String newValue;
        String oldValue = getItemImpl(db, key);
        if (oldValue == null) {
            newValue = value;
        } else {
            JSONObject oldJSON = new JSONObject(oldValue);
            JSONObject newJSON = new JSONObject(value);
            deepMergeInto(oldJSON, newJSON);
            newValue = oldJSON.toString();
        }
        return setItemImpl(db, key, newValue);
    }

    private static void deepMergeInto(JSONObject oldJSON, JSONObject newJSON) throws JSONException {
        Iterator<?> keys = newJSON.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONObject newJSONObject = newJSON.optJSONObject(key);
            JSONObject oldJSONObject = oldJSON.optJSONObject(key);
            if (newJSONObject != null && oldJSONObject != null) {
                deepMergeInto(oldJSONObject, newJSONObject);
                oldJSON.put(key, oldJSONObject);
            } else {
                oldJSON.put(key, newJSON.get(key));
            }
        }
    }
}
