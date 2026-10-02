package com.tencent.android.tpush;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import java.util.Map;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SettingsContentProvider extends ContentProvider {
    public static Uri BASE_URI = null;
    public static final String BOOLEAN_TYPE = "boolean";
    public static final String FLOAT_TYPE = "float";
    public static final String INT_TYPE = "integer";
    public static final String KEY = "key";
    public static final String LONG_TYPE = "long";
    public static final String PREFFERENCE_AUTHORITY = "TPUSH_PROVIDER";
    public static final String STRING_TYPE = "string";
    public static final String TYPE = "type";
    private static UriMatcher a;
    private static String b = null;
    private SharedPreferences c = null;

    private void a(Context context) {
        if (b == null) {
            b = context.getPackageName() + "." + PREFFERENCE_AUTHORITY;
        }
        if (a == null) {
            a = new UriMatcher(-1);
            a.addURI(b, "*/*", 65536);
        }
        if (BASE_URI == null) {
            BASE_URI = Uri.parse("content://" + b);
        }
        if (this.c == null) {
            this.c = context.getApplicationContext().getSharedPreferences(".tpns.settings.xml", 0);
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        a(getContext());
        return true;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return "vnd.android.cursor.item/vnd.TPUSH_PROVIDER.item";
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        switch (a.match(uri)) {
            case 65536:
                this.c.edit().clear().commit();
                break;
            default:
                com.tencent.android.tpush.a.a.i("SettingsContentProvider", "Unsupported uri " + uri);
                break;
        }
        return 0;
    }

    @Override // android.content.ContentProvider
    @SuppressLint({"NewApi"})
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (a.match(uri)) {
            case 65536:
                SharedPreferences.Editor editorEdit = this.c.edit();
                for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
                    Object value = entry.getValue();
                    String key = entry.getKey();
                    if (value == null) {
                        editorEdit.remove(key);
                    } else if (value instanceof String) {
                        editorEdit.putString(key, (String) value);
                    } else if (value instanceof Boolean) {
                        editorEdit.putBoolean(key, ((Boolean) value).booleanValue());
                    } else if (value instanceof Long) {
                        editorEdit.putLong(key, ((Long) value).longValue());
                    } else if (value instanceof Integer) {
                        editorEdit.putInt(key, ((Integer) value).intValue());
                    } else if (value instanceof Float) {
                        editorEdit.putFloat(key, ((Float) value).floatValue());
                    } else {
                        com.tencent.android.tpush.a.a.i("SettingsContentProvider", "Unsupported type " + uri);
                    }
                }
                if (Build.VERSION.SDK_INT > 8) {
                    editorEdit.apply();
                } else {
                    editorEdit.commit();
                }
                break;
            default:
                com.tencent.android.tpush.a.a.i("SettingsContentProvider", "Unsupported uri " + uri);
                break;
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        MatrixCursor matrixCursor;
        Object objValueOf;
        switch (a.match(uri)) {
            case 65536:
                String str3 = uri.getPathSegments().get(0);
                String str4 = uri.getPathSegments().get(1);
                MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{str3});
                if (this.c.contains(str3)) {
                    MatrixCursor.RowBuilder rowBuilderNewRow = matrixCursor2.newRow();
                    if (STRING_TYPE.equals(str4)) {
                        objValueOf = this.c.getString(str3, null);
                    } else if (BOOLEAN_TYPE.equals(str4)) {
                        objValueOf = Integer.valueOf(this.c.getBoolean(str3, false) ? 1 : 0);
                    } else if (LONG_TYPE.equals(str4)) {
                        objValueOf = Long.valueOf(this.c.getLong(str3, 0L));
                    } else if (INT_TYPE.equals(str4)) {
                        objValueOf = Integer.valueOf(this.c.getInt(str3, 0));
                    } else if (FLOAT_TYPE.equals(str4)) {
                        objValueOf = Float.valueOf(this.c.getFloat(str3, 0.0f));
                    } else {
                        com.tencent.android.tpush.a.a.i("SettingsContentProvider", "Unsupported type " + uri);
                        matrixCursor = matrixCursor2;
                    }
                    rowBuilderNewRow.add(objValueOf);
                    matrixCursor = matrixCursor2;
                } else {
                    return matrixCursor2;
                }
                break;
            default:
                com.tencent.android.tpush.a.a.i("SettingsContentProvider", "Unsupported uri " + uri);
                matrixCursor = null;
                break;
        }
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        com.tencent.android.tpush.a.a.i("SettingsContentProvider", "UnsupportedOperation: update!");
        return 0;
    }

    public static String getStringValue(Cursor cursor, String str) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                str = cursor.getString(0);
            }
            cursor.close();
        }
        return str;
    }

    public static boolean getBooleanValue(Cursor cursor, boolean z) {
        boolean z2 = false;
        if (cursor != null) {
            if (!cursor.moveToFirst()) {
                z2 = z;
            } else if (cursor.getInt(0) > 0) {
                z2 = true;
            }
            cursor.close();
            return z2;
        }
        return z;
    }

    public static int getIntValue(Cursor cursor, int i) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                i = cursor.getInt(0);
            }
            cursor.close();
        }
        return i;
    }

    public static long getLongValue(Cursor cursor, long j) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                j = cursor.getLong(0);
            }
            cursor.close();
        }
        return j;
    }

    public static float getFloatValue(Cursor cursor, float f) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                f = cursor.getFloat(0);
            }
            cursor.close();
        }
        return f;
    }

    public static final Uri getContentUri(Context context, String str, String str2) {
        if (BASE_URI == null) {
            if (b == null) {
                b = context.getPackageName() + "." + PREFFERENCE_AUTHORITY;
            }
            BASE_URI = Uri.parse("content://" + b);
        }
        return BASE_URI.buildUpon().appendPath(str).appendPath(str2).build();
    }
}
