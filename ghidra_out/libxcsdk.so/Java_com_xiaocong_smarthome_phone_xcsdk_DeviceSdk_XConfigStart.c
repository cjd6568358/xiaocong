// Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_XConfigStart  @ 00013644


void Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_XConfigStart
               (JNIEnv *env,jobject jobj,jstring ssid,jstring pass,jstring key,jbyteArray args)

{
  char *__s;
  char *__s_00;
  char *__s_01;
  size_t ssid_len;
  size_t pwd_len;
  size_t key_len;
  
                    /* Unresolved local var: char * nativeSSID@[???]
                       Unresolved local var: char * nativePass@[???]
                       Unresolved local var: char * nativeKey@[???] */
  __s = (*(*env)->GetStringUTFChars)(env,ssid,(jboolean *)0x0);
  __s_00 = (*(*env)->GetStringUTFChars)(env,pass,(jboolean *)0x0);
  __s_01 = (*(*env)->GetStringUTFChars)(env,key,(jboolean *)0x0);
  ssid_len = strlen(__s);
  pwd_len = strlen(__s_00);
  key_len = strlen(__s_01);
  xconfig_start(*(XConfig **)(DAT_00013750 + 0x13700),__s,ssid_len,__s_00,pwd_len,__s_01,key_len,
                (xconfig_para *)0x0);
  (*(*env)->ReleaseStringUTFChars)(env,ssid,__s);
  (*(*env)->ReleaseStringUTFChars)(env,pass,__s_00);
                    /* WARNING: Could not recover jumptable at 0x0001374c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  (*(*env)->ReleaseStringUTFChars)(env,pass,__s_01);
  return;
}


