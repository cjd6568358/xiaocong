// AesSetKeyLocal  @ 0001a900


int AesSetKeyLocal(Aes *aes,uint8_t *userKey,uint32_t keylen,uint8_t *iv,int dir)

{
  byte bVar1;
  byte bVar2;
  byte bVar3;
  Aes *pAVar4;
  uint32_t uVar5;
  uint uVar6;
  uint32_t uVar7;
  uint uVar8;
  uint uVar9;
  uint32_t *puVar10;
  uint uVar11;
  uint *puVar12;
  uint *puVar13;
  uint uVar14;
  Aes *pAVar15;
  uint uVar16;
  uint uVar17;
  int iVar18;
  int iVar19;
  uint uVar20;
  uint32_t uVar21;
  uint uVar22;
  uint local_3c;
  
                    /* Unresolved local var: uint32_t temp@[???]
                       Unresolved local var: uint32_t * rk@[???]
                       Unresolved local var: uint i@[???] */
  aes->rounds = (keylen >> 2) + 6;
  memcpy(aes,userKey,keylen);
  ByteReverseWords(aes->key,aes->key,keylen);
  if (keylen == 0x18) {
    iVar18 = DAT_0001af4c + 0x1ae7c;
    puVar13 = (uint *)(DAT_0001af4c + 0x1ae78);
    pAVar4 = aes;
    while( true ) {
      uVar8 = pAVar4->key[5];
      puVar13 = puVar13 + 1;
      uVar20 = *(uint *)(iVar18 + (((uVar8 & 0xffff) >> 8) + 0x400) * 4 + 0x28) & 0xff0000 ^
               *(uint *)(iVar18 + (((uVar8 & 0xffffff) >> 0x10) + 0x400) * 4 + 0x28) & 0xff000000 ^
               *puVar13 ^ pAVar4->key[0] ^
               *(uint *)(iVar18 + ((uVar8 & 0xff) + 0x400) * 4 + 0x28) & 0xff00 ^
               (uint)*(byte *)(iVar18 + ((uVar8 >> 0x18) + 0x400) * 4 + 0x28);
      uVar6 = pAVar4->key[1] ^ uVar20;
      uVar9 = pAVar4->key[2] ^ uVar6;
      pAVar4->key[6] = uVar20;
      uVar20 = pAVar4->key[3] ^ uVar9;
      pAVar4->key[7] = uVar6;
      pAVar4->key[8] = uVar9;
      pAVar4->key[9] = uVar20;
      if (pAVar4 == (Aes *)(aes->key + 0x2a)) break;
      uVar20 = uVar20 ^ pAVar4->key[4];
      pAVar4->key[10] = uVar20;
      pAVar4->key[0xb] = uVar8 ^ uVar20;
      pAVar4 = (Aes *)(pAVar4->key + 6);
    }
  }
  else if (keylen == 0x20) {
    puVar12 = (uint *)(DAT_0001af40 + 0x1aa74);
    pAVar4 = aes;
    puVar13 = puVar12;
    while( true ) {
      uVar6 = pAVar4->key[7];
      uVar9 = puVar12[((uVar6 & 0xffff) >> 8) + 0x40a] & 0xff0000 ^
              puVar12[((uVar6 & 0xffffff) >> 0x10) + 0x40a] & 0xff000000 ^
              pAVar4->key[0] ^ *puVar13 ^ puVar12[(uVar6 & 0xff) + 0x40a] & 0xff00 ^
              (uint)(byte)puVar12[(uVar6 >> 0x18) + 0x40a];
      uVar8 = pAVar4->key[1] ^ uVar9;
      uVar20 = pAVar4->key[2] ^ uVar8;
      pAVar4->key[8] = uVar9;
      uVar9 = uVar20 ^ pAVar4->key[3];
      pAVar4->key[9] = uVar8;
      pAVar4->key[10] = uVar20;
      pAVar4->key[0xb] = uVar9;
      if (pAVar4 == (Aes *)(aes->key + 0x30)) break;
      uVar9 = puVar12[(uVar9 >> 0x18) + 0x40a] & 0xff000000 ^
              (uint)(byte)puVar12[(uVar9 & 0xff) + 0x40a] ^ pAVar4->key[4] ^
              puVar12[((uVar9 & 0xffffff) >> 0x10) + 0x40a] & 0xff0000 ^
              puVar12[((uVar9 & 0xffff) >> 8) + 0x40a] & 0xff00;
      uVar20 = pAVar4->key[5] ^ uVar9;
      pAVar4->key[0xc] = uVar9;
      uVar9 = uVar20 ^ pAVar4->key[6];
      pAVar4->key[0xd] = uVar20;
      pAVar4->key[0xe] = uVar9;
      pAVar4->key[0xf] = uVar6 ^ uVar9;
      pAVar4 = (Aes *)(pAVar4->key + 8);
      puVar13 = puVar13 + 1;
    }
  }
  else {
    if (keylen != 0x10) {
      return -0xad;
    }
    puVar12 = (uint *)(DAT_0001af3c + 0x1a96c);
    pAVar4 = aes;
    puVar13 = puVar12;
    while( true ) {
      uVar9 = pAVar4->key[3];
      uVar6 = puVar12[((uVar9 & 0xffff) >> 8) + 0x40a] & 0xff0000 ^
              puVar12[((uVar9 & 0xffffff) >> 0x10) + 0x40a] & 0xff000000 ^
              pAVar4->key[0] ^ *puVar13 ^ puVar12[(uVar9 & 0xff) + 0x40a] & 0xff00 ^
              (uint)(byte)puVar12[(uVar9 >> 0x18) + 0x40a];
      uVar20 = pAVar4->key[1] ^ uVar6;
      pAVar4->key[4] = uVar6;
      uVar6 = pAVar4->key[2] ^ uVar20;
      pAVar4->key[5] = uVar20;
      pAVar4->key[6] = uVar6;
      pAVar4->key[7] = uVar9 ^ uVar6;
      if (pAVar4 == (Aes *)(aes->key + 0x24)) break;
      pAVar4 = (Aes *)(pAVar4->key + 4);
      puVar13 = puVar13 + 1;
    }
  }
  if (dir == 1) {
                    /* Unresolved local var: uint j@[???] */
    uVar6 = aes->rounds;
    if (uVar6 * 4 != 0) {
      uVar9 = 0;
      pAVar4 = aes;
      puVar10 = aes->key + uVar6 * 4;
      do {
        uVar5 = pAVar4->key[0];
        uVar9 = uVar9 + 4;
        pAVar4->key[0] = *puVar10;
        *puVar10 = uVar5;
        uVar5 = pAVar4->key[1];
        pAVar4->key[1] = puVar10[1];
        puVar10[1] = uVar5;
        uVar5 = pAVar4->key[2];
        pAVar4->key[2] = puVar10[2];
        puVar10[2] = uVar5;
        uVar5 = pAVar4->key[3];
        pAVar4->key[3] = puVar10[3];
        puVar10[3] = uVar5;
        pAVar4 = (Aes *)(pAVar4->key + 4);
        puVar10 = puVar10 + -4;
      } while (uVar9 < uVar6 * 4 - uVar9);
      uVar6 = aes->rounds;
    }
    if (1 < uVar6) {
      iVar19 = DAT_0001af44 + 0x1ac48;
      iVar18 = DAT_0001af48 + 0x1ac4c;
      local_3c = 1;
      pAVar4 = aes;
      do {
        uVar22 = pAVar4->key[5];
        pAVar15 = (Aes *)(pAVar4->key + 4);
        uVar9 = pAVar15->key[0];
        local_3c = local_3c + 1;
        uVar11 = pAVar4->key[6];
        bVar1 = *(byte *)(iVar18 + (((uVar11 & 0xffff) >> 8) + 0x400) * 4 + 0x28);
        uVar17 = *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + ((uVar11 >> 0x18) + 0x400) * 4 + 0x28)
                                    * 4 + -0xbd0);
        uVar8 = *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + ((uVar11 & 0xff) + 0x400) * 4 + 0x28) *
                                   4 + 0x30);
        uVar6 = *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + ((uVar22 & 0xff) + 0x400) * 4 + 0x28) *
                                   4 + 0x30);
        uVar16 = *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + (((uVar22 & 0xffffff) >> 0x10) + 0x400)
                                                             * 4 + 0x28) * 4 + -2000);
        uVar20 = *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + ((uVar22 >> 0x18) + 0x400) * 4 + 0x28)
                                    * 4 + -0xbd0);
        uVar14 = *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + (((uVar11 & 0xffffff) >> 0x10) + 0x400)
                                                             * 4 + 0x28) * 4 + -2000);
        uVar11 = pAVar4->key[7];
        uVar22 = *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + (((uVar22 & 0xffff) >> 8) + 0x400) * 4
                                                   + 0x28) * 4 + -0x3d0);
        bVar2 = *(byte *)(iVar18 + ((byte)pAVar4->key[7] + 0x400) * 4 + 0x28);
        pAVar15->key[0] =
             *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + (((uVar9 & 0xffff) >> 8) + 0x400) * 4 +
                                               0x28) * 4 + -0x3d0) ^
             *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + ((uVar9 >> 0x18) + 0x400) * 4 + 0x28) * 4 +
                      -0xbd0) ^
             *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + ((uVar9 & 0xff) + 0x400) * 4 + 0x28) * 4 +
                      0x30) ^
             *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + (((uVar9 & 0xffffff) >> 0x10) + 0x400) * 4
                                               + 0x28) * 4 + -2000);
        pAVar4->key[5] = uVar22 ^ uVar20 ^ uVar6 ^ uVar16;
        uVar9 = *(uint *)(iVar19 + (uint)bVar2 * 4 + 0x30);
        uVar6 = *(uint *)(iVar19 + (uint)*(byte *)(iVar18 + ((uVar11 >> 0x18) + 0x400) * 4 + 0x28) *
                                   4 + -0xbd0);
        bVar2 = *(byte *)(iVar18 + (((pAVar4->key[7] & 0xffffff) >> 0x10) + 0x400) * 4 + 0x28);
        bVar3 = *(byte *)(iVar18 + (((pAVar4->key[7] & 0xffff) >> 8) + 0x400) * 4 + 0x28);
        pAVar4->key[6] = uVar14 ^ uVar8 ^ uVar17 ^ *(uint *)(iVar19 + (uint)bVar1 * 4 + -0x3d0);
        pAVar4->key[7] =
             *(uint *)(iVar19 + (uint)bVar2 * 4 + -2000) ^ uVar6 ^ uVar9 ^
             *(uint *)(iVar19 + (uint)bVar3 * 4 + -0x3d0);
        pAVar4 = pAVar15;
      } while (local_3c < aes->rounds);
    }
  }
  if (iv == (uint8_t *)0x0) {
    return 0;
  }
  uVar21 = *(uint32_t *)(iv + 4);
  uVar5 = *(uint32_t *)(iv + 8);
  uVar7 = *(uint32_t *)(iv + 0xc);
  aes->reg[0] = *(uint32_t *)iv;
  aes->reg[1] = uVar21;
  aes->reg[2] = uVar5;
  aes->reg[3] = uVar7;
  return 0;
}


