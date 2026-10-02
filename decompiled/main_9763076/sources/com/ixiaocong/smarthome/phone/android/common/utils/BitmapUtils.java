package com.ixiaocong.smarthome.phone.android.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.config.AppConstans;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BitmapUtils {
    private static final String FILE_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/images";
    private static Uri cropUri;
    public static File protraitFile;
    public static String protraitPath;

    public static void startPhoto(Activity activity, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", getUploadTempFile(activity, uri));
        intent.putExtra("output", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 2);
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        Uri uritempFile = Uri.parse(AppConstans.USER_IMG_URL);
        intent.putExtra("output", uritempFile);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, ACConstants.TAG_LR_WIND_MODE1);
    }

    public static void getoSystemPhoto(Context context, int photoCode) {
        Intent intent_pick = new Intent("android.intent.action.PICK", (Uri) null);
        intent_pick.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((Activity) context).startActivityForResult(intent_pick, photoCode);
    }

    public static Uri getUploadTempFile(Activity activity, Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals("mounted")) {
            File saveDir = new File(FILE_SAVEPATH);
            if (saveDir.exists()) {
                saveDir.mkdir();
            }
        } else {
            ToastUtils.showShort(activity, "无法保存上传的头像,请稍后重试");
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String thePath = getAbsolutePathFromNoStandardUri(uri);
        if (StringUtils.isEmpty(thePath)) {
            thePath = getAbsoluteImagePath(activity, uri);
        }
        String ext = FileUtils.getFileFormat(thePath);
        if (StringUtils.isEmpty(ext)) {
            ext = "jpg";
        }
        String cropFileName = "fn_crop_" + timeStamp + "." + ext;
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);
        cropUri = Uri.fromFile(protraitFile);
        return cropUri;
    }

    public static String getAbsolutePathFromNoStandardUri(Uri mUri) {
        String mUriString = Uri.decode(mUri.toString());
        String pre1 = "file:///sdcard" + File.separator;
        String pre2 = "file:///mnt/sdcard" + File.separator;
        if (mUriString.startsWith(pre1)) {
            String filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + mUriString.substring(pre1.length());
            return filePath;
        }
        if (!mUriString.startsWith(pre2)) {
            return null;
        }
        String filePath2 = Environment.getExternalStorageDirectory().getPath() + File.separator + mUriString.substring(pre2.length());
        return filePath2;
    }

    public static String getAbsoluteImagePath(Activity context, Uri uri) {
        String[] proj = {"_data"};
        Cursor cursor = context.managedQuery(uri, proj, null, null, null);
        if (cursor == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        int column_index = cursor.getColumnIndexOrThrow("_data");
        if (cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            return Constants.MAIN_VERSION_TAG;
        }
        String imagePath = cursor.getString(column_index);
        return imagePath;
    }

    public static File saveBitmapToFile(Bitmap bm, Context context) {
        File file = new File(context.getCacheDir(), "user.png");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return file;
    }
}
