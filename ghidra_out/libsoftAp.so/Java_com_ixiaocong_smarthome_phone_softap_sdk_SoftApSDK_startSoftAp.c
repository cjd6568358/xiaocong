// Java_com_ixiaocong_smarthome_phone_softap_sdk_SoftApSDK_startSoftAp  @ 00016858


void Java_com_ixiaocong_smarthome_phone_softap_sdk_SoftApSDK_startSoftAp
               (JNIEnv *env,jobject obj,jstring addr,jstring ssid,jstring password,jstring domain,
               jstring crt,jstring clientId,jstring checkCode)

{
  jobject pvVar1;
  char *addr_00;
  char *ssid_00;
  char *password_00;
  char *domain_00;
  char *crt_00;
  char *clientId_00;
  char *check;
  
                    /* Unresolved local var: char * c_addr@[???]
                       Unresolved local var: char * c_ssid@[???]
                       Unresolved local var: char * c_password@[???]
                       Unresolved local var: char * c_domain@[???]
                       Unresolved local var: char * c_crt@[???]
                       Unresolved local var: char * c_clientId@[???]
                       Unresolved local var: char * c_checkCode@[???]
                       Unresolved local var: int err@[???] */
  **(undefined4 **)(DAT_000169a8 + 0x1686c) = env;
  pvVar1 = (*(*env)->NewGlobalRef)(env,obj);
  **(undefined4 **)(DAT_000169ac + 0x1687e) = pvVar1;
  addr_00 = (*(*env)->GetStringUTFChars)(env,addr,(jboolean *)0x0);
  if (addr_00 != (char *)0x0) {
    __android_log_print(4,DAT_000169b0 + 0x168a0,DAT_000169b4 + 0x168a2,addr_00);
    ssid_00 = (*(*env)->GetStringUTFChars)(env,ssid,(jboolean *)0x0);
    if (ssid_00 != (char *)0x0) {
      __android_log_print(4,DAT_000169b8 + 0x168c4,DAT_000169bc + 0x168ca,ssid_00);
      password_00 = (*(*env)->GetStringUTFChars)(env,password,(jboolean *)0x0);
      if (password_00 != (char *)0x0) {
        __android_log_print(4,DAT_000169c0 + 0x168ee,DAT_000169c4 + 0x168f4,password_00);
        domain_00 = (*(*env)->GetStringUTFChars)(env,domain,(jboolean *)0x0);
        if (domain_00 != (char *)0x0) {
          __android_log_print(4,DAT_000169c8 + 0x16918,DAT_000169cc + 0x1691e,domain_00);
          crt_00 = (*(*env)->GetStringUTFChars)(env,crt,(jboolean *)0x0);
          if (crt_00 != (char *)0x0) {
            __android_log_print(4,DAT_000169d0 + 0x16942,DAT_000169d4 + 0x16948,crt_00);
            clientId_00 = (*(*env)->GetStringUTFChars)(env,clientId,(jboolean *)0x0);
            if (clientId_00 != (char *)0x0) {
              __android_log_print(4,DAT_000169d8 + 0x1696c,DAT_000169dc + 0x1696e,clientId_00);
              check = (*(*env)->GetStringUTFChars)(env,checkCode,(jboolean *)0x0);
              if (check != (char *)0x0) {
                __android_log_print(4,DAT_000169e0 + 0x1698e,DAT_000169e4 + 0x16990,check);
                start_softap_app(addr_00,ssid_00,password_00,clientId_00,check,domain_00,crt_00);
              }
            }
          }
        }
      }
    }
  }
  return;
}


