// javaEventNotify  @ 000134c4


void javaEventNotify(void *pCtx,int type,char *value)

{
  undefined4 uVar1;
  undefined4 uVar2;
  int *piVar3;
  undefined4 uVar4;
  
                    /* Unresolved local var: JniContex * jniContex@[DW_OP_reg0(r0)]
                       Unresolved local var: JNIEnv * env@[???]
                       Unresolved local var: jobject o@[???]
                       Unresolved local var: jclass cls@[???]
                       Unresolved local var: char * sigStr@[???]
                       Unresolved local var: jmethodID mid@[???]
                       Unresolved local var: jstring string@[???] */
  piVar3 = *(int **)pCtx;
  uVar4 = *(undefined4 *)((int)pCtx + 4);
  uVar1 = (**(code **)(*piVar3 + 0x18))(piVar3,DAT_00013570 + 0x134ec);
  uVar1 = (**(code **)(*piVar3 + 0x84))(piVar3,uVar1,DAT_00013574 + 0x13508,DAT_00013578 + 0x13510);
  uVar2 = (**(code **)(*piVar3 + 0x29c))(piVar3,value);
  (**(code **)(*piVar3 + 0xf4))(piVar3,uVar4,uVar1,type,uVar2);
                    /* WARNING: Could not recover jumptable at 0x0001356c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  (**(code **)(*piVar3 + 0x5c))(piVar3,uVar2);
  return;
}


