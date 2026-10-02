// AES128_CBC_encrypt_buffer  @ 0001330c


int AES128_CBC_encrypt_buffer
              (uint8_t *output,uint8_t *input,uint32_t length,uint8_t *key,uint8_t *iv)

{
  int iVar1;
  uint uVar2;
  int iVar3;
  uint uVar4;
  undefined4 *puVar5;
  undefined4 *puVar6;
  
                    /* Unresolved local var: uintptr_t i@[???]
                       Unresolved local var: uint8_t remainders@[???] */
  iVar1 = 0;
  do {
                    /* Unresolved local var: uint8_t i@[???] */
    output[iVar1] = input[iVar1];
    iVar1 = iVar1 + 1;
  } while (iVar1 != 0x10);
  *(uint8_t **)(DAT_000133b8 + 0x13330) = output;
  if (key != (uint8_t *)0x0) {
    *(uint8_t **)(DAT_000133bc + 0x13338) = key;
    KeyExpansion();
  }
  uVar2 = length & 0xf;
  if (iv != (uint8_t *)0x0) {
    *(uint8_t **)(DAT_000133c0 + 0x1334a) = iv;
  }
  uVar4 = 0;
  puVar5 = (undefined4 *)(DAT_000133c4 + 0x1335a);
  puVar6 = (undefined4 *)(DAT_000133c8 + 0x1335c);
  do {
    iVar1 = 0;
    do {
      output[iVar1] = input[iVar1];
      iVar1 = iVar1 + 1;
    } while (iVar1 != 0x10);
    uVar4 = uVar4 + 0x10;
    if (length < uVar4) {
      __aeabi_memset(output + uVar2,0x10 - uVar2,0x10 - uVar2 & 0xff);
    }
                    /* Unresolved local var: uint8_t i@[???] */
    iVar3 = 0;
    iVar1 = *(int *)(DAT_000133cc + 0x13386);
    do {
      output[iVar3] = *(byte *)(iVar1 + iVar3) ^ output[iVar3];
      iVar3 = iVar3 + 1;
    } while (iVar3 != 0x10);
    *puVar5 = output;
    Cipher();
    *puVar6 = output;
    output = output + 0x10;
    input = input + 0x10;
  } while (uVar4 <= length);
  return (length + 0x10) - uVar2;
}


