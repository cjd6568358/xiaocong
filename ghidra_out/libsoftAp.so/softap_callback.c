// softap_callback  @ 000169e8


void softap_callback(char *mac,char *product_id)

{
  int iVar1;
  int iVar2;
  undefined4 uVar3;
  undefined4 uVar4;
  undefined4 *puVar5;
  
                    /* Unresolved local var: jclass cls@[???]
                       Unresolved local var: jmethodID mid@[???]
                       Unresolved local var: jstring j_mac@[???]
                       Unresolved local var: jstring j_product_id@[???] */
  __android_log_print(4,DAT_00016ac8 + 0x16a00,DAT_00016acc + 0x169fc);
  iVar1 = (**(code **)(*(int *)**(undefined4 **)(DAT_00016ad0 + 0x16a0a) + 0x18))
                    ((int *)**(undefined4 **)(DAT_00016ad0 + 0x16a0a),DAT_00016ad4 + 0x16a0c);
  if (iVar1 == 0) {
    __android_log_print(4,DAT_00016ad8 + 0x16a26,DAT_00016adc + 0x16a28);
  }
  iVar2 = (**(code **)(*(int *)**(undefined4 **)(DAT_00016ae0 + 0x16a32) + 0x84))
                    ((int *)**(undefined4 **)(DAT_00016ae0 + 0x16a32),iVar1,DAT_00016ae4 + 0x16a36,
                     DAT_00016ae8 + 0x16a3a);
  if (iVar2 == 0) {
    __android_log_print(4,DAT_00016aec + 0x16a56,DAT_00016af0 + 0x16a58);
  }
  puVar5 = *(undefined4 **)(DAT_00016af4 + 0x16a60);
  uVar3 = (**(code **)(*(int *)*puVar5 + 0x29c))((int *)*puVar5,mac);
  uVar4 = (**(code **)(*(int *)*puVar5 + 0x29c))((int *)*puVar5,product_id);
  __android_log_print(4,DAT_00016af8 + 0x16a86,DAT_00016afc + 0x16a88);
  (**(code **)(*(int *)*puVar5 + 0xf4))
            ((int *)*puVar5,**(undefined4 **)(DAT_00016b00 + 0x16a94),iVar2,uVar3,uVar4);
  (**(code **)(*(int *)*puVar5 + 0x5c))((int *)*puVar5,iVar1);
  (**(code **)(*(int *)*puVar5 + 0x5c))((int *)*puVar5,uVar3);
                    /* WARNING: Could not recover jumptable at 0x00016ac4. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  (**(code **)(*(int *)*puVar5 + 0x5c))((int *)*puVar5,uVar4);
  return;
}


