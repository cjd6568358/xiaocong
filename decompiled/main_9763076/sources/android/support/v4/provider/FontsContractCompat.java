package android.support.v4.provider;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.util.LruCache;
import android.support.v4.util.Preconditions;
import android.support.v4.util.SimpleArrayMap;
import android.widget.TextView;
import com.tencent.android.tpush.common.Constants;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.http.HttpStatus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FontsContractCompat {
    private static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);
    private static final SelfDestructiveThread sBackgroundThread = new SelfDestructiveThread("fonts", 10, Constants.ERRORCODE_UNKNOWN);
    private static final Object sLock = new Object();
    private static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>>> sPendingReplies = new SimpleArrayMap<>();
    private static final Comparator<byte[]> sByteArrayComparator = new Comparator<byte[]>() { // from class: android.support.v4.provider.FontsContractCompat.5
        @Override // java.util.Comparator
        public int compare(byte[] l, byte[] r) {
            if (l.length != r.length) {
                return l.length - r.length;
            }
            for (int i = 0; i < l.length; i++) {
                if (l[i] != r[i]) {
                    return l[i] - r[i];
                }
            }
            return 0;
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static Typeface getFontInternal(Context context, FontRequest request, int style) throws Throwable {
        try {
            FontFamilyResult result = fetchFonts(context, null, request);
            if (result.getStatusCode() == 0) {
                return TypefaceCompat.createFromFontInfo(context, null, result.getFonts(), style);
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static Typeface getFontSync(final Context context, final FontRequest request, final TextView targetView, int strategy, int timeout, final int style) {
        Typeface cached;
        final String id = request.getIdentifier() + "-" + style;
        Typeface cached2 = sTypefaceCache.get(id);
        if (cached2 == null) {
            boolean isBlockingFetch = strategy == 0;
            if (isBlockingFetch && timeout == -1) {
                return getFontInternal(context, request, style);
            }
            Callable<Typeface> fetcher = new Callable<Typeface>() { // from class: android.support.v4.provider.FontsContractCompat.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public Typeface call() throws Exception {
                    Typeface typeface = FontsContractCompat.getFontInternal(context, request, style);
                    if (typeface != null) {
                        FontsContractCompat.sTypefaceCache.put(id, typeface);
                    }
                    return typeface;
                }
            };
            if (isBlockingFetch) {
                try {
                    return (Typeface) sBackgroundThread.postAndWait(fetcher, timeout);
                } catch (InterruptedException e) {
                    return null;
                }
            }
            final WeakReference<TextView> textViewWeak = new WeakReference<>(targetView);
            SelfDestructiveThread.ReplyCallback<Typeface> reply = new SelfDestructiveThread.ReplyCallback<Typeface>() { // from class: android.support.v4.provider.FontsContractCompat.2
                @Override // android.support.v4.provider.SelfDestructiveThread.ReplyCallback
                public void onReply(Typeface typeface) {
                    TextView textView = (TextView) textViewWeak.get();
                    if (textView != null) {
                        targetView.setTypeface(typeface, style);
                    }
                }
            };
            synchronized (sLock) {
                if (sPendingReplies.containsKey(id)) {
                    sPendingReplies.get(id).add(reply);
                    cached = null;
                } else {
                    ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>> pendingReplies = new ArrayList<>();
                    pendingReplies.add(reply);
                    sPendingReplies.put(id, pendingReplies);
                    sBackgroundThread.postAndReply(fetcher, new SelfDestructiveThread.ReplyCallback<Typeface>() { // from class: android.support.v4.provider.FontsContractCompat.3
                        @Override // android.support.v4.provider.SelfDestructiveThread.ReplyCallback
                        public void onReply(Typeface typeface) {
                            ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>> replies;
                            synchronized (FontsContractCompat.sLock) {
                                replies = (ArrayList) FontsContractCompat.sPendingReplies.get(id);
                                FontsContractCompat.sPendingReplies.remove(id);
                            }
                            for (int i = 0; i < replies.size(); i++) {
                                replies.get(i).onReply(typeface);
                            }
                        }
                    });
                    cached = null;
                }
            }
            return cached;
        }
        return cached2;
    }

    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        public FontInfo(Uri uri, int ttcIndex, int weight, boolean italic, int resultCode) {
            this.mUri = (Uri) Preconditions.checkNotNull(uri);
            this.mTtcIndex = ttcIndex;
            this.mWeight = weight;
            this.mItalic = italic;
            this.mResultCode = resultCode;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResultCode() {
            return this.mResultCode;
        }
    }

    public static class FontFamilyResult {
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        public FontFamilyResult(int statusCode, FontInfo[] fonts) {
            this.mStatusCode = statusCode;
            this.mFonts = fonts;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }
    }

    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fonts, CancellationSignal cancellationSignal) {
        HashMap<Uri, ByteBuffer> out = new HashMap<>();
        for (FontInfo font : fonts) {
            if (font.getResultCode() == 0) {
                Uri uri = font.getUri();
                if (!out.containsKey(uri)) {
                    ByteBuffer buffer = TypefaceCompatUtil.mmap(context, cancellationSignal, uri);
                    out.put(uri, buffer);
                }
            }
        }
        return Collections.unmodifiableMap(out);
    }

    public static FontFamilyResult fetchFonts(Context context, CancellationSignal cancellationSignal, FontRequest request) throws Throwable {
        ProviderInfo providerInfo = getProvider(context.getPackageManager(), request, context.getResources());
        if (providerInfo == null) {
            return new FontFamilyResult(1, null);
        }
        FontInfo[] fonts = getFontFromProvider(context, request, providerInfo.authority, cancellationSignal);
        return new FontFamilyResult(0, fonts);
    }

    public static ProviderInfo getProvider(PackageManager packageManager, FontRequest request, Resources resources) throws PackageManager.NameNotFoundException {
        String providerAuthority = request.getProviderAuthority();
        ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
        if (info == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        }
        if (!info.packageName.equals(request.getProviderPackage())) {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
        }
        PackageInfo packageInfo = packageManager.getPackageInfo(info.packageName, 64);
        List<byte[]> signatures = convertToByteArrayList(packageInfo.signatures);
        Collections.sort(signatures, sByteArrayComparator);
        List<List<byte[]>> requestCertificatesList = getCertificates(request, resources);
        for (int i = 0; i < requestCertificatesList.size(); i++) {
            List<byte[]> requestSignatures = new ArrayList<>(requestCertificatesList.get(i));
            Collections.sort(requestSignatures, sByteArrayComparator);
            if (equalsByteArrayList(signatures, requestSignatures)) {
                return info;
            }
        }
        return null;
    }

    private static List<List<byte[]>> getCertificates(FontRequest request, Resources resources) {
        if (request.getCertificates() != null) {
            return request.getCertificates();
        }
        int resourceId = request.getCertificatesArrayResId();
        return FontResourcesParserCompat.readCerts(resources, resourceId);
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals(signatures.get(i), requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shas = new ArrayList<>();
        for (Signature signature : signatures) {
            shas.add(signature.toByteArray());
        }
        return shas;
    }

    static FontInfo[] getFontFromProvider(Context context, FontRequest request, String authority, CancellationSignal cancellationSignal) throws Throwable {
        Uri fileUri;
        ArrayList<FontInfo> result = new ArrayList<>();
        Uri uri = new Uri.Builder().scheme("content").authority(authority).build();
        Uri fileBaseUri = new Uri.Builder().scheme("content").authority(authority).appendPath("file").build();
        Cursor cursor = null;
        try {
            if (Build.VERSION.SDK_INT > 16) {
                cursor = context.getContentResolver().query(uri, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{request.getQuery()}, null, cancellationSignal);
            } else {
                cursor = context.getContentResolver().query(uri, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{request.getQuery()}, null);
            }
            if (cursor != null && cursor.getCount() > 0) {
                int resultCodeColumnIndex = cursor.getColumnIndex("result_code");
                ArrayList<FontInfo> result2 = new ArrayList<>();
                try {
                    int idColumnIndex = cursor.getColumnIndex("_id");
                    int fileIdColumnIndex = cursor.getColumnIndex("file_id");
                    int ttcIndexColumnIndex = cursor.getColumnIndex("font_ttc_index");
                    int weightColumnIndex = cursor.getColumnIndex("font_weight");
                    int italicColumnIndex = cursor.getColumnIndex("font_italic");
                    while (cursor.moveToNext()) {
                        int resultCode = resultCodeColumnIndex != -1 ? cursor.getInt(resultCodeColumnIndex) : 0;
                        int ttcIndex = ttcIndexColumnIndex != -1 ? cursor.getInt(ttcIndexColumnIndex) : 0;
                        if (fileIdColumnIndex == -1) {
                            long id = cursor.getLong(idColumnIndex);
                            fileUri = ContentUris.withAppendedId(uri, id);
                        } else {
                            long id2 = cursor.getLong(fileIdColumnIndex);
                            fileUri = ContentUris.withAppendedId(fileBaseUri, id2);
                        }
                        int weight = weightColumnIndex != -1 ? cursor.getInt(weightColumnIndex) : HttpStatus.SC_BAD_REQUEST;
                        boolean italic = italicColumnIndex != -1 && cursor.getInt(italicColumnIndex) == 1;
                        result2.add(new FontInfo(fileUri, ttcIndex, weight, italic, resultCode));
                    }
                    result = result2;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return (FontInfo[]) result.toArray(new FontInfo[0]);
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
