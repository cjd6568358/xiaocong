// create_ecdh_key  @ 00015b60


/* WARNING: Unknown calling convention -- yet parameter storage is locked */

void create_ecdh_key(void)

{
  uECC_Curve curve;
  uint8_t *public_key;
  int iVar1;
  uint8_t *private_key;
  int iVar2;
  int iVar3;
  int iVar4;
  
  curve = uECC_secp256k1();
  public_key = *(uint8_t **)(DAT_00015c00 + 0x15b76);
  private_key = *(uint8_t **)(DAT_00015c04 + 0x15b7a);
  **(undefined4 **)(DAT_00015bfc + 0x15b78) = curve;
  iVar1 = uECC_make_key(public_key,private_key,curve);
  if (iVar1 == 0) {
    __android_log_print(4,DAT_00015c08 + 0x15b90,DAT_00015c0c + 0x15b92);
  }
                    /* Unresolved local var: uint i@[???] */
  iVar3 = 0;
  iVar4 = DAT_00015c14 + 0x15ba4;
  iVar1 = *(int *)(DAT_00015c10 + 0x15ba0);
  iVar2 = DAT_00015c18 + 89000;
  do {
    __android_log_print(4,iVar4,iVar2,*(undefined1 *)(iVar1 + iVar3));
    iVar3 = iVar3 + 1;
  } while (iVar3 != 0x20);
  __android_log_print(4,DAT_00015c1c + 0x15bc2,DAT_00015c20 + 0x15bc4);
                    /* Unresolved local var: uint i@[???] */
  iVar3 = 0;
  iVar4 = DAT_00015c28 + 0x15bd6;
  iVar1 = *(int *)(DAT_00015c24 + 0x15bd2);
  iVar2 = DAT_00015c2c + 0x15bda;
  do {
    __android_log_print(4,iVar4,iVar2,*(undefined1 *)(iVar1 + iVar3));
    iVar3 = iVar3 + 1;
  } while (iVar3 != 0x40);
  __android_log_print(4,DAT_00015c30 + 0x15bf4,DAT_00015c34 + 0x15bf6);
  return;
}


