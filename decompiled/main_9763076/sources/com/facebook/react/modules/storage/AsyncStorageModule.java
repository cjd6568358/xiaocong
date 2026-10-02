package com.facebook.react.modules.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.SetBuilder;
import com.tencent.android.tpush.SettingsContentProvider;
import java.util.HashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AsyncStorageModule extends ReactContextBaseJavaModule {
    private static final int MAX_SQL_KEYS = 999;
    protected static final String NAME = "AsyncSQLiteDBStorage";
    private ReactDatabaseSupplier mReactDatabaseSupplier;
    private boolean mShuttingDown;

    public AsyncStorageModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mShuttingDown = false;
        this.mReactDatabaseSupplier = ReactDatabaseSupplier.getInstance(reactContext);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        super.initialize();
        this.mShuttingDown = false;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        this.mShuttingDown = true;
    }

    public void clearSensitiveData() {
        this.mReactDatabaseSupplier.clearAndCloseDatabase();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.modules.storage.AsyncStorageModule$1] */
    @ReactMethod
    public void multiGet(final ReadableArray keys, final Callback callback) {
        if (keys == null) {
            callback.invoke(AsyncStorageErrorUtil.getInvalidKeyError(null), null);
        } else {
            new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) { // from class: com.facebook.react.modules.storage.AsyncStorageModule.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.facebook.react.bridge.GuardedAsyncTask
                public void doInBackgroundGuarded(Void... params) {
                    if (!AsyncStorageModule.this.ensureDatabase()) {
                        callback.invoke(AsyncStorageErrorUtil.getDBError(null), null);
                        return;
                    }
                    String[] columns = {SettingsContentProvider.KEY, "value"};
                    HashSet<String> keysRemaining = SetBuilder.newHashSet();
                    WritableArray data = Arguments.createArray();
                    for (int keyStart = 0; keyStart < keys.size(); keyStart += AsyncStorageModule.MAX_SQL_KEYS) {
                        int keyCount = Math.min(keys.size() - keyStart, AsyncStorageModule.MAX_SQL_KEYS);
                        Cursor cursor = AsyncStorageModule.this.mReactDatabaseSupplier.get().query("catalystLocalStorage", columns, AsyncLocalStorageUtil.buildKeySelection(keyCount), AsyncLocalStorageUtil.buildKeySelectionArgs(keys, keyStart, keyCount), null, null, null);
                        keysRemaining.clear();
                        try {
                            try {
                                if (cursor.getCount() != keys.size()) {
                                    for (int keyIndex = keyStart; keyIndex < keyStart + keyCount; keyIndex++) {
                                        keysRemaining.add(keys.getString(keyIndex));
                                    }
                                }
                                if (cursor.moveToFirst()) {
                                    do {
                                        WritableArray row = Arguments.createArray();
                                        row.pushString(cursor.getString(0));
                                        row.pushString(cursor.getString(1));
                                        data.pushArray(row);
                                        keysRemaining.remove(cursor.getString(0));
                                    } while (cursor.moveToNext());
                                }
                                cursor.close();
                                for (String key : keysRemaining) {
                                    WritableArray row2 = Arguments.createArray();
                                    row2.pushString(key);
                                    row2.pushNull();
                                    data.pushArray(row2);
                                }
                                keysRemaining.clear();
                            } catch (Exception e) {
                                FLog.w("React", e.getMessage(), e);
                                callback.invoke(AsyncStorageErrorUtil.getError(null, e.getMessage()), null);
                                cursor.close();
                                return;
                            }
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
                    callback.invoke(null, data);
                }
            }.execute(new Void[0]);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.facebook.react.modules.storage.AsyncStorageModule$2] */
    @ReactMethod
    public void multiSet(final ReadableArray keyValueArray, final Callback callback) {
        if (keyValueArray.size() == 0) {
            callback.invoke(AsyncStorageErrorUtil.getInvalidKeyError(null));
        } else {
            new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) { // from class: com.facebook.react.modules.storage.AsyncStorageModule.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.facebook.react.bridge.GuardedAsyncTask
                public void doInBackgroundGuarded(Void... params) {
                    if (!AsyncStorageModule.this.ensureDatabase()) {
                        callback.invoke(AsyncStorageErrorUtil.getDBError(null));
                        return;
                    }
                    SQLiteStatement statement = AsyncStorageModule.this.mReactDatabaseSupplier.get().compileStatement("INSERT OR REPLACE INTO catalystLocalStorage VALUES (?, ?);");
                    WritableMap error = null;
                    try {
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().beginTransaction();
                            for (int idx = 0; idx < keyValueArray.size(); idx++) {
                                if (keyValueArray.getArray(idx).size() != 2) {
                                    WritableMap error2 = AsyncStorageErrorUtil.getInvalidValueError(null);
                                    try {
                                        AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                        return;
                                    } catch (Exception e) {
                                        FLog.w("React", e.getMessage(), e);
                                        if (error2 == null) {
                                            AsyncStorageErrorUtil.getError(null, e.getMessage());
                                            return;
                                        }
                                        return;
                                    }
                                }
                                if (keyValueArray.getArray(idx).getString(0) == null) {
                                    WritableMap error3 = AsyncStorageErrorUtil.getInvalidKeyError(null);
                                    try {
                                        AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                        return;
                                    } catch (Exception e2) {
                                        FLog.w("React", e2.getMessage(), e2);
                                        if (error3 == null) {
                                            AsyncStorageErrorUtil.getError(null, e2.getMessage());
                                            return;
                                        }
                                        return;
                                    }
                                }
                                if (keyValueArray.getArray(idx).getString(1) == null) {
                                    WritableMap error4 = AsyncStorageErrorUtil.getInvalidValueError(null);
                                    try {
                                        AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                        return;
                                    } catch (Exception e3) {
                                        FLog.w("React", e3.getMessage(), e3);
                                        if (error4 == null) {
                                            AsyncStorageErrorUtil.getError(null, e3.getMessage());
                                            return;
                                        }
                                        return;
                                    }
                                }
                                statement.clearBindings();
                                statement.bindString(1, keyValueArray.getArray(idx).getString(0));
                                statement.bindString(2, keyValueArray.getArray(idx).getString(1));
                                statement.execute();
                            }
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().setTransactionSuccessful();
                            try {
                                AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                            } catch (Exception e4) {
                                FLog.w("React", e4.getMessage(), e4);
                                if (0 == 0) {
                                    error = AsyncStorageErrorUtil.getError(null, e4.getMessage());
                                }
                            }
                        } catch (Exception e5) {
                            FLog.w("React", e5.getMessage(), e5);
                            error = AsyncStorageErrorUtil.getError(null, e5.getMessage());
                            try {
                                AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                            } catch (Exception e6) {
                                FLog.w("React", e6.getMessage(), e6);
                                if (error == null) {
                                    error = AsyncStorageErrorUtil.getError(null, e6.getMessage());
                                }
                            }
                        }
                        if (error != null) {
                            callback.invoke(error);
                        } else {
                            callback.invoke(new Object[0]);
                        }
                    } catch (Throwable th) {
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Exception e7) {
                            FLog.w("React", e7.getMessage(), e7);
                            if (0 == 0) {
                                AsyncStorageErrorUtil.getError(null, e7.getMessage());
                            }
                        }
                        throw th;
                    }
                }
            }.execute(new Void[0]);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.facebook.react.modules.storage.AsyncStorageModule$3] */
    @ReactMethod
    public void multiRemove(final ReadableArray keys, final Callback callback) {
        if (keys.size() == 0) {
            callback.invoke(AsyncStorageErrorUtil.getInvalidKeyError(null));
        } else {
            new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) { // from class: com.facebook.react.modules.storage.AsyncStorageModule.3
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.facebook.react.bridge.GuardedAsyncTask
                public void doInBackgroundGuarded(Void... params) {
                    if (!AsyncStorageModule.this.ensureDatabase()) {
                        callback.invoke(AsyncStorageErrorUtil.getDBError(null));
                        return;
                    }
                    WritableMap error = null;
                    try {
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().beginTransaction();
                            for (int keyStart = 0; keyStart < keys.size(); keyStart += AsyncStorageModule.MAX_SQL_KEYS) {
                                int keyCount = Math.min(keys.size() - keyStart, AsyncStorageModule.MAX_SQL_KEYS);
                                AsyncStorageModule.this.mReactDatabaseSupplier.get().delete("catalystLocalStorage", AsyncLocalStorageUtil.buildKeySelection(keyCount), AsyncLocalStorageUtil.buildKeySelectionArgs(keys, keyStart, keyCount));
                            }
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().setTransactionSuccessful();
                            try {
                                AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                            } catch (Exception e) {
                                FLog.w("React", e.getMessage(), e);
                                if (0 == 0) {
                                    error = AsyncStorageErrorUtil.getError(null, e.getMessage());
                                }
                            }
                        } catch (Throwable th) {
                            try {
                                AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                            } catch (Exception e2) {
                                FLog.w("React", e2.getMessage(), e2);
                                if (0 == 0) {
                                    AsyncStorageErrorUtil.getError(null, e2.getMessage());
                                }
                            }
                            throw th;
                        }
                    } catch (Exception e3) {
                        FLog.w("React", e3.getMessage(), e3);
                        error = AsyncStorageErrorUtil.getError(null, e3.getMessage());
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Exception e4) {
                            FLog.w("React", e4.getMessage(), e4);
                            if (error == null) {
                                error = AsyncStorageErrorUtil.getError(null, e4.getMessage());
                            }
                        }
                    }
                    if (error != null) {
                        callback.invoke(error);
                    } else {
                        callback.invoke(new Object[0]);
                    }
                }
            }.execute(new Void[0]);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.modules.storage.AsyncStorageModule$4] */
    @ReactMethod
    public void multiMerge(final ReadableArray keyValueArray, final Callback callback) {
        new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) { // from class: com.facebook.react.modules.storage.AsyncStorageModule.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.react.bridge.GuardedAsyncTask
            public void doInBackgroundGuarded(Void... params) {
                if (!AsyncStorageModule.this.ensureDatabase()) {
                    callback.invoke(AsyncStorageErrorUtil.getDBError(null));
                    return;
                }
                WritableMap error = null;
                try {
                    try {
                        AsyncStorageModule.this.mReactDatabaseSupplier.get().beginTransaction();
                        for (int idx = 0; idx < keyValueArray.size(); idx++) {
                            if (keyValueArray.getArray(idx).size() != 2) {
                                WritableMap error2 = AsyncStorageErrorUtil.getInvalidValueError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                    return;
                                } catch (Exception e) {
                                    FLog.w("React", e.getMessage(), e);
                                    if (error2 == null) {
                                        AsyncStorageErrorUtil.getError(null, e.getMessage());
                                        return;
                                    }
                                    return;
                                }
                            }
                            if (keyValueArray.getArray(idx).getString(0) == null) {
                                WritableMap error3 = AsyncStorageErrorUtil.getInvalidKeyError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                    return;
                                } catch (Exception e2) {
                                    FLog.w("React", e2.getMessage(), e2);
                                    if (error3 == null) {
                                        AsyncStorageErrorUtil.getError(null, e2.getMessage());
                                        return;
                                    }
                                    return;
                                }
                            }
                            if (keyValueArray.getArray(idx).getString(1) == null) {
                                WritableMap error4 = AsyncStorageErrorUtil.getInvalidValueError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                    return;
                                } catch (Exception e3) {
                                    FLog.w("React", e3.getMessage(), e3);
                                    if (error4 == null) {
                                        AsyncStorageErrorUtil.getError(null, e3.getMessage());
                                        return;
                                    }
                                    return;
                                }
                            }
                            if (!AsyncLocalStorageUtil.mergeImpl(AsyncStorageModule.this.mReactDatabaseSupplier.get(), keyValueArray.getArray(idx).getString(0), keyValueArray.getArray(idx).getString(1))) {
                                WritableMap error5 = AsyncStorageErrorUtil.getDBError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                    return;
                                } catch (Exception e4) {
                                    FLog.w("React", e4.getMessage(), e4);
                                    if (error5 == null) {
                                        AsyncStorageErrorUtil.getError(null, e4.getMessage());
                                        return;
                                    }
                                    return;
                                }
                            }
                        }
                        AsyncStorageModule.this.mReactDatabaseSupplier.get().setTransactionSuccessful();
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Exception e5) {
                            FLog.w("React", e5.getMessage(), e5);
                            if (0 == 0) {
                                error = AsyncStorageErrorUtil.getError(null, e5.getMessage());
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Exception e6) {
                            FLog.w("React", e6.getMessage(), e6);
                            if (0 == 0) {
                                AsyncStorageErrorUtil.getError(null, e6.getMessage());
                            }
                        }
                        throw th;
                    }
                } catch (Exception e7) {
                    FLog.w("React", e7.getMessage(), e7);
                    error = AsyncStorageErrorUtil.getError(null, e7.getMessage());
                    try {
                        AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                    } catch (Exception e8) {
                        FLog.w("React", e8.getMessage(), e8);
                        if (error == null) {
                            error = AsyncStorageErrorUtil.getError(null, e8.getMessage());
                        }
                    }
                }
                if (error != null) {
                    callback.invoke(error);
                } else {
                    callback.invoke(new Object[0]);
                }
            }
        }.execute(new Void[0]);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.modules.storage.AsyncStorageModule$5] */
    @ReactMethod
    public void clear(final Callback callback) {
        new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) { // from class: com.facebook.react.modules.storage.AsyncStorageModule.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.react.bridge.GuardedAsyncTask
            public void doInBackgroundGuarded(Void... params) {
                if (AsyncStorageModule.this.mReactDatabaseSupplier.ensureDatabase()) {
                    try {
                        AsyncStorageModule.this.mReactDatabaseSupplier.clear();
                        callback.invoke(new Object[0]);
                        return;
                    } catch (Exception e) {
                        FLog.w("React", e.getMessage(), e);
                        callback.invoke(AsyncStorageErrorUtil.getError(null, e.getMessage()));
                        return;
                    }
                }
                callback.invoke(AsyncStorageErrorUtil.getDBError(null));
            }
        }.execute(new Void[0]);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.modules.storage.AsyncStorageModule$6] */
    @ReactMethod
    public void getAllKeys(final Callback callback) {
        new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) { // from class: com.facebook.react.modules.storage.AsyncStorageModule.6
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.react.bridge.GuardedAsyncTask
            public void doInBackgroundGuarded(Void... params) {
                if (!AsyncStorageModule.this.ensureDatabase()) {
                    callback.invoke(AsyncStorageErrorUtil.getDBError(null), null);
                    return;
                }
                WritableArray data = Arguments.createArray();
                String[] columns = {SettingsContentProvider.KEY};
                Cursor cursor = AsyncStorageModule.this.mReactDatabaseSupplier.get().query("catalystLocalStorage", columns, null, null, null, null, null);
                try {
                    try {
                        if (cursor.moveToFirst()) {
                            do {
                                data.pushString(cursor.getString(0));
                            } while (cursor.moveToNext());
                        }
                        cursor.close();
                        callback.invoke(null, data);
                    } catch (Exception e) {
                        FLog.w("React", e.getMessage(), e);
                        callback.invoke(AsyncStorageErrorUtil.getError(null, e.getMessage()), null);
                        cursor.close();
                    }
                } catch (Throwable th) {
                    cursor.close();
                    throw th;
                }
            }
        }.execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean ensureDatabase() {
        return !this.mShuttingDown && this.mReactDatabaseSupplier.ensureDatabase();
    }
}
