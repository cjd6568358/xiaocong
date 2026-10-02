// uECC_shared_secret  @ 00016f3c


int uECC_shared_secret(uint8_t *public_key,uint8_t *private_key,uint8_t *secret,uECC_Curve curve)

{
  uECC_word_t uVar1;
  uint uVar2;
  byte bVar3;
  uint uVar4;
  uint uVar5;
  int iVar6;
  uECC_word_t *puVar7;
  uint uVar8;
  uECC_word_t *local_b0 [2];
  uECC_word_t auStack_a8 [8];
  uECC_word_t auStack_88 [8];
  uECC_word_t local_68 [16];
  int local_28;
  
                    /* Unresolved local var: uECC_word_t[16] _public@[DW_OP_breg13(sp): +88]
                       Unresolved local var: uECC_word_t[8] _private@[DW_OP_breg13(sp): +56]
                       Unresolved local var: uECC_word_t[8] tmp@[DW_OP_breg13(sp): +24]
                       Unresolved local var: uECC_word_t *[2] p2@[DW_OP_breg13(sp): +16]
                       Unresolved local var: uECC_word_t carry@[???]
                       Unresolved local var: uECC_word_t * initial_Z@[???]
                       Unresolved local var: wordcount_t num_words@[???]
                       Unresolved local var: wordcount_t num_bytes@[???] */
  local_b0[1] = auStack_a8;
  local_28 = **(int **)(DAT_00017074 + 0x16f50);
  local_b0[0] = auStack_88;
  uVar4._0_1_ = curve->num_words;
  uVar4._1_1_ = curve->num_bytes;
  uVar4._2_2_ = curve->num_n_bits;
  iVar6 = ((int)uVar4 >> 0x10) + 7;
  uECC_vli_bytesToNative
            (local_b0[0],private_key,(int)(iVar6 + ((uint)(iVar6 >> 0x1f) >> 0x1d)) >> 3);
  uVar8 = (int)((uVar4 & 0xff00) << 0x10) >> 0x18;
  uECC_vli_bytesToNative(local_68,public_key,uVar8);
  uECC_vli_bytesToNative
            ((uECC_word_t *)((int)local_68 + ((int)(uVar4 << 0x18) >> 0x16)),
             public_key + (char)((uVar4 & 0xff00) >> 8),uVar8);
  uVar1 = regularize_k(auStack_88,auStack_88,auStack_a8,curve);
  puVar7 = (uECC_word_t *)0x0;
  if (*(int *)(DAT_00017078 + 0x16fb6) != 0) {
    puVar7 = local_b0[uVar1];
    iVar6 = uECC_generate_random_int(puVar7,curve->p,(undefined1)uVar4);
    if (iVar6 == 0) {
      uVar8 = 0;
      goto LAB_0001705c;
    }
  }
  EccPoint_mult(local_68,local_68,local_b0[uVar1 == 0],puVar7,curve->num_n_bits + 1,curve);
                    /* Unresolved local var: wordcount_t i@[???] */
  if ('\0' < (char)uVar4._1_1_) {
    uVar5 = 0;
    uVar2 = uVar8 * 8;
    do {
      uVar8 = uVar8 - 1;
      uVar2 = uVar2 - 8;
      secret[uVar5] = (uint8_t)(*(uint *)((int)local_68 + (uVar8 & 0xfffffffc)) >> (uVar2 & 0x18));
      uVar5 = uVar5 + 1;
    } while ((uVar5 & 0xff) != (uVar4 & 0xffff) >> 8);
  }
  bVar3 = curve->num_words << 1;
                    /* Unresolved local var: wordcount_t i@[???]
                       Unresolved local var: uECC_word_t bits@[???] */
  if ((char)bVar3 < '\x01') {
    uVar8 = 1;
  }
  else {
    uVar8 = 0;
    uVar4 = 0;
    do {
      puVar7 = local_68 + uVar8;
      uVar8 = uVar8 + 1;
      uVar4 = uVar4 | *puVar7;
    } while ((uVar8 & 0xff) != (uint)bVar3);
    uVar8 = (uint)(uVar4 == 0);
  }
  uVar8 = uVar8 ^ 1;
LAB_0001705c:
  if (**(int **)(DAT_0001707c + 0x17064) != local_28) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail();
  }
  return uVar8;
}


