// base64_decode  @ 000135d4


int base64_decode(char *in,uint inlen,uchar *out)

{
  char cVar1;
  int iVar2;
  int iVar3;
  uchar uVar4;
  uint uVar5;
  uint uVar6;
  int iVar7;
  
                    /* Unresolved local var: uint i@[???]
                       Unresolved local var: uint j@[???] */
  iVar2 = DAT_0001367c;
  if (inlen == 0) {
    return 0;
  }
                    /* Unresolved local var: int c@[???]
                       Unresolved local var: int s@[???] */
  uVar6 = 0;
  iVar3 = 0;
  do {
    uVar5 = (uint)(byte)in[uVar6];
    if (uVar5 == 0x3d) {
      return iVar3;
    }
    if (0x4f < (uVar5 - 0x2b & 0xff)) {
      return 0;
    }
    cVar1 = *(char *)((int)&DAT_000135c4 + uVar5 + iVar2 + 3);
    iVar7 = (int)cVar1;
    if (cVar1 == -1) {
      return 0;
    }
    uVar5 = uVar6 & 3;
    if (uVar5 == 1) {
      out[iVar3] = out[iVar3] + (byte)((uint)(iVar7 << 0x1a) >> 0x1e);
      iVar3 = iVar3 + 1;
      if ((uVar6 < inlen - 3) || (in[inlen - 2] != '=')) {
        uVar4 = (uchar)(iVar7 << 4);
        goto LAB_00013668;
      }
    }
    else if (uVar5 == 2) {
      out[iVar3] = out[iVar3] + (byte)((uint)(iVar7 << 0x1a) >> 0x1c);
      iVar3 = iVar3 + 1;
      if ((uVar6 < inlen - 2) || (in[inlen - 1] != '=')) {
        uVar4 = (uchar)(iVar7 << 6);
        goto LAB_00013668;
      }
    }
    else if (uVar5 == 3) {
      out[iVar3] = cVar1 + out[iVar3];
      iVar3 = iVar3 + 1;
    }
    else {
      uVar4 = (uchar)(iVar7 << 2);
LAB_00013668:
      out[iVar3] = uVar4;
    }
    uVar6 = uVar6 + 1;
    if (inlen <= uVar6) {
      return iVar3;
    }
  } while( true );
}


