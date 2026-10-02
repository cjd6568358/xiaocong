package com.hianalytics.android.b.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.android.tpush.common.Constants;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class c {
    public static SharedPreferences a(Context context, String str) {
        return context.getSharedPreferences("hianalytics_" + str + "_" + context.getPackageName(), 0);
    }

    public static void a(Context context, JSONObject jSONObject, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        Throwable th;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput(d(context, str), 0);
                try {
                    fileOutputStreamOpenFileOutput.write(jSONObject.toString().getBytes(HTTP.UTF_8));
                    fileOutputStreamOpenFileOutput.flush();
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    fileOutputStream = fileOutputStreamOpenFileOutput;
                    th = th2;
                    if (fileOutputStream == null) {
                        throw th;
                    }
                    try {
                        fileOutputStream.close();
                        throw th;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                fileOutputStream = null;
                th = th3;
            }
        } catch (FileNotFoundException e3) {
            if (0 != 0) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        } catch (IOException e5) {
            if (0 != 0) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
        }
    }

    public static JSONObject b(Context context, String str) throws Throwable {
        FileInputStream fileInputStreamOpenFileInput;
        Throwable th;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        FileInputStream fileInputStream;
        boolean z = false;
        try {
            try {
                fileInputStreamOpenFileInput = context.openFileInput(d(context, str));
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(fileInputStreamOpenFileInput, HTTP.UTF_8));
                    try {
                        StringBuffer stringBuffer = new StringBuffer(Constants.MAIN_VERSION_TAG);
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            stringBuffer.append(line);
                        }
                        if (stringBuffer.length() != 0) {
                            JSONObject jSONObject = new JSONObject(stringBuffer.toString());
                            try {
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (fileInputStreamOpenFileInput != null) {
                                try {
                                    fileInputStreamOpenFileInput.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            return jSONObject;
                        }
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                        if (fileInputStreamOpenFileInput == null) {
                            return null;
                        }
                        try {
                            fileInputStreamOpenFileInput.close();
                            return null;
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            return null;
                        }
                    } catch (FileNotFoundException e5) {
                        bufferedReader2 = bufferedReader;
                        fileInputStream = fileInputStreamOpenFileInput;
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        }
                        if (fileInputStream == null) {
                            return null;
                        }
                        try {
                            fileInputStream.close();
                            return null;
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            return null;
                        }
                    } catch (IOException e8) {
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e9) {
                                e9.printStackTrace();
                            }
                        }
                        if (fileInputStreamOpenFileInput == null) {
                            return null;
                        }
                        try {
                            fileInputStreamOpenFileInput.close();
                            return null;
                        } catch (IOException e10) {
                            e10.printStackTrace();
                            return null;
                        }
                    } catch (JSONException e11) {
                        e = e11;
                        e.printStackTrace();
                        c(context, str);
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e12) {
                                e12.printStackTrace();
                            }
                        }
                        if (fileInputStreamOpenFileInput == null) {
                            return null;
                        }
                        try {
                            fileInputStreamOpenFileInput.close();
                            return null;
                        } catch (IOException e13) {
                            e13.printStackTrace();
                            return null;
                        }
                    } catch (Exception e14) {
                        e = e14;
                        e.printStackTrace();
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e15) {
                                e15.printStackTrace();
                            }
                        }
                        if (fileInputStreamOpenFileInput == null) {
                            return null;
                        }
                        try {
                            fileInputStreamOpenFileInput.close();
                            return null;
                        } catch (IOException e16) {
                            e16.printStackTrace();
                            return null;
                        }
                    }
                } catch (FileNotFoundException e17) {
                    bufferedReader2 = null;
                    fileInputStream = fileInputStreamOpenFileInput;
                } catch (IOException e18) {
                    bufferedReader = null;
                } catch (JSONException e19) {
                    e = e19;
                    bufferedReader = null;
                } catch (Exception e20) {
                    e = e20;
                    bufferedReader = null;
                } catch (Throwable th2) {
                    th = th2;
                    if (z != 0) {
                        try {
                            z.close();
                        } catch (IOException e21) {
                            e21.printStackTrace();
                        }
                    }
                    if (fileInputStreamOpenFileInput != null) {
                        try {
                            fileInputStreamOpenFileInput.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (FileNotFoundException e23) {
            bufferedReader2 = null;
            fileInputStream = null;
        } catch (IOException e24) {
            bufferedReader = null;
            fileInputStreamOpenFileInput = null;
        } catch (JSONException e25) {
            e = e25;
            bufferedReader = null;
            fileInputStreamOpenFileInput = null;
        } catch (Exception e26) {
            e = e26;
            bufferedReader = null;
            fileInputStreamOpenFileInput = null;
        } catch (Throwable th4) {
            fileInputStreamOpenFileInput = null;
            th = th4;
        }
    }

    public static void c(Context context, String str) {
        context.deleteFile(d(context, str));
    }

    private static String d(Context context, String str) {
        return "hianalytics_" + str + "_" + context.getPackageName();
    }
}
