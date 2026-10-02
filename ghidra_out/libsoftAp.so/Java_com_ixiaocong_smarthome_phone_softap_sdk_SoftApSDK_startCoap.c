// Java_com_ixiaocong_smarthome_phone_softap_sdk_SoftApSDK_startCoap  @ 000167e0


void Java_com_ixiaocong_smarthome_phone_softap_sdk_SoftApSDK_startCoap
               (JNIEnv *env,jobject obj,jstring addr,jstring productId,jstring mac)

{
  jobject pvVar1;
  char *pcVar2;
  char *pcVar3;
  char *pcVar4;
  
                    /* Unresolved local var: char * c_addr@[???]
                       Unresolved local var: char * c_productId@[???]
                       Unresolved local var: char * c_mac@[???] */
  **(undefined4 **)(DAT_00016850 + 0x167f2) = env;
  pvVar1 = (*(*env)->NewGlobalRef)(env,obj);
  **(undefined4 **)(DAT_00016854 + 0x16804) = pvVar1;
  pcVar2 = (*(*env)->GetStringUTFChars)(env,addr,(jboolean *)0x0);
  if (((pcVar2 != (char *)0x0) &&
      (pcVar3 = (*(*env)->GetStringUTFChars)(env,productId,(jboolean *)0x0), pcVar3 != (char *)0x0))
     && (pcVar4 = (*(*env)->GetStringUTFChars)(env,mac,(jboolean *)0x0), pcVar4 != (char *)0x0)) {
                    /* WARNING: Could not recover jumptable at 0x0001bc00. Too many branches */
                    /* WARNING: Treating indirect jump as call */
    (*(code *)(&DAT_0001bc08 + DAT_0001bc04))(pcVar2,pcVar3);
    return;
  }
  return;
}


