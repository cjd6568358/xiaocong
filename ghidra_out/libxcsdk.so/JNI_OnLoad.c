// JNI_OnLoad  @ 00013768


jint JNI_OnLoad(JavaVM *vm,void *reserved)

{
  jint jVar1;
  int *piVar2;
  void *venv;
  int local_c;
  
  piVar2 = *(int **)(DAT_000137dc + 0x1378c);
  local_c = *piVar2;
  jVar1 = (*(*vm)->GetEnv)(vm,&venv,0x10004);
  if (jVar1 == 0) {
    sdkInit((SdkContex *)(DAT_000137e0 + 0x137b4),(char *)0x0);
    jVar1 = 0x10004;
  }
  else {
    jVar1 = -1;
  }
  if (local_c == *piVar2) {
    return jVar1;
  }
                    /* WARNING: Subroutine does not return */
  __stack_chk_fail();
}


