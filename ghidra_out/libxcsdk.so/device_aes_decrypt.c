// device_aes_decrypt  @ 0001b674


/* WARNING: Restarted to delay deadcode elimination for space: stack */

int device_aes_decrypt(uint8_t *key,int keyLength,uint8_t *iv,uint8_t *pEncIn,int encLength,
                      uint8_t *pPlainOut,int maxOutLen)

{
  uint uVar1;
  byte bVar2;
  byte bVar3;
  byte bVar4;
  uint32_t *puVar5;
  uint32_t *puVar6;
  uint32_t *puVar7;
  uint32_t uVar8;
  uint32_t uVar9;
  uint uVar10;
  uint uVar11;
  int *piVar12;
  uint32_t uVar13;
  uint uVar14;
  int iVar15;
  uint32_t uVar16;
  uint uVar17;
  uint uVar18;
  uint uVar19;
  uint uVar20;
  uint uVar21;
  uint uVar22;
  uint uVar23;
  Aes *pAVar24;
  uint uVar25;
  uint uVar26;
  uint uVar27;
  int iVar28;
  uint uVar29;
  uint uVar30;
  uint uVar31;
  uint local_17c;
  uint32_t *local_174;
  uint32_t *local_170;
  uint local_16c;
  uint32_t s0;
  uint32_t s1;
  uint32_t s2;
  uint32_t s3;
  Aes dec;
  
                    /* Unresolved local var: int dat_len@[???] */
  piVar12 = *(int **)(DAT_0001bc70 + 0x1b6a0);
  iVar15 = *piVar12;
  if (maxOutLen < encLength) {
    iVar28 = -1;
  }
  else {
    if (keyLength == 0x20 || (keyLength & 0xfffffff7U) == 0x10) {
      AesSetKeyLocal(&dec,key,keyLength,iv,1);
    }
                    /* Unresolved local var: uint32_t blocks@[???] */
    local_16c = (uint)encLength >> 4;
    if (local_16c != 0) {
                    /* Unresolved local var: uint32_t t0@[???]
                       Unresolved local var: uint32_t t1@[???]
                       Unresolved local var: uint32_t t2@[???]
                       Unresolved local var: uint32_t t3@[???]
                       Unresolved local var: uint32_t r@[???]
                       Unresolved local var: uint32_t * rk@[???] */
      iVar28 = DAT_0001bc74 + 0x1b710;
      local_170 = (uint32_t *)pPlainOut;
      local_174 = (uint32_t *)pEncIn;
      do {
        uVar1 = dec.rounds >> 1;
        dec.tmp[0] = *local_174;
        uVar9 = local_174[1];
        uVar13 = local_174[2];
        uVar16 = local_174[3];
        dec.tmp[1] = uVar9;
        dec.tmp[2] = uVar13;
        dec.tmp[3] = uVar16;
        if (uVar1 - 1 < 7) {
          uVar8 = ByteReverseWord32(dec.tmp[0]);
          uVar9 = ByteReverseWord32(uVar9);
          uVar13 = ByteReverseWord32(uVar13);
          uVar16 = ByteReverseWord32(uVar16);
          uVar30 = dec.key[0] ^ uVar8;
          uVar10 = dec.key[2] ^ uVar13;
          uVar14 = dec.key[1] ^ uVar9;
          uVar17 = dec.key[3] ^ uVar16;
          pAVar24 = &dec;
          local_17c = uVar1;
          while( true ) {
            local_17c = local_17c - 1;
            uVar11 = *(uint *)(iVar28 + ((uVar17 & 0xffffff) >> 0x10) * 4 + -2000) ^
                     *(uint *)(iVar28 + (uVar30 >> 0x18) * 4 + -0xbd0) ^
                     *(uint *)(iVar28 + (uVar14 & 0xff) * 4 + 0x30) ^ pAVar24->key[4] ^
                     *(uint *)(iVar28 + ((uVar10 & 0xffff) >> 8) * 4 + -0x3d0);
            uVar22 = *(uint *)(iVar28 + (uVar14 >> 0x18) * 4 + -0xbd0) ^
                     *(uint *)(iVar28 + (uVar10 & 0xff) * 4 + 0x30) ^ pAVar24->key[5] ^
                     *(uint *)(iVar28 + ((uVar30 & 0xffffff) >> 0x10) * 4 + -2000) ^
                     *(uint *)(iVar28 + ((uVar17 & 0xffff) >> 8) * 4 + -0x3d0);
            uVar18 = *(uint *)(iVar28 + ((uVar30 & 0xffff) >> 8) * 4 + -0x3d0) ^
                     pAVar24->key[6] ^
                     *(uint *)(iVar28 + (uVar10 >> 0x18) * 4 + -0xbd0) ^
                     *(uint *)(iVar28 + (uVar17 & 0xff) * 4 + 0x30) ^
                     *(uint *)(iVar28 + ((uVar14 & 0xffffff) >> 0x10) * 4 + -2000);
            uVar17 = pAVar24->key[7] ^
                     *(uint *)(iVar28 + (uVar17 >> 0x18) * 4 + -0xbd0) ^
                     *(uint *)(iVar28 + (uVar30 & 0xff) * 4 + 0x30) ^
                     *(uint *)(iVar28 + ((uVar10 & 0xffffff) >> 0x10) * 4 + -2000) ^
                     *(uint *)(iVar28 + ((uVar14 & 0xffff) >> 8) * 4 + -0x3d0);
            if (local_17c == 0) break;
            puVar5 = pAVar24->key;
            uVar30 = *(uint *)(iVar28 + (uVar22 & 0xff) * 4 + 0x30) ^
                     *(uint *)(iVar28 + (uVar11 >> 0x18) * 4 + -0xbd0) ^ pAVar24->key[8] ^
                     *(uint *)(iVar28 + ((uVar17 & 0xffffff) >> 0x10) * 4 + -2000) ^
                     *(uint *)(iVar28 + ((uVar18 & 0xffff) >> 8) * 4 + -0x3d0);
            puVar6 = pAVar24->key;
            puVar7 = pAVar24->key;
            pAVar24 = (Aes *)(pAVar24->key + 8);
            uVar14 = puVar5[9] ^
                     *(uint *)(iVar28 + (uVar22 >> 0x18) * 4 + -0xbd0) ^
                     *(uint *)(iVar28 + (uVar18 & 0xff) * 4 + 0x30) ^
                     *(uint *)(iVar28 + ((uVar11 & 0xffffff) >> 0x10) * 4 + -2000) ^
                     *(uint *)(iVar28 + ((uVar17 & 0xffff) >> 8) * 4 + -0x3d0);
            uVar10 = *(uint *)(iVar28 + ((uVar11 & 0xffff) >> 8) * 4 + -0x3d0) ^
                     puVar7[10] ^
                     *(uint *)(iVar28 + (uVar18 >> 0x18) * 4 + -0xbd0) ^
                     *(uint *)(iVar28 + (uVar17 & 0xff) * 4 + 0x30) ^
                     *(uint *)(iVar28 + ((uVar22 & 0xffffff) >> 0x10) * 4 + -2000);
            uVar17 = puVar6[0xb] ^
                     *(uint *)(iVar28 + (uVar17 >> 0x18) * 4 + -0xbd0) ^
                     *(uint *)(iVar28 + (uVar11 & 0xff) * 4 + 0x30) ^
                     *(uint *)(iVar28 + ((uVar18 & 0xffffff) >> 0x10) * 4 + -2000) ^
                     *(uint *)(iVar28 + ((uVar22 & 0xffff) >> 8) * 4 + -0x3d0);
          }
          uVar25 = *(uint *)(iVar28 + (uVar22 >> 0x18) * 4 + 0x430);
          bVar2 = *(byte *)(iVar28 + (uVar18 & 0xff) * 4 + 0x430);
          uVar19 = *(uint *)(iVar28 + (uVar18 >> 0x18) * 4 + 0x430);
          bVar3 = *(byte *)(iVar28 + (uVar17 & 0xff) * 4 + 0x430);
          uVar26 = *(uint *)(iVar28 + ((uVar11 & 0xffff) >> 8) * 4 + 0x430);
          uVar10 = *(uint *)(iVar28 + ((uVar22 & 0xffffff) >> 0x10) * 4 + 0x430);
          uVar20 = dec.key[uVar1 * 8 + 1];
          uVar29 = *(uint *)(iVar28 + (uVar17 >> 0x18) * 4 + 0x430);
          bVar4 = *(byte *)(iVar28 + (uVar11 & 0xff) * 4 + 0x430);
          uVar31 = *(uint *)(iVar28 + ((uVar18 & 0xffffff) >> 0x10) * 4 + 0x430);
          uVar27 = *(uint *)(iVar28 + ((uVar11 & 0xffffff) >> 0x10) * 4 + 0x430);
          uVar14 = dec.key[uVar1 * 8 + 2];
          uVar21 = dec.key[uVar1 * 8 + 3];
          uVar30 = *(uint *)(iVar28 + ((uVar17 & 0xffff) >> 8) * 4 + 0x430);
          uVar23 = *(uint *)(iVar28 + ((uVar22 & 0xffff) >> 8) * 4 + 0x430);
          uVar9 = ByteReverseWord32(*(uint *)(iVar28 + ((uVar18 & 0xffff) >> 8) * 4 + 0x430) &
                                    0xff00 ^ *(uint *)(iVar28 + ((uVar17 & 0xffffff) >> 0x10) * 4 +
                                                      0x430) & 0xff0000 ^
                                             dec.key[uVar1 * 8] ^
                                             (uint)*(byte *)(iVar28 + (uVar22 & 0xff) * 4 + 0x430) ^
                                             *(uint *)(iVar28 + (uVar11 >> 0x18) * 4 + 0x430) &
                                             0xff000000);
          uVar13 = ByteReverseWord32(uVar25 & 0xff000000 ^ (uint)bVar2 ^ uVar20 ^ uVar27 & 0xff0000
                                     ^ uVar30 & 0xff00);
          uVar16 = ByteReverseWord32(uVar26 & 0xff00 ^
                                     uVar10 & 0xff0000 ^ uVar14 ^ (uint)bVar3 ^ uVar19 & 0xff000000)
          ;
          uVar8 = ByteReverseWord32(uVar23 & 0xff00 ^
                                    uVar31 & 0xff0000 ^ uVar29 & 0xff000000 ^ (uint)bVar4 ^ uVar21);
          *local_170 = uVar9;
          local_170[1] = uVar13;
          local_170[2] = uVar16;
          local_170[3] = uVar8;
        }
        local_174 = local_174 + 4;
        xorbuf((uint8_t *)local_170,(uint8_t *)dec.reg,0x10);
        local_16c = local_16c - 1;
        local_170 = local_170 + 4;
        dec.reg[0] = dec.tmp[0];
        dec.reg[1] = dec.tmp[1];
        dec.reg[2] = dec.tmp[2];
        dec.reg[3] = dec.tmp[3];
      } while (local_16c != 0);
    }
    iVar28 = encLength - (uint)pPlainOut[encLength + -1];
    if (encLength < iVar28 || iVar28 < 1) {
      iVar28 = 0;
    }
  }
  if (iVar15 != *piVar12) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail();
  }
  return iVar28;
}


