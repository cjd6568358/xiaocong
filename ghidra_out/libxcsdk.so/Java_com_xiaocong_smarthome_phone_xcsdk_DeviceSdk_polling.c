// Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_polling  @ 0001357c


jint Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_polling(JNIEnv *env,jobject object)

{
  int iVar1;
  int iVar2;
  int iVar3;
  undefined4 *puVar4;
  
                    /* Unresolved local var: jint retcode@[???]
                       Unresolved local var: jint * p@[???] */
  iVar2 = DAT_000135bc;
  puVar4 = (undefined4 *)(DAT_000135bc + 0x13588);
  iVar3 = *(int *)(DAT_000135bc + 0x13590);
  *puVar4 = env;
  *(jobject *)(iVar2 + 0x1358c) = object;
  iVar1 = DAT_000135c4;
  if (iVar3 == 0) {
    *(undefined4 **)(iVar2 + 0x13594) = puVar4;
    *(int *)(iVar2 + 0x13590) = iVar1 + 0x135b8;
  }
  iVar2 = polling((SdkContex *)(DAT_000135c0 + 0x135ac));
  return iVar2;
}


