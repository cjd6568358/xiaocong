// device_aes_encrypt  @ 0001afd0


/* WARNING: Restarted to delay deadcode elimination for space: stack */

int device_aes_encrypt(uint8_t *key,int keyLength,uint8_t *iv,uint8_t *pPlainIn,int plainLength,
                      uint8_t *pEncOut,int maxOutLen)

{
  byte bVar1;
  byte bVar2;
  byte bVar3;
  uint32_t uVar4;
  uint32_t uVar5;
  uint32_t uVar6;
  uint32_t uVar7;
  uint uVar8;
  int *piVar9;
  uint uVar10;
  uint8_t *puVar11;
  int iVar12;
  uint8_t *puVar13;
  uint uVar14;
  uint uVar15;
  uint uVar16;
  uint uVar17;
  uint uVar18;
  int iVar19;
  Aes *pAVar20;
  uint uVar21;
  uint uVar22;
  uint uVar23;
  uint uVar24;
  uint uVar25;
  uint uVar26;
  uint uVar27;
  uint uVar28;
  uint uVar29;
  uint uVar30;
  uint local_16c;
  uint32_t *local_164;
  uint local_160;
  uint local_148;
  Aes enc;
  
                    /* Unresolved local var: int i@[???]
                       Unresolved local var: int dat_len@[???] */
  piVar9 = *(int **)(DAT_0001b66c + 0x1aff0);
  local_164 = (uint32_t *)pEncOut;
  iVar12 = *piVar9;
  if (keyLength == 0x20 || (keyLength & 0xfffffff7U) == 0x10) {
    AesSetKeyLocal(&enc,key,keyLength,iv,0);
  }
  uVar18 = plainLength + 0xf;
  if (-1 < plainLength) {
    uVar18 = plainLength;
  }
  if (plainLength == (uVar18 & 0xfffffff0)) {
    if (maxOutLen < plainLength) goto LAB_0001b660;
    local_148 = plainLength;
    memcpy(pEncOut,pPlainIn,plainLength);
LAB_0001b61c:
    iVar19 = local_148 + 0x1f;
    puVar11 = pEncOut + (local_148 - 1);
    local_148 = local_148 + 0x10;
    do {
      puVar11 = puVar11 + 1;
      *puVar11 = '\x10';
    } while (puVar11 != pEncOut + iVar19);
  }
  else {
    local_148 = (uVar18 & 0xfffffff0) + 0x10;
    if (maxOutLen < (int)local_148) {
LAB_0001b660:
      local_148 = 0xffffffff;
      goto LAB_0001b5e4;
    }
    memcpy(pEncOut,pPlainIn,plainLength);
    if ((int)(local_148 - plainLength) < 1) {
      if (plainLength == local_148) goto LAB_0001b61c;
    }
    else if (plainLength < (int)local_148) {
      puVar11 = pEncOut + plainLength;
      do {
        puVar13 = puVar11 + 1;
        *puVar11 = (uint8_t)(local_148 - plainLength);
        puVar11 = puVar13;
      } while (puVar13 != pEncOut + local_148);
    }
  }
                    /* Unresolved local var: uint32_t blocks@[???] */
  local_160 = local_148 >> 4;
  if (local_160 != 0) {
                    /* Unresolved local var: uint32_t s0@[???]
                       Unresolved local var: uint32_t s1@[???]
                       Unresolved local var: uint32_t s2@[???]
                       Unresolved local var: uint32_t s3@[???]
                       Unresolved local var: uint32_t t0@[???]
                       Unresolved local var: uint32_t t1@[???]
                       Unresolved local var: uint32_t t2@[???]
                       Unresolved local var: uint32_t t3@[???]
                       Unresolved local var: uint32_t r@[???]
                       Unresolved local var: uint32_t * rk@[???] */
    iVar19 = DAT_0001b670 + 0x1b0cc;
    do {
      xorbuf((uint8_t *)enc.reg,(uint8_t *)local_164,0x10);
      uVar7 = enc.reg[3];
      uVar6 = enc.reg[2];
      uVar5 = enc.reg[1];
      uVar18 = enc.rounds >> 1;
      if (uVar18 - 1 < 7) {
        uVar4 = ByteReverseWord32(enc.reg[0]);
        uVar5 = ByteReverseWord32(uVar5);
        uVar6 = ByteReverseWord32(uVar6);
        uVar7 = ByteReverseWord32(uVar7);
        uVar27 = uVar6 ^ enc.key[2];
        uVar15 = uVar4 ^ enc.key[0];
        uVar16 = uVar5 ^ enc.key[1];
        uVar8 = uVar7 ^ enc.key[3];
        pAVar20 = &enc;
        local_16c = uVar18;
        while( true ) {
          local_16c = local_16c - 1;
          uVar17 = *(uint *)(iVar19 + ((uVar16 & 0xffffff) >> 0x10) * 4 + 0x428) ^
                   *(uint *)(iVar19 + (uVar15 >> 0x18) * 4 + 0x28) ^
                   *(uint *)(iVar19 + (uVar8 & 0xff) * 4 + 0xc28) ^ pAVar20->key[4] ^
                   *(uint *)(iVar19 + ((uVar27 & 0xffff) >> 8) * 4 + 0x828);
          uVar10 = *(uint *)(iVar19 + ((uVar27 & 0xffffff) >> 0x10) * 4 + 0x428) ^
                   pAVar20->key[5] ^
                   *(uint *)(iVar19 + (uVar16 >> 0x18) * 4 + 0x28) ^
                   *(uint *)(iVar19 + (uVar15 & 0xff) * 4 + 0xc28) ^
                   *(uint *)(iVar19 + ((uVar8 & 0xffff) >> 8) * 4 + 0x828);
          uVar14 = *(uint *)(iVar19 + ((uVar15 & 0xffff) >> 8) * 4 + 0x828) ^
                   *(uint *)(iVar19 + (uVar27 >> 0x18) * 4 + 0x28) ^
                   *(uint *)(iVar19 + (uVar16 & 0xff) * 4 + 0xc28) ^ pAVar20->key[6] ^
                   *(uint *)(iVar19 + ((uVar8 & 0xffffff) >> 0x10) * 4 + 0x428);
          uVar8 = *(uint *)(iVar19 + (uVar27 & 0xff) * 4 + 0xc28) ^
                  *(uint *)(iVar19 + (uVar8 >> 0x18) * 4 + 0x28) ^ pAVar20->key[7] ^
                  *(uint *)(iVar19 + ((uVar15 & 0xffffff) >> 0x10) * 4 + 0x428) ^
                  *(uint *)(iVar19 + ((uVar16 & 0xffff) >> 8) * 4 + 0x828);
          if (local_16c == 0) break;
          uVar15 = *(uint *)(iVar19 + ((uVar14 & 0xffff) >> 8) * 4 + 0x828) ^
                   *(uint *)(iVar19 + ((uVar10 & 0xffffff) >> 0x10) * 4 + 0x428) ^
                   *(uint *)(iVar19 + (uVar17 >> 0x18) * 4 + 0x28) ^
                   *(uint *)(iVar19 + (uVar8 & 0xff) * 4 + 0xc28) ^
                   ((Aes *)(pAVar20->key + 8))->key[0];
          uVar16 = pAVar20->key[9] ^
                   *(uint *)(iVar19 + (uVar10 >> 0x18) * 4 + 0x28) ^
                   *(uint *)(iVar19 + (uVar17 & 0xff) * 4 + 0xc28) ^
                   *(uint *)(iVar19 + ((uVar14 & 0xffffff) >> 0x10) * 4 + 0x428) ^
                   *(uint *)(iVar19 + ((uVar8 & 0xffff) >> 8) * 4 + 0x828);
          uVar27 = pAVar20->key[10] ^
                   *(uint *)(iVar19 + (uVar14 >> 0x18) * 4 + 0x28) ^
                   *(uint *)(iVar19 + (uVar10 & 0xff) * 4 + 0xc28) ^
                   *(uint *)(iVar19 + ((uVar8 & 0xffffff) >> 0x10) * 4 + 0x428) ^
                   *(uint *)(iVar19 + ((uVar17 & 0xffff) >> 8) * 4 + 0x828);
          uVar8 = pAVar20->key[0xb] ^
                  *(uint *)(iVar19 + (uVar8 >> 0x18) * 4 + 0x28) ^
                  *(uint *)(iVar19 + (uVar14 & 0xff) * 4 + 0xc28) ^
                  *(uint *)(iVar19 + ((uVar17 & 0xffffff) >> 0x10) * 4 + 0x428) ^
                  *(uint *)(iVar19 + ((uVar10 & 0xffff) >> 8) * 4 + 0x828);
          pAVar20 = (Aes *)(pAVar20->key + 8);
        }
        uVar25 = *(uint *)(iVar19 + ((uVar14 >> 0x18) + 0x400) * 4 + 0x28);
        uVar23 = *(uint *)(iVar19 + ((uVar10 >> 0x18) + 0x400) * 4 + 0x28);
        uVar27 = *(uint *)(iVar19 + (((uVar14 & 0xffffff) >> 0x10) + 0x400) * 4 + 0x28);
        bVar1 = *(byte *)(iVar19 + ((uVar17 & 0xff) + 0x400) * 4 + 0x28);
        bVar2 = *(byte *)(iVar19 + ((uVar14 & 0xff) + 0x400) * 4 + 0x28);
        uVar21 = *(uint *)(iVar19 + ((uVar8 >> 0x18) + 0x400) * 4 + 0x28);
        uVar29 = enc.key[uVar18 * 8 + 1];
        bVar3 = *(byte *)(iVar19 + ((uVar10 & 0xff) + 0x400) * 4 + 0x28);
        uVar16 = *(uint *)(iVar19 + (((uVar8 & 0xffff) >> 8) + 0x400) * 4 + 0x28);
        uVar26 = *(uint *)(iVar19 + (((uVar17 & 0xffffff) >> 0x10) + 0x400) * 4 + 0x28);
        uVar28 = *(uint *)(iVar19 + (((uVar10 & 0xffff) >> 8) + 0x400) * 4 + 0x28);
        uVar30 = enc.key[uVar18 * 8 + 2];
        uVar15 = *(uint *)(iVar19 + (((uVar8 & 0xffffff) >> 0x10) + 0x400) * 4 + 0x28);
        uVar24 = enc.key[uVar18 * 8 + 3];
        uVar22 = *(uint *)(iVar19 + (((uVar17 & 0xffff) >> 8) + 0x400) * 4 + 0x28);
        uVar5 = ByteReverseWord32(*(uint *)(iVar19 + (((uVar14 & 0xffff) >> 8) + 0x400) * 4 + 0x28)
                                  & 0xff00 ^
                                  *(uint *)(iVar19 + (((uVar10 & 0xffffff) >> 0x10) + 0x400) * 4 +
                                           0x28) & 0xff0000 ^
                                  *(uint *)(iVar19 + ((uVar17 >> 0x18) + 0x400) * 4 + 0x28) &
                                  0xff000000 ^
                                  (uint)*(byte *)(iVar19 + ((uVar8 & 0xff) + 0x400) * 4 + 0x28) ^
                                  enc.key[uVar18 * 8]);
        uVar6 = ByteReverseWord32(uVar16 & 0xff00 ^
                                  uVar27 & 0xff0000 ^ uVar23 & 0xff000000 ^ (uint)bVar1 ^ uVar29);
        uVar7 = ByteReverseWord32(uVar22 & 0xff00 ^
                                  uVar15 & 0xff0000 ^ uVar30 ^ (uint)bVar3 ^ uVar25 & 0xff000000);
        enc.reg[3] = ByteReverseWord32(uVar26 & 0xff0000 ^
                                       uVar21 & 0xff000000 ^ (uint)bVar2 ^ uVar24 ^ uVar28 & 0xff00)
        ;
        enc.reg[0] = uVar5;
        enc.reg[1] = uVar6;
        enc.reg[2] = uVar7;
      }
      local_160 = local_160 - 1;
      local_164[3] = enc.reg[3];
      *local_164 = enc.reg[0];
      local_164[1] = enc.reg[1];
      local_164[2] = enc.reg[2];
      local_164 = local_164 + 4;
    } while (local_160 != 0);
  }
LAB_0001b5e4:
  if (iVar12 != *piVar9) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail();
  }
  return local_148;
}


