// sendpacket  @ 00019b80


int sendpacket(int sk,char *dest,int len)

{
  ssize_t sVar1;
  int iVar2;
  int iVar3;
  int *piVar4;
  sockaddr_in addr;
  
                    /* Unresolved local var: int ret@[???] */
  piVar4 = *(int **)(DAT_00019c3c + 0x19ba8);
  addr.sin_addr.s_addr = 0;
  addr.__pad[0] = '\0';
  addr.__pad[1] = '\0';
  addr.__pad[2] = '\0';
  addr.__pad[3] = '\0';
  iVar3 = *piVar4;
  addr.__pad[4] = '\0';
  addr.__pad[5] = '\0';
  addr.__pad[6] = '\0';
  addr.__pad[7] = '\0';
  addr.sin_family = 2;
  addr.sin_port = 0x9c4;
  addr.sin_addr.s_addr = inet_addr(dest);
  if (sk < 0) {
    iVar2 = -1;
  }
  else {
    sVar1 = sendto(sk,(void *)(DAT_00019c40 + 0x19bfc),len,0,(sockaddr *)&addr,0x10);
    if (sVar1 < 0) {
                    /* Unresolved local var: int ret@[???]
                       Unresolved local var: sockaddr_in addr@[???] */
      __errno();
      iVar2 = -1;
    }
    else {
      iVar2 = 0;
    }
  }
  if (iVar3 != *piVar4) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail();
  }
  return iVar2;
}


