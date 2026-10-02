package com.tencent.android.tpush.service.channel.security;

import android.content.Context;
import android.content.SharedPreferences;
import bsh.ParserConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.e.m;
import java.io.File;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TpnsSecurity {
    private static final String DEVICE_ID_KEY_NAME = "deviceId_v1";
    private static final String DEVICE_ID_PREFIX = "deviceId_";
    private static final String DEVICE_ID_VERSION = "v1";
    private static final String SETTINGS_DEVICE_ID_KEY_NAME = ".com.tencent.tpush.cache.deviceId_v1";
    private static final String SETTINGS_DEVICE_ID_PREFIX = ".com.tencent.tpush.cache";
    private static final String SHAREPREFERENCE_FILE_NAME = "device_id";
    private static boolean loadedTpnsSecuritySo = false;
    public static g tea = null;
    public static final String tpnsSecurityLibFullName = "libtpnsSecurity.so";
    private static final String tpnsSecurityLibName = "tpnsSecurity";
    protected byte[] encKey;
    protected long incRemote;
    protected byte[] iv;
    protected byte[] key;
    protected long random;
    protected long inc = 0;
    String modulusStr = "C0EF17C0E492C4D366E236902188EF567990289AF267DDC48134C78F3D5632BACB469E1961DD7D61EFEC6B045A138C4DC2E53CC850E796B20664B8F8F58B96F81C9827F7F0C3A15CC4B5BDB5DA2AED5D70E804765F6025613522779A381F5EF3A20A9B043ECA001DB50F873E1CDF335AD382AC66BE3E419CA8F67009BFF3253F";

    public static native byte[] generateAESKey();

    public static native byte[] generateIV(long j);

    public static native String generateLocalSocketServieNameNative(Object obj);

    public static native String getBusinessDeviceIdNative(Object obj);

    public static native String getEncryptAPKSignatureNative(Object obj);

    public static native byte[] oiSymmetryDecrypt2Byte(byte[] bArr);

    public static native byte[] oiSymmetryEncrypt2Byte(String str);

    public native byte[] decryptByAES(byte[] bArr, long j);

    public native byte[] encryptByAES(byte[] bArr, long j);

    public native byte[] encryptByRSA(byte[] bArr);

    static {
        loadedTpnsSecuritySo = false;
        try {
            System.loadLibrary(tpnsSecurityLibName);
            loadedTpnsSecuritySo = true;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "can not load library,error:", th);
            loadedTpnsSecuritySo = false;
        }
        tea = null;
    }

    public static boolean checkTpnsSecurityLibSo(Context context) {
        if (loadedTpnsSecuritySo) {
            return true;
        }
        if (context != null) {
            String str = Constants.MAIN_VERSION_TAG;
            try {
                str = context.getDir("lib", 0).getParentFile().getAbsolutePath() + File.separator + "lib" + File.separator + tpnsSecurityLibFullName;
                System.load(str);
                loadedTpnsSecuritySo = true;
            } catch (Throwable th) {
                loadedTpnsSecuritySo = false;
                com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "can not load library from " + str + ",error:" + th);
            }
        }
        return loadedTpnsSecuritySo;
    }

    public long getRandom() {
        return this.random;
    }

    public byte[] getEncKey() {
        return this.encKey;
    }

    public long getInc() {
        long j = this.inc + 1;
        this.inc = j;
        return j;
    }

    public void checkRemoteInc(long j) {
        if (j <= this.incRemote) {
            throw new SecurityException("检查的inc小于等于当前记录的远端inc");
        }
        this.incRemote = j;
    }

    public void reset() {
        this.random = 0L;
    }

    public boolean needsUpdate() {
        return this.random == 0;
    }

    public void update() {
        this.random = 0L;
        while (this.random == 0) {
            this.random = (long) (Math.random() * 2.147483647E9d);
        }
        this.iv = generateIV(this.random);
        try {
            this.key = generateAESKey();
            this.encKey = encryptByRSA(this.key);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "update error:", th);
        }
    }

    public byte[] decryptData(byte[] bArr) {
        try {
            return decryptByAES(bArr, this.random);
        } catch (Throwable th) {
            th.printStackTrace();
            return bArr;
        }
    }

    public byte[] encryptData(byte[] bArr) {
        try {
            return encryptByAES(bArr, this.random);
        } catch (Throwable th) {
            th.printStackTrace();
            return bArr;
        }
    }

    private static String toCharsString(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length * 2];
        for (int i = 0; i < length; i++) {
            byte b = bArr[i];
            int i2 = (b >> 4) & 15;
            cArr[i * 2] = (char) (i2 >= 10 ? (i2 + 97) - 10 : i2 + 48);
            int i3 = b & 15;
            cArr[(i * 2) + 1] = (char) (i3 >= 10 ? (i3 + 97) - 10 : i3 + 48);
        }
        return new String(cArr);
    }

    /* JADX WARN: Code duplicated, block: B:6:0x000a A[Catch: Throwable -> 0x0045, TryCatch #0 {Throwable -> 0x0045, blocks: (B:4:0x0004, B:8:0x0014, B:10:0x001a, B:11:0x0035, B:13:0x003b, B:6:0x000a), top: B:18:0x0004 }] */
    public static String oiSymmetryEncrypt2(String str) {
        String strA;
        if (str == null) {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> oiSymmetryEncrypt2 加密内容输入为空");
            strA = Constants.MAIN_VERSION_TAG;
        } else {
            try {
                if (str.length() <= 0) {
                    com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> oiSymmetryEncrypt2 加密内容输入为空");
                    strA = Constants.MAIN_VERSION_TAG;
                } else {
                    byte[] bArrOiSymmetryEncrypt2Byte = oiSymmetryEncrypt2Byte(str);
                    if (bArrOiSymmetryEncrypt2Byte == null) {
                        com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> oiSymmetryEncrypt2 加密失败，返回空字符串 inBuff:" + str);
                        strA = "failed";
                    } else {
                        strA = e.a(bArrOiSymmetryEncrypt2Byte);
                        if (strA == null) {
                            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> oiSymmetryEncrypt2 Base64编码失败，返回空字符串");
                            strA = "failed";
                        }
                    }
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, ">> oiSymmetryEncrypt2 未知错误", th);
                return "failed";
            }
        }
        return strA;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x000d A[Catch: Throwable -> 0x003d, TryCatch #0 {Throwable -> 0x003d, blocks: (B:4:0x0007, B:8:0x0017, B:10:0x001d, B:13:0x002a, B:15:0x0030, B:17:0x0033, B:12:0x0020, B:6:0x000d), top: B:23:0x0007 }] */
    public static String oiSymmetryDecrypt2(String str) {
        String str2;
        Constants.MAIN_VERSION_TAG.getBytes();
        if (str == null) {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> oiSymmetryDecrypt2 解密内容输入为空");
            str2 = Constants.MAIN_VERSION_TAG;
        } else {
            try {
                if (str.length() <= 0) {
                    com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> oiSymmetryDecrypt2 解密内容输入为空");
                    str2 = Constants.MAIN_VERSION_TAG;
                } else {
                    byte[] bArrA = d.a(str);
                    if (bArrA == null || bArrA.length <= 0) {
                        com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> oiSymmetryDecrypt2 解码失败，返回空字符串");
                        str2 = "failed";
                    } else {
                        byte[] bArrOiSymmetryDecrypt2Byte = oiSymmetryDecrypt2Byte(bArrA);
                        if (bArrOiSymmetryDecrypt2Byte == null || bArrOiSymmetryDecrypt2Byte.length <= 0) {
                            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> oiSymmetryDecrypt2 解密失败，返回空字符串");
                            str2 = "failed";
                        } else {
                            str2 = new String(bArrOiSymmetryDecrypt2Byte);
                        }
                    }
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, ">> oiSymmetryEncrypt2 未知错误", th);
                return "failed";
            }
        }
        return str2;
    }

    public static String generateLocalSocketServieName(Context context) {
        if (context != null) {
            try {
                return generateLocalSocketServieNameNative(context);
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "generateLocalSocketServieName 未知错误", th);
            }
        }
        throw new SecurityException("generate local socket server name error");
    }

    public static String getBusinessDeviceId(Context context) {
        if (context == null) {
            throw new SecurityException("get device id error cause context is null");
        }
        String settingsLocalDeviceId = getSettingsLocalDeviceId(context);
        if (settingsLocalDeviceId == null) {
            String preferenceLocalDeviceId = getPreferenceLocalDeviceId(context);
            if (preferenceLocalDeviceId != null) {
                setSettingsLocalDeviceId(context, preferenceLocalDeviceId);
                return preferenceLocalDeviceId;
            }
            String businessDeviceIdNative = getBusinessDeviceIdNative(context);
            setPreferenceLocalDeviceId(context, businessDeviceIdNative);
            setSettingsLocalDeviceId(context, businessDeviceIdNative);
            return businessDeviceIdNative;
        }
        return settingsLocalDeviceId;
    }

    private static String getPreferenceLocalDeviceId(Context context) {
        String string;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_FILE_NAME, 0);
        if (!sharedPreferences.contains(com.tencent.android.tpush.encrypt.a.a(DEVICE_ID_KEY_NAME)) || (string = sharedPreferences.getString(com.tencent.android.tpush.encrypt.a.a(DEVICE_ID_KEY_NAME), null)) == null || string.trim().equals(Constants.MAIN_VERSION_TAG)) {
            return null;
        }
        String strDecrypt = Rijndael.decrypt(string);
        if (m.b(strDecrypt)) {
            return null;
        }
        return strDecrypt;
    }

    private static void setPreferenceLocalDeviceId(Context context, String str) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(SHAREPREFERENCE_FILE_NAME, 0).edit();
        editorEdit.putString(com.tencent.android.tpush.encrypt.a.a(DEVICE_ID_KEY_NAME), Rijndael.encrypt(str));
        editorEdit.commit();
    }

    private static String getSettingsLocalDeviceId(Context context) {
        String strA = com.tencent.android.tpush.service.e.f.a(context, SETTINGS_DEVICE_ID_KEY_NAME);
        if (strA == null) {
            return null;
        }
        String strDecrypt = Rijndael.decrypt(strA);
        if (m.b(strDecrypt)) {
            return null;
        }
        return strDecrypt;
    }

    private static void setSettingsLocalDeviceId(Context context, String str) {
        com.tencent.android.tpush.service.e.f.a(context, SETTINGS_DEVICE_ID_KEY_NAME, Rijndael.encrypt(str));
    }

    public static String getEncryptAPKSignature(Context context) {
        if (context != null) {
            return getEncryptAPKSignatureNative(context);
        }
        throw new SecurityException("get encrypt apk signature error");
    }

    public static byte[] java_generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(ParserConstants.LSHIFTASSIGN);
        return keyGenerator.generateKey().getEncoded();
    }

    public static byte[] java_generateIV(long j) {
        byte[] bArr = new byte[16];
        for (int i = 0; i < 4; i++) {
            int i2 = i * 4;
            byte b = (byte) ((j >> (i * 8)) & 255);
            bArr[i2] = b;
            bArr[i2 + 1] = b;
            bArr[i2 + 2] = b;
            bArr[i2 + 3] = b;
        }
        return bArr;
    }

    public byte[] java_encryptByRSA(byte[] bArr) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(new BigInteger(this.modulusStr, 16), new BigInteger("010001", 16)));
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        cipher.init(1, publicKeyGeneratePublic);
        return cipher.doFinal(bArr);
    }

    public byte[] java_encryptByAES(byte[] bArr, long j) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
        cipher.init(1, secretKeySpec, new IvParameterSpec(this.iv));
        return cipher.doFinal(bArr);
    }

    public byte[] java_decryptByAES(byte[] bArr, long j) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
        cipher.init(2, secretKeySpec, new IvParameterSpec(this.iv));
        return cipher.doFinal(bArr);
    }

    public static g getTEA() {
        if (tea == null) {
            tea = new g("0123456789abcdef".getBytes());
        }
        return tea;
    }

    public static byte[] java_oiSymmetryEncrypt2Byte(String str) {
        return getTEA().a(str.getBytes());
    }

    public static byte[] java_oiSymmetryDecrypt2Byte(byte[] bArr) {
        return getTEA().b(bArr);
    }
}
