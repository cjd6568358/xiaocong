// AES128_CBC_decrypt_buffer  @ 000133d0


int AES128_CBC_decrypt_buffer
              (uint8_t *output,uint8_t *input,uint32_t length,uint8_t *key,uint8_t *iv)

{
  int iVar1;
  int iVar2;
  uint uVar3;
  undefined4 *puVar4;
  int *piVar5;
  undefined4 *puVar6;
  
                    /* Unresolved local var: uintptr_t i@[???] */
  iVar1 = 0;
  do {
                    /* Unresolved local var: uint8_t i@[???] */
    output[iVar1] = input[iVar1];
    iVar1 = iVar1 + 1;
  } while (iVar1 != 0x10);
  *(uint8_t **)(DAT_00013464 + 0x133f4) = output;
  if (key != (uint8_t *)0x0) {
    *(uint8_t **)(DAT_00013468 + 0x133fc) = key;
    KeyExpansion();
  }
  if (iv != (uint8_t *)0x0) {
    *(uint8_t **)(DAT_0001346c + 0x13408) = iv;
  }
  uVar3 = 0;
  if (length != 0) {
                    /* Unresolved local var: uint8_t i@[???] */
    puVar4 = (undefined4 *)(DAT_00013470 + 0x13420);
    piVar5 = (int *)(DAT_00013474 + 0x13422);
    puVar6 = (undefined4 *)(DAT_00013478 + 0x13424);
    do {
      iVar1 = 0;
      do {
        output[iVar1] = input[iVar1];
        iVar1 = iVar1 + 1;
      } while (iVar1 != 0x10);
      *puVar4 = output;
      InvCipher();
      iVar1 = *piVar5;
      iVar2 = 0;
      do {
        output[iVar2] = *(byte *)(iVar1 + iVar2) ^ output[iVar2];
        iVar2 = iVar2 + 1;
      } while (iVar2 != 0x10);
      *puVar6 = input;
      uVar3 = uVar3 + 0x10;
      output = output + 0x10;
      input = input + 0x10;
    } while (uVar3 < length);
  }
  return uVar3 - output[-1];
}


