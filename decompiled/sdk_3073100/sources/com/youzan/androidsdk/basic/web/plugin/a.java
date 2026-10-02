package com.youzan.androidsdk.basic.web.plugin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.WebView;
import android.widget.Toast;
import com.youzan.androidsdk.basic.R;
import com.youzan.spiderman.utils.FileCallback;
import com.youzan.spiderman.utils.MD5Utils;
import com.youzan.spiderman.utils.OkHttpUtil;
import com.youzan.spiderman.utils.PermissionUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: compiled from: SaveImageProcessor.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static final String f84 = "yzsdk_files";

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private static final String f85 = "images";

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public boolean m65(final WebView webView) {
        final Context context = webView.getContext();
        if (context == null) {
            return false;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(new CharSequence[]{context.getString(R.string.yzappsdk_save_image)}, new DialogInterface.OnClickListener() { // from class: com.youzan.androidsdk.basic.web.plugin.a.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                a.this.m61(context, webView);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private File m60(Context context) {
        File extPath;
        String path = null;
        try {
            if (PermissionUtil.hasExtStroragePermision(context) && "mounted".equals(Environment.getExternalStorageState()) && (extPath = context.getExternalFilesDir(null)) != null) {
                path = extPath.getAbsolutePath() + File.separator + f84 + File.separator + f85;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(path)) {
            path = context.getFilesDir().getAbsolutePath() + File.separator + f84 + File.separator + f85;
        }
        File file = new File(path);
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public void m61(final Context context, final WebView webView) {
        File rootImagePath = m60(context);
        if (rootImagePath == null) {
            Toast.makeText(context, R.string.yzappsdk_save_image_failed, 0).show();
            return;
        }
        WebView.HitTestResult hitTestResult = webView.getHitTestResult();
        if (hitTestResult == null) {
            Toast.makeText(context, R.string.yzappsdk_save_image_failed, 0).show();
            return;
        }
        String imageUrl = hitTestResult.getExtra();
        if (imageUrl == null) {
            Toast.makeText(context, R.string.yzappsdk_save_image_failed, 0).show();
            return;
        }
        if (imageUrl.startsWith("data:")) {
            String imageData = imageUrl.replaceFirst("data:image\\/\\w+;base64,", "");
            byte[] imageDataBytes = Base64.decode(imageData, 0);
            File imageFile = new File(rootImagePath, MD5Utils.getStringMd5(imageData) + ".png");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                fileOutputStream.write(imageDataBytes);
                fileOutputStream.close();
                m62(context, imageFile);
                Toast.makeText(context, R.string.yzappsdk_save_image_succeed, 0).show();
                return;
            } catch (IOException e) {
                Toast.makeText(context, R.string.yzappsdk_save_image_succeed, 0).show();
                e.printStackTrace();
                return;
            }
        }
        Uri imageUri = Uri.parse(imageUrl);
        String fileName = imageUri.getLastPathSegment();
        if (TextUtils.isEmpty(fileName)) {
            fileName = MD5Utils.getStringMd5(imageUrl);
        }
        final File imageFile2 = new File(rootImagePath, fileName);
        OkHttpUtil.downloadFile(context, imageUrl, imageFile2, new FileCallback() { // from class: com.youzan.androidsdk.basic.web.plugin.a.2
            @Override // com.youzan.spiderman.utils.FileCallback
            public void success() {
                a.this.m62(context, imageFile2);
                webView.post(new Runnable() { // from class: com.youzan.androidsdk.basic.web.plugin.a.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(context, R.string.yzappsdk_save_image_succeed, 0).show();
                    }
                });
            }

            @Override // com.youzan.spiderman.utils.FileCallback
            public void fail(int i, Exception e2) {
                webView.post(new Runnable() { // from class: com.youzan.androidsdk.basic.web.plugin.a.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(context, R.string.yzappsdk_save_image_failed, 0).show();
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public void m62(Context context, File imageFile) {
        String imagePath = imageFile.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imagePath, "", "");
            if (Build.VERSION.SDK_INT >= 19) {
                Intent scanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                Uri contentUri = Uri.parse(imagePath);
                scanIntent.setData(contentUri);
                context.sendBroadcast(scanIntent);
            } else {
                context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse(imagePath)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
