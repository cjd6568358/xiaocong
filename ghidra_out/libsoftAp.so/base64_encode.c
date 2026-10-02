// base64_encode  @ 000134c4


int base64_encode(uchar *in,uint inlen,char *out)

{
  int iVar1;
  int iVar2;
  uint uVar3;
  int iVar4;
  char cVar5;
  int iVar6;
  uint uVar7;
  int iVar8;
  
                    /* Unresolved local var: uint i@[???]
                       Unresolved local var: uint j@[???] */
  if (inlen == 0) {
    iVar2 = 0;
    uVar3 = 0xffffffff;
  }
  else {
                    /* Unresolved local var: int s@[???] */
    uVar3 = 0;
    iVar4 = DAT_000135c0 + 0x134e0;
    iVar8 = DAT_000135c4 + 0x134e2;
    iVar6 = DAT_000135c8 + 0x134e8;
    iVar1 = 0;
    do {
      uVar7 = uVar3 % 3;
      if (uVar7 == 2) {
        out[iVar1] = *(char *)(iVar4 + ((uint)(in[uVar3] >> 6) | (in[uVar3 - 1] & 0xf) << 2));
        iVar2 = iVar1 + 2;
        out[iVar1 + 1] = *(char *)(iVar4 + (in[uVar3] & 0x3f));
      }
      else {
        if (uVar7 == 1) {
          cVar5 = *(char *)(iVar8 + ((uint)(in[uVar3] >> 4) | (in[uVar3 - 1] & 3) << 4));
        }
        else {
          iVar2 = iVar1;
          if (uVar7 != 0) goto LAB_0001354e;
          cVar5 = *(char *)(iVar6 + (uint)(in[uVar3] >> 2));
        }
        out[iVar1] = cVar5;
        iVar2 = iVar1 + 1;
      }
LAB_0001354e:
      uVar3 = uVar3 + 1;
      iVar1 = iVar2;
    } while (inlen != uVar3);
    uVar3 = inlen - 1;
  }
  if (uVar3 % 3 == 1) {
    out[iVar2] = *(char *)(DAT_000135cc + 0x135ae + (in[uVar3] & 0xf) * 4);
    out[iVar2 + 1] = '=';
    return iVar2 + 2;
  }
  if (uVar3 % 3 != 0) {
    return iVar2;
  }
  out[iVar2] = *(char *)(DAT_000135d0 + 0x1358e + (in[uVar3] & 3) * 0x10);
  out[iVar2 + 1] = '=';
  out[iVar2 + 2] = '=';
  return iVar2 + 3;
}


