// uECC_make_key  @ 00016c5c


int uECC_make_key(uint8_t *public_key,uint8_t *private_key,uECC_Curve curve)

{
  byte bVar1;
  int iVar2;
  uECC_word_t uVar3;
  uint uVar4;
  int iVar5;
  uint uVar6;
  char cVar7;
  uint uVar8;
  uECC_word_t auStack_88 [16];
  uECC_word_t auStack_48 [8];
  int local_28;
  
                    /* Unresolved local var: uECC_word_t[8] _private@[DW_OP_breg13(sp): +64]
                       Unresolved local var: uECC_word_t[16] _public@[DW_OP_breg13(sp): 0]
                       Unresolved local var: uECC_word_t tries@[???] */
  uVar8 = 0;
  local_28 = **(int **)(DAT_00016d78 + 0x16c72);
  do {
    iVar2 = curve->num_n_bits + 0x1f;
    iVar2 = uECC_generate_random_int
                      (auStack_48,curve->n,
                       (wordcount_t)((iVar2 + ((uint)(iVar2 >> 0x1f) >> 0x1b)) * 0x80000 >> 0x18));
    if (iVar2 == 0) break;
    uVar3 = EccPoint_compute_public_key(auStack_88,auStack_48,curve);
    if (uVar3 != 0) {
                    /* Unresolved local var: wordcount_t i@[???] */
      if (0 < curve->num_n_bits) {
        iVar2 = curve->num_n_bits + 7;
        iVar5 = 0;
                    /* Unresolved local var: uint b@[???] */
        cVar7 = '\0';
        iVar2 = (int)(iVar2 + ((uint)(iVar2 >> 0x1f) >> 0x1d)) >> 3;
        do {
          uVar8 = (iVar2 + -1) - iVar5;
          cVar7 = cVar7 + '\x01';
          private_key[iVar5] =
               (uint8_t)(*(uint *)((int)auStack_48 + (uVar8 & 0xfffffffc)) >> (uVar8 * 8 & 0x18));
          iVar5 = (int)cVar7;
        } while (iVar5 < iVar2);
      }
      bVar1 = curve->num_bytes;
      uVar8 = (uint)(char)bVar1;
                    /* Unresolved local var: wordcount_t i@[???] */
      if (0 < (int)uVar8) {
        uVar4 = uVar8 * 8;
        uVar6 = 0;
        do {
          uVar8 = uVar8 - 1;
          uVar4 = uVar4 - 8;
          public_key[uVar6] =
               (uint8_t)(*(uint *)((int)auStack_88 + (uVar8 & 0xfffffffc)) >> (uVar4 & 0x18));
          uVar6 = uVar6 + 1;
        } while ((uVar6 & 0xff) != (uint)bVar1);
        bVar1 = curve->num_bytes;
      }
      uVar8 = (uint)(char)bVar1;
                    /* Unresolved local var: wordcount_t i@[???] */
      if (0 < (int)uVar8) {
        cVar7 = curve->num_words;
        uVar4 = uVar8 * 8;
        uVar6 = 0;
        do {
          uVar8 = uVar8 - 1;
          uVar4 = uVar4 - 8;
          public_key[uVar6 + (int)(char)bVar1] =
               (uint8_t)(*(uint *)((int)auStack_88 + (uVar8 & 0xfffffffc) + cVar7 * 4) >>
                        (uVar4 & 0x18));
          uVar6 = uVar6 + 1;
        } while ((uVar6 & 0xff) != (uint)bVar1);
      }
      iVar2 = 1;
      goto LAB_00016d5e;
    }
    uVar8 = uVar8 + 1;
  } while (uVar8 < 0x40);
  iVar2 = 0;
LAB_00016d5e:
  if (**(int **)(DAT_00016d7c + 0x16d66) != local_28) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail();
  }
  return iVar2;
}


