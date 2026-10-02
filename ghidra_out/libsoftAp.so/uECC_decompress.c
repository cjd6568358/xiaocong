// uECC_decompress  @ 00017484


void uECC_decompress(uint8_t *compressed,uint8_t *public_key,uECC_Curve curve)

{
  ushort uVar1;
  ushort uVar2;
  uint uVar3;
  uint uVar4;
  int iVar5;
  uint uVar6;
  uECC_word_t uVar7;
  uint uVar8;
  uECC_word_t uVar9;
  uECC_word_t *puVar10;
  uECC_word_t uStack_68;
  uint auStack_64 [15];
  int local_28;
  
                    /* Unresolved local var: uECC_word_t[16] point@[DW_OP_breg13(sp): +8]
                       Unresolved local var: uECC_word_t * y@[???] */
  local_28 = **(int **)(DAT_000175c4 + 0x1749a);
  uVar1._0_1_ = curve->num_words;
  uVar1._1_1_ = curve->num_bytes;
  uECC_vli_bytesToNative(&uStack_68,compressed + 1,(int)((uint)uVar1 << 0x10) >> 0x18);
  iVar5 = (int)((uint)uVar1 << 0x18) >> 0x16;
  puVar10 = (uECC_word_t *)((int)&uStack_68 + iVar5);
  (*curve->x_side)(puVar10,&uStack_68,curve);
  (*curve->mod_sqrt)(puVar10,curve);
  uVar4 = *(uint *)((int)&uStack_68 + iVar5);
  if (((*compressed ^ uVar4) & 1) == 0) {
    uVar3 = (uint)(byte)curve->num_bytes;
  }
  else {
    uVar2._0_1_ = curve->num_words;
    uVar2._1_1_ = curve->num_bytes;
                    /* Unresolved local var: wordcount_t i@[???]
                       Unresolved local var: uECC_word_t borrow@[???] */
    uVar3 = (uint)(uVar2 >> 8);
    if ('\0' < (char)(undefined1)uVar2) {
                    /* Unresolved local var: uECC_word_t diff@[???] */
      uVar9 = curve->p[0];
      uVar4 = uVar9 - uVar4;
      *puVar10 = uVar4;
      if ((byte)(undefined1)uVar2 != 1) {
        uVar6 = 0;
        iVar5 = 0;
        do {
          uVar7 = curve->p[iVar5 + 1];
          if (uVar4 != uVar9) {
            uVar6 = (uint)(uVar9 < uVar4);
          }
          uVar4 = (uVar7 - auStack_64[(char)(undefined1)uVar1 + iVar5]) - uVar6;
          auStack_64[(char)(undefined1)uVar1 + iVar5] = uVar4;
          uVar8 = iVar5 + 2;
          iVar5 = iVar5 + 1;
          uVar9 = uVar7;
        } while ((uVar8 & 0xff) != (uint)(byte)(undefined1)uVar2);
      }
    }
  }
                    /* Unresolved local var: wordcount_t i@[???] */
  uVar4 = (uint)(char)uVar3;
  if (0 < (int)uVar4) {
    uVar8 = 0;
    uVar6 = uVar4 * 8;
    do {
      uVar4 = uVar4 - 1;
      uVar6 = uVar6 - 8;
      public_key[uVar8] =
           (uint8_t)(*(uint *)((int)&uStack_68 + (uVar4 & 0xfffffffc)) >> (uVar6 & 0x18));
      uVar8 = uVar8 + 1;
    } while ((uVar8 & 0xff) != uVar3);
    uVar3 = (uint)(byte)curve->num_bytes;
  }
  uVar4 = (uint)(char)uVar3;
                    /* Unresolved local var: wordcount_t i@[???] */
  if (0 < (int)uVar4) {
    uVar6 = uVar4 * 8;
    uVar8 = 0;
    do {
      uVar4 = uVar4 - 1;
      uVar6 = uVar6 - 8;
      public_key[uVar8 + (int)(char)uVar3] =
           (uint8_t)(*(uint *)((int)puVar10 + (uVar4 & 0xfffffffc)) >> (uVar6 & 0x18));
      uVar8 = uVar8 + 1;
    } while ((uVar8 & 0xff) != uVar3);
  }
  if (**(int **)(DAT_000175c8 + 0x175b2) != local_28) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail();
  }
  return;
}


