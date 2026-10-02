// network_newudp  @ 00014584


int network_newudp(char *host,uint16_t port,int isBroadcast,int isBind)

{
  int __fd;
  int iVar1;
  int iVar2;
  int *piVar3;
  int enable;
  sockaddr_in adr_inet;
  
                    /* Unresolved local var: int sk@[???] */
  piVar3 = *(int **)(DAT_00014684 + 0x145ac);
  enable = 1;
  iVar2 = *piVar3;
  __fd = socket(2,2,0);
  if ((__fd < 0) || ((isBroadcast != 0 && (iVar1 = setsockopt(__fd,1,6,&enable,4), iVar1 != 0)))) {
    __fd = -1;
  }
  else if (isBind != 0) {
    adr_inet.sin_port = port << 8 | port >> 8;
    adr_inet.sin_addr.s_addr = 0;
    adr_inet.__pad[0] = '\0';
    adr_inet.__pad[1] = '\0';
    adr_inet.__pad[2] = '\0';
    adr_inet.__pad[3] = '\0';
    adr_inet.__pad[4] = '\0';
    adr_inet.__pad[5] = '\0';
    adr_inet.__pad[6] = '\0';
    adr_inet.__pad[7] = '\0';
    adr_inet.sin_family = 2;
    if (host != (char *)0x0) {
      adr_inet.sin_addr.s_addr = inet_addr(host);
    }
    iVar1 = bind(__fd,(sockaddr *)&adr_inet,0x10);
    if (iVar1 == -1) {
      close(__fd);
      __fd = -1;
    }
  }
  if (iVar2 != *piVar3) {
                    /* WARNING: Subroutine does not return */
    __stack_chk_fail();
  }
  return __fd;
}


