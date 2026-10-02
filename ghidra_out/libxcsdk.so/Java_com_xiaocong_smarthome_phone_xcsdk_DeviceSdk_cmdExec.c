// Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_cmdExec  @ 000135c8


jstring Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_cmdExec
                  (JNIEnv *env,jobject object,jint type,jstring cmdString)

{
  char *pCmd;
  char *pcVar1;
  jstring pvVar2;
  
                    /* Unresolved local var: char * inputString@[???]
                       Unresolved local var: char * retBuffer@[???]
                       Unresolved local var: char * * p@[???]
                       Unresolved local var: jstring jString@[???] */
  pCmd = (*(*env)->GetStringUTFChars)(env,cmdString,(jboolean *)0x0);
  if (pCmd == (char *)0x0) {
    return (jstring)0x0;
  }
  pcVar1 = cmdExec((SdkContex *)(DAT_00013640 + 0x13610),type,pCmd);
  (*(*env)->ReleaseStringUTFChars)(env,cmdString,pCmd);
                    /* WARNING: Could not recover jumptable at 0x0001363c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pvVar2 = (*(*env)->NewStringUTF)(env,pcVar1);
  return pvVar2;
}


