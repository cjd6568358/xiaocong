package com.facebook.react.modules.camera;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CameraRollManager extends ReactContextBaseJavaModule {
    private static final String ERROR_UNABLE_TO_LOAD = "E_UNABLE_TO_LOAD";
    private static final String ERROR_UNABLE_TO_LOAD_PERMISSION = "E_UNABLE_TO_LOAD_PERMISSION";
    private static final String ERROR_UNABLE_TO_SAVE = "E_UNABLE_TO_SAVE";
    public static final boolean IS_JELLY_BEAN_OR_LATER;
    protected static final String NAME = "CameraRollManager";
    private static final String[] PROJECTION;
    private static final String SELECTION_BUCKET = "bucket_display_name = ?";
    private static final String SELECTION_DATE_TAKEN = "datetaken < ?";

    static {
        IS_JELLY_BEAN_OR_LATER = Build.VERSION.SDK_INT >= 16;
        if (IS_JELLY_BEAN_OR_LATER) {
            PROJECTION = new String[]{"_id", "mime_type", "bucket_display_name", "datetaken", IMediaFormat.KEY_WIDTH, IMediaFormat.KEY_HEIGHT, "longitude", "latitude"};
        } else {
            PROJECTION = new String[]{"_id", "mime_type", "bucket_display_name", "datetaken", "longitude", "latitude"};
        }
    }

    public CameraRollManager(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void saveToCameraRoll(String uri, String type, Promise promise) {
        new SaveToCameraRoll(getReactApplicationContext(), Uri.parse(uri), promise).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private static class SaveToCameraRoll extends GuardedAsyncTask<Void, Void> {
        private final Context mContext;
        private final Promise mPromise;
        private final Uri mUri;

        public SaveToCameraRoll(ReactContext context, Uri uri, Promise promise) {
            super(context);
            this.mContext = context;
            this.mUri = uri;
            this.mPromise = promise;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.react.bridge.GuardedAsyncTask
        public void doInBackgroundGuarded(Void... params) {
            boolean zIsOpen;
            String sourceName;
            String sourceExt;
            File source = new File(this.mUri.getPath());
            FileChannel input = null;
            FileChannel output = null;
            try {
                File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                exportDir.mkdirs();
                if (!exportDir.isDirectory()) {
                    this.mPromise.reject(CameraRollManager.ERROR_UNABLE_TO_LOAD, "External media storage directory not available");
                    if (output != null) {
                        if (zIsOpen) {
                            try {
                                return;
                            } catch (IOException e) {
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
                File dest = new File(exportDir, source.getName());
                int n = 0;
                String fullSourceName = source.getName();
                if (fullSourceName.indexOf(46) >= 0) {
                    sourceName = fullSourceName.substring(0, fullSourceName.lastIndexOf(46));
                    sourceExt = fullSourceName.substring(fullSourceName.lastIndexOf(46));
                } else {
                    sourceName = fullSourceName;
                    sourceExt = Constants.MAIN_VERSION_TAG;
                }
                while (!dest.createNewFile()) {
                    dest = new File(exportDir, sourceName + "_" + n + sourceExt);
                    n++;
                }
                input = new FileInputStream(source).getChannel();
                output = new FileOutputStream(dest).getChannel();
                output.transferFrom(input, 0L, input.size());
                input.close();
                output.close();
                MediaScannerConnection.scanFile(this.mContext, new String[]{dest.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.facebook.react.modules.camera.CameraRollManager.SaveToCameraRoll.1
                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                    public void onScanCompleted(String path, Uri uri) {
                        if (uri != null) {
                            SaveToCameraRoll.this.mPromise.resolve(uri.toString());
                        } else {
                            SaveToCameraRoll.this.mPromise.reject(CameraRollManager.ERROR_UNABLE_TO_SAVE, "Could not add image to gallery");
                        }
                    }
                });
            } catch (IOException e2) {
                this.mPromise.reject(e2);
            } finally {
                if (input != null && input.isOpen()) {
                    try {
                        input.close();
                    } catch (IOException e3) {
                        FLog.e("React", "Could not close input channel", e3);
                    }
                }
                if (output != null && output.isOpen()) {
                    try {
                        output.close();
                    } catch (IOException e4) {
                        FLog.e("React", "Could not close output channel", e4);
                    }
                }
            }
        }
    }

    @ReactMethod
    public void getPhotos(ReadableMap params, Promise promise) {
        int first = params.getInt("first");
        String after = params.hasKey("after") ? params.getString("after") : null;
        String groupName = params.hasKey("groupName") ? params.getString("groupName") : null;
        ReadableArray mimeTypes = params.hasKey("mimeTypes") ? params.getArray("mimeTypes") : null;
        if (params.hasKey("groupTypes")) {
            throw new JSApplicationIllegalArgumentException("groupTypes is not supported on Android");
        }
        new GetPhotosTask(getReactApplicationContext(), first, after, groupName, mimeTypes, promise).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private static class GetPhotosTask extends GuardedAsyncTask<Void, Void> {
        private final String mAfter;
        private final Context mContext;
        private final int mFirst;
        private final String mGroupName;
        private final ReadableArray mMimeTypes;
        private final Promise mPromise;

        private GetPhotosTask(ReactContext context, int first, String after, String groupName, ReadableArray mimeTypes, Promise promise) {
            super(context);
            this.mContext = context;
            this.mFirst = first;
            this.mAfter = after;
            this.mGroupName = groupName;
            this.mMimeTypes = mimeTypes;
            this.mPromise = promise;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.react.bridge.GuardedAsyncTask
        public void doInBackgroundGuarded(Void... params) {
            StringBuilder selection = new StringBuilder("1");
            List<String> selectionArgs = new ArrayList<>();
            if (!TextUtils.isEmpty(this.mAfter)) {
                selection.append(" AND datetaken < ?");
                selectionArgs.add(this.mAfter);
            }
            if (!TextUtils.isEmpty(this.mGroupName)) {
                selection.append(" AND bucket_display_name = ?");
                selectionArgs.add(this.mGroupName);
            }
            if (this.mMimeTypes != null && this.mMimeTypes.size() > 0) {
                selection.append(" AND mime_type IN (");
                for (int i = 0; i < this.mMimeTypes.size(); i++) {
                    selection.append("?,");
                    selectionArgs.add(this.mMimeTypes.getString(i));
                }
                selection.replace(selection.length() - 1, selection.length(), ")");
            }
            WritableMap response = new WritableNativeMap();
            ContentResolver resolver = this.mContext.getContentResolver();
            try {
                Cursor photos = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CameraRollManager.PROJECTION, selection.toString(), (String[]) selectionArgs.toArray(new String[selectionArgs.size()]), "datetaken DESC, date_modified DESC LIMIT " + (this.mFirst + 1));
                if (photos != null) {
                    try {
                        CameraRollManager.putEdges(resolver, photos, response, this.mFirst);
                        CameraRollManager.putPageInfo(photos, response, this.mFirst);
                        return;
                    } finally {
                        photos.close();
                        this.mPromise.resolve(response);
                    }
                }
                this.mPromise.reject(CameraRollManager.ERROR_UNABLE_TO_LOAD, "Could not get photos");
            } catch (SecurityException e) {
                this.mPromise.reject(CameraRollManager.ERROR_UNABLE_TO_LOAD_PERMISSION, "Could not get photos: need READ_EXTERNAL_STORAGE permission", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putPageInfo(Cursor photos, WritableMap response, int limit) {
        WritableMap pageInfo = new WritableNativeMap();
        pageInfo.putBoolean("has_next_page", limit < photos.getCount());
        if (limit < photos.getCount()) {
            photos.moveToPosition(limit - 1);
            pageInfo.putString("end_cursor", photos.getString(photos.getColumnIndex("datetaken")));
        }
        response.putMap("page_info", pageInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putEdges(ContentResolver resolver, Cursor photos, WritableMap response, int limit) {
        WritableArray edges = new WritableNativeArray();
        photos.moveToFirst();
        int idIndex = photos.getColumnIndex("_id");
        int mimeTypeIndex = photos.getColumnIndex("mime_type");
        int groupNameIndex = photos.getColumnIndex("bucket_display_name");
        int dateTakenIndex = photos.getColumnIndex("datetaken");
        int widthIndex = IS_JELLY_BEAN_OR_LATER ? photos.getColumnIndex(IMediaFormat.KEY_WIDTH) : -1;
        int heightIndex = IS_JELLY_BEAN_OR_LATER ? photos.getColumnIndex(IMediaFormat.KEY_HEIGHT) : -1;
        int longitudeIndex = photos.getColumnIndex("longitude");
        int latitudeIndex = photos.getColumnIndex("latitude");
        int i = 0;
        while (i < limit && !photos.isAfterLast()) {
            WritableMap edge = new WritableNativeMap();
            WritableMap node = new WritableNativeMap();
            boolean imageInfoSuccess = putImageInfo(resolver, photos, node, idIndex, widthIndex, heightIndex);
            if (imageInfoSuccess) {
                putBasicNodeInfo(photos, node, mimeTypeIndex, groupNameIndex, dateTakenIndex);
                putLocationInfo(photos, node, longitudeIndex, latitudeIndex);
                edge.putMap("node", node);
                edges.pushMap(edge);
            } else {
                i--;
            }
            photos.moveToNext();
            i++;
        }
        response.putArray("edges", edges);
    }

    private static void putBasicNodeInfo(Cursor photos, WritableMap node, int mimeTypeIndex, int groupNameIndex, int dateTakenIndex) {
        node.putString("type", photos.getString(mimeTypeIndex));
        node.putString("group_name", photos.getString(groupNameIndex));
        node.putDouble("timestamp", photos.getLong(dateTakenIndex) / 1000.0d);
    }

    private static boolean putImageInfo(ContentResolver resolver, Cursor photos, WritableMap node, int idIndex, int widthIndex, int heightIndex) {
        WritableMap image = new WritableNativeMap();
        Uri photoUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, photos.getString(idIndex));
        image.putString("uri", photoUri.toString());
        float width = -1.0f;
        float height = -1.0f;
        if (IS_JELLY_BEAN_OR_LATER) {
            width = photos.getInt(widthIndex);
            height = photos.getInt(heightIndex);
        }
        if (width <= 0.0f || height <= 0.0f) {
            try {
                AssetFileDescriptor photoDescriptor = resolver.openAssetFileDescriptor(photoUri, "r");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(photoDescriptor.getFileDescriptor(), null, options);
                photoDescriptor.close();
                width = options.outWidth;
                height = options.outHeight;
            } catch (IOException e) {
                FLog.e("React", "Could not get width/height for " + photoUri.toString(), e);
                return false;
            }
        }
        image.putDouble(IMediaFormat.KEY_WIDTH, width);
        image.putDouble(IMediaFormat.KEY_HEIGHT, height);
        node.putMap("image", image);
        return true;
    }

    private static void putLocationInfo(Cursor photos, WritableMap node, int longitudeIndex, int latitudeIndex) {
        double longitude = photos.getDouble(longitudeIndex);
        double latitude = photos.getDouble(latitudeIndex);
        if (longitude > 0.0d || latitude > 0.0d) {
            WritableMap location = new WritableNativeMap();
            location.putDouble("longitude", longitude);
            location.putDouble("latitude", latitude);
            node.putMap("location", location);
        }
    }
}
