// dump8  @ 0001a004


void dump8(char *tag,char *buf,uint8_t *p,int split,int len)

{
  bool bVar1;
  byte bVar2;
  int iVar3;
  int extraout_r1;
  char *__format;
  int iVar4;
  char *__format_00;
  int iVar5;
  
                    /* Unresolved local var: int i@[???]
                       Unresolved local var: int index@[???] */
  if (0 < len) {
    __format_00 = (char *)(DAT_0001a0c0 + 0x1a034);
    __format = (char *)(DAT_0001a0c4 + 0x1a038);
    iVar5 = 0;
    iVar4 = 1;
    do {
      while ((split != 0 && (__aeabi_idivmod(iVar4), extraout_r1 == 0))) {
        bVar2 = *p;
        p = p + 1;
        iVar3 = snprintf(buf + iVar5,0x200 - iVar5,__format,(uint)bVar2);
        iVar5 = iVar5 + iVar3;
        bVar1 = len <= iVar4;
        iVar4 = iVar4 + 1;
        if (bVar1) {
          return;
        }
      }
      iVar3 = snprintf(buf + iVar5,0x200 - iVar5,__format_00,(uint)*p);
      p = p + 1;
      iVar5 = iVar5 + iVar3;
      bVar1 = iVar4 < len;
      iVar4 = iVar4 + 1;
    } while (bVar1);
  }
  return;
}


